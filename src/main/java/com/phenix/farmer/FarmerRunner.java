package com.phenix.farmer;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.phenix.admin.AdminService;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.command.FarmerCommandFactory;
import com.phenix.farmer.config.AdminConfig;
import com.phenix.farmer.event.DailySettleEvent;
import com.phenix.farmer.event.MarketOpenEvent;
import com.phenix.farmer.event.PersistSignalEvent;
import com.phenix.farmer.signal.SignalMode;
import com.phenix.tdf.TDFClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * TODO: centralized order book check
 * 1. data time 2 current time check
 * 2. future and security syncronize check
 */

public class FarmerRunner {
	private static Logger LOGGER = LoggerFactory.getLogger(FarmerRunner.class);
    private final static CountDownLatch LATCH = new CountDownLatch(1);
    private AdminService adminService;
    private ExecutorService esAdmin;
    private TDFClient tdfCient;
    private ExecutorService esTdf;
    private ScheduledExecutorService esShutDown;

    public static void main(String[] args) {
        FarmerRunner runner = new FarmerRunner();
        try {
            String engineConfigPath = System.getProperty("com.phenix.farmer.config");
            String logConfigPath = System.getProperty("com.phenix.farmer.logconfig");
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            StatusPrinter.print(lc);

			LOGGER.info("Init FarmerConfigManager");
			System.out.println("Init FarmerConfigManager");
			FarmerConfigManager hunterConfigManager = FarmerConfigManager.getInstance();
			hunterConfigManager.init(engineConfigPath, logConfigPath);

			LOGGER.info("Init FarmerDataManager");
			System.out.println("Init FarmerDataManager");
			FarmerDataManager.getInstance().init(hunterConfigManager);

			LOGGER.info("Going to start FarmerRunner");
			System.out.println("Going to start FarmerRunner");

            runner.start();
			LOGGER.info("HunterRunner Started");
			System.out.println("HunterRunner Started");

            LATCH.await();

            runner.stop();
        } catch (Exception e_) {
            LOGGER.error(e_.getMessage(), e_);
            System.out.println(e_);
            System.exit(-1);
        }
    }

    public void start() {

        //start controller
		FarmerController.getInstance().reinitSignal();
        FarmerController.getInstance().setSignalMode(SignalMode.PAPER_TEST);
        FarmerController.getInstance().setSignalMode(FarmerConfigManager.getInstance().getInstanceConfig().getSignalMode());
        FarmerController.getInstance().work();
        FarmerController.getInstance().enqueueEvent(new MarketOpenEvent(FarmerDataManager.getInstance().TODAY));
		System.out.println("HunterController started");
		LOGGER.info("HunterController started");

        //start admin server
		AdminConfig ac = FarmerConfigManager.getInstance().getAdminConfig();
		adminService = AdminService.of(ac.getIp(), ac.getPort(), FarmerCommandFactory.getInstance().getHandler());
        esAdmin = Executors.newSingleThreadExecutor();
        esAdmin.submit(() -> adminService.start());

        System.out.println("AdminService started");
        LOGGER.info("AdminService started");

        //start honghui
        tdfCient = new TDFClient();
        esTdf = Executors.newSingleThreadExecutor();
        esTdf.submit(() -> {
            //System.out.println(Thread.currentThread().getName());
			tdfCient.init(FarmerConfigManager.getInstance().getTdfConfig());
            tdfCient.connect();
            tdfCient.start();
        });
        System.out.println("TDF started");
        LOGGER.info("TDF started");

        //TODO: start ctp

        //setup shutdown timer
		long delay = FarmerConfigManager.getInstance().getInstanceConfig().getShutDownDelayInSecs();
        LOGGER.info("Engine will be shutdown in " + delay + " seconds");
        System.out.println("Engine will be shutdown in " + delay + " seconds");
        if (delay < 0) {
            throw new IllegalDataException("delay should >= 0 while delay = " + delay);
        }
        esShutDown = Executors.newSingleThreadScheduledExecutor();
		esShutDown.schedule(() -> {
		    try {
		    LOGGER.info("Going to shutdown...");
            LOGGER.info("Going to store signal...");
//            FarmerController.getInstance().enqueueEvent(new StoredSignalEvent());
            FarmerController.getInstance().storeSignal2DB();
            LOGGER.info("Going to persist signal...");
            FarmerController.getInstance().enqueueEvent(new PersistSignalEvent(FarmerConfigManager.getInstance().getInstanceConfig().getPersistPath()));
            try {
                Thread.sleep(1 * 60 * 1000);//wait 10 secs for persistent event done
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
            LOGGER.info("Going to dailySettle...");
            FarmerController.getInstance().enqueueEvent(new DailySettleEvent(FarmerDataManager.getInstance().TODAY));
            LOGGER.info("Finish Shutdown jobs");
		    } catch (Exception e) {
		        LOGGER.error(e.getMessage(), e);
            } finally {
                LATCH.countDown();
            }
		}, delay, TimeUnit.SECONDS);
        esShutDown.scheduleAtFixedRate(() -> {
            //System.out.println(Thread.currentThread().getName() + "****");
			boolean stopFlag = FarmerController.getInstance().getStopFlag();
            if (stopFlag) LATCH.countDown();
        }, 10, 5, TimeUnit.SECONDS);


		long initialDelay = FarmerDataManager.STOCK_TIME_CONTINUOUS_START.toSecondOfDay() - FarmerDataManager.getInstance().getTimeNowInSecs();
        FarmerDataManager.getInstance().registerOrderBookPublisher(Math.max(initialDelay, 10));
    }

    public void stop() throws InterruptedException {
        LOGGER.info("Going to stop farmer");
        adminService.cleanup();
        esAdmin.shutdown();
        System.out.println("AdminService shutdown");
        LOGGER.info("AdminService shutdown");

        tdfCient.cleanup();
        Thread.sleep(3000);
        esTdf.shutdown();

        System.out.println("TDF shutdown");
        LOGGER.info("TDF shutdown");
        esShutDown.shutdown();

		FarmerController.getInstance().shutdown();
        FarmerDataManager.getInstance().cleanup();
		System.out.println("HunterDataManager shutdown");
		LOGGER.info("HunterDataManager shutdown");

        System.out.println("All components shutdown");
        LOGGER.info("All components shutdown");
        System.exit(0); //stupid honghui cleanup
    }
}
