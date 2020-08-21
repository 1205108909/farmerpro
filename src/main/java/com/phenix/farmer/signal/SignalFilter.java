package com.phenix.farmer.signal;

import com.phenix.data.Direction;
import com.phenix.orderbook.OrderBook;

public interface SignalFilter {
    boolean filter(OrderBook b_, Direction d_, boolean gpsMomentumOrReverse_, StringBuilder msg_);

    boolean filter(OrderBook b_, Direction d_, StringBuilder msg_);
}
