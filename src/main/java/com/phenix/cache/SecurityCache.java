package com.phenix.cache;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.RunningMode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phenix.exception.CacheOperationException;
import com.phenix.util.Alert;
import com.phenix.util.Alert.Severity;
import com.phenix.util.DateUtil;

public class SecurityCache extends EngineCache<Security, Security> {	
	private final static transient Logger Logger = LoggerFactory.getLogger(SecurityCache.class);	
	private final SecurityCacheKeyAsString securityCacheKeyAsString = new SecurityCacheKeyAsString();
	@Override
	public void init(RunningMode mode_, Object... params_) {
		if(RunningMode.LOCAL == mode_) {
			initLocal(params_);
			securityCacheKeyAsString.initLocal(params_);
		} else {
			initFromDB(params_);
			securityCacheKeyAsString.initFromDB(params_);
		}
	}

	private void initLocal(Object... params_) {
		// !!! on purpose, no need to support thread-safe reinit, only used for backtest
		String path = (String)params_[0];
		List<String> ls;
		try {
			ls = FileUtils.readLines(new File(path));
			clear();

			Map<String, Security> tmp = new HashMap<>();
			for(int i = 1; i < ls.size(); i++) {
				String[] ss = ls.get(i).split(",");
				String strEx = StringUtils.substringAfter(ss[0], ".");
				Security s;
				if ("na".equalsIgnoreCase(ss[6])) {
					s = Security.of(ss[0], SecurityType.parse(ss[2]), TradeStatus.TRADABLE, Exchange.parse(strEx), ss[0]);
					tmp.put(s.getSymbol(), s);
				} else {
					Security sec = tmp.get(ss[6]);
					if(sec == null)
						throw new IllegalDataException("Unknown underlyging: " + ss[5]);
					s = new IndexFuture(ss[0],SecurityType.parse(ss[2]), TradeStatus.TRADABLE, 300, 0.1, sec);
				}
				put(s, s);
			}
			tmp.clear();
		} catch (IOException e_) {
			throw new CacheOperationException(e_);
		}
	}

	private void initFromDB(Object... params_) {
		DataSource ds = (DataSource) params_[0];
		LocalDate dateNow = (LocalDate) params_[1];
		Connection conn = null;
		ResultSet rs = null;		
		//used to support thread-safe reinit
		Set<Security> touchedKeys = new HashSet<>();		

		try {
			conn = ds.getConnection();
			
			String sql = "[spu_GetTradableSecurity] ?";
			String param = DateUtil.date2Str(dateNow);
			Logger.info(String.format("%s - %s", sql, param));
			
			PreparedStatement ps = conn.prepareStatement(sql);			
			ps.setString(1, param);
			rs = ps.executeQuery();			
			while(rs.next()) {
				String symbol = rs.getString("symbol");
				String exchange = rs.getString("exchange");
				String mdSymbol = symbol + "." + exchange;
				String name = rs.getString("stockName");
				String type = rs.getString("sType");				
				TradeStatus ts = TradeStatus.parse(rs.getInt("isTradable"));				
				Security sec = Security.of(mdSymbol, SecurityType.parse(type), ts, Exchange.parse(exchange.toUpperCase()), name);
				
				if(SecurityType.UNKNOWN == sec.getType()) {
					String msg = String.format("[%s] has unknown SecType[%s]", mdSymbol, type);
					Logger.error(msg);
					Alert.fireAlert(Severity.Major, msg, msg);
				}
				
				put(sec, sec);				
				touchedKeys.add(sec);
			}			
			
			keySet().removeIf(e -> !touchedKeys.contains(e));
			Logger.info(String.format("[%s] init done - Totally loading securities inited [%s] securities", SecurityCache.class.getName(), touchedKeys.size()));
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new CacheOperationException(e);
		} finally {
			if (conn != null) {
				try {
					//rs.close();
					conn.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

	public Security getBySymbol(String symbol_) {
		return securityCacheKeyAsString.get(symbol_);
	}

	@Override
	public Security remove(Security sec_) {
		Security sec = super.remove(sec_);
		securityCacheKeyAsString.remove(sec_.getSymbol());
		return sec;
	}

	@Override
	public void clear() {
		super.clear();
		securityCacheKeyAsString.clear();
	}

	@Override
	public void put(Security key_, Security value_) {
		super.put(key_, value_);
		securityCacheKeyAsString.put(key_.getSymbol(), value_);
	}

	/**
	 * below code are exactly the same as SecurityCache
	 * only difference is String as Key
	 */
	private final static class SecurityCacheKeyAsString extends EngineCache<String, Security> {
		@Override
		public void init(RunningMode mode_, Object... params_) {
			if(RunningMode.LOCAL == mode_) initLocal(params_);
			else initFromDB(params_);
		}

		private void initLocal(Object... params_) {
			// !!! on purpose, no need to support thread-safe reinit, only used for backtest
			String path = (String)params_[0];
			List<String> ls;
			try {
				ls = FileUtils.readLines(new File(path));
				clear();

				Map<String, Security> tmp = new HashMap<>();
				for(int i = 1; i < ls.size(); i++) {
					String[] ss = ls.get(i).split(",");
					String strEx = StringUtils.substringAfter(ss[0], ".");
					Security s;
					if ("na".equalsIgnoreCase(ss[6])) {
						s = Security.of(ss[0], SecurityType.parse(ss[2]), TradeStatus.TRADABLE, Exchange.parse(strEx), ss[0]);
						tmp.put(s.getSymbol(), s);
					} else {
						Security sec = tmp.get(ss[6]);
						if(sec == null)
							throw new IllegalDataException("Unknown underlying: " + ss[5]);
						s = new IndexFuture(ss[0],SecurityType.parse(ss[2]), TradeStatus.TRADABLE, 300, 0.1, sec);
					}
					put(s.getSymbol(), s);
				}
				tmp.clear();
			} catch (IOException e_) {
				throw new CacheOperationException(e_);
			}
		}

		private void initFromDB(Object... params_) {
			DataSource ds = (DataSource) params_[0];
			LocalDate dateNow = (LocalDate) params_[1];
			Connection conn = null;
			ResultSet rs = null;
			//used to support thread-safe reinit
			Set<String> touchedKeys = new HashSet<>();

			try {
				conn = ds.getConnection();

				String sql = "[spu_GetTradableSecurity] ?";
				String param = DateUtil.date2Str(dateNow);
				Logger.info(String.format("%s - %s", sql, param));

				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, param);
				rs = ps.executeQuery();
				while (rs.next()) {
					String symbol = rs.getString("symbol");
					String exchange = rs.getString("exchange");
					String mdSymbol = symbol + "." + exchange;
					String name = rs.getString("stockName");
					String type = rs.getString("sType");
					TradeStatus ts = TradeStatus.parse(rs.getInt("isTradable"));
					Security sec = Security.of(mdSymbol, SecurityType.parse(type), ts, Exchange.parse(exchange.toUpperCase()), name);

					if (SecurityType.UNKNOWN == sec.getType()) {
						String msg = String.format("[%s] has unknown SecType[%s]", mdSymbol, type);
						Logger.error(msg);
						Alert.fireAlert(Severity.Major, msg, msg);
					}

					put(sec.getSymbol(), sec);
					touchedKeys.add(sec.getSymbol());
				}

				keySet().removeIf(e -> !touchedKeys.contains(e));
				Logger.info(String.format("[%s] init done - Totally loading securities inited [%s] securities", SecurityCache.class.getName(), touchedKeys.size()));

				rs.close();
				ps.close();
			} catch (SQLException e) {
				throw new CacheOperationException(e);
			} finally {
				if (conn != null) {
					try {
						//rs.close();
						conn.close();
					} catch (Exception ignore) {
					}
				}
			}
		}
	}
}
