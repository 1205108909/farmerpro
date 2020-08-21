package com.phenix.farmer.signal.transactionrate;

import com.google.common.collect.ImmutableList;
import com.phenix.data.FeatureFactory;
import com.phenix.data.IntervalStat;
import com.phenix.data.OrderSide;
import com.phenix.farmer.signal.FeatureHolder;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Transaction;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import lombok.Getter;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TransactionRateIntervalStat extends IntervalStat {
    private final static transient org.slf4j.Logger Logger = LoggerFactory.getLogger(TransactionRateIntervalStat.class);

    public final static TransactionRateIntervalStat INVALID = new TransactionRateIntervalStat();
    public final static FeatureFactory<TransactionRateIntervalStat> DefaultFactory = () -> new TransactionRateIntervalStat();
    public final static List<TransactionRateIntervalStat> INVALID_RANGE = ImmutableList.of(INVALID);

    @Getter
    private int SellVol;
    @Getter
    private int BuyVol;
    @Getter
    private int SellNum;
    @Getter
    private int BuyNum;
    @Getter
    private double aggressiveSellVol;
    @Getter
    private double aggressiveSellNum;
    @Getter
    private double aggressiveBuyVol;
    @Getter
    private double aggressiveBuyNum;
    @Getter
    private double ratioByNum;
    @Getter
    private double ratioByATS;
    @Getter
    private double highestInTerm;
    @Getter
    private double lowestInTerm;
    @Getter
    private double tradePeriod;
    private long totalABVolume;
    private long totalVolume;

    public TransactionRateIntervalStat(){
        SellVol = 0;
        BuyVol = 0;
        SellNum = 0;
        BuyNum = 0;
        aggressiveSellVol = 0;
        aggressiveSellNum = 0;
        aggressiveBuyVol = 0;
        aggressiveBuyNum = 0;
        ratioByATS = 0;
        ratioByNum = 0;
        highestInTerm = Double.MIN_VALUE;
        lowestInTerm = Double.MAX_VALUE;
        totalABVolume = 0;
        totalVolume = 0;
    }

    public void handleTransaction(Transaction tr_, OrderBook ob_, FeatureHolder<TransactionRateIntervalStat> fh_){
        boolean bValid = ob_.isValid();
        double bp = ob_.getBidPrice(0);
        double ap = ob_.getAskPrice(0);
        if (OrderSide.SELL == tr_.getSide() && "c" != tr_.getFunctionCode()) {
            SellNum++;
            SellVol += tr_.getQty();
            if (bValid) {
                if (tr_.getPrice() <= bp + 0.00001) {
                    aggressiveSellNum++;
                    aggressiveSellVol += tr_.getQty();
                }
            }
        } else if (OrderSide.BUY == tr_.getSide() && "c" != tr_.getFunctionCode()) {
            BuyNum++;
            BuyVol += tr_.getQty();
            if (bValid) {
                if (tr_.getPrice() >= ap - 0.00001) {
                    aggressiveBuyNum++;
                    aggressiveBuyVol += tr_.getQty();
                }
            }
        }
        compute(tr_, fh_);
        if (!ready)
            ready = true;
    }

    public void compute(Transaction tr_, FeatureHolder<TransactionRateIntervalStat> fh_){
//        double[] result = new double[4];
        List<TransactionRateIntervalStat> stats =  fh_.getOrCreateRange(tr_.getSecurity(), DateUtil.time2Int(tr_.getTime()), 900);// trComputationSignal.getFeatureRange(tr_, 1, 900);
        double longTermSellRate = 0, longTermBuyRate = 0, shortTermSellRate = 0, shortTermBuyRate = 0;
        double longTermBuyRateByVol = 0, longTermSellRateByVol = 0, shortTermBuyRateByVol = 0, shortTermSellRateByVol = 0;
        double highPrice = Double.MIN_VALUE, lowPrice = Double.MAX_VALUE;
        int size = stats.size();
        for(int i = 0; i < size; i++) {//count 900s
            TransactionRateIntervalStat is = stats.get(i);
            if(is.isReady()) {
                longTermSellRate += is.getSellNum();
                longTermBuyRate += is.getBuyNum();
                longTermBuyRateByVol += is.getBuyVol();
                longTermSellRateByVol += is.getSellVol();
                highPrice = Math.max(highPrice, is.getHigh());
                lowPrice = Math.min(lowPrice, is.getLow());
            }
        }
        longTermSellRate /= size;
        longTermBuyRate /= size;
        longTermBuyRateByVol /= size;
        longTermSellRateByVol /= size;
        size = 10;
        for(int i = 1; i <= size; i++) {//count 10s
            TransactionRateIntervalStat is = stats.get(stats.size() - i);
            if(is.isReady()) {
                shortTermSellRate += is.getSellNum();
                shortTermBuyRate += is.getBuyNum();
                shortTermSellRateByVol += is.getSellVol();
                shortTermBuyRateByVol += is.getBuyVol();
            }
        }
        shortTermSellRate /= size;
        shortTermBuyRate /= size;

        ratioByNum = Util.roundQtyNear4Digit((shortTermBuyRate - longTermBuyRate) - (shortTermSellRate - longTermSellRate));
        highestInTerm = highPrice;
        lowestInTerm = lowPrice;
    }

    public void handleOrderBook(OrderBook b_) {
        super.handleOrderBook(b_);
//        计算一个周期内，sum(bidVolume1+askVolume1)/次数 再除以 累积成交量
        totalABVolume += b_.getAskQty(0) + b_.getBidQty(0);
        totalVolume += b_.getVolume();
        if (totalVolume > 0){
            tradePeriod = 2 * (totalABVolume/totalVolume);
        }
    }
}
