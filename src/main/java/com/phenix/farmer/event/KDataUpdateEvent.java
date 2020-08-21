package com.phenix.farmer.event;

import com.phenix.data.KLineData;
import com.phenix.farmer.FarmerController;
import lombok.Getter;

public class KDataUpdateEvent extends AbstractEngineEvent {
    @Getter
    private KLineData kLineData;

    public KDataUpdateEvent(KLineData book_) {
        kLineData = book_;
        consumer = FarmerController.getInstance().getKDataUpdateEventConsumer();
    }

    public static KDataUpdateEvent of(KLineData b_) {
        return new KDataUpdateEvent(b_);
    }

    @Override
    public int hashCode() {
        return kLineData.getSecurity().getBasketID();
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", getClass().getSimpleName(), kLineData.getSecurity().toString());
    }
}
