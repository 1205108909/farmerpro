package com.phenix.farmer.runner;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.google.common.base.Stopwatch;
import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.FarmerConfigManager;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.event.*;
import com.phenix.farmer.signal.SignalMode;
import com.phenix.farmer.signal.SignalType;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.TimeSeriesSecurityData;
import com.phenix.orderbook.Transaction;
import com.phenix.provider.*;
import com.phenix.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.toList;

//TODO:
// 1. Modularize the whole package
public class BackTestStockRunner {
	private static Logger Logger = LoggerFactory.getLogger(BackTestStockRunner.class);
	private static BackTestRunner runner;
	private final static LocalTime TIME_0930 = LocalTime.of(9, 30, 00);

	public static void main(String[] args) {
		String engineConfigPath = System.getProperty("com.phenix.farmer.config");
		String logConfigPath = System.getProperty("com.phenix.farmer.logconfig");

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
		private static Logger Logger = LoggerFactory.getLogger(BackTestRunner.class);
		private final static String TRADING_DAY_PATH = "f:/data/static/tradingDay.csv";
		private final static String INDEX_ORDERBOOK_PATH = "f:/data/h5data/index/tick";
		private final static String STOCK_ORDERBOOK_PATH = "f:/data/h5data/stock/tick";
		private final static String FUTURE_ORDERBOOK_PATH = "f:/data/h5data/index_future/tick";
//		private final static String STOCK_ORDER_PATH = "f:/data/h5data/stock/order";
		private final static String STOCK_TRANSACTION_PATH = "f:/data/h5data/stock/transaction";
//		private final static String OBI_PATH = "e:/data/obi/k5s";
		private final static String INDEX_UNIVERSE_PATH = "d:/work/model_prod/indices.csv";
//		private final static String STOCK_UNIVERSE_PATH = "d:/work/stock_universe.csv";
		private final static String STOCK_UNIVERSE_PATH = "d:/work/model_prod/stocks_20.csv";
		private final static String REPORT_PATH = "e:/result/intra_day_report.csv";
		private final static LocalDate DATE_20150516 = LocalDate.of(2015, 5, 16);

		private List<LocalDate> tradingDay;
		private final LocalDate FROM = FarmerConfigManager.getInstance().getBackTestConfig().getStartDate();
		private final LocalDate TO = FarmerConfigManager.getInstance().getBackTestConfig().getEndDate();
		private List<Security> secs = new ArrayList<>();
		private Map<SignalType, TreeMap<LocalDate, List<Security>>> securities = new HashMap<>();
		ExecutorService es = Executors.newFixedThreadPool(1);

		public void startTest() {
			try {
				tradingDay = Files.lines(Paths.get(TRADING_DAY_PATH)).skip(1)
						.map(e -> DateUtil.getDate(e))
						.filter(e -> !e.isBefore(FROM) && !e.isAfter(TO))
						.sorted()
						.collect(toList());
				Files.lines(Paths.get(STOCK_UNIVERSE_PATH)).skip(1)
						.map(e -> e.split(","))
						.forEach(e -> {
							String strEx = StringUtils.substringAfter(e[0], ".");
							Security s;
							if("na".equalsIgnoreCase(e[5]))
								s = Security.of(e[0], SecurityType.parse(e[2]), TradeStatus.TRADABLE, Exchange.parse(strEx), e[0]);
							else {
								Security sec = FarmerDataManager.getInstance().getSecurity(e[5]);
								if(sec == null) {
									throw new IllegalDataException("Unknown underlying: " + e[5]);
								}
								s = new IndexFuture(e[0], SecurityType.parse(e[2]), TradeStatus.TRADABLE, 300, 0.1, sec);
							}

							//s.setExchange(Exchange.parse(strEx));
							secs.add(s);
						});

				IIntraDayDataProvider indexOrderbookProvider = new CSVIndexOrderBookProvider(INDEX_ORDERBOOK_PATH, secs, tradingDay);
				IIntraDayDataProvider futureOrderBookProvider = new CSVIndexFutureOrderBookProvider(FUTURE_ORDERBOOK_PATH, secs, tradingDay);
//				IIntraDayDataProvider obiProvider = new OBIKLineDataProvider(OBI_PATH, secs, tradingDay);
				IIntraDayDataProvider stockOrderBookProvider = new HDF5OrderBookProvider(STOCK_ORDERBOOK_PATH, secs, tradingDay);
//				IIntraDayDataProvider stockOrderProvider = new HDF5OrderProvider(STOCK_ORDER_PATH, secs, tradingDay);
				IIntraDayDataProvider stockTransactionProvider = new HDF5TransactionProvider(STOCK_TRANSACTION_PATH, secs, tradingDay);

				FarmerController.getInstance().reinitSignal();
				FarmerController.getInstance().setSignalMode(SignalMode.BACK_TEST);
				FarmerController.getInstance().work();
				AtomicLong futureFileMissCounter = new AtomicLong(0);
				AtomicLong illegalFutureDataCounter = new AtomicLong(0);

				Stopwatch watcher = Stopwatch.createStarted();
				//for each date, publish the event
				for(LocalDate date : tradingDay) {
                    Stopwatch watcherd = Stopwatch.createStarted();
					Logger.info("Start date " + date.toString());
					FarmerController.getInstance().enqueueEvent(new MarketOpenEvent(date));
					List<TimeSeriesSecurityData> tsd = new ArrayList<>(20_000_000);

					final CountDownLatch latch = new CountDownLatch(secs.size());
					for (int i = 0; i < secs.size(); i++) {
						final int ii = i;
						es.execute(() -> {
                            Stopwatch watcherSecurity = Stopwatch.createStarted();
							Security s = secs.get(ii);
							Logger.info("Start security " + s + " : " + ii + " of " + secs.size());
							List<OrderBook> indexObs = Collections.emptyList();
							List<OrderBook> stockObs = Collections.emptyList();
							try {
								if(SecurityType.INDEX == s.getType())
									indexObs = indexOrderbookProvider.getData(s, date);
//								if(obs.size() < 100) {
//									errorDates.put(date, date);
//									return;
//								}
//								List<OrderBook> futureObs = Collections.emptyList();
//								if (SecurityType.INDEX == s.getType()) {
//									IndexFuture indexFuture = (IndexFuture) FarmerDataManager.getInstance().getIndexFuture(s, date, IndexFutureType.CURRENT_MONTH);
//									if (date.isBefore(DATE_20150516) //zz500 and sz50 starts 20150416
//											&& (Security.INDEX_ZZ500.equals(s) || Security.INDEX_SZ50.equals(s))) {
//										indexFuture = null;//to early ignore the 1st month indexfuture
//									}
//									if (indexFuture != null) {
//										try {//additional try catch here so back test continue in case future data misses
//											futureObs = futureOrderBookProvider.getData(indexFuture, date);
//											Logger.info(futureObs.size() + " orderbooks are found for future " + indexFuture);
//
//											if (futureObs.size() < 12000 || !futureObs.get(0).getTime().isBefore(TIME_0930)) {
//												illegalFutureDataCounter.incrementAndGet();
//												Logger.error("Illegal Future Index with size=" + futureObs.size() + ",1st data time:" + futureObs.get(0).getTime());
//												futureObs.clear();
//												//throw new IllegalDataException("Illegal Future Index with size=" + futureObs.size() + ",1st data time:" + futureObs.get(0).getTime());
//											}
//										} catch (IllegalDataException e_) {
//											futureFileMissCounter.incrementAndGet();
//											Logger.error(String.format("Loading Data Error:[Security = %s, Date = %s", s.getSymbol(), date));
//											Logger.error(e_.getMessage(), e_);
//										}
//									} else {
//										Logger.info("no index future found for sec: " + s);
//									}
//								}
//								if (!futureObs.isEmpty()) {
//								} else {
//									//Logger.info("No future data used for: " + date);
//								}
								//read stock orderbook
								if(SecurityType.STOCK == s.getType())
									stockObs = stockOrderBookProvider.getData(s, date);
								//read feature
//								List<KLineData> obi = Collections.emptyList();
//								try {
//									//obi = obiProvider.getData(s, date);
//								} catch (IllegalDataException e_) {
//									Logger.error(String.format("Loading OBI Error:[Security = %s, Date = %s", s.getSymbol(), date));
//								}
								//read orderFlow
								List<Transaction> tr = Collections.emptyList();
								try {
									if(SecurityType.STOCK == s.getType())
										tr = stockTransactionProvider.getData(s, date);
								} catch (IllegalDataException e_) {
									Logger.error(String.format("Loading OBI Error:[Security = %s, Date = %s", s.getSymbol(), date));
								}

								tsd.addAll(indexObs);
//								tsd.addAll(futureObs);
								tsd.addAll(stockObs);
//								tsd.addAll(obi);
								tsd.addAll(tr);
								indexObs.clear();
//								futureObs.clear();
								stockObs.clear();
//								obi.clear();
								tr.clear();
								Logger.info("Date = " + date + ", sec = " + s + " data loading done, spent time: " + watcherSecurity.stop());
							} catch (IllegalDataException e_) {
								Logger.error(String.format("Loading Data Error:[Security = %s, Date = %s", s.getSymbol(), date));
								Logger.error(e_.getMessage(), e_);
							}

							latch.countDown();
						});
					}

					//wait for the completion of the execution serviece
					latch.await(50000, TimeUnit.MILLISECONDS);
					long counter = latch.getCount();
					if (counter > 0) {//make sure all data are feeded
						throw new IllegalStateException("Task didn't finished due to count =" + counter);
					}

                    Logger.info("load date[" + date + "] totally spend time: " + watcherd.stop());
                    Stopwatch watcherSort= Stopwatch.createStarted();
                    Logger.info("Date = " + date + " start sort, size = " + tsd.size());
					tsd.sort(TimeSeriesSecurityData.COMPARATOR_DATATIME_FIRST);
                    Logger.info("Date = " + date + " sort done, spend time: " + watcherSort.stop());
                    Stopwatch watcherEvent= Stopwatch.createStarted();
                    tsd.forEach(e -> FarmerController.getInstance().enqueueEvent(new TimeSeriesDataUpdateEvent(e)));
                    tsd.clear();
                    futureOrderBookProvider.clear(date);
					indexOrderbookProvider.clear(date);
					stockOrderBookProvider.clear(date);
					Logger.info("Going to finish date " + date.toString());

					FarmerController.getInstance().enqueueEvent(new PersistSignalEvent(FarmerConfigManager.getInstance().getInstanceConfig().getPersistPath()));
					FarmerController.getInstance().enqueueEvent(new DailySettleEvent(date));

					Logger.info("Finish date " + date.toString() + ", handle event spent time:" + watcherEvent.stop());
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
	}
}
