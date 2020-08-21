package com.phenix.farmer.config;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotImplementedException;
import lombok.Getter;
import org.jdom2.Element;

import java.util.List;

public class MQConfig implements IEngineConfig {
    @Getter
    private String connectionName;
    @Getter
    private String serverIp;
    @Getter
    private String port;
    @Getter
    private String protocal;
    @Getter
    private String defaultParams;
    @Getter
    private String connStr;
    @Getter
    private int reconnectDelay;
    @Getter
    private int maxReconnectAttemps;
    @Getter
    private int connectionPerFactory;
    @Getter
    private int sessionPerConnection;
    @Getter
    private List<String> topics;
    @Getter
    private String userName;
    @Getter
    private String password;

    public MQConfig(String connectionName, String serverIp, String port, String protocal, String defaultParams,
                    int reconnectDelay, int maxReconnectAttemps, int connPerFactory_, int sessionPerConn_, boolean failover_
            , List<String> topics_, String userName_, String password_) {
        this.connectionName = connectionName;
        this.serverIp = serverIp;
        this.port = port;
        this.protocal = protocal;
        this.defaultParams = defaultParams;
        this.reconnectDelay = reconnectDelay;
        this.maxReconnectAttemps = maxReconnectAttemps;
        this.connectionPerFactory = connPerFactory_;
        this.sessionPerConnection = sessionPerConn_;

        connStr = protocal + "://" + serverIp + ":" + port;
        if (failover_) {
            connStr = "failover:(" + connStr + ")";
            connStr += "?" + defaultParams;
        } else {
            connStr += "?" + defaultParams;
        }
        this.topics = topics_;
        userName = userName_;
        password = password_;
    }

    @Override
    public boolean readConfig(Element e_) {
        throw new NotImplementedException("not implemented yet");
    }
}
