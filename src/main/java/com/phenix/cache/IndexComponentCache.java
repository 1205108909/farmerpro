package com.phenix.cache;

import com.google.common.collect.*;
import com.phenix.data.*;
import com.phenix.exception.CacheOperationException;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.FarmerConfigManager;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.RunningMode;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class IndexComponentCache extends EngineCache<LocalDate, Table<Security, Security, IndexComponent>> {
    private final static transient org.slf4j.Logger Logger = LoggerFactory.getLogger(IndexComponentCache.class);
    @Override
    public void init(RunningMode mode_, Object... params_) {
        if(RunningMode.LOCAL == mode_) initLocal(params_);
        else initFromDB(params_);
    }

    public void initLocal(Object... params_) {
        List<String> indexName = (List<String>) params_[0];
        if(indexName.size() == 0) {
            Logger.info("indexname is empty, no component is initialized");
            return;
        }
        TradingDayCache tradingDayCache = (TradingDayCache) params_[1];
        for(String s : indexName) {
            Security index = FarmerDataManager.getInstance().getSecurity(s);
            if (SecurityType.INDEX != index.getType())
                throw new IllegalDataException("security " + index.getSymbol() + " is not index");
            initLocal(index, tradingDayCache);
        }

        Logger.info(IndexComponentCache.class.getName() + " init done");
    }

    private void initLocal(Security index_, TradingDayCache tradingDayCache_) {
        try {
            for (LocalDate td : tradingDayCache_.values()){
                Table<Security, Security, IndexComponent> t = HashBasedTable.create();
                String path = FarmerConfigManager.getInstance().getInstanceConfig().getIndexComponentPath();
                File f = new File(path + File.separator + DateUtil.date2Str(td) + File.separator + index_.getSimpleSymbol() + ".csv");
                List<String> weights = Files.lines(f.toPath()).skip(1).collect(Collectors.toList());
                for(String s : weights) {
                    String []ss = StringUtils.split(s, ",");
                    Security stock = FarmerDataManager.getInstance().getSecurity(ss[0]);
                    if(stock == null) {
                        Logger.info("date = " + td + ": " + ss[0] + ", can not found it in security cache");
                        stock = Security.of(ss[0], SecurityType.STOCK, TradeStatus.UNKNOWN, Exchange.parse(StringUtils.split(ss[0], ".")[1]), ss[0]);
//                        continue;
                    }
                    double weight = Util.roundQtyNear4Digit(Double.parseDouble(ss[1]));
                    IndexComponent ic = new IndexComponent(index_, stock, weight);
                    t.put(index_, stock, ic);
                }
                ImmutableTable<Security, Security, IndexComponent> it = ImmutableTable.copyOf(t);
                t.clear();
                put(td, it);
            }
        } catch (Exception e_) {
            throw new CacheOperationException(e_);
        }
    }

    public void initFromDB(Object... params_) {
        Logger.info("Going to init IndexComponentCache from DB");
        DataSource ds = (DataSource) params_[0];
        List<String> indexList = (List<String>) params_[1];
        Table<Security, Security, IndexComponent> t = HashBasedTable.create();
        LocalDate today = (LocalDate) params_[2];
        Connection conn = null;
        try {
            conn = ds.getConnection();
            for (String indexName : indexList){
                String innerCode = getJYDBInnerCode(conn, StringUtils.split(indexName, ".")[0]);
                if (null == innerCode){
                    throw new IllegalDataException("Can not get innerCode from JYDB, the index is " + indexName);
                }
                String tableName = getJYDBIndexTableName(indexName);
                String date = getLastUpdateDate(conn, innerCode, tableName);
                if (null == date){
                    throw new IllegalDataException("Can not get lastUpdateDate, the innerCode is " + innerCode);
                }
                Security index = Security.of(indexName, SecurityType.INDEX);
                t.putAll(getIndexComponent(conn, index, innerCode, tableName, date));
            }
            ImmutableTable<Security, Security, IndexComponent> it = ImmutableTable.copyOf(t);
            t.clear();
            put(today, it);
            Logger.info("Inited IndexComponentCache" );
        } catch (SQLException e) {
            Logger.error("Error in IndexComponentCache initialization " + e.getMessage());
            throw  new CacheOperationException(e);
        } finally {
            if (null != conn){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String getJYDBInnerCode(Connection conn_, String indexName_){
        Logger.info("Going to getJYDBInnerCode");
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn_.createStatement();
            String sql = "select InnerCode from SecuMain where SecuCategory = 4 and SecuMarket in (83, 90) and SecuCode = '"  + indexName_ + "'";
            Logger.info(String.format("%s - %s", sql, indexName_));
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getString("innerCode");
            }
        } catch (SQLException e) {
            Logger.error("Error in getJYDBInnerCode " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getLastUpdateDate(Connection conn_, String innerCode_, String tableName_){
        Logger.info("Going to getLastUpdateDate");
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn_.createStatement();
            String sql = "SELECT top 1 EndDate FROM " + tableName_ + " t1 LEFT JOIN SecuMain t2 ON t2.SecuCategory = 1 AND t1.InnerCode = t2.InnerCode WHERE IndexCode = '"
             + innerCode_ + "' order BY EndDate desc";
            Logger.info(String.format("%s - %s", sql, innerCode_));
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getString("EndDate");
            }
        } catch (SQLException e) {
            Logger.error("Error in getJYDBInnerCode " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getJYDBIndexTableName(String indexName_){
        if (indexName_.startsWith("801")){
            return "LC_SWSIndexCW ";
        } else {
            return "LC_IndexComponentsWeight";
        }
    }

    private Table<Security, Security, IndexComponent> getIndexComponent(Connection conn_, Security index_, String innerCode_, String tableName_, String date_){
        Logger.info("Going to getLastUpdateDate");
        Table<Security, Security, IndexComponent> table = HashBasedTable.create();
        Set<Security> touchedKeys = new HashSet<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn_.createStatement();
            String sql = "select CASE t2.SecuMarket " +
                    "WHEN 83 THEN " +
                    "t2.SecuCode + '.sh' " +
                    "WHEN 90 THEN " +
                    "t2.SecuCode + '.sz' " +
                    "END AS Symbol, " +
                    " t1.Weight, " +
                    "t1.EndDate " +
                    "FROM " +
                     tableName_ + " t1 " +
                    "LEFT JOIN SecuMain t2 ON t2.SecuCategory = 1 " +
                    "AND t1.InnerCode = t2.InnerCode " +
                    "WHERE " +
                    "IndexCode = '" + innerCode_ + "' " +
                    "and EndDate = '" + date_ + "'";
            Logger.info(String.format("%s - %s - %s", sql, innerCode_, date_));
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String symbol = rs.getString("Symbol");
                Security stock = FarmerDataManager.getInstance().getSecurity(symbol);
                if(stock == null) {
                    Logger.info("date = " + date_ + ": " + symbol + ", can not found it in security cache");
                    Exchange ex = Exchange.parse(StringUtils.split(symbol, ".")[1]);
                    stock = Security.of(symbol, SecurityType.STOCK, TradeStatus.UNKNOWN, ex, symbol);
                }
                double weight = rs.getDouble("Weight");
                IndexComponent ic = new IndexComponent(index_, stock, weight);
                table.put(index_, stock, ic);
                touchedKeys.add(stock);
            }
            table.row(index_).keySet().removeIf(e -> !touchedKeys.contains(e));
        } catch (SQLException e) {
            Logger.error("Error in getJYDBInnerCode " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return table;
    }

    @Override
    public void clear() {
//        Collection<Table<Security, Security, IndexComponent>> m = values();
//        m.forEach(e -> e.clear());
        super.clear();
    }
}