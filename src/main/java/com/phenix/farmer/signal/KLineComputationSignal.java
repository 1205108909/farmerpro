package com.phenix.farmer.signal;

import com.google.common.collect.ImmutableList;
import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.Controller;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.data.IntervalStat;
import com.phenix.farmer.event.DailySettleEvent;
import com.phenix.farmer.event.IEngineEvent;
import com.phenix.farmer.event.OrderBookUpdateEvent;
import com.phenix.farmer.event.TransactionUpdateEvent;
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

/**
 * Author: yangfei
 */
public class KLineComputationSignal extends AbstractSignal {
    private final static transient org.slf4j.Logger Logger = LoggerFactory.getLogger(KLineComputationSignal.class);

    private final PositionBalanceManager pbManager;
    private final Set<Security> secs = new HashSet();
    private final List<FeatureHolder<IntervalStat>> isHolders;
    private final Controller controller;
    boolean future2IndexPxValidationCheck;
    /**
     * 10s 30s 60s
     */
    private List<Integer> intervals = ImmutableList.of(30, 60);
    private KLineComputationSignalConfig config;

    private final LocalTime timeFrom;
    private final LocalTime timeTo;

    public KLineComputationSignal(Controller controller_, PositionBalanceManager pbManager_) {
        controller = controller_;
        pbManager = pbManager_;
        isHolders = new ArrayList<>(8);
        buildCache();
        timeFrom = DateUtil.getTime2("091500000");
        timeTo = DateUtil.getTime2("150100000");
    }

    private void buildCache() {
        SessionGroup sgSz = FarmerDataManager.getInstance().getSessionGroup(Exchange.SZ);
        SessionGroup sgSh = FarmerDataManager.getInstance().getSessionGroup(Exchange.SS);
        SessionGroup sgIf = FarmerDataManager.getInstance().getSessionGroup(Exchange.CFFEX);
        for (int i : intervals) {
            FeatureHolder<IntervalStat> isHolder = new FeatureHolder<>(i, IntervalStat.DefaultFactory);
            isHolder.addTimeSlots(Exchange.SS, sgSh);
            isHolder.addTimeSlots(Exchange.SZ, sgSz);
            isHolder.addTimeSlots(Exchange.CFFEX, sgIf);
            isHolder.addTimeSlots(Exchange.SI, sgSz);
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

    private void refreshConfig(Security s_) {
        config = getConfig(KLineComputationSignalConfig.class, s_);
    }

    public void cleanup() {
        for (FeatureHolder<IntervalStat> isHolder : isHolders)
            isHolder.cleanup();
        isHolders.clear();
        secs.clear();
        Logger.info(String.format("%s is cleaned up", getClass().getSimpleName()));
    }

    @Override
    public void handleDailySettleEvent(IEngineEvent e_) {
        DailySettleEvent e = (DailySettleEvent) e_;
        reinit();
        Logger.info(getClass().getSimpleName() + "is cleaned up");
    }

    @Override
    public void handleOrderBookUpdateEvent(IEngineEvent event_) {
        OrderBookUpdateEvent e = (OrderBookUpdateEvent) event_;
        OrderBook b = e.getOrderBook();
        secs.add(b.getSecurity());
//        refreshConfig(b.getSecurity());

        if (b.getTime().isBefore(timeFrom) || b.getTime().isAfter(timeTo)) {
//            Logger.warn("Illegal orderbook: sec = " + b.getSymbol() + ", time = " + b.getTime());
            return;
        }
        if (!Util.isValidLimitPrice(b.getLastPx())) {
            return;
        }

        //set future-index px flag
        future2IndexPxValidationCheck = false;
        if (SecurityType.INDEX == b.getSecurityType()) {
            IndexFuture future = getIndexFuture(b);
            if (future != null && controller.future2IndexPxValidationCheck(b.getSecurity(), future, 8)) {
                future2IndexPxValidationCheck = true;
            }
        }

        computeSignal(b);
    }

    @Override
    public void handleTransactionUpdateEvent(IEngineEvent event_){
        TransactionUpdateEvent e = (TransactionUpdateEvent) event_;
        Transaction t = e.getTransaction();
        secs.add(t.getSecurity());
        if (t.getTime().isBefore(timeFrom) || t.getTime().isAfter(timeTo)) {
            return;
        }
        if (!Util.isValidLimitPrice(t.getPrice())){
            return;
        }
        computeSignal(t);
    }

    private void computeSignal(OrderBook b_) {
        int iTime = DateUtil.time2Int(b_.getTime());
        for (FeatureHolder<IntervalStat> fh : isHolders) {
            IntervalStat v = fh.getOrCreate(b_.getSecurity(), iTime);
            if (v != null) {
                v.handleOrderBook(b_);
            } else {
                throw new IllegalDataException("IntervalStat shouldn't be null: time = " + b_.getTime());
            }
        }
    }

    private void computeSignal(Transaction t_){
        int iTime = DateUtil.time2Int(t_.getTime());
        for(FeatureHolder<IntervalStat>fh : isHolders){
            IntervalStat v = fh.getOrCreate(t_.getSecurity(), iTime);
            if (null != v){
                v.handleTransaction(t_);
            }
        }

    }

    @Override
    public SignalType getSignalType() {
        return SignalType.KLineComputationSignal;
    }

    @Override
    public void persistSignal(String p_) {
        for (int i : intervals) {
            String path = p_ + "/k" + i + "s/" + DateUtil.date2Str(controller.getTransactionDate());
            File fd = new File(path);
            if (!fd.exists())
                fd.mkdirs();

            for (Security sec : secs) {
                if (SecurityType.STOCK != sec.getType() && SecurityType.INDEX != sec.getType())
                    continue;
                File f = new File(path + "/" + sec.getSymbol().toLowerCase() + ".csv");
                if (f.exists()) {
                    f.delete();
                }
                Transaction tr = controller.getTransaction(sec);
                OrderBook ob = controller.getOrderBook(sec);
                List<String> ss = new ArrayList<>();
                String title = "Time,Open,High,Low,Close,Avg,Volume,Turnover,";
                title += SecurityType.INDEX_FUTURE == tr.getSecurityType() ? "PreSettle" : "PreClose";
                ss.add(title);
                StringBuilder sb = new StringBuilder();
                for (FeatureHolder<IntervalStat> fh : isHolders) {
                    if (fh.getBucketDurationInSecs() != i)
                        continue;
                    List<IntervalStat> iss = fh.getFeatures(sec);
                    if (iss == null) continue;
                    double prePrice = SecurityType.INDEX_FUTURE == ob.getSecurityType() ? ob.getPreSettle() : ob.getPreClose();
                    prePrice = Util.roundQtyNear4Digit(prePrice);
                    for (IntervalStat is : iss) {
                        //todo:检验数据是否异常，如果异常则取上一笔close价
                        sb.append(DateUtil.time2str(is.getInterval().getStart())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getOpen())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getHigh())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getLow())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getCls())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getAvg())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getVol())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getTurnOver())).append(",");
                        sb.append(prePrice);
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

    private void stored2HDF(List<IntervalStat> iis, String path){
//todo
    }
}
