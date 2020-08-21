package com.phenix.farmer.event;

import com.phenix.farmer.FarmerController;
import com.phenix.orderbook.OrderFlow;
import lombok.Getter;

public class OrderFlowUpdateEvent extends AbstractEngineEvent {
	@Getter
	private OrderFlow orderFlow;

	public OrderFlowUpdateEvent(OrderFlow o_) {
		orderFlow = o_;
		consumer = FarmerController.getInstance().getOrderFlowUpdateEventConsumer();
	}
	
	@Override
	public int hashCode() {
		return orderFlow.getSecurity().getBasketID();
	}
	
	@Override
	public String toString() {
		return String.format("[%s, %s]", getClass().getSimpleName(), orderFlow.getSecurity().toString());
	}
	
	public static OrderFlowUpdateEvent of(OrderFlow o_) {
		return new OrderFlowUpdateEvent(o_);
	}
}
