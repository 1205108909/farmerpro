package com.phenix.farmer.config;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotImplementedException;
import lombok.Value;
import org.jdom2.Element;

@Value
public class AdminConfig implements IEngineConfig {
    private String ip;
    private int port;

    public String getAdminStr() {
        return ip + ":" + port;
    }

    @Override
    public boolean readConfig(Element e_) {
        throw new NotImplementedException("not implemented yet");
    }
}
