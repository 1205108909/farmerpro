package com.phenix.farmer.event;

import com.phenix.farmer.FarmerController;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.TimeSeriesSecurityData;
import lombok.Getter;

import java.util.function.Consumer;

public class TimeSeriesDataUpdateEvent extends AbstractEngineEvent {
	@Getter
	private TimeSeriesSecurityData data;

	public TimeSeriesDataUpdateEvent(TimeSeriesSecurityData data_) {
		this(data_, FarmerController.getInstance().getTimeSeriesDataUpdateEventConsumer());
	}

	public TimeSeriesDataUpdateEvent(TimeSeriesSecurityData data_, Consumer<IEngineEvent> e_) {
		data = data_;
		consumer = e_;
	}

	@Override
	public int hashCode() {
		return data.getSecurity().getBasketID();
	}
	
	@Override
	public String toString() {
		return String.format("[%s, %s]", getClass().getSimpleName(), data.getSecurity().toString());
	}
	public static TimeSeriesDataUpdateEvent of(OrderBook b_) {
		return new TimeSeriesDataUpdateEvent(b_);
	}
}
