package com.phenix.data;

import lombok.Getter;

/**
 *  store Order related information
 */
public class OrderHandler {
    @Getter
    private final Order order;
    @Getter
    private final Order linkedOrder; //the corresponding linkage info

    private OrderHandler(Order o_, Order linkedOrder_) {
        this.order = o_;
        linkedOrder = linkedOrder_;
    }

    public static OrderHandler of(Order o_, Order linkedOrder) {
        return new OrderHandler(o_, linkedOrder);
    }

    public String getOrderID() {
        return order.getClOrdId();
    }

    public String getLinkedOrderID() {
        return linkedOrder.getClOrdId();
    }
}
