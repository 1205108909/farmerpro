package com.phenix.farmer.signal.transactionrate;

import com.google.common.collect.ImmutableList;
import com.phenix.data.*;
import com.phenix.farmer.Controller;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.event.IEngineEvent;
import com.phenix.farmer.event.TransactionUpdateEvent;
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
import java.util.List;
import java.util.stream.Collectors;

import static com.phenix.util.DateUtil.TIME_HHMMSS_SSS2;

public class TransactionRateMomentumSignal extends OrderGenerationSignal implements Orderable {
    private final static transient org.slf4j.Logger Logger = LoggerFactory.getLogger(TransactionRateMomentumSignal.class);

    private final PositionBalanceManager pbManager;
    private final List<Integer> intervals = ImmutableList.of(1, 30, 60);
    private Security firstSec = null;
    private LocalTime timeCounter = LocalTime.MIN;
    private final List<FeatureHolder<TransactionRateIntervalStat>> isHolders;
    private final Controller controller;
    private TransactionRateComputationSignal trComputationSignal;

    public TransactionRateMomentumSignal(Controller controller_, PositionBalanceManager pbManager_){
        super(controller_, pbManager_);
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
        buildCache();
        Logger.info(String.format("%s is inited", getClass().getSimpleName()));
    }

    @Override
    public void refreshConfig(Security s_) {
        config = getConfig(TransactionRateMomentumSignalConfig.class, s_);
        trComputationSignal = controller.findSignal(TransactionRateComputationSignal.class);
    }

    public void cleanup() {
        for (FeatureHolder<TransactionRateIntervalStat> isHolder : isHolders)
            isHolder.cleanup();
        isHolders.clear();
        secs.clear();
        firstSec = null;
        timeCounter = LocalTime.MIN;
        super.cleanup();
        Logger.info(String.format("%s is cleaned up", getClass().getSimpleName()));
    }


    @Override
    public void handleTransactionUpdateEvent(IEngineEvent e_){
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

        TransactionRateIntervalStat is = trComputationSignal.getFeature(tr.getSecurity(), tr.getTime(), 1, 0);
        double[] ratio = new double[3];
        ratio[0] = is.getRatioByNum();
        ratio[1] = is.getHighestInTerm();
        ratio[2] = is.getLowestInTerm();

        OrderBook ob = controller.getOrderBook(tr.getSecurity());
        //close order first
        List<Order> orders = unclosedOrder.get(tr.getSecurity());
        boolean flag = false;

        for (Order o : orders) {
            if(Direction.LONG == o.getDirection() && (ratio[1] <= -6 )) {
                flag = true;
                String reason = Util.roundQtyNear4Digit(ratio[0]) + "|" +
                        Util.roundQtyNear4Digit(ratio[1]) + "|" +
                        Util.roundQtyNear2Digit(tr.getPrice()) + "|" +
                        Util.roundQtyNear2Digit(ob.getBidPrice(0)) + "|" +
                        (int)(ob.getBidQty(0));
                createClosePosOrder(o, false, ob, tr.getDateTime(), reason);
            }
            else if(Direction.SHORT == o.getDirection() && (ratio[1] >= 6 )) {//修改了平仓规则
                flag = true;
                String reason = Util.roundQtyNear4Digit(ratio[0]) + "|" +
                        Util.roundQtyNear4Digit(ratio[1]) + "|" +
                        Util.roundQtyNear2Digit(tr.getPrice()) + "|" +
                        Util.roundQtyNear2Digit(ob.getAskPrice(0)) + "|" +
                        (int)(ob.getAskQty(0));
                createClosePosOrder(o, false, ob, tr.getDateTime(), reason);
            }
        }
        if(flag) return;
        flag = continueCheck(ob);
        if(!flag) return;

        if (tr.getTime().isAfter(config.getTimeStopOpenPosition())) {
            return;
        }
//        if (ofl.getTime().toSecondOfDay() - recentOrderPlacementTime.toSecondOfDay() <= config.getOrderPlacementTimeThresholdInSecs()) {
//            return;
//        }
        if(!tr.getTime().isAfter(TIME_094000))
            return;
//        if(ofl.getTime().isBefore(TIME_143000))
//            return;
        // qty >= 5 * 20000
        //同时使用ratio[0]和ratio[1]进行判断
        if (ratio[1] >= 6
//                && ratio[0] >= 3
                && 0 == unclosedOrder.get(tr.getSecurity()).size()
                && (tr.getPrice() > ob.getAskPrice(0) + 0.0001) //有效
                && (ob.getLastPx()/ob.getPreClose() < 1.095)
                && (ob.getLastPx()/ob.getPreClose() > 0.905)
                && (tr.getPrice() >= ratio[2])// 有效，是否可修改为 大于一定范围如tr.getPrice() >= ratio[2] * (1 - 0.001)
        ) {
            String reason = Util.roundQtyNear4Digit(ratio[0]) + "|" +
                    Util.roundQtyNear4Digit(ratio[1]) + "|" +
                    Util.roundQtyNear2Digit(tr.getPrice()) + "|" +
                    Util.roundQtyNear2Digit(ob.getAskPrice(0)) + "|" +
                    (int)(ob.getAskQty(0)) + "|" +
                    Util.roundQtyNear2Digit(ratio[2]);
            createOrder(ob, Direction.LONG, tr.getDateTime(), false, reason);
        }
        if (ratio[1] <= -6
//                && ratio[0] <= -3
                && 0 == unclosedOrder.get(tr.getSecurity()).size()
                && (tr.getPrice() < ob.getBidPrice(0) - 0.0001)
                && (ob.getLastPx()/ob.getPreClose() < 1.095)
                && (ob.getLastPx()/ob.getPreClose() > 0.905)
                && (tr.getPrice() <= ratio[2])
        ) {
            String reason = Util.roundQtyNear4Digit(ratio[0]) + "|" +
                    Util.roundQtyNear4Digit(ratio[1]) + "|" +
                    Util.roundQtyNear2Digit(tr.getPrice()) + "|" +
                    Util.roundQtyNear2Digit(ob.getBidPrice(0)) + "|" +
                    (int)(ob.getBidQty(0)) + "|" +
                    Util.roundQtyNear2Digit(ratio[2]);
            createOrder(ob, Direction.SHORT, tr.getDateTime(), false, reason);
        }
    }

    private double[] computeSignal(Transaction tr_) {
        double[] result = new double[4];
        List<TransactionRateIntervalStat> stats = trComputationSignal.getFeatureRange(tr_, 1, 900);
        double longTermSellRate = 0, longTermBuyRate = 0, shortTermSellRate = 0, shortTermBuyRate = 0;
        double longTermBuyRateByVol = 0, longTermSellRateByVol = 0, shortTermBuyRateByVol = 0, shortTermSellRateByVol = 0;
        double highPrice = Double.MIN_VALUE, lowPrice = Double.MAX_VALUE;
        int size = stats.size();
        for(int i = 0; i < size; i++) {//count 900s
            TransactionRateIntervalStat is = stats.get(i);
            if(is.isReady()) {
                longTermSellRate += is.getSellNum();
                longTermBuyRate += is.getBuyNum();
                longTermBuyRateByVol += is.getBuyVol();
                longTermSellRateByVol += is.getSellVol();
                highPrice = Math.max(highPrice, is.getHigh());
                lowPrice = Math.min(lowPrice, is.getLow());
            }
        }
        longTermSellRate /= size;
        longTermBuyRate /= size;
        size = 10;
        for(int i = 1; i <= size; i++) {//count 10s
            TransactionRateIntervalStat is = stats.get(stats.size() - i);
            if(is.isReady()) {
                shortTermSellRate += is.getSellNum();
                shortTermBuyRate += is.getBuyNum();
                shortTermSellRateByVol += is.getSellVol();
                shortTermBuyRateByVol += is.getBuyVol();
            }
        }
        shortTermSellRate /= size;
        shortTermBuyRate /= size;
        result[0] = Util.roundQtyNear4Digit((shortTermBuyRate - longTermBuyRate) - (shortTermSellRate - longTermSellRate));
        result[1] = highPrice;
        result[2] = lowPrice;
        return result;
    }

    @Override
    public void handlePattern(OrderBook b_) {

    }

    @Override
    public double computeMaxRevert(OrderBook b_, Direction d_) {
        double maxRevert = 0.1;
        if(b_.getTime().isAfter(TIME_143000))
            maxRevert = 0.05;
        return Util.roundQtyNear4Digit(maxRevert * b_.getPreClose());
    }

    @Override
    public double computeOpenQty(OrderBook b_, Direction d_) {
        return 100;
    }

    @Override
    public SignalState getOrCreateSignalState(Security s_) {
        SignalState state = signalState.get(s_);
        if(state == null) {
            state = new TransactionRateComputationState();
            signalState.put(s_, state);
        }
        return state;
    }

    private final static class TransactionRateComputationState extends SignalState {
        TransactionRateComputationState(){
            super();
        }
    }

    @Override
    public SignalType getSignalType(){
        return SignalType.TransactionRateMomentumSignal;
    }

    @Override
    public void persistSignal(String p_){
        if(controller.getTransactionDate() == null)
            return;

        p_ += "transactionrate/orders";
        File f = new File(p_ + "/" + DateUtil.date2Str(controller.getTransactionDate()));
        if(!f.exists())
            f.mkdirs();
        for (Security sec : secs) {
            List<String> sOrders = new ArrayList<>();
            sOrders.add("OTime,OValue,ODirection,OReason,OQty,OSpread,OPrice");
            List<Order> orders = id2ClosePosOrder.values()
                    .stream()
                    .filter(e -> e.getSec().equals(sec))
                    .collect(Collectors.toList());
            orders.addAll(id2Order.values()
                    .stream()
                    .filter(e -> e.getSec().equals(sec))
                    .collect(Collectors.toList()));
            orders.sort(Order.ORDER_COMPARATOR_BY_PENDINGTIME);
            for (Order o : orders) {
                String d = o.getDirection() == Direction.LONG ? "L" : "S";
                d = o.isClosePosOrder() ? "C" + d : d;
                OrderBook ob = controller.getOrderBook(o.getSec());
                double v = Util.roundQtyNear6Digit((o.getPrice() / ob.getPreClose() - 1));
                String s = DateUtil.time2str(o.getTime(), TIME_HHMMSS_SSS2) + "," + v + "," + d + "," + (o.getPlacementReason() == null ? "" : o.getPlacementReason()) + "," + o.getOrderQty();
                sOrders.add(s + "," + o.getSpread() + "," + o.getPrice());

            }

            //persist order symbol by symbol
            File save = new File(f.getAbsolutePath() + "/" + sec.getSymbol() + ".csv");
            try {
                Files.write(save.toPath(), sOrders);
            } catch (IOException e_) {
                throw new RuntimeException(e_);
            }
            orders.clear();
            sOrders.clear();
        }
    }
}
