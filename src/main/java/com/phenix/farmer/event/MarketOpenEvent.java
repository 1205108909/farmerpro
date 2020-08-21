package com.phenix.farmer.event;

import com.phenix.farmer.FarmerController;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Created by yangfei on 2017/5/15.
 */
public class MarketOpenEvent extends AbstractEngineEvent {
    @Getter
    private LocalDate date;

    public MarketOpenEvent(LocalDate date) {
        this.date = date;
        consumer = FarmerController.getInstance().getMarketOpenEventConsumer();
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", getClass().getSimpleName(), date.toString());
    }
}
