package com.phenix.farmer.signal;

import com.phenix.data.Order;
import com.phenix.data.Security;
import com.phenix.farmer.event.IEngineEvent;

import java.util.List;

public interface ISignal extends ISignlDiagnosis {
    void reinit();
    void handleTransactionUpdateEvent(IEngineEvent e_);
    void handleOrderBookUpdateEvent(IEngineEvent e_);
    void handleKDataUpdateEvent(IEngineEvent e_);
    void handleOrderFlowUpdateEvent(IEngineEvent e_);
    void handleMarketOpenEvent(IEngineEvent e_);
    void handleDailySettleEvent(IEngineEvent e_);
    void handleExecutionReportEvent(IEngineEvent e_);
    void persistSignal(String p_);
    void closeOrder(Security sec_);
    List<Order> getOrders();
    Order getOrder(String orderId_);
    SignalType getSignalType();
}
