package com.phenix.farmer;

import com.phenix.data.Order;
import com.phenix.data.OrderStatus;
import com.phenix.farmer.event.ExecutionReportEvent;
import com.phenix.message.ExecType;
import com.phenix.message.ExecutionReport;

import java.util.UUID;

public class Matcher {
    public static void match(Order o_) {
        FarmerController.getInstance().enqueueEvent(new ExecutionReportEvent(createExecutionReport(o_, ExecType.FILL)));
    }

    //Different Matching Strategy could be scheduled here
    public static ExecutionReport createExecutionReport(Order o_, ExecType t_) {
        ExecutionReport t = new ExecutionReport();
        t.setExecId(UUID.randomUUID().toString());
        t.setClOrdId(o_.getClOrdId());
        t.setSecurity(o_.getSec());
        t.setOrderStatus(OrderStatus.FILLED);
        t.setCumQty(o_.getOrderQty());
        t.setLeavesQty(0);
        t.setAvgPrice(o_.getPrice());
        t.setLastShares(o_.getOrderQty());
        t.setLastPx(o_.getPrice());
        t.setExecType(ExecType.FILL);
        t.setTransactionTime(o_.getInPendingTime().plusNanos(1000));
        return t;
    }
}
