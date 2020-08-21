package com.phenix.farmer.event;

import com.phenix.farmer.FarmerController;
import com.phenix.util.DateUtil;
import lombok.Getter;

import java.time.LocalDateTime;

public class AdminEvent extends AbstractEngineEvent {
    @Getter
    private LocalDateTime wakeupTime;

    public AdminEvent(LocalDateTime wakeupTime_) {
        wakeupTime = wakeupTime_;
        consumer = FarmerController.getInstance().getAdminEventConsumer();
    }

    @Override
    public String toString() {
        return String.format("[%s, WakeupTime=%s]", getClass().getSimpleName(), DateUtil.dateTime2Str(wakeupTime));
    }
}
