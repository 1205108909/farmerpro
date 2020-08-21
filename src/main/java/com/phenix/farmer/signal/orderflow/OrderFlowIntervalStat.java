package com.phenix.farmer.signal.orderflow;

import com.google.common.collect.ImmutableList;
import com.phenix.data.FeatureFactory;
import com.phenix.data.IntervalStat;
import com.phenix.data.OrderSide;
import com.phenix.data.OrderType;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.OrderFlow;
import com.phenix.orderbook.Transaction;
import lombok.Getter;

import java.util.List;

public class OrderFlowIntervalStat extends IntervalStat {
    public final static OrderFlowIntervalStat INVALID = new OrderFlowIntervalStat();
    public final static FeatureFactory<OrderFlowIntervalStat> DefaultFactory = () -> new OrderFlowIntervalStat();
    public final static List<OrderFlowIntervalStat> INVALID_RANGE = ImmutableList.of(INVALID);

    @Getter
    private double limitAskVol;
    @Getter
    private double limitBidVol;
    @Getter
    private double limitAskNum;
    @Getter
    private double limitBidNum;
    @Getter
    private double marketAskVol;
    @Getter
    private double marketBidVol;
    @Getter
    private double marketAskNum;
    @Getter
    private double marketBidNum;
    @Getter
    private double aggressiveLimitAskVol;
    @Getter
    private double aggressiveLimitAskNum;
    @Getter
    private double aggressiveLimitBidVol;
    @Getter
    private double aggressiveLimitBidNum;

    public OrderFlowIntervalStat() {
        super();
        limitAskVol = 0;
        limitBidVol = 0;
        limitAskNum = 0;
        limitBidNum = 0;
        marketAskVol = 0;
        marketBidVol = 0;
        marketAskNum = 0;
        marketBidNum = 0;
        aggressiveLimitAskVol = 0;
        aggressiveLimitBidVol = 0;
        aggressiveLimitAskNum = 0;
        aggressiveLimitBidNum = 0;
    }

    public void handleTransaction(Transaction t_) {
        throw new UnsupportedOperationException(getClass().getSimpleName() + " do not support transaction computation");
    }

    public void handleOrderFlow(OrderFlow b_, OrderBook ob_) {
        boolean bValid = ob_.isValid();
        double bp = ob_.getBidPrice(0);
        double ap = ob_.getAskPrice(0);
        if (OrderSide.SELL == b_.getSide() && OrderType.LIMIT == b_.getType()) {
            limitAskNum++;
            limitAskVol += b_.getQty();
            if (bValid) {
                if (b_.getPrice() <= bp) {
                    aggressiveLimitAskNum++;
                    aggressiveLimitAskVol += b_.getQty();
                }
            }
        } else if (OrderSide.BUY == b_.getSide() && OrderType.LIMIT == b_.getType()) {
            limitBidNum++;
            limitBidVol += b_.getQty();
            if (bValid) {
                if (b_.getPrice() >= ap) {
                    aggressiveLimitBidNum++;
                    aggressiveLimitBidVol += b_.getQty();
                }
            }
        } else if (OrderSide.SELL == b_.getSide() && OrderType.MARKET == b_.getType()) {
            marketAskNum++;
            marketAskVol += b_.getQty();
            if (bValid) {
                aggressiveLimitAskNum++;
                aggressiveLimitAskVol += b_.getQty();
            }
        } else if (OrderSide.BUY == b_.getSide() && OrderType.MARKET == b_.getType()) {
            marketBidNum++;
            marketBidVol += b_.getQty();
            if (bValid) {
                aggressiveLimitBidNum++;
                aggressiveLimitBidVol += b_.getQty();
            }
        }
        if (!ready)
            ready = true;
    }

    public void handleOrderFlowIndex(OrderFlow b_, OrderBook ob_, double weight_) {
        boolean bValid = ob_.isValid();
        double bp = ob_.getBidPrice(0);
        double ap = ob_.getAskPrice(0);
        if (OrderSide.SELL == b_.getSide() && OrderType.LIMIT == b_.getType()) {
            limitAskNum += 1 * weight_;
            limitAskVol += b_.getQty() * weight_;
            if (bValid) {
                if (b_.getPrice() <= bp) {
                    aggressiveLimitAskNum += 1 * weight_;
                    aggressiveLimitAskVol += b_.getQty() * weight_;
                }
            }
        } else if (OrderSide.BUY == b_.getSide() && OrderType.LIMIT == b_.getType()) {
            limitBidNum += 1 * weight_;
            limitBidVol += b_.getQty() * weight_;
            if (bValid) {
                if (b_.getPrice() >= ap) {
                    aggressiveLimitBidNum += 1 * weight_;
                    aggressiveLimitBidVol += b_.getQty() * weight_;
                }
            }
        } else if (OrderSide.SELL == b_.getSide() && OrderType.MARKET == b_.getType()) {
            marketAskNum += 1 * weight_;
            marketAskVol += b_.getQty() * weight_;
            if (bValid) {
                aggressiveLimitAskNum += 1 * weight_;
                aggressiveLimitAskVol += b_.getQty() * weight_;
            }
        } else if (OrderSide.BUY == b_.getSide() && OrderType.MARKET == b_.getType()) {
            marketBidNum += 1 * weight_;
            marketBidVol += b_.getQty() * weight_;
            if (bValid) {
                aggressiveLimitBidNum += 1 * weight_;
                aggressiveLimitBidVol += b_.getQty() * weight_;
            }
        }
        if (!ready)
            ready = true;
    }

    public void applyWeight(double w_) {
        limitAskVol /= w_;
        limitBidVol /= w_;
        limitAskNum /= w_;
        limitBidNum /= w_;
        marketAskVol /= w_;
        marketBidVol /= w_;
        marketAskNum /= w_;
        marketBidNum /= w_;
        aggressiveLimitAskVol /= w_;
        aggressiveLimitBidVol /= w_;
        aggressiveLimitAskNum /= w_;
        aggressiveLimitBidNum /= w_;
    }
}
