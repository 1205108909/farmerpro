package com.phenix.farmer.signal.orderflow;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.Controller;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.event.*;
import com.phenix.farmer.signal.*;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.OrderFlow;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 Author: yangfei
 TODO: if no orderbook after close time , should trigger an event to close the alpha
 */
public class OrderFlowComputationSignal extends AbstractSignal implements Orderable {
    private final static transient org.slf4j.Logger Logger = LoggerFactory.getLogger(OrderFlowComputationSignal.class);
    private final PositionBalanceManager pbManager;
    private final Set<Security> secs = new HashSet();
    /**10s 30s 60s*/
    private final List<Integer> intervals = ImmutableList.of(1, 60);
    private Security firstSec = null;
    private LocalTime timeCounter = LocalTime.MIN;
    private final List<FeatureHolder<OrderFlowIntervalStat>> isHolders;
    private final Controller controller;
    private OrderFlowComputationSignalConfig config;
    private final static List<Security> SUPPORTED_INDEX = ImmutableList.of(Security.INDEX_ZZ500);
    //keep the index component used today
    private Table<Security, Security, IndexComponent> dailyIndexComponent;
    //keep the index component used for a day, in case stock data missing
    private Table<Security, Security, IndexComponent> dailyUsedComponents = HashBasedTable.create();

    public OrderFlowComputationSignal(Controller controller_, PositionBalanceManager pbManager_) {
        controller = controller_;
        pbManager = pbManager_;
        isHolders = new ArrayList<>(8);
        buildCache();
    }

    private void buildCache() {
        SessionGroup sgSz = FarmerDataManager.getInstance().getSessionGroup(Exchange.SZ);
        SessionGroup sgSh = FarmerDataManager.getInstance().getSessionGroup(Exchange.SS);
        SessionGroup sgIf = FarmerDataManager.getInstance().getSessionGroup(Exchange.CFFEX);
        for(int i : intervals) {
            FeatureHolder<OrderFlowIntervalStat> isHolder = new FeatureHolder<>(i, OrderFlowIntervalStat.DefaultFactory);
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
        config = getConfig(OrderFlowComputationSignalConfig.class, s_);
    }

    public void cleanup() {
        for (FeatureHolder<OrderFlowIntervalStat> isHolder : isHolders)
            isHolder.cleanup();
        isHolders.clear();
        secs.clear();
        firstSec = null;
        timeCounter = LocalTime.MIN;
        dailyUsedComponents.clear();
        Logger.info(String.format("%s is cleaned up", getClass().getSimpleName()));
    }

    @Override
    public void handleMarketOpenEvent(IEngineEvent e_) {
        LocalDate date = ((MarketOpenEvent) e_).getDate();
        dailyIndexComponent = FarmerDataManager.getInstance().getIndexComponent(date);
    }

    @Override
    public void handleDailySettleEvent(IEngineEvent e_) {
        DailySettleEvent e = (DailySettleEvent) e_;
        reinit();
        Logger.info(getClass().getSimpleName() + "is cleaned up");
    }

    @Override
    public void handleOrderFlowUpdateEvent(IEngineEvent e_) {
        OrderFlowUpdateEvent e = (OrderFlowUpdateEvent) e_;
        OrderFlow ofl = e.getOrderFlow();
        if(Exchange.SZ != ofl.getSecurity().getExchange() || SecurityType.STOCK != ofl.getSecurityType())
            return;

        secs.add(ofl.getSecurity());
        refreshConfig(ofl.getSecurity());
        if (ofl.getTime().isBefore(config.getTimeFrom()) || ofl.getTime().isAfter(config.getTimeTo())) {
            //Logger.warn("Illegal OrderFlow: sec = " + ofl.getSymbol() + ", time = " + ofl.getTime());
            return;
        }
        if (!Util.isValidQty(ofl.getQty()))//px could be 0 for orderType == Market
            return;

        computeSignal(ofl);
    }

    @Override
    public void handleOrderBookUpdateEvent(IEngineEvent e_) {
        OrderBookUpdateEvent e = (OrderBookUpdateEvent) e_;
        OrderBook b = e.getOrderBook();
        if(Exchange.SZ != b.getSecurity().getExchange() || SecurityType.STOCK != b.getSecurityType())
            return;

        secs.add(b.getSecurity());
        refreshConfig(b.getSecurity());
        if(b.getTime().isBefore(config.getTimeFrom()) || b.getTime().isAfter(config.getTimeTo()))
            return;
        if(!Util.isValidLimitPrice(b.getLastPx()) || !Util.isValidQty(b.getVolume()))
            return;
        computeSignal(b);
    }

    private void computeSignal(OrderFlow ofl_) {
        int iTime = DateUtil.time2Int(ofl_.getTime());
        for(FeatureHolder<OrderFlowIntervalStat> fh : isHolders) {
            OrderFlowIntervalStat v = fh.getOrCreate(ofl_.getSecurity(), iTime);
            if (v != null) {
                OrderBook b = controller.getOrderBook(ofl_.getSecurity());
                if(SecurityType.STOCK == b.getSecurityType()) {
                    //compute stock
                    v.handleOrderFlow(ofl_, b);

                    //compute index
                    for(Security index : SUPPORTED_INDEX) {
                        IndexComponent ic = dailyIndexComponent.get(index, ofl_.getSecurity());
                        //not an index component
                        if(ic == null)
                            return;
                        if(!dailyUsedComponents.contains(index, ofl_.getSecurity()))
                            dailyUsedComponents.put(index, ofl_.getSecurity(), ic);
                        OrderFlowIntervalStat vIndex = fh.getOrCreate(index, iTime);
                        vIndex.handleOrderFlowIndex(ofl_, b, ic.getWeight());
                    }
                }

            } else {
                throw new IllegalDataException("IntervalStat shouldn't be null: time = " + ofl_.getTime());
            }
        }
    }

    //only cls px is used in this signal
    private void computeSignal(OrderBook b_) {
        int iTime = DateUtil.time2Int(b_.getTime());
        for(FeatureHolder<OrderFlowIntervalStat> fh : isHolders) {
            OrderFlowIntervalStat v = fh.getOrCreate(b_.getSecurity(), iTime);
            if (v != null) {
                v.handleOrderBook(b_);
            } else {
                throw new IllegalDataException("IntervalStat shouldn't be null: time = " + b_.getTime());
            }
        }
    }

    public List<OrderFlowIntervalStat> getFeatureRange(OrderFlow b_, int countingIntervalInSecs_, int lookingBackInSecs_) {
        return getFeatureRange(b_.getSecurity(), b_.getTime(), countingIntervalInSecs_, lookingBackInSecs_);
    }

    public List<OrderFlowIntervalStat> getFeatureRange(Security s_, LocalTime t_, int countingIntervalInSecs_, int lookingBackInSecs_) {
        for(FeatureHolder<OrderFlowIntervalStat> fh : isHolders) {
            if(countingIntervalInSecs_ == fh.getBucketDurationInSecs())
                return fh.getOrCreateRange(s_, DateUtil.time2Int(t_), lookingBackInSecs_);
        }
        return OrderFlowIntervalStat.INVALID_RANGE;
    }

    public OrderFlowIntervalStat getFeature(Security s_, LocalTime t_, int countingIntervalInSecs_, int lookingBackInSecs_) {
        for(FeatureHolder<OrderFlowIntervalStat> fh : isHolders) {
            if(countingIntervalInSecs_ == fh.getBucketDurationInSecs())
                return fh.getOrCreate(s_, DateUtil.time2Int(t_), lookingBackInSecs_);
        }
        return OrderFlowIntervalStat.INVALID;
    }

    public OrderFlowIntervalStat getFeature(OrderBook b_, int countingIntervalInSecs_, int lookingBackInSecs_) {
        return getFeature(b_.getSecurity(), b_.getTime(), countingIntervalInSecs_, lookingBackInSecs_);
    }

    @Override
    public SignalType getSignalType() {
        return SignalType.OrderFlowComputationSignal;
    }

    @Override
    public void persistSignal(String p_) {
        //no security is dispatched on this controller
        if(controller.getTransactionDate() == null)
            return;

//        p_ = "e:/data/orderflow";
        p_ += "orderflow/";
        for (int i : intervals) {
            String path = p_ + "/k" + i + "s/" + DateUtil.date2Str(controller.getTransactionDate());
            File fd = new File(path);
            if(!fd.exists())
                fd.mkdirs();
            secs.addAll(SUPPORTED_INDEX);
            for(Security sec : secs) {
                if(SecurityType.INDEX != sec.getType() && SecurityType.STOCK != sec.getType())
                    continue;
                if(SecurityType.INDEX == sec.getType() && config.isIgnoreIndex())//index data always wrong under multiple controller
                    continue;
                File f = new File(path + "/" + sec.getSymbol() + ".csv");
                if (f.exists()) {
                    f.delete();
                }
                OrderBook ob = controller.getOrderBook(sec);
                List<String> ss = new ArrayList<>();
                String title = "Time,limitAskVol,limitBidVol,limitAskNum,limitBidNum,marketAskVol,marketBidVol,marketAskNum," +
                        "marketBidNum,aggressiveLimitAskVol,aggressiveLimitAskNum,aggressiveLimitBidVol,aggressiveLimitBidNum,Close,";
                title += SecurityType.INDEX_FUTURE == ob.getSecurityType() ? "PreSettle" : "PreClose";
                double weight = 1;
                if(SecurityType.INDEX == sec.getType()) {
                    weight = dailyUsedComponents.row(sec).values()
                            .stream()
                            .mapToDouble(e -> e.getWeight())
                            .sum();
                    title += ",applyWeight=" + weight;
                }

                ss.add(title);
                StringBuilder sb = new StringBuilder();
                for (FeatureHolder<OrderFlowIntervalStat> fh : isHolders) {
                    if (fh.getBucketDurationInSecs() != i)
                        continue;
                    List<OrderFlowIntervalStat> iss = fh.getFeatures(sec);
                    if (iss == null) continue;
                    for (OrderFlowIntervalStat is : iss) {
                        if(SecurityType.INDEX == sec.getType())
                            is.applyWeight(weight);
                        sb.append(DateUtil.time2str(is.getInterval().getStart())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getLimitAskVol())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getLimitBidVol())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getLimitAskNum())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getLimitBidNum())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getMarketAskVol())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getMarketBidVol())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getMarketAskNum())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getMarketBidNum())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getAggressiveLimitAskVol())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getAggressiveLimitAskNum())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getAggressiveLimitBidVol())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getAggressiveLimitBidNum())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getCls())).append(",");
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

    //TODO: filter
}
