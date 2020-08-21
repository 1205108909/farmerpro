package com.phenix.dailyjob;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.google.common.base.Stopwatch;
import com.phenix.data.Exchange;
import com.phenix.data.Security;
import com.phenix.data.SecurityType;
import com.phenix.data.TradeStatus;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.FarmerConfigManager;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.event.*;
import com.phenix.farmer.signal.SignalMode;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.TimeSeriesSecurityData;
import com.phenix.orderbook.Transaction;
import com.phenix.provider.CSVIndexOrderBookProvider;
import com.phenix.provider.HDF5OrderBookProvider;
import com.phenix.provider.HDF5TransactionProvider;
import com.phenix.provider.IIntraDayDataProvider;
import com.phenix.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

//TODO:
// 1. Modularize the whole package
public class KLineGenerator {
    private static Logger Logger = LoggerFactory.getLogger(KLineGenerator.class);
    private static BackTestRunner runner;

    public static void main(String[] args) {
        String engineConfigPath = System.getProperty("com.phenix.farmer.config");
        String logConfigPath = System.getProperty("com.phenix.farmer.logconfig");
        System.out.println("engineConfigPath = " + engineConfigPath);
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

        Logger.info("Init FarmerConfigManager");
        FarmerConfigManager farmerConfigManager = FarmerConfigManager.getInstance();
        farmerConfigManager.init(engineConfigPath, logConfigPath);

        Logger.info("Init FarmerDataManager");
        FarmerDataManager.getInstance().init(farmerConfigManager);
        Logger.info("Start FarmerPatternRunner");

        runner = new BackTestRunner();
        runner.startTest();
        FarmerController.getInstance().shutdown();
        FarmerDataManager.getInstance().cleanup();
    }

    public static class BackTestRunner {
        private final static String STOCK_ORDERBOOK_FILE_PATH = "Y:/Data/h5data/stock/tick";
        private final static String STOCK_TRANSACTION_FILE_PATH = "Y:/Data/h5data/stock/Transaction";
        private final static String ORDERBOOK_FILE_PATH = "Y:/Data/h5data/index/tick/";
        private final static String REPORT_PATH = "d:/work/intra_day_report.csv";
        private static Logger Logger = LoggerFactory.getLogger(BackTestRunner.class);
//        private final LocalDate FROM = DateUtil.getDate(String.valueOf(FarmerConfigManager.getInstance().getBackTestConfig().getStartDate()));
//        private final LocalDate TO = DateUtil.getDate(String.valueOf(FarmerConfigManager.getInstance().getBackTestConfig().getEndDate()));
        ExecutorService es = Executors.newFixedThreadPool(1);
        private List<LocalDate> tradingDay;
        private List<Security> secs = new ArrayList<>();

        public void startTest() {

            try {
                tradingDay = List.copyOf(FarmerDataManager.getInstance().getTradingDayCache());
                IIntraDayDataProvider stockOrderBookProvider = new HDF5OrderBookProvider(STOCK_ORDERBOOK_FILE_PATH, secs, tradingDay);
                IIntraDayDataProvider stockTransactionProvider = new HDF5TransactionProvider(STOCK_TRANSACTION_FILE_PATH, secs, tradingDay);
                IIntraDayDataProvider indexOrderbookProvider = new CSVIndexOrderBookProvider(ORDERBOOK_FILE_PATH, secs, tradingDay);

                FarmerController.getInstance().reinitSignal();
                FarmerController.getInstance().setSignalMode(SignalMode.BACK_TEST);
                FarmerController.getInstance().work();
                AtomicLong futureFileMissCounter = new AtomicLong(0);
                AtomicLong illegalFutureDataCounter = new AtomicLong(0);
                Stopwatch watcher = Stopwatch.createStarted();
                //for each date, publish the event
                for (LocalDate date : tradingDay) {
                    Logger.info("Start date " + date.toString());
                    Stopwatch dailyWatcher = Stopwatch.createStarted();
                    FarmerController.getInstance().enqueueEvent(new MarketOpenEvent(date));
                    List<TimeSeriesSecurityData> tsd = new ArrayList<>(2048*1024);

                    List<Security> allStocks = getAllStocks(DateUtil.date2Str(date));
                    secs.addAll(allStocks);
                    final CountDownLatch latch = new CountDownLatch(secs.size());
                    for (int i = 0; i < secs.size(); i++) {
                        final int ii = i;
                        es.execute(() -> {
                            Security s = secs.get(ii);
                            Logger.info("Start security " + s + " : " + ii + " of " + secs.size());
                            List<Transaction> obs = new ArrayList<>(1024*1024);
                            List<OrderBook> sobs = new ArrayList<>(1024*1024);
                            try {
                                if (SecurityType.STOCK == s.getType()){
                                    obs = stockTransactionProvider.getData(s, date);
                                    sobs = stockOrderBookProvider.getData(s,date);
                                }else if (SecurityType.INDEX == s.getType()){
                                    obs =indexOrderbookProvider.getData(s, date);
                                }
                                tsd.addAll(obs);
                                tsd.addAll(sobs);

                                if(!tsd.isEmpty()){
                                    tsd.sort(TimeSeriesSecurityData.COMPARATOR_DATATIME_FIRST);
                                }
                                obs.forEach(e -> FarmerController.getInstance().enqueueEvent(new TransactionUpdateEvent(e)));
                                sobs.forEach(e -> FarmerController.getInstance().enqueueEvent(new OrderBookUpdateEvent(e)));
                                obs.clear();
                                sobs.clear();
                            } catch (IllegalDataException e_) {
                                Logger.error(e_.getMessage(), e_);
                            }
                            latch.countDown();
                        });
                    }

                    //wait for the completion of the execution serviece
                    latch.await(5000000, TimeUnit.MILLISECONDS);
                    long counter = latch.getCount();
                    if (counter > 0) {//make sure all data are feeded
                        throw new IllegalStateException("Task didn't finished due to count =" + counter);
                    }
                    secs.clear();
                    Logger.info("Going to finish date " + date.toString());
//                    String persistPath = FarmerConfigManager.getInstance().getInstanceConfig().getPersistPath();
                    String persistPath = "y:\\data\\h5data\\stock\\minuteBar\\";
                    FarmerController.getInstance().enqueueEvent(new PersistSignalEvent(persistPath));
                    FarmerController.getInstance().enqueueEvent(new DailySettleEvent(date));
                    Logger.info("Finish date " + date.toString() + " spend : "+ dailyWatcher.stop());
                }
                Logger.info("Totally " + futureFileMissCounter.get() + " files are missed");
                Logger.info("Totally " + illegalFutureDataCounter.get() + " files are invalid");
                Logger.info("BackTest totally spent time: " + watcher.stop());

                Thread.sleep(1000);//wait all signals finish theire job
                String rpt = FarmerController.getInstance().getPositionBalanceManager().generateReport();
                //Files.writeString(Paths.get(REPORT_PATH), rpt);
                FileUtils.writeStringToFile(new File(REPORT_PATH), rpt, "UTF-8");

                es.shutdown();
                FarmerController.getInstance().shutdown();
                FarmerDataManager.getInstance().cleanup();
            } catch (Exception ex_) {
                ex_.printStackTrace();
                System.exit(-1);
            }
        }

        private List<Security> getAllStocks(String tradingDay){
            List<Security> list = new ArrayList<>(5000);
            String tradableFileName = "Y://Data//static//TradableList//" + tradingDay + ".csv";
            try {
                Files.lines(Paths.get(tradableFileName)).skip(1).forEach(line -> {
                    if (line.split(",")[3].equals("1") && line.split(",")[2].equals("EQA")){
                        String symbol = line.split(",")[0];
                        Security sec = Security.of(symbol, SecurityType.STOCK, TradeStatus.TRADABLE, Exchange.parse(StringUtils.split(symbol, ".")[1]), symbol);
                        list.add(sec);

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
}
