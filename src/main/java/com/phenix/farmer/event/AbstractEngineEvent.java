package com.phenix.farmer.event;

import java.util.function.Consumer;

public abstract class AbstractEngineEvent implements IEngineEvent {
    protected Consumer<IEngineEvent> consumer;

    @Override
    public void setConsumer(Consumer<IEngineEvent> c_) {
        consumer = c_;
    }

    public void handleEvent(IEngineEvent e_) {
        consumer.accept(e_);
    }
}
