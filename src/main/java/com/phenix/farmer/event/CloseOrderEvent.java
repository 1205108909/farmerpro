package com.phenix.farmer.event;

import com.phenix.data.Security;
import com.phenix.farmer.FarmerController;
import lombok.Getter;

public class CloseOrderEvent extends AbstractEngineEvent {
    @Getter
    private Security sec;

    public CloseOrderEvent(Security sec_) {
        sec = sec_;
        consumer = FarmerController.getInstance().getCloseOrderEventConsumer();
    }

    public static CloseOrderEvent of(Security sec_) {
        return new CloseOrderEvent(sec_);
    }

    @Override
    public int hashCode() {
        return sec.getBasketID();
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", getClass().getSimpleName(), sec.toString());
    }
}
