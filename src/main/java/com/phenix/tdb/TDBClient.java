package com.phenix.tdb;

import cn.com.wind.td.tdb.*;
import com.phenix.data.Exchange;
import com.phenix.data.Security;
import com.phenix.orderbook.OrderBook;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TDBClient {
    private static final Logger logger = LoggerFactory.getLogger(TDBClient.class);
    public static final TDBClient client = new TDBClient();
    private String[] DEFAULT_L2_MARKETS = null;
    // Default setting for data retrieval.
    private static final int DEFAULT_BEGIN_DATE = 0;
    private static final int DEFAULT_BEGIN_TIME = 0;
    private static final int DEFAULT_END_TIME = 0;
    private final static Map<Exchange, String> EXCHANGE2MARKET_MAPPING = new HashMap<>();

    // Tdb Client
    private cn.com.wind.td.tdb.TDBClient l2Client = new cn.com.wind.td.tdb.TDBClient();
    // Tdb Client Settings
    private final OPEN_SETTINGS L2_TdbSettings = new OPEN_SETTINGS();

    static {
        EXCHANGE2MARKET_MAPPING.put(Exchange.CFFEX, "CF-1-0");
        EXCHANGE2MARKET_MAPPING.put(Exchange.SS, "SH-2-0");
        EXCHANGE2MARKET_MAPPING.put(Exchange.SZ, "SZ-2-0");
    }


    /**
     * Constructor
     */
    private TDBClient() {
        loadConfig();
    }

    /**
     * Load configuration file.
     */
    private void loadConfig() {
        try {
            DEFAULT_L2_MARKETS = StringUtils.split("SH-2-0;SZ-2-0;CF-1-0", ";"); //用于根据市场编号获得所有证券代码

            L2_TdbSettings.setIP("172.10.10.12");
            L2_TdbSettings.setPort("10100");
            L2_TdbSettings.setUser("test");
            L2_TdbSettings.setPassword("test");

            L2_TdbSettings.setRetryCount(10);
            L2_TdbSettings.setRetryGap(10);
            L2_TdbSettings.setTimeOutVal(20);

        } catch (Exception exception) {
            throw new IllegalStateException("Cannot load tdb config.", exception);
        }
    }

    /**
     * Connect
     * When login failed, ResLogin is null, and no info is given. Always throws UNKNOWN.
     */

    public void connect() {
        ResLogin login1 = l2Client.open(L2_TdbSettings);
        if (null == login1) {
            throw new IllegalStateException("TDB Login Error");
        }

        logger.info("Successfully connect to Tdb server: " + L2_TdbSettings.getIP()
                + ":" + L2_TdbSettings.getPort());
    }

    /**
     * Recover history future data
     *
     * @param code wind code class
     */
    private List<OrderBook> getFutureOrderBook(Security code) {
        String security = code.getSymbol();
        String market = EXCHANGE2MARKET_MAPPING.get(code.getExchange());

        ReqTick reqFuture = new ReqTick();
        reqFuture.setCode(security);
        reqFuture.setMarketKey(market);
        reqFuture.setDate(DEFAULT_BEGIN_DATE);
        reqFuture.setBeginTime(DEFAULT_BEGIN_TIME);
        reqFuture.setEndTime(0);

        Tick[] futureTicks = l2Client.getTick(reqFuture);
        if (null != futureTicks) {
            List<OrderBook> obs = new ArrayList<>(futureTicks.length);
            for (Tick future : futureTicks) {
                OrderBook ob = TDBDataParser.parseTDBIndexFutureOrderBook(future);
                obs.add(ob);
            }
            return obs;
        } else {
            logger.info("Futures " + security + " is empty");
            return null;
        }
    }

    /**
     * Recover history market data
     *
     * @param code wind code class
     */
    private List<OrderBook> getIndexOrderBook(Security code) {
        String security = code.getSymbol();
        String marketKey = EXCHANGE2MARKET_MAPPING.get(code.getExchange());
        ReqTick reqTick = new ReqTick();
        reqTick.setMarketKey(marketKey);
        reqTick.setDate(DEFAULT_BEGIN_DATE);
        reqTick.setBeginTime(DEFAULT_BEGIN_TIME);
        reqTick.setEndTime(DEFAULT_END_TIME);
        reqTick.setCode(security);
        Tick[] ticks = l2Client.getTick(reqTick);

        if (null != ticks) {
            List<OrderBook> obs = new ArrayList<>(ticks.length);
            for (Tick future : ticks) {
                OrderBook ob = TDBDataParser.parseTDBIndexOrderBook(future);
                obs.add(ob);
            }
            return obs;
        } else {
            logger.info("Market Data " + code + " is empty");
            return null;
        }
    }

    /**
     * Close components.
     */
    public void close() {
        try {
            l2Client.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}