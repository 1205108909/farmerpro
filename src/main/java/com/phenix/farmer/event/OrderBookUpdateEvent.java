package com.phenix.farmer.event;

import com.phenix.farmer.FarmerController;
import com.phenix.orderbook.OrderBook;
import lombok.Getter;

import java.util.function.Consumer;

public class OrderBookUpdateEvent extends AbstractEngineEvent {
    @Getter
    private OrderBook orderBook;

    public OrderBookUpdateEvent(OrderBook book_) {
        this(book_, FarmerController.getInstance().getOrderBookUpdateEventConsumer());
    }

    public OrderBookUpdateEvent(OrderBook book_, Consumer<IEngineEvent> e_) {
        orderBook = book_;
        consumer = e_;
    }

    public static OrderBookUpdateEvent of(OrderBook b_) {
        return new OrderBookUpdateEvent(b_);
    }

    @Override
    public int hashCode() {
        return orderBook.getSecurity().getBasketID();
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", getClass().getSimpleName(), orderBook.getSecurity().toString());
    }
}
