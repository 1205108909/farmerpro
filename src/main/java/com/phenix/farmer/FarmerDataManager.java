package com.phenix.farmer;

import com.google.common.collect.*;
import com.phenix.activemq.ActiveMQManager;
import com.phenix.cache.*;
import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.config.DBConfig;
import com.phenix.farmer.feature.Featurable;
import com.phenix.farmer.signal.SignalMode;
import com.phenix.farmer.signal.SignalType;
import com.phenix.orderbook.OrderBook;
import com.phenix.util.Alert;
import com.phenix.util.Constants;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.util.ConcurrentHashSet;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.IllegalStateException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * TODO:
 * put universe and index-future-mapping in db
 * put signal security mapping in db
 */
public class FarmerDataManager {
    private final static transient Logger Logger = LoggerFactory.getLogger(FarmerDataManager.class);

    public final static LocalTime FutureMarketOpen = LocalTime.of(9, 15);
    public final static LocalTime FutureMarketClose = LocalTime.of(15, 00);
    public final static LocalTime FutureMarketBreakStart = LocalTime.of(11, 30);
    public final static LocalTime FutureMarketBreakEnd = LocalTime.of(13, 00);
    public final static LocalTime STOCK_TIME_CONTINUOUS_START = LocalTime.of(9, 30, 00);

    public final static LocalDate TODAY = LocalDate.now();

    private Function<String, String> topic2MQ;
    private ActiveMQManager amqMgr;

    private Map<String, DataSource> dbSourceMap = new ConcurrentHashMap<>();
    private SecurityCache securityCache = new SecurityCache();
    private SessionGroupCache sessionGroupCache = new SessionGroupCache();
    private TradingDayCache tradingDayCache = new TradingDayCache();
    private DailyFeatureCache dailyFeautureCache = new DailyFeatureCache();
    private IndexComponentCache indexComponentCache = new IndexComponentCache();
	private SignalSecurityMappingCache signalSecurityMappingCache = new SignalSecurityMappingCache();
    private ConcurrentMap<Security, OrderBook> orderBooks = new ConcurrentHashMap<>();
    public final static Map<Security, Security> INTRESTED_INDEX = new HashMap<>();

    private final RunningMode mode = FarmerConfigManager.getInstance().getInstanceConfig().getRunningMode();
    private Map<Security, TreeMap<LocalDate, DailyStat>> dailyStats = new HashMap<>();

    //used in prod
    @Getter
    private BiMap<Security, Security> index2FutureMapping;

    //used by backtest
    @Getter
    private Table<Security, LocalDate, List<IndexFuture>> index2FutureMappingAll;
    @Getter
    private Map<IndexFuture, Security> future2IndexMapping;
    private Set<Security> forbiddenList;
    private Set<Security> unbookedList;
    @Getter
    private final String orderBookXml;
    @Getter
    @Setter
    private String tradingDay;

    private ScheduledExecutorService esOrderBook;


    static {
        INTRESTED_INDEX.put(Security.INDEX_HS300, Security.INDEX_HS300);
        INTRESTED_INDEX.put(Security.INDEX_ZZ500, Security.INDEX_ZZ500);
        INTRESTED_INDEX.put(Security.INDEX_SZ50, Security.INDEX_SZ50);
        INTRESTED_INDEX.put(Security.INDEX_ZZ1000, Security.INDEX_ZZ1000);
    }

    public double getTickSize(String mdSymbol_) {
        if (mdSymbol_.matches("^IF.*$|^IH.*$|^if.*$|^ih.*$|^000300.*$|^000016.*$")) {
            return 0.2;
        } else if (mdSymbol_.matches("^IC.*$|^ic.*$|^000905.*$|^000852.*$")) {
            return 0.2;
        } else if (mdSymbol_.endsWith(".sh") || mdSymbol_.endsWith(".sz"))
            return 0.01;
        else
            throw new IllegalDataException("Unknown symbol: " + mdSymbol_);
    }

    public double getAvgTradeSize(String mdSymbol_) {
        return 1;
    }

    public double getLotSize(String mdSymbo_) {
        return 100;
    }

    public boolean isTradable(LocalTime time_) {
        return isTradable(time_, IntervalType.Start);
    }

    public static double getContractValue(String mdSymbol_) {
        if (mdSymbol_.matches("^IF.*$|^IH.*$|^if.*$|^ih.*$|^000300.*$|^000016.*$")) {
            //".startsWith("IF") || mdSymbol_.startsWith("IH")) {
            return 300.0;
        } else if (mdSymbol_.matches("^IC.*$|^ic.*$|^000905.*$|^000852.*$")) {
            //(mdSymbol_.startsWith("IC")) {
            return 200.0;
        } else {
            throw new UnsupportedOperationException("UNKNOWN Symbol " + mdSymbol_);
        }
    }

    public static double getMarginRate(String mdSymbol_) {
        if (mdSymbol_.matches("^IF.*$|^IH.*$|^if.*$|^ih.*$|^000300.*$|^000016.*$")) {
            return 0.1;
        } else if (mdSymbol_.matches("^IC.*$|^ic.*$|^000905.*$")) {
            return 0.15;
        } else if (mdSymbol_.matches("^000852.*$")) {
            return 0.15;
        } else {
            throw new UnsupportedOperationException("UNKNOWN Symbol " + mdSymbol_);
        }
    }


    public boolean isTradable(LocalTime time_, IntervalType type_) {
        if (type_ == IntervalType.Start) {// time >= FutureMarketOpen
            if ((time_.equals(FutureMarketOpen) || time_.isAfter(FutureMarketOpen)) && time_.isBefore(FutureMarketBreakStart))
                return true;
            if ((time_.equals(FutureMarketBreakEnd) || time_.isAfter(FutureMarketBreakEnd)) && time_.isBefore(FutureMarketClose))
                return true;
        } else if (type_ == IntervalType.End) {
            if (time_.isAfter(FutureMarketOpen) && (time_.equals(FutureMarketBreakStart) || time_.isBefore(FutureMarketBreakStart)))
                return true;
            if ((time_.equals(FutureMarketBreakEnd) && (time_.equals(FutureMarketClose)) || time_.isBefore(FutureMarketClose)))
                return true;
        }

        return false;
    }

    public SessionGroup getSessionGroup(Exchange exch_) {
        return sessionGroupCache.get(exch_);
    }

    public static double getCommission(Security sec_, OrderSide s_, boolean intraDay_) {
//		if(sec_.equals(Security.INDEX_ZZ1000)) {
//			return 0;
//		}
        double commission;
        switch (sec_.getType()) {
            case STOCK:
            case ETF:
				commission = OrderSide.BUY == s_ ? 0.00015 : 0.00115;
                break;
            case INDEX:
            case INDEX_FUTURE:
                commission = intraDay_ ? 0.0007 : 0.000024; //Exchange Commission + 0.00001
                break;
            default:
                throw new UnsupportedOperationException("Unsupported Type " + sec_.getType());
        }

        return commission;
    }

	public static double getTickSize(Security sec_) {
		double tickSize;
		switch (sec_.getType()) {
			case STOCK:
				tickSize = 0.01;
				break;
			case ETF:
				tickSize = 0.001;
				break;
			case INDEX:
			case INDEX_FUTURE:
				tickSize = 0.2; //Exchange Commission + 0.00001
				break;
			default:
				throw new UnsupportedOperationException("Unsupported Type " + sec_.getType());
		}
		return tickSize;
	}


    @Getter
    private final static FarmerDataManager Instance = new FarmerDataManager();

    private FarmerDataManager() {
        try (InputStream is = OrderBook.class.getResourceAsStream("OrderBookExample.xml")) {
            orderBookXml = IOUtils.toString(is);
        } catch (IOException e_) {
            throw new IllegalDataException(e_);
        }

        esOrderBook = Executors.newSingleThreadScheduledExecutor();

		Logger.info(String.format("[%s] is inited", FarmerDataManager.class));
    }

    public Security getSecurity(String mdSymbol_) {
        Security sec = null;
        //sec = securityCache.get(Security.of(mdSymbol_, SecurityType.STOCK));
        sec = securityCache.getBySymbol(mdSymbol_);
		/*if(sec == null) {
			throw new IllegalDataException(String.format("[%s] is not found", mdSymbol_));
		}*/
        return sec;
    }

    public void init(FarmerConfigManager configMgr_) {
        topic2MQ = e -> FarmerConfigManager.getInstance().topic2MQName(e);
        forbiddenList = new ConcurrentHashSet<>();
        unbookedList = new ConcurrentHashSet<>();
        initDB(configMgr_);
        initCache(configMgr_);
        initIndexFutureMapping(configMgr_);
        initMQ(configMgr_);
    }

    private void initDB(FarmerConfigManager configMgr_) {
        Logger.info("Going to init DB");

        Map<String, DBConfig> dbconfigs = configMgr_.getDbConfigs();
        for (Entry<String, DBConfig> e : dbconfigs.entrySet()) {
            PoolProperties p = new PoolProperties();
            p.setUrl(e.getValue().getConnectionStr());
            p.setDriverClassName(e.getValue().getDriver());
            p.setUsername(e.getValue().getUserName());
            p.setPassword(e.getValue().getPassword());
            p.setMaxWait(10000);
            p.setValidationQueryTimeout(3);
            p.setTestOnBorrow(true);
            p.setValidationQuery("select 1");
            DataSource ds = new DataSource(p);
            dbSourceMap.put(e.getKey(), ds);
        }

        Logger.info(String.format("totally init [%s] connections using tomcat pool", dbconfigs.size()));
    }

    public void initMQ(FarmerConfigManager mgr_) {
        Logger.info("Going to init MQ");
        amqMgr = ActiveMQManager.newInstance().initConnections(mgr_.getMqConfigs());
        Logger.info(String.format("totally init [%s] connections", mgr_.getMqConfigs().size()));
    }

    private void initCache(FarmerConfigManager mgr_) {
        LocalDate date = mgr_.getInstanceConfig().getDataDate().toLocalDate();
        if (RunningMode.LOCAL == mode) {
			securityCache.init(mode, FarmerConfigManager.getInstance().getInstanceConfig().getUniversePath(), date);
			if (SignalMode.isBackTestMode(FarmerConfigManager.getInstance().getInstanceConfig().getSignalMode())){
                tradingDayCache.init(mode, FarmerConfigManager.getInstance().getInstanceConfig().getTradingDayPath(),
                        mgr_.getBackTestConfig().getStartDate(), mgr_.getBackTestConfig().getEndDate()); //only care tradingDay after 20070101
            } else {
                tradingDayCache.init(mode, FarmerConfigManager.getInstance().getInstanceConfig().getTradingDayPath(), -1, -1);
            }
            sessionGroupCache.init(mode, dbSourceMap.get(Constants.DB_JYDB));
			dailyFeautureCache.init(mode,FarmerConfigManager.getInstance().getFeatureConfig().getDailyFeature(), securityCache);
			indexComponentCache.init(mode, FarmerConfigManager.getInstance().getBackTestConfig().getIndexName(), tradingDayCache);
            signalSecurityMappingCache.init(mode, securityCache, FarmerConfigManager.getInstance().getInstanceConfig().getSignalSecurityMappingPath());
        } else if (RunningMode.REMOTE == mode) {
            securityCache.init(mode, dbSourceMap.get(Constants.DB_JYDB), date);
            tradingDayCache.init(mode, dbSourceMap.get(Constants.DB_JYDB), "20070101"); //only care tradingDay after 20070101
            sessionGroupCache.init(mode, dbSourceMap.get(Constants.DB_DATASERVICE), date);
            indexComponentCache.init(mode, dbSourceMap.get(Constants.DB_JYDB),
                    FarmerConfigManager.getInstance().getBackTestConfig().getIndexName(), date);
            signalSecurityMappingCache.init(mode, dbSourceMap.get(Constants.DB_DATASERVICE), date);
        }
    }

    private void initIndexFutureMapping(FarmerConfigManager mgr_) {
        //todo:add init from db
        index2FutureMapping = HashBiMap.create();
        List<String> lines;
        try {
            lines = FileUtils.readLines(new File(mgr_.getInstanceConfig().getUniversePath()));
            for (int i = 1; i < lines.size(); i++) {
                String s = lines.get(i);
                String[] ss = StringUtils.split(s, ",");
                if ("NA".equalsIgnoreCase(ss[ss.length - 1])) {
                    continue;
                }

                Security indexFuture = getSecurity(ss[0]);
                if (indexFuture == null) {
                    throw new IllegalDataException("Unknown security: " + ss[0]);
                }
                Security sec = getSecurity(ss[ss.length - 1]);
                index2FutureMapping.put(sec, indexFuture);
            }
        } catch (IOException e_) {
            throw new IllegalStateException(e_);
        }
        index2FutureMapping = ImmutableBiMap.copyOf(index2FutureMapping);

        if (SignalMode.BACK_TEST != mgr_.getInstanceConfig().getSignalMode())
            return;

        //init mapping for back test
        index2FutureMappingAll = HashBasedTable.create();
        future2IndexMapping = new HashMap<>();
        try {
            lines = FileUtils.readLines(new File(mgr_.getInstanceConfig().getIndexFutureMappingPath()));
            for (int i = 1; i < lines.size(); i++) {
                String s = lines.get(i);
                String[] ss = StringUtils.split(s, ",");
                if (!ss[4].trim().equals("1")) //only care about current month contract for now
                    continue;
                String symbol = ss[0].toLowerCase() + "." + "cfe";
                Security underlying = getSecurity(ss[7]);
                if (underlying == null)
                    throw new IllegalDataException("Unknown security: " + ss[7]);

                Security future = getSecurity(symbol);
                LocalDate date = DateUtil.getDate(ss[2]);
                if (future == null) {
                    future = new IndexFuture(symbol, SecurityType.INDEX_FUTURE, TradeStatus.UNTRADABLE,
                            getContractValue(symbol), getMarginRate(symbol), underlying);
                    securityCache.put(future, future);
                }
                List<IndexFuture> futures = index2FutureMappingAll.get(underlying, date);
                if (futures == null) {
                    futures = new ArrayList<>(4);
                    index2FutureMappingAll.put(underlying, date, futures);
                }
                futures.add((IndexFuture) future);
                future2IndexMapping.put((IndexFuture) future, underlying);
            }
        } catch (IOException e_) {
            throw new IllegalStateException(e_);
        }
    }

    public Security getIndexFuture(Security sec_) {
        return index2FutureMapping.get(sec_);
    }

    public Security getUnderlyingSecurity(Security sec_) {
        return SignalMode.BACK_TEST == FarmerConfigManager.getInstance().getInstanceConfig().getSignalMode() ?
                future2IndexMapping.get(sec_) : index2FutureMapping.inverse().get(sec_);
    }

    public Security getIndexFuture(Security sec_, LocalDate date_, IndexFutureType t_) {
        List<IndexFuture> future = index2FutureMappingAll.get(sec_, date_);
        return future == null ? null : future.get(0);
    }

    public DailyStat getDailyStat(Security s_, LocalDate date_) {
        DailyStat ds = null;

        Map<LocalDate, DailyStat> m = dailyStats.get(s_);
        if (m != null) {
            ds = m.get(date_);
        }

        return ds;
    }

    public DailyStat getPrevDailyStat(Security s_, LocalDate date_) {
        DailyStat ds = null;

        TreeMap<LocalDate, DailyStat> m = dailyStats.get(s_);
        if (m != null) {
			Map.Entry<LocalDate, DailyStat> e = m.floorEntry(date_.minusDays(1));
            if (e != null) {
                ds = e.getValue();
            }
        }
        return ds;
    }

    public int daysBetween(LocalDate d1_, LocalDate d2_) {
        List<LocalDate> ls = (List<LocalDate>) tradingDayCache.values();
        int ind1 = Collections.binarySearch(ls, d1_);
        int ind2 = Collections.binarySearch(ls, d2_);
        return ind2 - ind1;
    }

    /**
     * find the recent tradingDay before/on date_
     *
     * @param date_
     * @return
     */
    public LocalDate getTradingDay(LocalDate date_) {
        LocalDate ld = tradingDayCache.get(date_);

        if (ld == null) {
            List<LocalDate> dates = (List<LocalDate>) tradingDayCache.values();
            int ind = Collections.binarySearch(dates, date_);
            if (ind < 0) {
                ind = Math.negateExact(ind) - 1; //the insertion point;
                ind = ind - 1;
                ld = dates.get(ind);
            }
        }

        return ld;
    }

    public Collection<LocalDate> getTradingDayCache(){
        return tradingDayCache.values();
    }

    public List<LocalDate> tradingDayBetween(LocalDate start_, LocalDate end_) {
        List<LocalDate> dates = (List<LocalDate>) tradingDayCache.values();
        List<LocalDate> res = new ArrayList<>(dates.size());
        for(LocalDate ld : dates)
            if(!ld.isBefore(start_) && !ld.isAfter(end_))
                res.add(ld);
        return res;
    }

    public LocalDate getLastTradingDay(){
        int size = tradingDayCache.values().size();
        List<LocalDate> dates = (List<LocalDate>) tradingDayCache.values();
        return dates.get(size - 2);
    }

    public Table<Security, Security, IndexComponent> getIndexComponent(LocalDate date_) {
        return indexComponentCache.get(date_);
    }

    public DataSource getDataSource(String key_){
        return dbSourceMap.get(key_);
    }

    public void cleanup() {
        if (amqMgr != null) {
            amqMgr.cleanup();
        }

        dbSourceMap.forEach((k, v) -> v.close(true));
        dbSourceMap.clear();
        securityCache.clear();
        tradingDayCache.clear();
        sessionGroupCache.clear();
        dailyFeautureCache.clear();
        indexComponentCache.clear();
        esOrderBook.shutdownNow();
    }

    public boolean publishMessage(String topic_, Map<String, String> msg_) {
        return publishMessage(topic_, msg_, true);
    }

    public boolean publishMessage(String topic_, Map<String, String> msg_, boolean isTopic_) {
        boolean success = false;
        Session session = null;

        try {
            session = amqMgr.getSession(topic2MQ.apply(topic_));
            MapMessage msg = session.createMapMessage();
            StringBuilder sb = new StringBuilder();

			for (Map.Entry<String, String> e : msg_.entrySet()) {
				//sb.append(String.format("%s=%s, ", e.getKey(), e.getOrderPlacementCounter()));
                sb.append(e.getKey() + "=" + e.getValue() + ", ");
                msg.setString(e.getKey(), e.getValue());
            }
            String txt = sb.toString();

            Destination dest = isTopic_ ? session.createTopic(topic_) : session.createQueue(topic_);
            MessageProducer producer = session.createProducer(dest);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(dest, msg);

            Logger.info(String.format("Text %s is published to topic %s", txt, topic_));
            success = true;
        } catch (Exception e_) {
            throw new RuntimeException(e_);
        } finally {
            if (session != null) {
                try {
                    session.close();//PooledSession will return this to pool but not close it
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return success;
    }

    public boolean publishMessage(String topic_, String msg_, String filter_) {
        return publishMessage(topic_, msg_, true, filter_);
    }

    public boolean publishMessage(String topic_, String msg_, boolean isTopic_, String filter_) {
        boolean success = false;
        Session session = null;

        try {
            session = amqMgr.getSession(topic2MQ.apply(topic_));
            TextMessage msg = session.createTextMessage(msg_);
            if (filter_ != null) {
                msg.setStringProperty("myFilter", filter_);
            }
            Destination dest = isTopic_ ? session.createTopic(topic_) : session.createQueue(topic_);
            MessageProducer producer = session.createProducer(dest);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(dest, msg);

            Logger.info(String.format("Text %s is published to topic %s", msg_, topic_));
            success = true;
        } catch (Exception e_) {
            success = false;
            throw new RuntimeException(e_);
        } finally {
            if (session != null) {
                try {
                    session.close();//PooledSession will return this to pool but not close it
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return success;
    }

    public void updateOrderBook(OrderBook ob_) {
        orderBooks.put(ob_.getSecurity(), ob_);
    }

    public OrderBook getOrderBook(Security sec_) {
        return orderBooks.get(sec_);
    }

	public boolean isInterestedInSecurity(SignalType st_, Security s_) {
//		if(SecurityType.STOCK == s_.getType())
//			return true;
		Table<SignalType, Security, Security> t = signalSecurityMappingCache.get(st_);
		if(t == null) return false;
		//return signal2SecurityMapping.contains(st_, s_);
		return t.contains(st_, s_);
    }

    public boolean isForbidden(Security s_) {
        return forbiddenList.contains(s_);
    }

    public List<Security> getForbiddenList() {
        List<Security> l = Lists.newArrayList(forbiddenList);
        Collections.sort(l, Security::compareTo);
        return ImmutableList.copyOf(l);
    }

    public void addForbiddenSecurity(Security s_) {
        if (forbiddenList.contains(s_)) {
            return;
        } else {
            forbiddenList.add(s_);
        }
    }

    public boolean removeForbiddenSecurity(Security s_) {
        return forbiddenList.remove(s_);
    }

    public boolean isUnbooked(Security s_) {
        return unbookedList.contains(s_);
    }

    public List<Security> getUnbookedList() {
        List<Security> l = Lists.newArrayList(unbookedList);
        Collections.sort(l, Security::compareTo);
        return ImmutableList.copyOf(l);
    }

    public void addUnbookedSecurity(Security s_) {
        if (unbookedList.contains(s_)) {
            return;
        } else {
            unbookedList.add(s_);
        }
    }

    public boolean removeUnbookedSecurity(Security s_) {
        return unbookedList.remove(s_);
    }

    public boolean publishOrderInfo(Order o_) {
        if (SignalMode.BACK_TEST == FarmerController.getInstance().getSignalMode())
            return true;

        Map<String, String> v = new HashMap<>();
        v.put("accountId", o_.getAccount() == null ? "DummyAccount" : o_.getAccount());
        v.put("clOrdId", o_.getClOrdId());
        v.put("symbol", o_.getSymbol());
        v.put("secType", o_.getSecType().toString());
        v.put("orderSide", o_.getOrderSide().toString());
        v.put("direction", o_.getDirection().toString());
        v.put("orderType", o_.getOrderType().toString());
        v.put("price", String.valueOf(o_.getPrice()));
        v.put("refPrice", String.valueOf(o_.getRefPx()));
        v.put("qty", String.valueOf(o_.getOrderQty()));
        v.put("openClose", (o_.isClosePosOrder() ? OpenClose.Close : OpenClose.Open).toString());
        v.put("inPendingTime", o_.getInPendingTime().toString());
        v.put("closedTime", o_.getClosedTime().toString());
        v.put("alphaType", o_.getSignalType().getCode());
        v.put("spread", String.valueOf(o_.getSpread()));
        return publishMessage("ENG.ORDER." + o_.getClOrdId(), v);
    }

	public boolean publishAlphaOrderInfo(Order o_) {
		if(SignalMode.BACK_TEST == FarmerController.getInstance().getSignalMode())
			return true;

		Map<String, String> v = new HashMap<>();
		v.put("accountId", o_.getAccount() == null ? "DummyAccount" : o_.getAccount());
		v.put("clOrdId", o_.getClOrdId());
		v.put("symbol", o_.getSymbol());
		v.put("name", o_.getSec().getName());
		v.put("exchange", o_.getSec().getExchange().toString());
		v.put("secType", o_.getSecType().toString());
		v.put("orderSide", o_.getOrderSide().toString());
		v.put("direction", o_.getDirection().toString());
		v.put("orderType", o_.getOrderType().toString());
		v.put("price", String.valueOf(o_.getPrice()));
		v.put("refPrice", String.valueOf(o_.getRefPx()));
		v.put("qty", String.valueOf(o_.getOrderQty()));
		v.put("openClose", (o_.isClosePosOrder() ? OpenClose.Close : OpenClose.Open).toString());
		v.put("inPendingTime", DateUtil.dateTime2Str(o_.getInPendingTime()));
		v.put("closedTime", DateUtil.dateTime2Str(o_.getClosedTime()));
		v.put("alphaType", o_.getSignalType().getCode());
		v.put("spread", String.valueOf(o_.getSpread()));
		v.put("counter", String.valueOf(o_.getCounter()));
		String topic = "ALPHA.SIGNAL_" + o_.getSymbol();
		publishMessage(topic, v);
		String topic2 = "MONITOR.SIGNAL.ORDER." + o_.getSymbol() + "." +  o_.getClOrdId();
		return publishMessage(topic2, v);
	}

	public boolean publishAlphaStateInfo(Order o_, int longCounter, int shortCounter){
        if(SignalMode.BACK_TEST == FarmerController.getInstance().getSignalMode())
            return true;
        Map<String, String> v = new HashMap<>();
        v.put("accountId", o_.getAccount() == null ? "DummyAccount" : o_.getAccount());
        v.put("clOrdId", o_.getClOrdId());
        v.put("symbol", o_.getSymbol());
        v.put("name", o_.getSec().getName());
        v.put("exchange", o_.getSec().getExchange().toString());
        v.put("secType", o_.getSecType().toString());
        v.put("orderSide", o_.getOrderSide().toString());
        v.put("direction", o_.isClosePosOrder() ? Direction.UNKNOWN.toString() : o_.getDirection().toString());
        v.put("orderType", o_.getOrderType().toString());
        v.put("price", String.valueOf(o_.getPrice()));
        v.put("refPrice", String.valueOf(o_.getRefPx()));
        v.put("qty", String.valueOf(o_.getOrderQty()));
        v.put("openClose", (o_.isClosePosOrder() ? OpenClose.Close : OpenClose.Open).toString());
        v.put("inPendingTime", DateUtil.dateTime2Str(o_.getInPendingTime()));
        v.put("closedTime", DateUtil.dateTime2Str(o_.getClosedTime()));
        v.put("alphaType", o_.getSignalType().getCode());
        v.put("spread", String.valueOf(o_.getSpread()));
        v.put("counter", String.valueOf(o_.getCounter()));
        v.put("longCounter", String.valueOf(longCounter));
        v.put("shortCounter", String.valueOf(shortCounter));
        String topic = "MONITOR.SIGNAL.STATE." + o_.getSymbol();
        return publishMessage(topic, v);
    }

    public LocalTime getTimeNow() {
        return LocalTime.now();
    }

    public long getTimeNowInSecs() {
        return getTimeNow().toSecondOfDay();
    }

    public void registerOrderBookPublisher(long initialDelay_) {
        if (SignalMode.BACK_TEST == FarmerController.getInstance().getSignalMode())
            return;

        esOrderBook.scheduleAtFixedRate(() -> {
            try {
                if (FarmerController.getInstance().getStopFlag()) {
                    return;
                }
                Element example = Util.readStrAsXml(orderBookXml);

                for (Security sec : INTRESTED_INDEX.values()) {
                    OrderBook ob = FarmerController.getInstance().getOrderBook(sec);
                    if (ob != null) {
                        Element ele = example.clone();
                        ele.getChild("body").getChild("record").setAttribute("stkcode", sec.getSymbol());
                        ele.getChild("body").getChild("record").setAttribute("lastprice", String.valueOf(ob.getLastPx()));
                        String timeStr = DateUtil.time2str(ob.getTime(), DateUtil.TIME_HHMMSS_SSS2);
                        ele.getChild("body").getChild("record").setAttribute("time", timeStr);
                        String exch = Exchange.SZ == sec.getExchange() ? Exchange.SZ.toString() : "sh";
                        String filter = "hq" + sec.getSimpleSymbol() + exch.toLowerCase();
                        publishMessage("quotahq", new XMLOutputter().outputString(ele), filter);
                    }
                }
            } catch (Exception e_) {
                Logger.error(e_.getMessage(), e_);
                Alert.fireAlert(Alert.Severity.Major, "OrderBook publish error", "OrderBook publish error");
            }
        }, initialDelay_, 5, TimeUnit.SECONDS);
    }

    public void publishOrderBook(OrderBook ob_) {
        if (ob_ == null) return;
        if (FarmerController.getInstance().getStopFlag()) return;

        try {
            Element example = Util.readStrAsXml(orderBookXml);
            Element ele = example.clone();
            Element record = ele.getChild("body").getChild("record");
            record.setAttribute("stkcode", ob_.getSymbol());
            record.setAttribute("lastprice", String.valueOf(ob_.getLastPx()));
            String timeStr = DateUtil.time2str(ob_.getTime(), DateUtil.TIME_HHMMSS_SSS2);
            record.setAttribute("time", timeStr);
            record.setAttribute("donevolume", String.valueOf(ob_.getVolume()));
            record.setAttribute("turnover", String.valueOf(ob_.getTurnover()));
            record.setAttribute("openprice", String.valueOf(ob_.getOpenPx()));
            record.setAttribute("highestprice", String.valueOf(ob_.getHighPx()));
            record.setAttribute("lowestprice", String.valueOf(ob_.getLowPx()));
            record.setAttribute("closeprice", String.valueOf(ob_.getClosePx()));
            record.setAttribute("preclose", String.valueOf(ob_.getPreClose()));

            String exch = Exchange.SZ == ob_.getSecurity().getExchange() ? Exchange.SZ.toString() : "sh";
            String filter = "hq" + ob_.getSecurity().getSimpleSymbol() + exch.toLowerCase();
            publishMessage("quotahq", new XMLOutputter().outputString(ele), filter);

        } catch (Exception e_) {
            Logger.error(e_.getMessage(), e_);
            Alert.fireAlert(Alert.Severity.Major, "OrderBook publish error", "OrderBook publish error");
        }
    }

    public<T extends Featurable> T getDailyFeature(Class<T> clazz_, Security sec_, LocalDate date_) {
	    return (T)dailyFeautureCache.get(clazz_).get(sec_, date_);
    }

    public int getSignalCounter(Security sec_) {
		AtomicInteger ai = signalSecurityMappingCache.getSignalCounter(sec_);
		if(ai == null)
			throw new IllegalDataException("ai == null, sec = " + sec_.getSymbol());
		return ai.incrementAndGet();
	}
}
