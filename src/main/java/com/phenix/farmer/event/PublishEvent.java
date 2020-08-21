package com.phenix.farmer.event;

import com.phenix.farmer.FarmerController;
import com.phenix.util.DateUtil;
import lombok.Getter;

import java.time.LocalDateTime;

public class PublishEvent extends AbstractEngineEvent {
    @Getter
    private LocalDateTime publishTime;

    public PublishEvent(LocalDateTime publishTime_) {
        publishTime = publishTime_;
        consumer = FarmerController.getInstance().getPublishEventConsumer();
    }

    @Override
    public String toString() {
        return String.format("[%s, PublishTime=%s]", getClass().getSimpleName(), DateUtil.dateTime2Str(publishTime));
    }
}
