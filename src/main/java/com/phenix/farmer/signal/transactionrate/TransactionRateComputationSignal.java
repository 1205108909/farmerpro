package com.phenix.farmer.signal.transactionrate;

import com.google.common.collect.ImmutableList;
import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.Controller;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.event.*;
import com.phenix.farmer.signal.*;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Transaction;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransactionRateComputationSignal extends AbstractSignal implements Orderable {
    private final static transient org.slf4j.Logger Logger = LoggerFactory.getLogger(TransactionRateComputationSignal.class);
    private final PositionBalanceManager pbManager;
    private final Set<Security> secs = new HashSet();
    private final List<Integer> intervals = ImmutableList.of(1, 60);
    private Security firstSec = null;
    private LocalTime timeCounter = LocalTime.MIN;
    private final List<FeatureHolder<TransactionRateIntervalStat>> isHolders;
    private final Controller controller;
    private TransactionRateComputationSignalConfig config;
    private final static List<Security> SUPPORTED_INDEX = ImmutableList.of(Security.INDEX_ZZ500);
    //keep the index component used today
//    private Table<Security, Security, IndexComponent> dailyIndexComponent;
    //keep the index component used for a day, in case stock data missing
//    private Table<Security, Security, IndexComponent> dailyUsedComponents = HashBasedTable.create();

    public TransactionRateComputationSignal(Controller controller_, PositionBalanceManager pbManager_){
        controller = controller_;
        pbManager = pbManager_;
        isHolders = new ArrayList<>(8);
        buildCache();
    }

    private void buildCache(){
        SessionGroup sgSz = FarmerDataManager.getInstance().getSessionGroup(Exchange.SZ);
        SessionGroup sgSh = FarmerDataManager.getInstance().getSessionGroup(Exchange.SS);
        SessionGroup sgIf = FarmerDataManager.getInstance().getSessionGroup(Exchange.CFFEX);
        for(int i : intervals) {
            FeatureHolder<TransactionRateIntervalStat> isHolder = new FeatureHolder<>(i, TransactionRateIntervalStat.DefaultFactory);
            isHolder.addTimeSlots(Exchange.SS, sgSh);
            isHolder.addTimeSlots(Exchange.SZ, sgSz);
            isHolder.addTimeSlots(Exchange.CFFEX, sgIf);
            isHolders.add(isHolder);
        }
    }

    @Override
    public void reinit() {
        super.reinit();
        cleanup();
        buildCache();
        Logger.info(String.format("%s is inited", getClass().getSimpleName()));
    }

    public void refreshConfig(Security s_) {
        config = getConfig(TransactionRateComputationSignalConfig.class, s_);
    }

    public void cleanup() {
        for (FeatureHolder<TransactionRateIntervalStat> isHolder : isHolders)
            isHolder.cleanup();
        isHolders.clear();
        secs.clear();
        firstSec = null;
        timeCounter = LocalTime.MIN;
//        dailyUsedComponents.clear();
        Logger.info(String.format("%s is cleaned up", getClass().getSimpleName()));
    }

    @Override
    public void handleMarketOpenEvent(IEngineEvent e_) {
//        LocalDate date = ((MarketOpenEvent) e_).getDate();
//        dailyIndexComponent = FarmerDataManager.getInstance().getIndexComponent(date);
    }

    @Override
    public void handleDailySettleEvent(IEngineEvent e_) {
        DailySettleEvent e = (DailySettleEvent) e_;
        reinit();
        Logger.info(getClass().getSimpleName() + "is cleaned up");
    }

    @Override
    public void handleTransactionUpdateEvent(IEngineEvent e_) {
        TransactionUpdateEvent e = (TransactionUpdateEvent) e_;
        Transaction tr = e.getTransaction();
        secs.add(tr.getSecurity());
        refreshConfig(tr.getSecurity());
        if (tr.getTime().isBefore(config.getTimeFrom()) || tr.getTime().isAfter(config.getTimeTo())) {
            //Logger.warn("Illegal OrderFlow: sec = " + ofl.getSymbol() + ", time = " + ofl.getTime());
            return;
        }
        if (!Util.isValidQty(tr.getQty()))//px could be 0 for orderType == Market
            return;

        computeSignal(tr);
    }

    @Override
    public void handleOrderBookUpdateEvent(IEngineEvent e_){
        OrderBookUpdateEvent e = (OrderBookUpdateEvent) e_;
        OrderBook b = e.getOrderBook();
        if(Exchange.SS != b.getSecurity().getExchange() || SecurityType.STOCK != b.getSecurityType())
            return;

        secs.add(b.getSecurity());
        refreshConfig(b.getSecurity());
        if(b.getTime().isBefore(config.getTimeFrom()) || b.getTime().isAfter(config.getTimeTo()))
            return;
        if(!Util.isValidLimitPrice(b.getLastPx()) || !Util.isValidQty(b.getVolume()))
            return;
        computeSignal(b);
    }

    private void computeSignal(Transaction tr_) {
        int iTime = DateUtil.time2Int(tr_.getTime());
        for(FeatureHolder<TransactionRateIntervalStat> fh : isHolders) {
            TransactionRateIntervalStat v = fh.getOrCreate(tr_.getSecurity(), iTime);
            if (v != null) {
                OrderBook b = controller.getOrderBook(tr_.getSecurity());
                if(SecurityType.STOCK == b.getSecurityType()) {
                    //compute stock
                    v.handleTransaction(tr_, b, fh);

//                    //compute index
//                    for(Security index : SUPPORTED_INDEX) {
//                        IndexComponent ic = dailyIndexComponent.get(index, tr_.getSecurity());
//                        //not an index component
//                        if(ic == null)
//                            return;
//                        if(!dailyUsedComponents.contains(index, tr_.getSecurity()))
//                            dailyUsedComponents.put(index, tr_.getSecurity(), ic);
//                        OrderFlowIntervalStat vIndex = fh.getOrCreate(index, iTime);
//                        vIndex.handleOrderFlowIndex(tr_, b, ic.getWeight());
//                    }
                }

            } else {
                throw new IllegalDataException("IntervalStat shouldn't be null: time = " + tr_.getTime());
            }
        }
    }

    private void computeSignal(OrderBook b_) {
        int iTime = DateUtil.time2Int(b_.getTime());
        for (FeatureHolder<TransactionRateIntervalStat> fh : isHolders) {
            TransactionRateIntervalStat v = fh.getOrCreate(b_.getSecurity(), iTime);
            if (v != null) {
                OrderBook b = controller.getOrderBook(b_.getSecurity());
                if (SecurityType.STOCK == b.getSecurityType()) {
                    //compute stock
                    v.handleOrderBook(b_);

                } else {
                    throw new IllegalDataException("IntervalStat shouldn't be null: time = " + b_.getTime());
                }
            }
        }
    }


    public List<TransactionRateIntervalStat> getFeatureRange(Transaction tr_, int countingIntervalInSecs_, int lookingBackInSecs_) {
        return getFeatureRange(tr_.getSecurity(), tr_.getTime(), countingIntervalInSecs_, lookingBackInSecs_);
    }

    public List<TransactionRateIntervalStat> getFeatureRange(Security s_, LocalTime t_, int countingIntervalInSecs_, int lookingBackInSecs_) {
        for(FeatureHolder<TransactionRateIntervalStat> fh : isHolders) {
            if(countingIntervalInSecs_ == fh.getBucketDurationInSecs())
                return fh.getOrCreateRange(s_, DateUtil.time2Int(t_), lookingBackInSecs_);
        }
        return TransactionRateIntervalStat.INVALID_RANGE;
    }

    public TransactionRateIntervalStat getFeature(Security s_, LocalTime t_, int countingIntervalInSecs_, int lookingBackInSecs_) {
        for(FeatureHolder<TransactionRateIntervalStat> fh : isHolders) {
            if(countingIntervalInSecs_ == fh.getBucketDurationInSecs())
                return fh.getOrCreate(s_, DateUtil.time2Int(t_), lookingBackInSecs_);
        }
        return TransactionRateIntervalStat.INVALID;
    }

    public TransactionRateIntervalStat getFeature(OrderBook b_, int countingIntervalInSecs_, int lookingBackInSecs_) {
        return getFeature(b_.getSecurity(), b_.getTime(), countingIntervalInSecs_, lookingBackInSecs_);
    }

    @Override
    public SignalType getSignalType(){
        return SignalType.TransactionRateComputationSignal;
    }

    @Override
    public void persistSignal(String p_){
        if(controller.getTransactionDate() == null)
            return;
        p_ += "transactionrate/";

        for (int i : intervals) {
            String path = p_ + "/k" + i + "s/" + DateUtil.date2Str(controller.getTransactionDate());
            File fd = new File(path);
            if(!fd.exists())
                fd.mkdirs();
//            secs.addAll(SUPPORTED_INDEX);
            for(Security sec : secs) {
                if(SecurityType.INDEX != sec.getType() && SecurityType.STOCK != sec.getType())
                    continue;
                File f = new File(path + "/" + sec.getSimpleSymbol().toUpperCase() + ".csv");
                if (f.exists()) {
                    f.delete();
                }
                OrderBook ob = controller.getOrderBook(sec);
                List<String> ss = new ArrayList<>();
                String title = "Time,"+
                        "ratioByNum,ratioByATS,highestInTerm,lowestInTerm,tradePeriod";
                title += SecurityType.INDEX_FUTURE == ob.getSecurityType() ? "PreSettle" : "PreClose";
//                double weight = 1;
//                if(SecurityType.INDEX == sec.getType()) {
//                    weight = dailyUsedComponents.row(sec).values()
//                            .stream()
//                            .mapToDouble(e -> e.getWeight())
//                            .sum();
//                    title += ",applyWeight=" + weight;
//                }

                ss.add(title);
                StringBuilder sb = new StringBuilder();
                for (FeatureHolder<TransactionRateIntervalStat> fh : isHolders) {
                    if (fh.getBucketDurationInSecs() != i)
                        continue;
                    List<TransactionRateIntervalStat> iss = fh.getFeatures(sec);
                    if (iss == null) continue;
                    for (TransactionRateIntervalStat is : iss) {
//                        if(SecurityType.INDEX == sec.getType())
//                            is.applyWeight(weight);
                        sb.append(DateUtil.time2str(is.getInterval().getStart())).append(",");
//                        sb.append(Util.roundQtyNear4Digit(is.getBuyVol())).append(",");
//                        sb.append(Util.roundQtyNear4Digit(is.getSellVol())).append(",");
//                        sb.append(Util.roundQtyNear4Digit(is.getBuyNum())).append(",");
//                        sb.append(Util.roundQtyNear4Digit(is.getSellNum())).append(",");
//                        sb.append(Util.roundQtyNear4Digit(is.getAggressiveBuyVol())).append(",");
//                        sb.append(Util.roundQtyNear4Digit(is.getAggressiveBuyNum())).append(",");
//                        sb.append(Util.roundQtyNear4Digit(is.getAggressiveSellVol())).append(",");
//                        sb.append(Util.roundQtyNear4Digit(is.getAggressiveSellNum())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getRatioByNum())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getRatioByATS())).append(",");
                        sb.append(Util.roundQtyNear2Digit(is.getHighestInTerm())).append(",");
                        sb.append(Util.roundQtyNear2Digit(is.getLowestInTerm())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getTradePeriod())).append(",");
                        sb.append(SecurityType.INDEX_FUTURE == ob.getSecurityType() ? ob.getPreSettle() : ob.getPreClose());
                        ss.add(sb.toString());
                        sb.delete(0, sb.length());
                    }
                }
                try {
                    Files.write(f.toPath(), ss);
                    ss.clear();
                } catch (IOException e_) {
                    throw new RuntimeException(e_);
                }
            }
        }
    }

}
