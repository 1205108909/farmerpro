package com.phenix.cache;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.phenix.data.Security;
import com.phenix.exception.CacheOperationException;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.RunningMode;
import com.phenix.farmer.signal.SignalType;
import com.phenix.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import org.apache.tomcat.jdbc.pool.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SignalSecurityMappingCache extends EngineCache<SignalType, Table<SignalType, Security, Security>> {
    private final static transient org.slf4j.Logger Logger = LoggerFactory.getLogger(SignalSecurityMappingCache.class);
    private Map<Security, AtomicInteger> signalCounter = new HashMap<>();
    @Override
    public void init(RunningMode mode_, Object... params_) {
        if(RunningMode.LOCAL == mode_) initLocal(params_);
        else initFromDB(params_);
    }

    public void initLocal(Object... params_) {
        SecurityCache sc = (SecurityCache) params_[0];
        List<String> lines;
        try {
            lines = FileUtils.readLines(new File((String)params_[1]));
            for (int i = 1; i < lines.size(); i++) {
                String s = lines.get(i);
                String[] ss = StringUtils.split(s, ",");
                SignalType st = SignalType.getSignalByCode(ss[0]);
                if (st == SignalType.UNKNOWN) {
                    throw new IllegalDataException("Unknown signal: " + ss[0]);
                }
                Security sec = sc.getBySymbol(ss[1]);
                if (sec == null) {
                    throw new IllegalDataException("Unknown security: " + ss[1]);
                }
                Table<SignalType, Security, Security> t = get(st);
                if(t == null) {
                    t = HashBasedTable.create();
                    put(st, t);
                }
                t.put(st, sec, sec);

                AtomicInteger counter = signalCounter.get(sec);
                if(counter == null) {
                    counter = new AtomicInteger(0);
                    signalCounter.put(sec, counter);
                }
            }
        } catch (IOException e) {
            throw new CacheOperationException(e);
        }
        Logger.info(SignalSecurityMappingCache.class.getName() + " init done");
    }

    public void initFromDB(Object... params_) {
        Logger.info("Going to init SignalSecurityMappingCache from DB");
        DataSource ds = (DataSource) params_[0];
        LocalDate today = (LocalDate) params_[1];
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT b.Abbr, a.stockId from SignalSecurityMap a JOIN SystemConst b on a.alphaType = b.DomainValue where b.DomainIdx = 1 and validDate <= '" + DateUtil.date2Str(today) + "'";
            Logger.info(String.format("%s - %s", sql, DateUtil.date2Str(today)));
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                String symbol = rs.getString("stockId");
                Security sec = FarmerDataManager.getInstance().getSecurity(symbol);
                if (null == sec){
                    throw new IllegalDataException("Unknown security: " + symbol);
                }
                String signalType = rs.getString("Abbr");
                SignalType st = SignalType.getSignalByCode(signalType);
                if (SignalType.UNKNOWN == st){
                    throw new IllegalDataException("Unknown signal: " + signalType);
                }
                Table<SignalType, Security, Security> t = get(st);
                if (null == t){
                    t = HashBasedTable.create();
                    put(st, t);
                }
                t.put(st, sec, sec);

                AtomicInteger counter = signalCounter.get(sec);
                if(null == counter) {
                    counter = new AtomicInteger(0);
                    signalCounter.put(sec, counter);
                }
            }
            Logger.info("Inited SignalSecurityMappingCache");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AtomicInteger getSignalCounter(Security sec_) {
        return signalCounter.get(sec_);
    }

    @Override
    public void clear() {
        Collection<Table<SignalType, Security, Security>> values = values();
        values.forEach(e -> e.clear());
        super.clear();
        signalCounter.clear();
    }
}
