package com.phenix.farmer.event;

import com.phenix.farmer.FarmerController;
import com.phenix.util.DateUtil;
import lombok.Getter;

import java.time.LocalDateTime;

public class WakeupEvent extends AbstractEngineEvent {
    @Getter
    private LocalDateTime wakeupTime;

    public WakeupEvent(LocalDateTime wakeupTime_) {
        wakeupTime = wakeupTime_;
        consumer = FarmerController.getInstance().getWakeupEventConsumer();
    }

    @Override
    public String toString() {
        return String.format("[%s, WakeupTime=%s]", getClass().getSimpleName(), DateUtil.dateTime2Str(wakeupTime));
    }
}
