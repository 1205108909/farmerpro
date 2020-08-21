package com.phenix.data;

import com.google.common.collect.ImmutableList;
import com.phenix.math.TimedInterval;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

/**
 * The atomic data holder for time-series Stat
 */
public class IntervalStat {
    public final static IntervalStat INVALID = new IntervalStat();
    public final static FeatureFactory<IntervalStat> DefaultFactory = () -> new IntervalStat();
    public final static List<IntervalStat> INVALID_RANGE = ImmutableList.of(INVALID);

    @Getter
    @Setter
    protected double high;
    @Getter
    @Setter
    protected LocalTime highTime;
    @Getter
    @Setter
    protected double low;
    @Getter
    @Setter
    protected LocalTime lowTime;
    @Getter
    @Setter
    protected double avg;
    @Getter
    @Setter
    protected double vol;
    @Getter
    @Setter
    protected double cls;
    @Getter
    @Setter
    protected double open;
    @Setter
    @Getter
    protected double turnOver;
    @Setter
    @Getter
    protected int match;
    @Setter
    protected boolean ready;
    @Getter
    @Setter
    private TimedInterval interval;



    public IntervalStat() {
        high = -Double.MAX_VALUE;
        low = Double.MAX_VALUE;
        highTime = LocalTime.MIN;
        lowTime = LocalTime.MIN;
        interval = TimedInterval.INVALID;
        open = -1;
        cls = -1;
        vol = 0;
        avg = 0;
        ready = false;
        turnOver = 0;
        match = 0;
    }

    public boolean isReady() {
        return ready;
    }

    public void handleTransaction(Transaction t_) {
        if ("c" != t_.getFunctionCode() && t_.getQty() > 0) {
            doCompute(t_.getPrice(), t_.getQty(), t_.getTime());
        }
    }

    public void handleOrderBook(OrderBook b_) {
        doCompute(b_.getLastPx(), b_.getVolume(), b_.getTime());
    }

    public void handleFeature(double px_, double qty_, LocalTime t_) {
        doCompute(px_, qty_, t_);
    }

    private void doCompute(double px_, double qty_, LocalTime t_) {
        if (px_ > high) highTime = t_;
        if (px_ < low) lowTime = t_;
        high = Math.max(high, px_);
        low = Math.min(low, px_);
        if (qty_ > 0) {
            turnOver += px_ * qty_;
            match++;
            avg = turnOver / (vol + qty_);
        }
        vol += qty_;
        cls = px_;

        if (!ready) {
            open = px_;
            ready = true;
            highTime = t_;
            lowTime = t_;
        }
    }

    public void cleanup() {
        high = -Double.MAX_VALUE;
        low = Double.MAX_VALUE;
        highTime = LocalTime.MIN;
        lowTime = LocalTime.MIN;
        interval = TimedInterval.INVALID;
        open = -1;
        cls = -1;
        vol = 0;
        avg = 0;
        ready = false;
    }
}
