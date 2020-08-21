package com.phenix.farmer.signal;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.Controller;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.Matcher;
import com.phenix.farmer.event.*;
import com.phenix.message.ExecType;
import com.phenix.orderbook.OrderBook;
import com.phenix.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public abstract class OrderGenerationSignal extends AbstractSignal implements Orderable {
    private final static transient Logger LOGGER = LoggerFactory.getLogger(OrderGenerationSignal.class);
    protected final Controller controller;
    protected final PositionBalanceManager pbManager;
    private final String name;
    protected final Set<Security> secs;

    protected Map<Security, DailyStat> dailyStats;
    protected Map<Security, SignalState> signalState;
    protected OrderGenerationSignalConfig config;
    boolean future2IndexPxValidationCheck;

    /**below fields for order management*/
    protected Map<String, Order> id2Order;
    //close position order
    protected Map<String, ClosePosOrder> id2ClosePosOrder;
    //open position order id 2 close position order mapping
    protected Multimap<String, String> id2ClosePosOrderId;
    //close position order with open status
    protected Multimap<Security, ClosePosOrder> sec2OClosePosOrder;
    protected LinkedListMultimap<Security, ClosePosOrder> sec2ClosePosOrder;
    //open position order which has not been hedged(cleared)
    protected LinkedListMultimap<Security, Order> unclosedOrder;
    //open position order with open status
    protected Multimap<Security, Order> sec2OOrder;
    //help order for performance
    protected Map<Security, Order> recentOrder;
    protected Map<Security, Order> recentOOrder;
    /**end of order management*/

    protected Order recentUnclosedOrder;
    protected LocalTime recentOrderPlacementTime;
    protected CloseOrderType closeOrderType;

    public OrderGenerationSignal(Controller controller_, PositionBalanceManager pbManager_) {
        name = OrderGenerationSignal.class.getSimpleName();
        controller = controller_;
        dailyStats = new HashMap<>();
        pbManager = pbManager_;
        secs = new HashSet<>();

        id2Order = new HashMap<>();
        id2ClosePosOrder = new HashMap<>();
        id2ClosePosOrderId = LinkedListMultimap.create();
        sec2OOrder = LinkedListMultimap.create();
        sec2OClosePosOrder = LinkedListMultimap.create();
        unclosedOrder = LinkedListMultimap.create();
        recentOrder = new HashMap<>();
        recentOOrder = new HashMap<>();
        sec2ClosePosOrder = LinkedListMultimap.create();
        signalState = new HashMap<>();

        buildCache();
    }

    public abstract void handlePattern(OrderBook b_);
    public abstract double computeMaxRevert(OrderBook b_, Direction d_);
    public abstract double computeOpenQty(OrderBook b_, Direction d_);
    public abstract SignalState getOrCreateSignalState(Security s_);
    public abstract void refreshConfig(Security s_);

    private void buildCache() {
    }

    @Override
    public void reinit() {
        super.reinit();
        cleanup();
        buildCache();
        LOGGER.info(String.format("%s is inited", getClass().getSimpleName()));
    }

    public void cleanup() {
        secs.clear();
        dailyStats.clear();
        signalState.clear();

        id2Order.clear();
        id2ClosePosOrder.clear();
        id2ClosePosOrderId.clear();
        sec2OOrder.clear();
        sec2OClosePosOrder.clear();
        sec2ClosePosOrder.clear();
        unclosedOrder.clear();
        recentOrder.clear();
        recentOOrder.clear();

        LOGGER.info(String.format("%s is cleaned up", getClass().getSimpleName()));
    }

    @Override
    public SignalType getSignalType() {
        return SignalType.UNKNOWN;
    }

    @Override
    public List<Order> getOrders() {
        List<Order> o = new ArrayList<>();
        o.addAll(id2Order.values());
        o.addAll(id2ClosePosOrder.values());
        return o;
    }

    @Override
    public void handleDailySettleEvent(IEngineEvent e_) {
        DailySettleEvent e = (DailySettleEvent) e_;
        reinit();
        LOGGER.info(name + "is cleaned up");
    }

    @Override
    public void handleOrderBookUpdateEvent(IEngineEvent event_) {
        OrderBookUpdateEvent e = (OrderBookUpdateEvent) event_;
        OrderBook b = e.getOrderBook();
        secs.add(b.getSecurity());
        refreshConfig(b.getSecurity());

        if (!config.isEnable()) {
            //LOGGER.debug("Signal:" + name + " was disabled, skip");
            //LOGGER.info(String.format("Signal:%s for symbol [%s] was disabled, skip", this.getClass().getSimpleName(),b.getSecurity()));

            return;
        }
        //no timeTo check on purpose
        if(b.getTime().isBefore(config.getTimeFrom())) {
            //LOGGER.warn("Illegal orderbook: sec = " + b.getSymbol() + ", time = " + b.getTime());
            return;
        }
        if (!Util.isValidLimitPrice(b.getLastPx())) {
            return;
        }

        recentOrderPlacementTime = LocalTime.MIN;
        computeHighLow(b);
        future2IndexPxValidationCheck = future2IndexPxValidationCheck(b);
        updateUnclosedPosition(b);
        boolean flag = handleClosePosition(b);
        if(flag) return;//close order has been created, no further action till order closed
        flag = continueCheck(b);
        if(!flag) return;

        if (b.getTime().isAfter(config.getTimeStopOpenPosition())) {
            //LOGGER.debug("Signal has been disabled: orderBookTime[" + b.getTime() + "] > TO[" + config.getTimeStopOpenPosition() + "]");
            return;
        }
        //too short time of order placement interval
        if (b.getTime().toSecondOfDay() - recentOrderPlacementTime.toSecondOfDay() <= config.getOrderPlacementTimeThresholdInSecs()) {
            //LOGGER.debug("Security: " + b.getSymbol() + " has an order placed recently at: " + recentOrderPlacementTime + " so ignore signal due to time threshold, skip");
            return;
        }
        //too many number of order placement
        SignalState state = getOrCreateSignalState(b.getSecurity());
        int totalCounter = state.getLongOrderPlacementCounter() + state.getShortOrderPlacementCounter();
        if (totalCounter >= config.getMaxNumberOfOrderPlacement()) {
            LOGGER.debug("Security: " + b.getSymbol() +  " has been placed " + totalCounter + " times with maxAllowedCounter = " + config.getMaxNumberOfOrderPlacement() + ", skip");
            return;
        }
        if(SecurityType.INDEX == b.getSecurityType() && !future2IndexPxValidationCheck) // do not open positon if future/index not in sync
            return;

        handlePattern(b);
    }

    /**
     * put below all to a template pattern
     */
    protected DailyStat computeHighLow(OrderBook b_) {
        DailyStat ds = dailyStats.get(b_.getSecurity());
        if (ds == null) {
            ds = new DailyStat(-1, controller.getTransactionDate());
            dailyStats.put(b_.getSecurity(), ds);
        }
        double refPx = SecurityType.INDEX_FUTURE == b_.getSecurityType() ? b_.getPreSettle() : b_.getPreClose();
        setDailyStat(ds, b_, refPx);
        return ds;
    }

    protected boolean future2IndexPxValidationCheck(OrderBook b_) {
        if(SecurityType.INDEX == b_.getSecurityType()) {
            IndexFuture future = getIndexFuture(b_);
            if(future != null && controller.future2IndexPxValidationCheck(b_.getSecurity(), future, 8)) {
                return true;
            }
        }
        return false;
    }

    protected void updateUnclosedPosition(OrderBook b_) {
        Collection<Order> orders = unclosedOrder.get(b_.getSecurity());
        recentUnclosedOrder = null;
        //keep the h/l px since order creation, no update once order closed
        for (Order o : orders) {
            if (b_.getLastPx() > o.getHighPx()) {
                o.setHighPx(b_.getLastPx());
                o.setHighTime(b_.getTime());
            }
            if (b_.getLastPx() < o.getLowPx()) {
                o.setLowPx(b_.getLastPx());
                o.setLowTime(b_.getTime());
            }
            double maxRevert = Direction.SHORT == o.getDirection() ? b_.getLastPx() - o.getLowPx() : o.getHighPx() - b_.getLastPx();

            o.setMaxRevert(Util.roundQtyNear4Digit(maxRevert));
            if (o.getTime().isAfter(recentOrderPlacementTime)) {
                recentOrderPlacementTime = o.getTime();
                recentUnclosedOrder = o;
            }
        }

        //if future, update the linked future order
        if (SecurityType.INDEX_FUTURE == b_.getSecurityType()) {
            Security underlying = FarmerDataManager.getInstance().getUnderlyingSecurity(b_.getSecurity());
            if (underlying == null) {
                LOGGER.error("No underlying found for IndexFuture: " + b_.getSecurity());
                throw new IllegalDataException("No underlying found for IndexFuture: " + b_.getSecurity());
            } else {
                orders = unclosedOrder.get(underlying);
                for (Order o : orders) {
                    OrderHandler oh = orderHandler.get(o.getClOrdId());
                    if (oh == null)
                        continue;

                    Order lo = oh.getLinkedOrder();
                    if (b_.getLastPx() > lo.getHighPx()) {
                        lo.setHighPx(b_.getLastPx());
                        lo.setHighTime(b_.getTime());
                    }
                    if (b_.getLastPx() < lo.getLowPx()) {
                        lo.setLowPx(b_.getLastPx());
                        lo.setLowTime(b_.getTime());
                    }
                    double maxRevertInTicks =  Direction.SHORT == o.getDirection() ? b_.getLastPx() - lo.getLowPx() : lo.getHighPx() - b_.getLastPx();
                    lo.setMaxRevert(Math.max(lo.getMaxRevert(), Util.roundQtyNear4Digit(maxRevertInTicks)));
                }
            }
        }
    }
    protected boolean continueCheck(OrderBook b_) {
        if (!FarmerDataManager.getInstance().isInterestedInSecurity(getSignalType(), b_.getSecurity())) {
            return false;
        }
        if (FarmerDataManager.getInstance().isForbidden(b_.getSecurity())) {
            return false;
        }
        if (!controller.allowAutoOrderHandling()) {
            LOGGER.debug("AutoOrderHandling has been disabled, pls use command line to close all order");
            return false;
        }
        //order in the air
        if (sec2OOrder.containsKey(b_.getSecurity()) || sec2OClosePosOrder.containsKey(b_.getSecurity())) {
            LOGGER.debug("Security: " + b_.getSymbol() + " has an order in the air, do not do any order handling");
            return false;
        }
        return true;
    }
    protected boolean handleClosePosition(OrderBook b_) {
        double chgInPct = Util.roundQtyNear4Digit(b_.getLastPx() / b_.getPreClose() - 1);
        double chgInPctFuture = chgInPct;
        boolean closePosOrderPlaced = false;
        List<Order> orders = unclosedOrder.get(b_.getSecurity());
        for (Order o : orders) {
            double pctOfMaxRevert = o.getMaxRevert();
            if (!sec2OClosePosOrder.containsKey(o.getSec())) {
                if (b_.getTime().isAfter(config.getTimeClosePosition())) {
                    createClosePosOrder(o, false, b_, controller.getTimeNow(), "closed by time:" + config.getTimeClosePosition());
                    closePosOrderPlaced = true;
                } else {
                    int sign = Direction.LONG == o.getDirection() ? 1 : -1;
                    double chg = sign * chgInPct;
                    double chgF = sign * chgInPctFuture;
                    if (chg >= config.getPxChgCap2ClearPosition() || chgF >= config.getPxChgCap2ClearPosition()) {
                        createClosePosOrder(o, false, b_, controller.getTimeNow(), "closed by PxChgCap:" + config.getPxChgCap2ClearPosition());
                        closePosOrderPlaced = true;
                    } else {//max loss hit
                        double maxRevert = computeMaxRevert(b_, o.getDirection());
                        if(pctOfMaxRevert >= maxRevert) {
                            createClosePosOrder(o, false, b_, controller.getTimeNow(), "closed by maxRevert:" + maxRevert);
                            closePosOrderPlaced = true;
                        }
                    }
                }
                if (closePosOrderPlaced)
                    return true;
            }
        }
        return false;
    }
    /**end of the template*/

    protected void createOrder(OrderBook b_, Direction d_, LocalDateTime t_, boolean force_, String placementReason_) {
        if (!force_ && sec2OOrder.size() > 0 && sec2OClosePosOrder.size() > 0) {
            LOGGER.info("some open/close pos order are in the air, do not allow till all order closed");
            return;
        }

        //boolean openSucceed = false;
        double tickSize = FarmerDataManager.getInstance().getTickSize(b_.getSymbol());
        OrderSide side = Direction.LONG == d_ ? OrderSide.BUY : OrderSide.SELL;
        double px = controller.getOrderBook(b_.getSecurity()).getFarPrice(side);
        int sign = OrderSide.BUY == side ? 1 : -1;
        px = Util.roundQtyNear4Digit(px + sign * config.getNumberOfTicksHitFar() * tickSize);
        Order o = Order.of(b_.getSecurity(), px, computeOpenQty(b_, d_), t_);
        o.setDirection(d_);
        o.setOrderSide(side);
        o.setLowPx(b_.getLastPx());
        o.setHighPx(b_.getLastPx());
        o.setRefPx(b_.getLastPx());
        o.setPlacementReason(placementReason_);
        o.setSignalType(getSignalType());
        o.setOrderType(OrderType.LIMIT);

        int counter = FarmerDataManager.getInstance().getSignalCounter(b_.getSecurity());
        o.setCounter(counter);

        IndexFuture future = getIndexFuture(o);
        if(future != null && future2IndexPxValidationCheck) {
            OrderBook obi = controller.getOrderBook(future);
            o.setSpread(Util.roundQtyNear4Digit(obi.getLastPx() - b_.getLastPx()));
        }
        Basket<Order> b = Basket.of(Lists.newArrayList(o));
        SignalState gs = signalState.get(o.getSec());

        boolean bookPos = !FarmerDataManager.getInstance().isUnbooked(b_.getSecurity());
        boolean openSucceed = bookPos ? pbManager.handleOpenPosition(getSignalType(), b, controller.getTimeNow()) : true;
        if (openSucceed) {
            handleOpenPosition(b);
            if (gs.getRecentDirection() == Direction.UNKNOWN) {
                //good to go
            } else if (gs.getRecentDirection() != o.getDirection()) {
                gs.incrementDirectionSwitchCounter();
            }
            gs.setRecentDirection(o.getDirection());
            gs.incrementOrderPlacementCounter(d_);
            handleExecutionReportEvent(new ExecutionReportEvent(Matcher.createExecutionReport(b.getEntries().get(0), ExecType.FILL)));
        } else {
            LOGGER.info("Not enough cash to open position, skip");
        }
    }

    protected void createClosePosOrder(Order o_, boolean force_, OrderBook b_, LocalDateTime t_, String placementReason_) {
        if (!force_ && sec2OOrder.size() > 0 && sec2OClosePosOrder.size() > 0) {
            LOGGER.info("some open/close pos order are in the air, do not allow till all order closed");
            return;
        }
        double tickSize = FarmerDataManager.getInstance().getTickSize(o_.getSymbol());
        OrderSide side = o_.getOrderSide().opposite();
        double px = controller.getOrderBook(o_.getSec()).getFarPrice(side);
        int sign = OrderSide.BUY == side ? 1 : -1;
        px = Util.roundQtyNear4Digit(px + sign * config.getNumberOfTicksHitFar() * tickSize);

        Collection<Order> oOrders = unclosedOrder.get(o_.getSec());
        List<Order> cOrders = new ArrayList<>(oOrders.size());
        for (Order o : oOrders) {
            Order clsOrder = o.createCloseOrder(t_, px);
            clsOrder.setRefPx(b_.getLastPx());
            clsOrder.setPlacementReason(placementReason_);
            clsOrder.setSignalType(getSignalType());
            clsOrder.setOrderType(OrderType.LIMIT);

            int counter = FarmerDataManager.getInstance().getSignalCounter(b_.getSecurity());
            clsOrder.setCounter(counter);

            IndexFuture future = getIndexFuture(o);
            if(future != null && future2IndexPxValidationCheck) {
                OrderBook obi = controller.getOrderBook(future);
                clsOrder.setSpread(Util.roundQtyNear4Digit(obi.getLastPx() - b_.getLastPx()));
            }
            cOrders.add(clsOrder);
        }
        Basket<Order> b = Basket.of(cOrders);

        boolean bookPos = !FarmerDataManager.getInstance().isUnbooked(o_.getSec());
        boolean openSucceed = bookPos ? pbManager.handleOpenPosition(getSignalType(), b, controller.getTimeNow()) : true;
        if (openSucceed) {
            handleOpenPosition(b);
            for (Order o : b.getEntries()) {
                handleExecutionReportEvent(new ExecutionReportEvent(Matcher.createExecutionReport(o, ExecType.FILL)));
            }
        } else {
            LOGGER.info("Not enough cash so close position, skip");
        }
    }

    public void handleOpenPosition(Basket<? extends Order> b_) {
        for (Order o : b_.getEntries()) {
            if (o.isOpenPosOrder()) {
                id2Order.put(o.getClOrdId(), o);
                sec2OOrder.put(o.getSec(), o);
                unclosedOrder.put(o.getSec(), o);
                if(SecurityType.INDEX == o.getSecType()) {
                    createOrderHandler(o);
                }
            } else {
                id2ClosePosOrder.put(o.getClOrdId(), (ClosePosOrder) o);
                sec2OClosePosOrder.put(o.getSec(), (ClosePosOrder) o);
                sec2ClosePosOrder.put(o.getSec(), (ClosePosOrder) o);
                id2ClosePosOrderId.put(((ClosePosOrder) o).getRootClOrdId(), o.getClOrdId());
            }

            Order other = recentOrder.get(o.getSec());
            if(other == null || o.getTime().isAfter(other.getTime())) {
                recentOrder.put(o.getSec(), o);
                if(o.isOpenPosOrder()) {
                    recentOOrder.put(o.getSec(), o);
                }
            }
        }
    }
    private void createOrderHandler(Order o_) {
        if (!future2IndexPxValidationCheck)
            return;
        IndexFuture future = getIndexFuture(o_);
        OrderBook obi = controller.getOrderBook(future);
        Order linkedOrder = Order.of(UUID.randomUUID().toString(), future, obi.getLastPx(), o_.getOrderQty(), o_.getInPendingTime());
        linkedOrder.setHighPx(obi.getLastPx());
        linkedOrder.setLowPx(obi.getLastPx());
        linkedOrder.setRefPx(obi.getLastPx());
        linkedOrder.setHighTime(obi.getTime());
        linkedOrder.setLowTime(obi.getTime());
        OrderHandler oh = OrderHandler.of(o_, linkedOrder);
        orderHandler.put(o_.getClOrdId(), oh);
    }

    public void publishOrderInfo(Order o_) {
        FarmerDataManager.getInstance().publishOrderInfo(o_);
    }

    @Override
    public void handleExecutionReportEvent(IEngineEvent e_) {
        ExecutionReportEvent e = (ExecutionReportEvent) e_;
        String clOrderId = e.getReport().getClOrdId();
        Order o = id2Order.get(clOrderId);
        if (o == null) {
            o = id2ClosePosOrder.get(clOrderId);
        }
        if (o != null) {
            o.handleExecutionReport(e.getReport());
            publishOrderInfo(o);
            if (Util.isClosedStatus(o.getOrderStatus())) {
                boolean r;
                if (o.isClosePosOrder()) {
                    r = sec2OClosePosOrder.remove(o.getSec(), o);
                    /*below close the order*/
                    //11 41 returned in execution report???
                    ClosePosOrder clo = (ClosePosOrder) o;
                    Order oorder = id2Order.get(clo.getRootClOrdId());
                    Collection<String> closePosOrderIds = id2ClosePosOrderId.get(oorder.getClOrdId());
                    double closedQty = 0;
                    for (String id : closePosOrderIds) {
                        closedQty += id2ClosePosOrder.get(id).getCumQty();
                    }
                    closedQty = Math.round(closedQty);
                    if (oorder.getCumQty() == closedQty) {
                        unclosedOrder.remove(o.getSec(), oorder);
                    } else {//open postion order was closed by several close order
                        throw new IllegalStateException("Unexpected, this shouldn't happen in backtest:" + "open.CumQty = " + oorder.getCumQty() + ", close.CumQty = " + closedQty);
                    }

                    //set spread
                    if(SecurityType.INDEX == o.getSecType()) {
                        int sign = Direction.LONG == oorder.getDirection() ? 1 : -1;
                        double realizedSpread = sign * (clo.getSpread() - oorder.getSpread());//spread may be invalid in case of infrequent future_index trade
                        if(!Double.isNaN(realizedSpread))
                            clo.setRealizedSpread(realizedSpread);
                    }
                } else {
                    r = sec2OOrder.remove(o.getSec(), o);
                }
                if (!r) {
                    throw new IllegalStateException("No Order/ClosePosOrder/sec2OOrder found for clOrderId:" + o.getClOrdId());
                }

                boolean bookPos = !FarmerDataManager.getInstance().isUnbooked(o.getSec());
                if (bookPos)
                    pbManager.handleUpdatePosition(o, true);
            }
        } else {
            throw new IllegalDataException("Unknown clOrderId: " + clOrderId + ", with Symbol=" + e.getReport().getSymbol());
        }
    }
}
