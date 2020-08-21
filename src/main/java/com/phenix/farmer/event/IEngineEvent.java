package com.phenix.farmer.event;

import java.util.function.Consumer;

public interface IEngineEvent {
    void handleEvent(IEngineEvent e_);

    void setConsumer(Consumer<IEngineEvent> e_);
}