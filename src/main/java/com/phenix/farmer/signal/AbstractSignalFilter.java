package com.phenix.farmer.signal;

import com.phenix.data.Direction;
import com.phenix.orderbook.OrderBook;

public class AbstractSignalFilter implements SignalFilter {
    @Override
    public boolean filter(OrderBook b_, Direction d_, boolean gpsMomentumOrReverse_, StringBuilder msg_) {
        return false;
    }

    @Override
    public boolean filter(OrderBook b_, Direction d_, StringBuilder msg_) {
        return false;
    }
}
