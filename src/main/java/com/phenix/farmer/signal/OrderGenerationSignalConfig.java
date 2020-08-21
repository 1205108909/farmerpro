package com.phenix.farmer.signal;

import com.phenix.data.IEngineConfig;

import java.time.LocalTime;

public abstract class OrderGenerationSignalConfig implements IEngineConfig {
    public abstract boolean isEnable();
    public abstract LocalTime getTimeFrom();
    public abstract LocalTime getTimeTo();
    public abstract LocalTime getTimeClosePosition();
    public abstract LocalTime getTimeStopOpenPosition();
    public abstract double getPxChgCap2ClearPosition();
    public abstract int getNumberOfTicksHitFar();
    public abstract int getOrderPlacementTimeThresholdInSecs();
    public abstract int getMaxNumberOfOrderPlacement();
}
