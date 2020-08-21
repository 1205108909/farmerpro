package com.phenix.farmer.signal;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.FarmerConfigManager;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.event.IEngineEvent;
import com.phenix.orderbook.OrderBook;
import com.phenix.util.Util;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSignal implements ISignal {
    protected final static LocalTime TIME_094000 = LocalTime.of(9, 40, 00);
    protected final static LocalTime TIME_100000 = LocalTime.of(10, 00, 00);
    protected final static LocalTime TIME_103000 = LocalTime.of(10, 30, 00);
    protected final static LocalTime TIME_110000 = LocalTime.of(11, 00, 00);
    protected final static LocalTime TIME_112500 = LocalTime.of(11, 25, 00);
    protected final static LocalTime TIME_113000 = LocalTime.of(11, 30, 00);
    protected final static LocalTime TIME_132000 = LocalTime.of(13, 20, 00);
    protected final static LocalTime TIME_133000 = LocalTime.of(13, 30, 00);
    protected final static LocalTime TIME_140000 = LocalTime.of(14, 00, 00);
    protected final static LocalTime TIME_141000 = LocalTime.of(14, 10, 00);
    protected final static LocalTime TIME_142000 = LocalTime.of(14, 20, 00);
    protected final static LocalTime TIME_143000 = LocalTime.of(14, 30, 00);
    protected final static LocalTime TIME_144500 = LocalTime.of(14, 45, 00);
    protected final static LocalTime TIME_CONTINUOUS_START = LocalTime.of(9, 30, 00);
    protected final static int TIME_CONTINUOUS_START_AS_INT = TIME_CONTINUOUS_START.toSecondOfDay();
    protected final static LocalTime TIME_ORDERBOOK_START = LocalTime.of(9, 25, 00);
    protected final static int TIME_ORDERBOOK_START_AS_INT = TIME_ORDERBOOK_START.toSecondOfDay();
    private final static Table<Security, String, String> EMPTY_DIAGNOSTIC_INFO = ImmutableTable.of();
    protected final Map<String, OrderHandler> orderHandler = new HashMap<>();

    protected Table<Class<?>, Security, IEngineConfig> configs = HashBasedTable.create();

    @Override
    public void reinit() {
        configs.clear();
        orderHandler.clear();
    }

    protected <T extends IEngineConfig> T getConfig(Class<T> clazz_, Security sec_) {
        T config = (T) configs.get(clazz_, sec_);
        if (config == null) {
            config = FarmerConfigManager.getInstance().getConfig(clazz_, sec_);
            if (config == null) {
                throw new IllegalDataException("No config " + clazz_ + " found for security:" + sec_);
            }
            configs.put(clazz_, sec_, config);
        }
        return config;
    }

    @Override
    public SignalType getSignalType() {
        throw new IllegalDataException("Signal used as adaptor");
    }

    @Override
    public void handleTransactionUpdateEvent(IEngineEvent e_) {
    }

    @Override
    public void handleOrderBookUpdateEvent(IEngineEvent e_) {
    }

    @Override
    public void handleKDataUpdateEvent(IEngineEvent e_) {
    }

    @Override
    public void handleOrderFlowUpdateEvent(IEngineEvent e_) {
    }

    @Override
    public void handleMarketOpenEvent(IEngineEvent e_) {
    }

    @Override
    public void handleDailySettleEvent(IEngineEvent e_) {
    }

    @Override
    public void handleExecutionReportEvent(IEngineEvent e_) {
    }

    @Override
    public void persistSignal(String s_) {
    }

    @Override
    public void closeOrder(Security sec_) {
    }

    @Override
    public Table<Security, String, String> getDiagInfo(List<Security> secs_) {
        return EMPTY_DIAGNOSTIC_INFO;
    }

    @Override
    public List<Order> getOrders() {
        return Collections.emptyList();
    }

    @Override
    public Order getOrder(String orderId_) {
        return null;
    }

    protected void setDailyStat(DailyStat ds, OrderBook b_, double refPx) {
        if(ds.getHighPx() < b_.getLastPx()) {
            ds.setHighPx(b_.getLastPx());
            ds.setHighTime(b_.getTime());
            ds.setHighValue(Util.roundQtyNear4Digit(b_.getLastPx() / refPx - 1));
        }
        if(ds.getLowPx() > b_.getLastPx()) {
            ds.setLowPx(b_.getLastPx());
            ds.setLowTime(b_.getTime());
            ds.setLowValue(Util.roundQtyNear4Digit(b_.getLastPx() / refPx - 1));
        }
    }

    protected IndexFuture getIndexFuture(OrderBook ob_) {
        IndexFuture future;
        if (SignalMode.BACK_TEST == FarmerConfigManager.getInstance().getInstanceConfig().getSignalMode())
            future = (IndexFuture) FarmerDataManager.getInstance().getIndexFuture(ob_.getSecurity(), ob_.getDate(), IndexFutureType.CURRENT_MONTH);
        else
            future = (IndexFuture) FarmerDataManager.getInstance().getIndexFuture(ob_.getSecurity());
        return future;
    }

    protected IndexFuture getIndexFuture(Order o_) {
        IndexFuture future;
        if (SignalMode.BACK_TEST == FarmerConfigManager.getInstance().getInstanceConfig().getSignalMode())
            future = (IndexFuture) FarmerDataManager.getInstance().getIndexFuture(o_.getSec(), o_.getDate(), IndexFutureType.CURRENT_MONTH);
        else
            future = (IndexFuture) FarmerDataManager.getInstance().getIndexFuture(o_.getSec());
        return future;
    }

    protected static abstract class SignalState {
        @Getter
        private boolean directionSwitchCounterReset = false;
        @Getter
        private int resetCounter;
        @Getter
        int longOrderPlacementCounter;
        @Getter
        int shortOrderPlacementCounter;
        @Getter
        int directionSwitchCounter;
        @Getter
        @Setter
        Direction recentDirection = Direction.UNKNOWN;

        protected SignalState() {}
        public void incrementOrderPlacementCounter(Direction d_) {
            if(Direction.LONG == d_) {
                longOrderPlacementCounter++;
            } else if(Direction.SHORT == d_) {
                shortOrderPlacementCounter++;
            }
        }

        public void incrementDirectionSwitchCounter() {
            directionSwitchCounter++;
        }

        public void resetDirectionSwitchCounter() {
            directionSwitchCounter = 0;
            resetCounter++;
            directionSwitchCounterReset = true;
        }
    }
}
