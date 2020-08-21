package com.phenix.farmer.config;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotImplementedException;
import lombok.Getter;
import org.jdom2.Element;

public class OrderBookConfig implements IEngineConfig {
    @Getter
    private String connStr;

    public OrderBookConfig(String connStr_) {
        connStr = connStr_;
    }

    @Override
    public boolean readConfig(Element e_) {
        throw new NotImplementedException("not implemented yet");
    }
}
