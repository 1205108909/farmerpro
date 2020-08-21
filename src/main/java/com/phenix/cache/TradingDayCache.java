package com.phenix.cache;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.phenix.exception.CacheOperationException;
import com.phenix.farmer.FarmerConfigManager;
import com.phenix.farmer.RunningMode;
import com.phenix.farmer.signal.SignalMode;
import com.phenix.util.DateUtil;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by phenix on 2017/4/30.
 */
public class TradingDayCache extends EngineCache<LocalDate, LocalDate> {
    private final static transient org.slf4j.Logger Logger = LoggerFactory.getLogger(SecurityCache.class);
    private List<LocalDate> tradingDays = ImmutableList.of();

    private void initLocal(Object... params_) {
        // !!! on purpose, no need to support thread-safe reinit, only used for backtest
        if (params_[1].equals(-1)){
            tradingDays = ImmutableList.of(LocalDate.now());
        } else{
            String path = (String)params_[0];
            if (params_[1] == "-1"){
                tradingDays.add(LocalDate.now());
                return;
            }
            LocalDate dateFrom = (LocalDate) params_[1];
            LocalDate dateTo = (LocalDate) params_[2];
            try {
                List<LocalDate> l = Files.lines(Paths.get(path)).skip(1)
                        .map(e -> DateUtil.getDate(e))
                        .filter(e -> !e.isBefore(dateFrom))
                        .filter(e -> !e.isAfter(dateTo))
                        .sorted()
                        .collect(toList());
                tradingDays = ImmutableList.copyOf(l);
            } catch (IOException e_) {
                throw new CacheOperationException(e_);
            }
        }
    }

    @Override
    public void init(RunningMode mode_, Object... params_) {
        if(RunningMode.LOCAL == mode_) initLocal(params_);
        else initFromDB(params_);
    }

    private void initFromDB(Object... params_) {
        DataSource ds = (DataSource) params_[0];
        String param = (String) params_[1];
        Connection conn = null;
        ResultSet rs = null;
        //used to support thread-safe reinit
        Set<LocalDate> touchedKeys = new HashSet<>();

        try {
            conn = ds.getConnection();
            String sql = "[spu_GetTradingDate] ?";
            Logger.info(String.format("%s - %s", sql, param));

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, param);
            rs = ps.executeQuery();
            while(rs.next()) {
                String sDate = rs.getString("TradingDate");
                LocalDate date = DateUtil.getDate(sDate);
                put(date, date);
                touchedKeys.add(date);
            }

            keySet().removeIf(e -> !touchedKeys.contains(e));
            List l = Lists.newArrayList(touchedKeys);
            Collections.sort(l);
            tradingDays = ImmutableList.copyOf(l);

            Logger.info(String.format("[%s] init done - Totally loading tradingDay inited [%s] ", TradingDayCache.class.getSimpleName(), touchedKeys.size()));
            rs.close();
            ps.close();
        } catch (SQLException e_) {
            throw new CacheOperationException(e_);
        } finally {
            if (conn != null) {
                try {
                    //rs.close();
                    conn.close();
                } catch (Exception e_) {
                    throw new CacheOperationException(e_);
                }
            }
        }
    }

    @Override
    public Collection<LocalDate> values() {
        return tradingDays;
    }

    public static LocalDate shift(LocalDate ld_, int days){
        if (SignalMode.isBackTestMode(FarmerConfigManager.getInstance().getInstanceConfig().getSignalMode())){
            String path = FarmerConfigManager.getInstance().getInstanceConfig().getTradingDayPath();
            try {
                List<LocalDate> l = Files.lines(Paths.get(path)).skip(1)
                        .map(e -> DateUtil.getDate(e))
                        .sorted()
                        .collect(toList());
                int index = -1;
                if (l.contains(ld_)){
                    index = l.indexOf(ld_);
                }
                if (index >= 0){
                    return l.get(index + days);
                }
            } catch (IOException e_) {
                throw new CacheOperationException(e_);
            }
        }
        return null;
    }
}
