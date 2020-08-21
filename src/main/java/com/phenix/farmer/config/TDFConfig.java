package com.phenix.farmer.config;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotImplementedException;
import lombok.Getter;
import lombok.Value;
import org.jdom2.Element;

@Value
public class TDFConfig implements IEngineConfig {
    @Getter
    private String ip;
    @Getter
    private String port;
    @Getter
    private String userName;
    @Getter
    private String password;
    @Getter
    private String market;
    @Getter
    private String time;

    public TDFConfig(String ip, String port, String userName, String password, String market, String time) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.market = market;
        this.time = time;
    }

    @Override
    public boolean readConfig(Element e_) {
        throw new NotImplementedException("not implemented yet");
    }
}
