package com.phenix.farmer.signal;

import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.data.IntervalStat;
import com.phenix.math.TimedInterval;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureHolder<T> {
    @Getter
    private int bucketDurationInSecs;
	private Map<Security, List<T>> features = new HashMap<>();	
    private FeatureFactory<T> factory;
    private Map<Exchange, TimeSlot> timeSlots = new HashMap<>();
    private Map<Exchange, List<TimedInterval>> timedIntervals = new HashMap<>();
    private Map<Exchange, SessionGroup> sessionGroups = new HashMap<>();
    public final static int DEFAULT_COMPACITY = 2000;
	
	public List<Security> getSecurities() {
		return new ArrayList<>(features.keySet());
	}

    public FeatureHolder(int bucketDurationInSecs_, FeatureFactory<T> factory_) {
        bucketDurationInSecs = bucketDurationInSecs_;
        factory = factory_;
    }

    public boolean addTimeSlots(Exchange ex_, SessionGroup sessionGroup_) {
        TimeSlot ts = timeSlots.get(ex_);
        if (ts == null) {
            ts = new TimeSlot();
            timeSlots.put(ex_, ts);
            ts.addTimeSlots(sessionGroup_);
            sessionGroups.put(ex_, sessionGroup_);
        } else {
            throw new IllegalDataException(ex_ + "was already added");
        }

        return true;
    }

    public T getOrCreate(Security sec_, int time_) {
        List<T> feas = features.get(sec_);

        if (feas == null) {
            feas = createList(sec_);
            features.put(sec_, feas);
        }

        int bucketIndex = getBucketIndex(sec_, time_);
        return feas.get(bucketIndex);
    }

	public T getOrCreate(Security sec_, int time_, int lookBackPeriodInSecs_) {
		int end = getBucketIndex(sec_, time_);
		int start = Math.max(0, end - lookBackPeriodInSecs_ / this.bucketDurationInSecs);

		List<T> feas = features.get(sec_);
		if (feas == null) {
			feas = createList(sec_);
			features.put(sec_, feas);
		}

		return feas.get(start);
	}
	
	public List<T> getOrCreateRange(Security sec_, int time_, int lookBackPeriodInSecs_) {
        int end = getBucketIndex(sec_, time_);
        int start = Math.max(0, end - lookBackPeriodInSecs_ / this.bucketDurationInSecs);

        List<T> feas = features.get(sec_);
        if (feas == null) {
            feas = createList(sec_);
            features.put(sec_, feas);
        }

        return feas.subList(start, end + 1);
    }

    public List<T> getFeatures(Security sec_) {
        return features.get(sec_);
    }

    public void set(Security sec_, int time_, T f_) {
        List<T> feas = features.get(sec_);
        if (feas == null) {
            feas = createList(sec_);
            features.put(sec_, feas);
        }

        int bucketIndex = getBucketIndex(sec_, time_);
        feas.set(bucketIndex, f_);
    }
	
    private List<T> createList(Security sec_) {
        TimeSlot ts = timeSlots.get(sec_.getExchange());
        List<T> feas = new ArrayList<>(DEFAULT_COMPACITY);
        int numOfElements = ts.getMaxOffset() / this.bucketDurationInSecs;
        //for (int j = 0; j <= numOfElements; j++) { // Closed interval on both ends.
        for (int j = 0; j < numOfElements; j++) {
            feas.add(factory.newInstance());
        }
        if(!(feas.get(0) instanceof IntervalStat))
            return feas;

        //populate timedInterval
        List<TimedInterval> tis = getTimedInterval(sec_.getExchange());
		if(tis.size() != feas.size())
            throw new IllegalDataException("sizeOfTimedInterval = " + tis.size() + " while sizeOfCutBins = " + feas.size());
        for(int i = 0; i < feas.size(); i++) {
            ((IntervalStat)feas.get(i)).setInterval(tis.get(i));
        }
        return feas;
    }

    private List<TimedInterval> getTimedInterval(Exchange ex_) {
        List<TimedInterval> tis = timedIntervals.get(ex_);
	    if(tis != null)
            return tis;

        tis = new ArrayList<>(10);
        SessionGroup sg = sessionGroups.get(ex_);
        List<TradingSession> tss = sg.getSessions();
        for(TradingSession ts : tss) {
            if(!ts.isTradable())
                continue;
            LocalTime start = ts.getStartTime().toLocalTime();
            LocalTime end = ts.getEndTime().toLocalTime();
            LocalTime time = start;
            while (time.isBefore(end)) {
                LocalTime bstart = time;
                LocalTime bend = time.plusSeconds(bucketDurationInSecs);
                tis.add(new TimedInterval(bstart, bend));
                time = bend;
            }
        }

        timedIntervals.put(ex_, tis);
        return tis;
    }

    public int getBucketIndex(Security sec, int time) {
        TimeSlot ts = timeSlots.get(sec.getExchange());
        return ts.getOffset(time) / this.bucketDurationInSecs;
    }

    public void cleanup() {
        timeSlots.forEach((k, v) -> v.cleanup());
        timeSlots.clear();
        timedIntervals.forEach((k, v) -> v.clear());
        timedIntervals.clear();
        features.forEach((k, v) -> v.clear());
        features.clear();
        sessionGroups.clear();
    }
}
