package com.phenix.farmer;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.phenix.tdf.TDFClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TDFRunner {
    private static Logger Logger = LoggerFactory.getLogger(TDFRunner.class);

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


        final TDFClient client = new TDFClient();
        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            client.init(null);
            client.connect();
            client.start();
        });

        try {
            t.start();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("fucku");
        client.cleanup();
        System.out.println("fucku finished");
    }
}
