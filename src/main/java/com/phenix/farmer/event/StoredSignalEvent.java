package com.phenix.farmer.event;

import com.phenix.farmer.FarmerController;

public class StoredSignalEvent extends AbstractEngineEvent {

    public StoredSignalEvent() {
        consumer = FarmerController.getInstance().getStoredSignalEvent();
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", getClass().getSimpleName());
    }
}
