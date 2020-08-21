package com.phenix.farmer.signal;

import com.phenix.farmer.Controller;
import com.phenix.farmer.event.IEngineEvent;
import com.phenix.farmer.signal.orderflow.OrderFlowComputationSignalConfig;
import org.slf4j.LoggerFactory;

/**
 * Created by phenix on 2017/7/10.
 */
public class DummySignal extends AbstractSignal {
    private final static transient org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DummySignal.class);
    private final Controller controller;
    private OrderFlowComputationSignalConfig config;

    private PositionBalanceManager positionBalanceManager;

    public DummySignal(Controller controller_, PositionBalanceManager positionBalanceManager_) {
        this.controller = controller_;
        this.positionBalanceManager = positionBalanceManager_;
    }

    @Override
    public void handleOrderFlowUpdateEvent(IEngineEvent e_) {
    }

    @Override
    public void handleOrderBookUpdateEvent(IEngineEvent e_) {

    }

    @Override
    public void reinit() {
        LOGGER.info(String.format("%s is reinited", getClass().getSimpleName()));
    }

    @Override
    public SignalType getSignalType() {
        return SignalType.DummySignal;
    }

    @Override
    public void handleDailySettleEvent(IEngineEvent e_) {
        positionBalanceManager.readyForSettle(controller.getId());
    }
}
