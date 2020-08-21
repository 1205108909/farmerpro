package com.phenix.farmer.config;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotImplementedException;
import lombok.Getter;
import org.jdom2.Element;

public class DBConfig implements IEngineConfig {
    //c#: SqlServerConnStr="Data Source=10.101.237.135;Initial Catalog=DataService;User ID=sa;Password=sa;"
    //java: String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=test"
    @Getter
    public String connectionStr;
    @Getter
    private String connectionName;
    @Getter
    private String hostName;
    @Getter
    private String userName;
    @Getter
    private String password;
    @Getter
    private String dbName;
    @Getter
    private String driver;

    public DBConfig(String connectionName_, String hostName_, String userName_, String password_, String dbName_, String driver_) {
        connectionName = connectionName_;
        hostName = hostName_;
        userName = userName_;
        password = password_;
        dbName = dbName_;
        driver = driver_;
        connectionStr = makeConnStr();
    }

    private String makeConnStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:sqlserver://").append(hostName).append("; DatabaseName=").append(dbName);
        return sb.toString();
    }

    @Override
    public boolean readConfig(Element e_) {
        throw new NotImplementedException("not implemented yet");
    }
}
