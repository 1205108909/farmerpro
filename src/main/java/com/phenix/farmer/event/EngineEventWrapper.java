package com.phenix.farmer.event;

import lombok.Getter;
import lombok.Setter;

public final class EngineEventWrapper {
    @Getter
    @Setter
    private IEngineEvent event;

    public static EngineEventWrapper newInstance() {
        return new EngineEventWrapper();
    }
}
