package com.phenix.cache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import com.google.common.collect.Lists;
import com.phenix.exception.CacheOperationException;
import com.phenix.farmer.RunningMode;
import com.phenix.data.Exchange;
import com.phenix.data.SessionGroup;
import com.phenix.data.TradingSession;
import com.phenix.data.AuctionType;
import com.phenix.data.SessionType;
import com.phenix.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class SessionGroupCache extends EngineCache<Exchange, SessionGroup> {
    private final static transient Logger Logger = LoggerFactory.getLogger(SessionGroupCache.class);
    @Override
	public void init(RunningMode mode_, Object... params_) {
		if(RunningMode.LOCAL == mode_) initLocal(params_);
		else initFromDB(params_);
	}

	public void initLocal(Object... params_) {
		LocalDate today = LocalDate.now();
		TradingSession ts1 = new TradingSession("default", "default.sh", SessionType.PreMarketOpen, 
				LocalDateTime.of(today, LocalTime.of(0, 0, 0)), LocalDateTime.of(today, LocalTime.of(9, 15, 0)), "NONE", AuctionType.None, false);
		TradingSession ts2 = new TradingSession("default", "default.sh", SessionType.AMAuction, 
				LocalDateTime.of(today, LocalTime.of(9, 15, 0)), LocalDateTime.of(today, LocalTime.of(9, 20, 0)), "NEWLIMIT,CANCEL", AuctionType.AMAuction, true);
		TradingSession ts3 = new TradingSession("default", "default.sh", SessionType.AMMatching,
				LocalDateTime.of(today, LocalTime.of(9, 20, 0)), LocalDateTime.of(today, LocalTime.of(9, 25, 0)), "NEWLIMIT", AuctionType.AMAuction, true);
		TradingSession ts4 = new TradingSession("default", "default.sh", SessionType.AMBlocking, 
				LocalDateTime.of(today, LocalTime.of(9, 25, 0)), LocalDateTime.of(today, LocalTime.of(9, 30, 0)), "NONE", AuctionType.None, false);
		TradingSession ts5 = new TradingSession("default", "default.sh", SessionType.AMContinuous, 
				LocalDateTime.of(today, LocalTime.of(9, 30, 0)), LocalDateTime.of(today, LocalTime.of(11, 30, 0)), "NEWLIMIT,CANCEL,NEWMARKET", AuctionType.None, true);
		
		TradingSession ts6 = new TradingSession("default", "default.sh", SessionType.LunchBreak, 
				LocalDateTime.of(today, LocalTime.of(11, 30, 0)), LocalDateTime.of(today, LocalTime.of(13, 0, 0)), "NONE", AuctionType.None, false);
		
		TradingSession ts7 = new TradingSession("default", "default.sh", SessionType.PMContinuous, 
				LocalDateTime.of(today, LocalTime.of(13, 0, 0)), LocalDateTime.of(today, LocalTime.of(14, 57, 0)), "NEWLIMIT,NEWMARKET,CANCEL", AuctionType.None, true);
		TradingSession ts8 = new TradingSession("default", "default.sh", SessionType.PMContinuous,
				LocalDateTime.of(today, LocalTime.of(14, 57, 0)), LocalDateTime.of(today, LocalTime.of(15, 0, 0)), "NEWLIMIT", AuctionType.CloseAuction, true);

		TradingSession ts9 = new TradingSession("default", "default.sh", SessionType.MarketClose,
				LocalDateTime.of(today, LocalTime.of(15, 0, 0)), LocalDateTime.of(today, LocalTime.of(23, 59, 59)), "NONE", AuctionType.None, false);
		
		
		List<TradingSession> tradingSessions = Lists.newArrayList(ts1, ts2, ts3, ts4, ts5, ts6, ts7, ts8, ts9);
		put(Exchange.SS, new SessionGroup(Exchange.SS.toString(), tradingSessions));
		put(Exchange.SZ, new SessionGroup(Exchange.SZ.toString(), tradingSessions));
		put(Exchange.CFFEX, new SessionGroup(Exchange.CFFEX.toString(), tradingSessions));
	}

	private void initFromDB(Object ...params_) {
        Logger.info("Going to init SessionGroupCache from DB");
		DataSource ds = (DataSource) params_[0];
        LocalDate dateNow = (LocalDate) params_[1];
		Connection conn = null;
        ResultSet rs = null;
        Set<Exchange> touchedKeys = new HashSet<>();

        try {
            conn = ds.getConnection();
            String sql = "[spu_GetTradingSession] ?";
            String param = DateUtil.date2Str(dateNow);
            Logger.info(String.format("%s - %s", sql, param));

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, param);
            rs = ps.executeQuery();
            List<TradingSession> sessions = new ArrayList<TradingSession>();
            while (rs.next()){
                String[] group = StringUtils.split(rs.getString("sessionGroup"), ".");
                Exchange ex = Exchange.parse(group[group.length - 1]);
                if (2 != group.length || Exchange.UNKNOWN == ex){
                    continue;
                }
                String d = rs.getString("date");
                String sg = rs.getString("sessionGroup");
                SessionType st = SessionType.valueOf(rs.getString("sessionName"));
                LocalDateTime t1 = LocalDateTime.of(dateNow, DateUtil.getTime(rs.getString("startTime"), DateUtil.TIME_HHMMSS2));
                LocalDateTime t2 = LocalDateTime.of(dateNow, DateUtil.getTime(rs.getString("endTime"), DateUtil.TIME_HHMMSS2));
                String o = rs.getString("operations");
                AuctionType at = AuctionType.getAucType(rs.getInt("auctionFlag"));
                boolean it = rs.getBoolean("isTradable");
                TradingSession ts = new TradingSession(d, sg, st, t1, t2, o, at, it);
                sessions.add(ts);
            }
            sessions.sort(TradingSession::compareTo);
            Map<String, SessionGroup> sessionGroups = TradingSession.session2SessionGroup(sessions);
            sessionGroups.forEach((sg, ts) -> {
                Exchange exchange = Exchange.parse(StringUtils.split(sg, ".")[1]);
                put(exchange, ts);
                touchedKeys.add(exchange);
            });
            sessionGroups.clear();
            sessions.clear();
            keySet().removeIf(e -> !touchedKeys.contains(e));
            Logger.info("Inited SessionGroupCache" );

            rs.close();
            ps.close();
        } catch (SQLException e) {
            Logger.error("Error in sessionGroupCache initilization " + e.getMessage());
            throw new CacheOperationException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
	}
}
