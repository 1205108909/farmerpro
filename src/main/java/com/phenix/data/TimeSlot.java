package com.phenix.data;

import com.phenix.util.DateUtil;
import lombok.Getter;

import java.time.LocalTime;
import java.util.*;

public class TimeSlot{
	private Map<Integer, Integer> offsetMap;
	private int offset;
	
	@Getter
	private int stepInSecs;

	public TimeSlot() {
		offsetMap = new HashMap<>();
		offset = 0;
		stepInSecs = 1;
	}

	public void addTimeSlots(SessionGroup sessionGroup_) {
        List<TradingSession> sessions = new ArrayList<>(sessionGroup_.getSessions());
        Collections.sort(sessions);
        SessionGroup.validateTradingSessions(sessions, sessionGroup_.getName());
		
		for(TradingSession session : sessions) {
			LocalTime startTime = session.getStartTime().toLocalTime();
			LocalTime endTime  = session.getEndTime().toLocalTime();
			LocalTime time = startTime;

            while (time.isBefore(endTime)) {
                int timeStamp = Integer.parseInt(time.format(DateUtil.TIME_HHMMSS));
                if (session.isTradable()) {
                    offset++;
                }

                // To adjust so that like 91500 is mapped to 0; and 113000's mapping is same as 112959's.
                int adjustedOffset = offset == 0 ? offset : offset - 1;

                offsetMap.put(timeStamp, adjustedOffset);
                time = time.plusSeconds(stepInSecs);
            }
		}
	}

	public int getOffset(int timeStampInSec) {
		return offsetMap.get(timeStampInSec);
	}

	public int getMaxOffset() {
		return offset;
	}
	
	public void cleanup() {
		offsetMap.clear();
		offset = 0;
	}
}
