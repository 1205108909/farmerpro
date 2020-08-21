package com.phenix.farmer.signal;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.Controller;
import com.phenix.farmer.FarmerConfigManager;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.event.DailySettleEvent;
import com.phenix.farmer.event.IEngineEvent;
import com.phenix.farmer.event.OrderBookUpdateEvent;
import com.phenix.farmer.feature.DailyFeatureStat;
import com.phenix.farmer.feature.FutureIndexSpreadStat;
import com.phenix.math.MathUtil;
import com.phenix.orderbook.OrderBook;
import com.phenix.util.Alert;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;

/**
 * Tracking the spread used by other order-generating signal
 * MaxRevert = 0.001
 *
 * 20190715, spread(index - future) shrinks so quickly, a good sign for up-trend
 * TODO:
 * track 20190702, spread(index - future) expands so quickly, a good sign for down-trend
  1. compute Future - Index under different frequency(10s, 30s, 60s)
  2. provide the stats for these different frequency
 */
public class FutureIndexSpreadSignal extends AbstractSignal {
    private final static transient org.slf4j.Logger Logger = LoggerFactory.getLogger(FutureIndexSpreadSignal.class);

    private final PositionBalanceManager pbManager;
    private final Set<Security> secs = new HashSet();
    //LocalTime -> computed which time, default is LocalTime.MIN
    private Map<LocalTime, Table<Security, Integer, FutureIndexSpreadStat>> rtStats = new HashMap<>();
    //private Table<Security, Integer, FutureIndexSpreadStat> rtStats = HashBasedTable.create();
    /**10s 30s 60s*/
    private List<Integer> intervals = ImmutableList.of(30, 60);
    private final int rtFreq = 10;//intervals.stream().mapToInt(e -> e).min().getAsInt();
    private Security firstSec = null;
    private LocalTime timeCounter = LocalTime.MIN;
    private final List<FeatureHolder<IntervalStat>> isHolders;
    private final Controller controller;
    private FutureIndexSpreadSignalConfig config;
    boolean future2IndexPxValidationCheck;
    private int rtStatsCounter;

    public FutureIndexSpreadSignal(Controller controller_, PositionBalanceManager pbManager_) {
        controller = controller_;
        pbManager = pbManager_;
        isHolders = new ArrayList<>(8);
        rtStatsCounter = 0;
        rtStats.put(LocalTime.MIN, HashBasedTable.create());
        buildCache();
    }

    private void buildCache() {
        SessionGroup sgSz = FarmerDataManager.getInstance().getSessionGroup(Exchange.SZ);
        SessionGroup sgSh = FarmerDataManager.getInstance().getSessionGroup(Exchange.SS);
        SessionGroup sgIf = FarmerDataManager.getInstance().getSessionGroup(Exchange.CFFEX);
        for(int i : intervals) {
            FeatureHolder<IntervalStat> isHolder = new FeatureHolder<>(i, IntervalStat.DefaultFactory);
            isHolder.addTimeSlots(Exchange.SS, sgSh);
            isHolder.addTimeSlots(Exchange.SZ, sgSz);
            isHolder.addTimeSlots(Exchange.CFFEX, sgIf);
            isHolders.add(isHolder);
        }
    }

    @Override
    public void reinit() {
        super.reinit();
        cleanup();
        rtStats.put(LocalTime.MIN, HashBasedTable.create());
        buildCache();
        Logger.info(String.format("%s is inited", getClass().getSimpleName()));
    }

    private void refreshConfig(Security s_) {
        config = getConfig(FutureIndexSpreadSignalConfig.class, s_);
    }

    public void cleanup() {
        for (FeatureHolder<IntervalStat> isHolder : isHolders)
            isHolder.cleanup();
        isHolders.clear();
        secs.clear();
        firstSec = null;
        for (Table<Security, Integer, FutureIndexSpreadStat> value : rtStats.values()) {
            value.clear();
        }
        rtStats.clear();
        rtStatsCounter = 0;
        timeCounter = LocalTime.MIN;
        Logger.info(String.format("%s is cleaned up", getClass().getSimpleName()));
    }

    @Override
    public void handleDailySettleEvent(IEngineEvent e_) {
        DailySettleEvent e = (DailySettleEvent) e_;
        reinit();
        Logger.info(getClass().getSimpleName() + "is cleaned up");
    }

    @Override
    public void handleOrderBookUpdateEvent(IEngineEvent event_) {
        OrderBookUpdateEvent e = (OrderBookUpdateEvent) event_;
        OrderBook b = e.getOrderBook();
        if(firstSec == null && SecurityType.INDEX == b.getSecurityType()) firstSec = b.getSecurity();
        secs.add(b.getSecurity());
        refreshConfig(b.getSecurity());

        if(b.getTime().isBefore(config.getTimeFrom()) || b.getTime().isAfter(config.getTimeTo())) {
            Logger.warn("Illegal orderbook: sec = " + b.getSymbol() + ", time = " + b.getTime());
            return;
        }
        if (!Util.isValidLimitPrice(b.getLastPx())) {
            return;
        }

        //set future-index px flag
        future2IndexPxValidationCheck = false;
        if(SecurityType.INDEX == b.getSecurityType()) {
            IndexFuture future = getIndexFuture(b);
            if(future != null && controller.future2IndexPxValidationCheck(b.getSecurity(), future, 8)) {
                future2IndexPxValidationCheck = true;
            }
        }

        computeSignal(b);
    }

    /**
     * compute based on Index OrderBook, try future???
     */
    private void computeSignal(OrderBook b_) {
        if (SecurityType.INDEX != b_.getSecurityType())
            return;
        //TODO:if rebooted during trading, future is republished then index, this signal is invalid anymore
        if (!future2IndexPxValidationCheck)
            return;

        IndexFuture future = getIndexFuture(b_);
        OrderBook fb = controller.getOrderBook(future);
        //double spread = (fb.getLastPx() - b_.getLastPx()) / b_.getPreClose();
        double spread = fb.getLastPx() - b_.getLastPx();
        int iTime = DateUtil.time2Int(b_.getTime());
        for (FeatureHolder<IntervalStat> fh : isHolders) {
            IntervalStat v = fh.getOrCreate(b_.getSecurity(), iTime);
            if (v != null) {
                v.handleFeature(spread, b_.getTurnover(), b_.getTime());
            } else {
                throw new IllegalDataException("IntervalStat shouldn't be null: time = " + b_.getTime());
            }
        }

        if(firstSec != null && firstSec.equals(b_.getSecurity()) && b_.getTime().isAfter(config.getRtFeatureTimeFrom())) {
            if(LocalTime.MIN == timeCounter) {
                computeRTStat(b_);
                timeCounter = b_.getTime();
            } else if(DateUtil.tradingTimeDiffInSecs(timeCounter.toSecondOfDay(), b_.getTimeInSecs()) >= rtFreq) {
                computeRTStat(b_);
                timeCounter = b_.getTime();
            }
        }
    }

    //compute every 30 secs for simplicity, by default compute for all security
    private void computeRTStat(OrderBook b_) {
        computeRTStat(b_, b_.getTime(), LocalTime.MIN, true);
    }


    /**
     * 1. for realtime compute all
     * 2. for specified to_, only compute the security
     * @param b_
     * @param to_
     * @param key_
     * @param all_
     */
    private void computeRTStat(OrderBook b_, LocalTime to_, LocalTime key_, boolean all_) {
        int iTime = DateUtil.time2Int(to_);
        for(Security sec : secs) {
            if(!all_ && !sec.equals(b_.getSecurity()))
                continue;
            for(FeatureHolder<IntervalStat> fh : isHolders) {
                List<IntervalStat> v = fh.getOrCreateRange(sec, iTime, 1000000);//just a large enough number
                double []d = v.stream().filter(e -> e.isReady())
                        .mapToDouble(e -> e.getCls())
                        .toArray();
                Table<Security, Integer, FutureIndexSpreadStat> t = rtStats.get(key_);
                if(t == null) {
                    t = HashBasedTable.create();
                    rtStats.put(key_, t);
                    System.out.println(++rtStatsCounter);
                }
                if(d.length == 0) {
                    t.put(sec, fh.getBucketDurationInSecs(), FutureIndexSpreadStat.INVALID);
                    continue;
                }
                double []res = new double[DailyFeatureStat.DEFAULT_QUANTILES.length];
                for(int i = 0; i < res.length; i++) {
                    res[i] = Util.roundQtyNear4Digit(MathUtil.quantile(d, DailyFeatureStat.DEFAULT_QUANTILES[i]));
                }
                double min = Util.roundQtyNear4Digit(MathUtil.min(d));
                double max = Util.roundQtyNear4Digit(MathUtil.max(d));
                double mean = Util.roundQtyNear4Digit(MathUtil.mean(d));
                double std = Util.roundQtyNear4Digit(MathUtil.std(d));
                int i = 0;
                FutureIndexSpreadStat s = new FutureIndexSpreadStat(sec, b_.getDate(), to_, res[i++], res[i++],
                        res[i++], res[i++], res[i++], res[i++], res[i++], res[i++], res[i++], res[i++], res[i++], min,
                        max, mean, std);
                t.put(sec, fh.getBucketDurationInSecs(), s);
            }
        }
    }


    public IntervalStat getFeature(Security s_, LocalTime t_, int countingIntervalInSecs_, int lookingBackInSecs_) {
        for(FeatureHolder<IntervalStat> fh : isHolders) {
            if(countingIntervalInSecs_ == fh.getBucketDurationInSecs())
                return fh.getOrCreate(s_, DateUtil.time2Int(t_), lookingBackInSecs_);
        }
        return IntervalStat.INVALID;
    }

    public IntervalStat getFeature(OrderBook b_, int countingIntervalInSecs_, int lookingBackInSecs_) {
        return getFeature(b_.getSecurity(), b_.getTime(), countingIntervalInSecs_, lookingBackInSecs_);
    }

    public List<IntervalStat> getFeatureRange(OrderBook b_, int countingIntervalInSecs_, int lookingBackInSecs_) {
        for(FeatureHolder<IntervalStat> fh : isHolders) {
            if(countingIntervalInSecs_ == fh.getBucketDurationInSecs())
                return fh.getOrCreateRange(b_.getSecurity(), DateUtil.time2Int(b_.getTime()), lookingBackInSecs_);
        }
        return IntervalStat.INVALID_RANGE;
    }

    @Override
    public SignalType getSignalType() {
        return SignalType.FutureIndexSpreadSignal;
    }

    public FutureIndexSpreadStat getRTFeature(OrderBook b_, int countingIntervalInSecs_) {
        for(FeatureHolder<IntervalStat> fh : isHolders) {
            if(countingIntervalInSecs_ == fh.getBucketDurationInSecs()) {
                Table<Security, Integer, FutureIndexSpreadStat> t = rtStats.get(LocalTime.MIN);
                FutureIndexSpreadStat v = t.get(b_.getSecurity(), countingIntervalInSecs_);
                if(v == null) {//no need to compute, triggered by orderbook update event
                    if (FarmerController.getInstance().getSignalMode() == SignalMode.BACK_TEST)
                        throw new IllegalDataException("shouldn't be null, sec=" + b_.getSecurity() + ", time=" + b_.getTime());
                    else {
                        Alert.fireAlert(Alert.Severity.Major, "HUNTER_IN_RECEOVERY_MODE_" + firstSec.getSymbol(), "rt future is replaced by FutureIndexSpreadStat.INVALID, sec=" + b_.getSecurity() + ", time=" + b_.getTime());
                        v = FutureIndexSpreadStat.INVALID;
                    }
                }
                return v;
            }
        }
        return FutureIndexSpreadStat.INVALID;
    }

    public FutureIndexSpreadStat getRTFeature(OrderBook b_, int countingIntervalInSecs_, LocalTime to_) {
        for(FeatureHolder<IntervalStat> fh : isHolders) {
            if(countingIntervalInSecs_ == fh.getBucketDurationInSecs()) {
                Table<Security, Integer, FutureIndexSpreadStat> t = rtStats.get(to_);
                if(t == null) {
                    computeRTStat(b_, to_, to_, false);
                    t = rtStats.get(to_);
                }
                FutureIndexSpreadStat v = t.get(b_.getSecurity(), countingIntervalInSecs_);
                if(v == null) {
                    computeRTStat(b_, to_, to_, false);
                    v = t.get(b_.getSecurity(), countingIntervalInSecs_);
                    if(v == null)//no need to compute, triggered by orderbook update event
                        throw new IllegalDataException("shouldn't be null, sec=" + b_.getSecurity() + ", time=" + b_.getTime());
                }
                return v;
            }
        }
        return FutureIndexSpreadStat.INVALID;
    }
    @Override
    public void persistSignal(String p_) {
        p_ = FarmerConfigManager.getInstance().getFeatureConfig().getFutureIndexSpread();
        for (int i : intervals) {
            for (Security sec : secs) {
                if(SecurityType.INDEX_FUTURE == sec.getType())
                    continue;
                String path = p_ + "/k" + i + "s/";
                File f = new File(path);
                if (!f.exists()) {
                    f.mkdirs();
                }
                path += sec.getSimpleSymbol() + "_" + DateUtil.date2Str(controller.getTransactionDate()) + ".csv";
                f = new File(path);
                if(f.exists()) {
                    f.delete();
                }
                OrderBook ob = controller.getOrderBook(sec);
                List<String> ss = new ArrayList<>();
                ss.add("Time,Open,High,Low,Close,Avg,Volume,Turnover,PreClose");
                StringBuilder sb = new StringBuilder();
                for (FeatureHolder<IntervalStat> fh : isHolders) {
                    if (fh.getBucketDurationInSecs() != i)
                        continue;
                    List<IntervalStat> iss = fh.getFeatures(sec);
                    if (iss == null) continue;
                    for (IntervalStat is : iss) {
                        sb.append(DateUtil.time2str(is.getInterval().getStart())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getOpen())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getHigh())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getLow())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getCls())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getAvg())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getVol())).append(",");
                        sb.append(Util.roundQtyNear4Digit(is.getVol())).append(",");
                        sb.append(ob.getPreClose());
                        ss.add(sb.toString());
                        sb.delete(0, sb.length());
                    }
                }
                try {
                    Files.write(Paths.get(path), ss);
                    ss.clear();
                } catch (IOException e_) {
                    throw new RuntimeException(e_);
                }
            }
        }
    }
}
