package com.phenix.farmer.event;

import com.phenix.farmer.FarmerController;
import lombok.Getter;

public class PersistSignalEvent extends AbstractEngineEvent {
    @Getter
    private String path;

    public PersistSignalEvent(String path_) {
        path = path_;
        consumer = FarmerController.getInstance().getPersistSignalEventConsumer();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Unexpected Invocation: hashCode is not allowed to be used");
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", getClass().getSimpleName(), path);
    }
}
