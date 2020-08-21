package com.phenix.farmer.signal.orderflow;

import com.google.common.collect.ImmutableList;
import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.Controller;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.event.IEngineEvent;
import com.phenix.farmer.event.OrderFlowUpdateEvent;
import com.phenix.farmer.signal.*;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.OrderFlow;
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

/**
 Author: yangfei
 */
public class OrderFlowMomentumSignal extends OrderGenerationSignal implements Orderable {
    private final static transient org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OrderFlowMomentumSignal.class);

    private final PositionBalanceManager pbManager;
    /**10s 30s 60s*/
    private final List<Integer> intervals = ImmutableList.of(1);
    private Security firstSec = null;
    private LocalTime timeCounter = LocalTime.MIN;
    private final List<FeatureHolder<OrderFlowIntervalStat>> isHolders;
    private final Controller controller;
    private OrderFlowComputationSignal oflComputationSignal;
    private double qty = 0;
    private OrderFlowMomHighLowLimitFilter oflmHighLowLimitFilter = new OrderFlowMomHighLowLimitFilter();
    private List<AbstractSignalFilter> filters = ImmutableList.of(oflmHighLowLimitFilter);

    public OrderFlowMomentumSignal(Controller controller_, PositionBalanceManager pbManager_) {
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
        buildCache();
        LOGGER.info(String.format("%s is inited", getClass().getSimpleName()));
    }

    @Override
    public void refreshConfig(Security s_) {
        config = getConfig(OrderFlowMomentumSignalConfig.class, s_);
        oflComputationSignal = controller.findSignal(OrderFlowComputationSignal.class);
    }

    public void cleanup() {
        for (FeatureHolder<OrderFlowIntervalStat> isHolder : isHolders)
            isHolder.cleanup();
        isHolders.clear();
        secs.clear();
        firstSec = null;
        timeCounter = LocalTime.MIN;
        super.cleanup();
        LOGGER.info(String.format("%s is cleaned up", getClass().getSimpleName()));
    }

    @Override
    public void handleOrderFlowUpdateEvent(IEngineEvent e_) {
        OrderFlowUpdateEvent e = (OrderFlowUpdateEvent) e_;
        OrderFlow ofl = e.getOrderFlow();
        OrderBook ob = controller.getOrderBook(ofl.getSecurity());
        if (null != ob && !isInterestSecurity(ofl.getSecurity(), ob.getPreClose())){
            return;
        }

        secs.add(ofl.getSecurity());
        refreshConfig(ofl.getSecurity());
        if (ofl.getTime().isBefore(config.getTimeFrom()) || ofl.getTime().isAfter(config.getTimeTo())) {
            //LOGGER.warn("Illegal OrderFlow: sec = " + ofl.getSymbol() + ", time = " + ofl.getTime());
            return;
        }
        if (!Util.isValidQty(ofl.getQty()))//px could be 0 for orderType == Market
            return;

        double ratio = computeSignal(ofl);
        //close order first
        List<Order> orders = unclosedOrder.get(ofl.getSecurity());
        boolean flag = false;
        for (Order o : orders) {
            if(Direction.LONG == o.getDirection() && ratio <= -3) {
                flag = true;
                createClosePosOrder(o, false, ob, ofl.getDateTime(), "close: ratio <= -3");
            }
            else if(Direction.SHORT == o.getDirection() && ratio >= 3) {
                flag = true;
                createClosePosOrder(o, false, ob, ofl.getDateTime(), "close: ratio >= 3");
            }
        }
        if(flag) return;
        flag = continueCheck(ob);
        if(!flag) return;

        if (ofl.getTime().isAfter(config.getTimeStopOpenPosition())) {
            return;
        }
//        if (ofl.getTime().toSecondOfDay() - recentOrderPlacementTime.toSecondOfDay() <= config.getOrderPlacementTimeThresholdInSecs()) {
//            return;
//        }
        if(!ofl.getTime().isAfter(TIME_094000))
            return;
//        if(ofl.getTime().isBefore(TIME_143000))
//            return;
//         qty >= 5 * 20000
        String prefix = "Security:" + ob.getSymbol() + ", ";
        if (ratio >= 3 && unclosedOrder.get(ofl.getSecurity()).size() == 0) {
            StringBuilder sb = new StringBuilder();
            for(AbstractSignalFilter f : filters) {
                if(f.filter(ob, Direction.LONG, sb)) {
                    LOGGER.info(prefix + " filtered out by " + f.getClass().getName() + " with message:[" + sb.toString() + "], skip");
                    return;
                }
            }
            createOrder(ob, Direction.LONG, ofl.getDateTime(), false, "ratio >= 3");
        } else if (ratio <= -3 && unclosedOrder.get(ofl.getSecurity()).size() == 0) {
            StringBuilder sb = new StringBuilder();
            for(AbstractSignalFilter f : filters) {
                if(f.filter(ob, Direction.SHORT, sb)) {
                    LOGGER.info(prefix + " filtered out by " + f.getClass().getName() + " with message:[" + sb.toString() + "], skip");
                    return;
                }
            }
            createOrder(ob, Direction.SHORT, ofl.getDateTime(), false, "ratio <= -3");
        }
    }

    private double computeSignal(OrderFlow ofl_) {
        List<OrderFlowIntervalStat> stats = oflComputationSignal.getFeatureRange(ofl_, 1, 900);
        double long_ask_rate_mean = 0, long_bid_rate_mean = 0, short_ask_rate_mean = 0, short_bid_rate_mean = 0;
        double long_agressiveAskQty = 0, long_agreesiveBidQty = 0, short_agressiveAskQty = 0, short_agreesiveBidQty = 0;
        int size = 0;
        for(int i = 0; i < stats.size(); i++) {//count 900s
            OrderFlowIntervalStat ofl = stats.get(i);
            if(ofl.isReady()) {
                long_ask_rate_mean += ofl.getLimitAskNum();
                long_bid_rate_mean += ofl.getLimitBidNum();
                long_agreesiveBidQty += ofl.getAggressiveLimitBidVol();
                long_agressiveAskQty += ofl.getAggressiveLimitAskVol();
            }
            size++;
        }
        long_ask_rate_mean = long_ask_rate_mean / size;
        long_bid_rate_mean = long_bid_rate_mean / size;
        size = 0;
        for(int i = 1; i <= 10; i++) {//count 10s
            OrderFlowIntervalStat ofl = stats.get(stats.size() - i);
            if(ofl.isReady()) {
                short_ask_rate_mean += ofl.getLimitAskNum();
                short_bid_rate_mean += ofl.getLimitBidNum();
            }
            size++;
        }
        short_ask_rate_mean = short_ask_rate_mean / size;
        short_bid_rate_mean = short_bid_rate_mean / size;
        for(int i = 1; i <= 30; i++) {
            OrderFlowIntervalStat ofl = stats.get(stats.size() - i);
            if(ofl.isReady()) {
                short_agreesiveBidQty += ofl.getAggressiveLimitBidVol();
                short_agressiveAskQty += ofl.getAggressiveLimitAskVol();
            }
        }
        qty = short_agreesiveBidQty - short_agressiveAskQty;
        double res = Util.roundQtyNear4Digit((short_bid_rate_mean - long_bid_rate_mean) - (short_ask_rate_mean - long_ask_rate_mean));

        //only cls is used, do not use the volume!!!
        int iTime = DateUtil.time2Int(ofl_.getTime());
        OrderBook ob = controller.getOrderBook(ofl_.getSecurity());
        for(FeatureHolder<OrderFlowIntervalStat> fh : isHolders) {
            OrderFlowIntervalStat v = fh.getOrCreate(ofl_.getSecurity(), iTime);
            v.handleFeature(res, ob.getVolume(), ofl_.getTime());
        }

        return res;
    }

    @Override
    public void handlePattern(OrderBook b_) {
        //do nothing on purpose
    }

    @Override
    public double computeMaxRevert(OrderBook b_, Direction d_) {
        double maxRevert = 0.01;
        if(b_.getTime().isAfter(TIME_143000))
            maxRevert = 0.005;

        double maxRevert2 = computeOrderTimeCheck(b_);
        maxRevert = Math.min(maxRevert, maxRevert2);
        return Util.roundQtyNear4Digit(maxRevert * b_.getPreClose());
    }

    private double computeOrderTimeCheck(OrderBook b_) {
        double maxRevert = 0.01;
        if(recentUnclosedOrder == null)//no order placed
            return maxRevert;
        if(recentOrderPlacementTime.toSecondOfDay() >= b_.getTimeInSecs())
            return maxRevert;
        int timeDurationInSecs = (int)DateUtil.tradingTimeDiffInSecs(recentOrderPlacementTime.toSecondOfDay(), b_.getTimeInSecs());
        if(timeDurationInSecs >= 60 * 5) {
            //double tickSize = HunterDataManager.getTickSize(b_.getSecurity());
            if(Direction.LONG == recentUnclosedOrder.getDirection()) {
                if(b_.getLastPx() < recentUnclosedOrder.getRefPx())
                    maxRevert = -0.0001;
            } else if(Direction.SHORT == recentUnclosedOrder.getDirection()) {
                if(b_.getLastPx() > recentUnclosedOrder.getRefPx())
                    maxRevert = -0.0001;
            }
        }

        return maxRevert;
    }

    @Override
    public double computeOpenQty(OrderBook b_, Direction d_) {
        return 100;
    }
    @Override
    public SignalState getOrCreateSignalState(Security s_) {
        SignalState state = signalState.get(s_);
        if(state == null) {
            state = new OrderFlowComputationSignalState();
            signalState.put(s_, state);
        }
        return state;
    }
    private final static class OrderFlowComputationSignalState extends SignalState {
        OrderFlowComputationSignalState() {
            super();
        }
    }

    @Override
    public SignalType getSignalType() {
        return SignalType.OrderFlowMomentumSignal;
    }

    @Override
    public void publishOrderInfo(Order o_) {
        OrderFlowMomentumSignalConfig cfg = (OrderFlowMomentumSignalConfig)config;
        if(SignalMode.ALPHA != cfg.getMode())
            throw new IllegalDataException("currently only suppport Alpha Mode");
        FarmerDataManager.getInstance().publishAlphaOrderInfo(o_);
        List<Order> longOrders = id2Order.values()
                .stream()
                .filter(e -> e.getSec().equals(o_.getSec()))
                .filter(e -> e.getDirection().equals(Direction.LONG))
                .collect(Collectors.toList());
        List<Order> shortOrders = id2Order.values()
                .stream()
                .filter(e -> e.getSec().equals(o_.getSec()))
                .filter(e -> e.getDirection().equals(Direction.SHORT))
                .collect(Collectors.toList());
        FarmerDataManager.getInstance().publishAlphaStateInfo(o_, longOrders.size(), shortOrders.size());
    }

    @Override
    public void persistSignal(String p_) {
        persistOrder(p_);
//        _persistSignal(p_);
    }

    public void persistOrder(String p_) {
        if(controller.getTransactionDate() == null)
            return;
        OrderFlowMomentumSignalConfig cfg = (OrderFlowMomentumSignalConfig)config;

        p_ += "/orderFlow/orders";
        File f = new File(p_ + "/" + DateUtil.date2Str(controller.getTransactionDate()));
        if(!f.exists())
            f.mkdirs();
        for (Security sec : secs) {
            if(SecurityType.INDEX == sec.getType() && cfg.isIgnoreIndex()
                    && FarmerDataManager.getInstance().isInterestedInSecurity(SignalType.OrderFlowMomentumSignal, sec))//index data always wrong under multiple controller
                continue;
            List<String> sOrders = new ArrayList<>();
            sOrders.add("OTime,OValue,ODirection,OReason,OQty,OSpread");
            List<Order> orders = id2Order.values()
                    .stream()
                    .filter(e -> e.getSec().equals(sec))
                    .collect(Collectors.toList());
            orders.addAll(id2ClosePosOrder.values()
                    .stream()
                    .filter(e -> e.getSec().equals(sec))
                    .collect(Collectors.toList()));
            orders.sort(Order.ORDER_COMPARATOR_BY_PENDINGTIME);
            for (Order o : orders) {
                String d = o.getDirection() == Direction.LONG ? "L" : "S";
                d = o.isClosePosOrder() ? "C" + d : d;
                OrderBook ob = controller.getOrderBook(o.getSec());
                double v = Util.roundQtyNear6Digit((o.getPrice() / ob.getPreClose() - 1));
                String time2Str = DateUtil.time2str(o.getTime(), DateUtil.TIME_HHMMSS_SSS2);
                String s = time2Str + "," + v + "," + d + "," + (o.getPlacementReason() == null ? "" : o.getPlacementReason()) + "," + o.getOrderQty();
                sOrders.add(s + "," + o.getSpread());
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

    private void _persistSignal(String p_) {
        if(controller.getTransactionDate() == null)
            return;

        OrderFlowMomentumSignalConfig cfg = (OrderFlowMomentumSignalConfig)config;
        p_ += "orderFlowMon/";
        for (int i : intervals) {
            String path = p_ + "/k" + i + "s/" + DateUtil.date2Str(controller.getTransactionDate());
            File fd = new File(path);
            if(!fd.exists())
                fd.mkdirs();

            for(Security sec : secs) {
                if(SecurityType.INDEX != sec.getType() && SecurityType.STOCK != sec.getType())
                    continue;
                if(SecurityType.INDEX == sec.getType() && cfg.isIgnoreIndex())//index data always wrong under multiple controller
                    continue;
                File f = new File(path + "/" + sec.getSymbol() + ".csv");
                if (f.exists()) {
                    f.delete();
                }
                OrderBook ob = controller.getOrderBook(sec);
                List<String> ss = new ArrayList<>();
                String title = "Time,Open,High,Low,Close,Avg,Volume,Turnover,";
                title += SecurityType.INDEX_FUTURE == ob.getSecurityType() ? "PreSettle" : "PreClose";
                ss.add(title);
                StringBuilder sb = new StringBuilder();
                for (FeatureHolder<OrderFlowIntervalStat> fh : isHolders) {
                    if (fh.getBucketDurationInSecs() != i)
                        continue;
                    List<OrderFlowIntervalStat> iss = fh.getFeatures(sec);
                    if (iss == null) continue;
                    for (IntervalStat is : iss) {
                        sb.append(DateUtil.time2str(is.getInterval().getStart())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getOpen())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getHigh())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getLow())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getCls())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getAvg())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getVol())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getVol())).append(",");
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

    private boolean isInterestSecurity(Security sec_, double pc_){
        if((Exchange.SZ == sec_.getExchange() && SecurityType.STOCK == sec_.getType()) || pc_ < 4){
            return true;
        } else {
            return false;
        }
    }

    private class OrderFlowMomHighLowLimitFilter extends AbstractSignalFilter {
        @Override
        public boolean filter(OrderBook b_, Direction d_, StringBuilder msg_) {
            OrderSide side = Direction.LONG == d_ ? OrderSide.BUY : OrderSide.SELL;
            double px = b_.getFarPrice(side);
            if(!Util.isValidLimitPrice(px)) {
                px = b_.getNearPrice(side);
                if(!Util.isValidLimitPrice(px))
                    return false;
            }

            //1.0 do not forecast in case 0.095
            int sign = OrderSide.BUY == side ? 1 : -1;
            double tickSize = FarmerDataManager.getInstance().getTickSize(b_.getSecurity());
            px = Util.roundQtyNear4Digit(px + sign * config.getNumberOfTicksHitFar() * tickSize);
            double last2Prev = Math.abs(Util.roundQtyNear4Digit(px / b_.getPreClose() - 1));
            if (last2Prev >= 0.095) {
                msg_.append("abs(last2Prev)>=" + 0.095 + ", stop forecast");
                return true;
            }


            //2.0 TODO: do not give short/long near uplimit/downlimit
            double hlimit = Util.roundQtyNear4Digit(b_.getPreClose() * (1 + 0.098));
            double llimit = Util.roundQtyNear4Digit(b_.getPreClose() * (1 - 0.098));
            if(Direction.SHORT == d_ && b_.getHighPx() >= hlimit) {
                double last2High = Util.roundQtyNear4Digit((b_.getLastPx() - b_.getHighPx()) / b_.getPreClose());
                if(last2High >= -0.02) {
                    msg_.append("last2High >= " + -0.02 + ", too close to highlimit");
                    return true;
                }
            } else if(Direction.LONG == d_ && b_.getLowPx() <= llimit) {
                double last2Low = Util.roundQtyNear4Digit((b_.getLastPx() - b_.getLowPx()) / b_.getPreClose());
                if(last2Low <= 0.02) {
                    msg_.append("last2Low <= " + 0.02 + ", too close to lowlimit");
                    return true;
                }
            }
            return false;
        }
    }
}
