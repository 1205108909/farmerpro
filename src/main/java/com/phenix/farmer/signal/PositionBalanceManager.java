package com.phenix.farmer.signal;

import com.google.common.collect.Lists;
import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.exception.TimeOutException;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.config.PositionBalanceConfig;
import com.phenix.orderbook.OrderBook;
import com.phenix.util.DateUtil;
import com.phenix.util.FormattedTable;
import com.phenix.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by phenix on 2018/5/30.
 * <p>
 * thread-safe position & balance manager
 */
public class PositionBalanceManager implements ITradingReport {
    private final static transient org.slf4j.Logger Logger = LoggerFactory.getLogger(PositionBalanceManager.class);
    private final static long DEFAULT_TIMEOUT = 500000;
    private final double initialCash;//30_000_000;
    private final int numOfWorker;
    private StandardDeviation std = new StandardDeviation();

    //Map<String, Position.PositionEntry> store the orderId -> positionEntry mapping
    private TreeMap<LocalDate, Map<String, Position.PositionEntry>> positions = new TreeMap<>();
    private TreeMap<LocalDate, Position.PositionEntry> dailyCash = new TreeMap<>();
    private TreeMap<LocalDate, Position.PositionEntry> dailyFreezedCash = new TreeMap<>();
    private TreeMap<LocalDate, Double> dailyNetValue = new TreeMap<>();
    //temporary solution for backtest. TODO: decomission
    private TreeMap<LocalDate, Position.PositionEntry> dailySpread = new TreeMap<>();
    private CountDownLatch latch;
    private double totalTurnover = 0;

    public PositionBalanceManager(int numOfWorker_, PositionBalanceConfig config_) {
        numOfWorker = numOfWorker_;
        latch = new CountDownLatch(numOfWorker_);
        initialCash = config_.getInitialCash();
    }

    public void init() {
        dailyCash.clear();
        dailyNetValue.clear();
        dailyFreezedCash.clear();
        dailySpread.clear();
        positions.clear();
        positions.put(LocalDate.MIN, new ConcurrentHashMap<>());
    }

    public boolean hasPosition(LocalDate date_) {
        return positions.get(date_).size() > 0;
    }

    public void handleMarketOpen(LocalDate date_) {
        if (latch.getCount() == 0) {
            latch = new CountDownLatch(numOfWorker);
        }

        Position.PositionEntry pe = Util.deepCopy(Position.PositionEntry.Cash);
        pe.setQty(0);
        dailySpread.put(date_, pe);

        //copy cash from previous tradingDay
        if (dailyCash.size() == 0) {
            pe = Util.deepCopy(Position.PositionEntry.Cash);
            pe.setQty(initialCash);
            dailyCash.put(date_, pe);

            pe = Util.deepCopy(Position.PositionEntry.Cash);
            pe.setQty(0);
            dailyFreezedCash.put(date_, pe);
        } else {
            pe = dailyCash.floorEntry(date_.minusDays(1)).getValue();
            dailyCash.put(date_, Util.deepCopy(pe));

            pe = Util.deepCopy(Position.PositionEntry.Cash);
            pe.setQty(0);
            dailyFreezedCash.put(date_, pe);
        }


        //copy positions from previous tradingDay
        Map<String, Position.PositionEntry> recentPos = positions.floorEntry(date_.minusDays(1)).getValue();
        Map<String, Position.PositionEntry> pos = new ConcurrentHashMap<>();
        recentPos.values().stream()
                .filter(e -> !e.isClosed())
                .forEach(e -> pos.put(e.getId(), Util.deepCopy(e)));
        positions.put(date_, pos);

        handleCorpAction(date_);
    }

    private void handleCorpAction(LocalDate date_) {
        int counter = 0;
        Map<String, Position.PositionEntry> positionsT = positions.get(date_);
        double cash = 0;

        for (Position.PositionEntry e : positionsT.values()) {
            DailyStat ds = FarmerDataManager.getInstance().getDailyStat(e.getSec(), date_);
            DailyStat preDs = FarmerDataManager.getInstance().getPrevDailyStat(e.getSec(), date_);
            if (ds == null || preDs == null) {
                Logger.error("DailyStat or PreDailyStat on Day[%s] is null, potential data error", date_);
                continue;
            }

            if (ds.getAdjustingConst() != preDs.getAdjustingConst() || ds.getAdjustingFactor() != preDs.getAdjustingFactor()) {
                double tickSize = FarmerDataManager.getInstance().getTickSize(e.getSec().getSymbol());
                double cashDiv = (ds.getAdjustingConst() - preDs.getAdjustingConst()) / preDs.getAdjustingFactor();
                double stockDiv = ds.getAdjustingFactor() / preDs.getAdjustingFactor() - 1;

                cash += cashDiv * e.getQty(); //???
                double qty = e.getQty();
                double openPx = e.getOpenPx();

                double newQty = qty + Util.roundQtyDown(e.getQty() * stockDiv, 1);
                double newOpenPx = Util.roundQtyNear((e.getOpenPx() - cashDiv) / (1 + stockDiv), tickSize);
                double newHighestPx = Util.roundQtyNear((e.getHighestPx() - cashDiv) / (1 + stockDiv), tickSize);
                double newLowestPx = Util.roundQtyNear((e.getLowestPx() - cashDiv) / (1 + stockDiv), tickSize);
                e.setOpenPx(newOpenPx);
                e.setQty(newQty);
                e.setHighestPx(newHighestPx);
                e.setLowestPx(newLowestPx);

                counter++;

                Logger.info(String.format("Security=[%s], CashDiv=[%s], StockDiv=[%s], qty updated from [%s] to [%s], open updated from [%s] to [%s]", e.getSec().getSymbol(), cashDiv, stockDiv, qty, newQty, openPx, newOpenPx));
            }
        }

        Position.PositionEntry pe = dailyCash.get(date_);
        pe.setQty(Util.roundQtyNear4Digit(pe.getQty() + cash));

        Logger.info(String.format("CorpAction - totally [%s] corp actions are handled", counter));
    }

    public double getAvailableCash(LocalDate date_) {
        if (dailyCash.containsKey(date_)) {
            return Util.roundQtyNear4Digit(dailyCash.get(date_).getQty());
        } else {
            return Double.NaN;
        }
    }

    public boolean handleOpenPosition(SignalType st_, Basket<? extends Order> basket_, LocalDateTime dt_) {
        double cashNeeded = basket_.getEntries().stream()
                .mapToDouble(e -> computeCashNeeded(e))
                .sum();
        boolean openSucceed = false;

        //TODO: add other position-level check
        Position.PositionEntry availableCash = dailyCash.get(dt_.toLocalDate());
        double v = availableCash.addAndGet(-cashNeeded);

        if (v > 0) {
            Position.PositionEntry freezedCash = dailyFreezedCash.get(dt_.toLocalDate());
            freezedCash.addAndGet(cashNeeded);

            openSucceed = true;
            for (Order e : basket_.getEntries()) {
                if (e.isOpenPosOrder()) {
                    //Assume the open order could always be executed [1 Entry -> 1 Order(or CloseOrder)]
                    Position.PositionEntry newpe = new Position.PositionEntry(e.getSec(), e.getOrderQty(), e.getClOrdId(), e.getDirection());
                    newpe.setHighestPx(e.getPrice());
                    newpe.setLowestPx(e.getPrice());
                    newpe.setOpenTime(dt_);
                    newpe.setSignalType(st_);
                    newpe.setQty(e.getOrderQty());
                    positions.get(dt_.toLocalDate()).put(e.getClOrdId(), newpe);
                    Logger.info(String.format("Signal[%s] - Open Position on Security[%s] on Date[%s]-Time[%s] at price=[%s] and qty=[%s]", e.getSignalType(), e.getSec().getSymbol(), dt_.toLocalDate(), dt_.toLocalTime(), e.getPrice(), e.getOrderQty()));
                }
            }
            //Send Order Here???
            //basket_.getEntries().forEach(e -> Matcher.match(e));
        } else {
            availableCash.addAndGet(cashNeeded);
        }
        return openSucceed;
    }

    //No Close Order Entry
    public void handleUpdatePosition2(Order o_) {
        String id = o_.isOpenPosOrder() ? o_.getClOrdId() : ((ClosePosOrder) o_).getRootClOrdId();
        Position.PositionEntry pe = positions.get(o_.getDate()).get(id);
        if (pe == null) {
            throw new IllegalDataException("Can not find pe for order:" + o_.getClOrdId());
        }
        if (!Util.isClosedStatus(o_.getOrderStatus())) {
            return;
        }

        //TODO: 1 PE -> multiple entry
        if (o_.isOpenPosOrder()) {
            pe.setOpenPx(o_.getAvgPrice());
            pe.setQty(o_.getCumQty());
        } else {
            pe.setClosePx(o_.getAvgPrice());
            pe.setClosedQty(o_.getCumQty());
            if (pe.getQty() == pe.getClosedQty()) {
                pe.setClosed(true);
                pe.setCloseTime(o_.getClosedTime());
            }

            //compute profit
            double profit = 0;
            double contractValue = SecurityType.STOCK == o_.getSecType() ? 1 : FarmerDataManager.getContractValue(o_.getSymbol());
            double buyCommission = FarmerDataManager.getCommission(o_.getSec(), OrderSide.BUY, false);
            double sellCommission = FarmerDataManager.getCommission(o_.getSec(), OrderSide.SELL, false);
            if (Direction.SHORT == o_.getDirection()) {
                profit = pe.getClosePx() * pe.getClosedQty() * contractValue * (1 - sellCommission)
                        - pe.getOpenPx() * pe.getQty() * contractValue * (1 + buyCommission);
            } else if (Direction.LONG == o_.getDirection()) {
                profit = pe.getOpenPx() * pe.getQty() * contractValue * (1 - sellCommission)
                        - pe.getClosePx() * pe.getClosedQty() * contractValue * (1 + buyCommission);
            }
            profit = Util.roundQtyNear4Digit(profit);
            pe.setProfit(profit);
        }

        double cash = computeCashNeeded(o_);
        double spentCash = computeCashSpent(o_, pe);
        Position.PositionEntry availableCash = dailyCash.get(o_.getDate());
        Position.PositionEntry freezedCash = dailyFreezedCash.get(o_.getDate());
        double residual = cash - spentCash;
        availableCash.addAndGet(residual);
        totalTurnover += Math.abs(spentCash);
        if (o_.getLeavesQty() == 0) {//TODO: in the middle
            freezedCash.addAndGet(-cash);
        }
    }

    public void handleUpdatePosition(Order o_) {
        handleUpdatePosition(o_, false);
    }

    public void handleUpdatePosition(Order o_, boolean handleSpread_) {
        String id = o_.isOpenPosOrder() ? o_.getClOrdId() : ((ClosePosOrder) o_).getRootClOrdId();
        Position.PositionEntry pe = positions.get(o_.getDate()).get(id);
        if (pe == null) {
            throw new IllegalDataException("Can not find pe for order:" + o_.getClOrdId());
        }
        if (!Util.isClosedStatus(o_.getOrderStatus())) {
            return;
        }

        double spread = 0;
        //TODO: 1 PE -> multiple entry
        if (o_.isOpenPosOrder()) {
            pe.setOpenPx(o_.getAvgPrice());
            pe.setQty(o_.getCumQty());
        } else {
            ClosePosOrder co = (ClosePosOrder) o_;
            pe.setClosePx(o_.getAvgPrice());
            pe.setClosedQty(o_.getCumQty());
            if (pe.getQty() == pe.getClosedQty()) {
                pe.setClosed(true);
                pe.setCloseTime(o_.getClosedTime());
            }

            //compute profit
            double profit = 0;
            double contractValue = SecurityType.STOCK == o_.getSecType() ? 1 : FarmerDataManager.getContractValue(o_.getSymbol());
            double buyCommission = FarmerDataManager.getCommission(o_.getSec(), OrderSide.BUY, false);
            double sellCommission = FarmerDataManager.getCommission(o_.getSec(), OrderSide.SELL, false);
            if (Direction.SHORT == o_.getDirection()) {
                profit = pe.getClosePx() * pe.getClosedQty() * contractValue * (1 - sellCommission)
                        - pe.getOpenPx() * pe.getQty() * contractValue * (1 + buyCommission);
            } else if (Direction.LONG == o_.getDirection()) {
                profit = pe.getOpenPx() * pe.getQty() * contractValue * (1 - sellCommission)
                        - pe.getClosePx() * pe.getClosedQty() * contractValue * (1 + buyCommission);
            }
            if (handleSpread_) {
                spread = co.getRealizedSpread() * co.getCumQty() * contractValue;
                profit += spread;
            }
            profit = Util.roundQtyNear4Digit(profit);
            pe.setProfit(profit);

            Position.PositionEntry pspread = dailySpread.get(o_.getDate());
            pspread.addAndGet(spread);
        }

        double cash = computeCashNeeded(o_);
        double spentCash = computeCashSpent(o_, pe);
        Position.PositionEntry availableCash = dailyCash.get(o_.getDate());
        Position.PositionEntry freezedCash = dailyFreezedCash.get(o_.getDate());
        double residual = cash - spentCash;
        availableCash.addAndGet(residual);
        totalTurnover += Math.abs(spentCash);
        if (o_.getLeavesQty() == 0) {//TODO: in the middle
            freezedCash.addAndGet(-cash);
        }
    }

    public void readyForSettle(int controllerId_) {
        latch.countDown();
        Logger.info("Controller " + controllerId_ + " is ready for settle");
    }

    //update highest/lowest price based on close instead of realtime px
    public void handleDailySettle(LocalDate date_) {
        try {
            latch.await(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
            long counter = latch.getCount();
            if (counter > 0) {
                throw new IllegalStateException("Task didn't finished due to count =" + latch.getCount());
            }
        } catch (InterruptedException e) {
            Logger.error(e.getMessage(), e);
            throw new TimeOutException(e);
        }

        double spread = dailySpread.get(date_).getQty();
        double netValue = 0;
        Map<String, Position.PositionEntry> positionsT = positions.get(date_);
        Position.PositionEntry cash = dailyCash.get(date_);
        Position.PositionEntry freezedCash = dailyFreezedCash.get(date_);
        cash.setQty(cash.getQty() + freezedCash.getQty() + spread);
        freezedCash.setQty(0);

        for (Position.PositionEntry pe : positionsT.values()) {
            if (pe.isClosed()) {
                continue;// already count in cash
            }

            if (pe.getSecType() == SecurityType.STOCK) {
                DailyStat ds = FarmerDataManager.getInstance().getDailyStat(pe.getSec(), date_);
                if (ds == null) {
                    //throw new IllegalDataException(String.format("No DailyStat Found for Security[%s], Date[%s]", pe.getSec().getSymbol(), date_));
                    ds = FarmerDataManager.getInstance().getPrevDailyStat(pe.getSec(), date_);
                    Logger.info(String.format("No DailyStat Found for Security[%s], Date[%s], so get DailyStat from recent tradingDay[%s]", pe.getSec().getSymbol(), date_, ds.getDate()));
                }

                if (ds.getClosePx() <= 0) {
                    System.out.println("error closePx = " + ds.getClosePx() + ",sec=" + pe.getSec() + ",tradingDay=" + date_);
                }
                netValue += pe.getQty() * ds.getClosePx();
            } else if (pe.getSecType() == SecurityType.INDEX_FUTURE || pe.getSecType() == SecurityType.INDEX) {
                //TODO: multiday support
                //leave them for now as we close all the position intraday,
            }
        }
        netValue = Util.roundQtyNear4Digit(netValue + cash.getQty());
        System.out.println("date =" + date_ + ", netValue = " + netValue + ", spread = " + spread);
        dailyNetValue.put(date_, netValue);
        Logger.info(String.format("Daily Settle Done for Date[%s]", date_));
    }

    public double computeCashNeeded(Order o_) {
        return computeCash(o_, true, null);
    }

    public double computeCashSpent(Order o_) {
        return computeCashSpent(o_, null);
    }

    public double computeCashSpent(Order o_, Position.PositionEntry pe_) {
        return computeCash(o_, false, pe_);
    }

    //NOTE: !!!make sure account has enough cash for commission
    private double computeCash(Order o_, boolean needOrSpent_, Position.PositionEntry pe_) {
        double cashNeeded = 0;
        boolean isIntraDay = o_.isClosePosOrder() ? ((ClosePosOrder) o_).isCloseIntraDay() : false;
        double price = needOrSpent_ ? o_.getPrice() : o_.getAvgPrice();
        double qty = needOrSpent_ ? o_.getOrderQty() : o_.getCumQty();
        double commissionRate = FarmerDataManager.getCommission(o_.getSec(), o_.getOrderSide(), isIntraDay);
        if (SecurityType.STOCK == o_.getSecType() || SecurityType.ETF == o_.getSecType()) {
            if (OrderSide.BUY == o_.getOrderSide()) {
                cashNeeded = price * qty * (1 + commissionRate);
            } else if (OrderSide.SELL == o_.getOrderSide() && needOrSpent_) {
                //good to go
            } else if (OrderSide.SELL == o_.getOrderSide() && !needOrSpent_) {
                cashNeeded = -price * qty * (1 - commissionRate);
            }
        } else if (SecurityType.INDEX == o_.getSecType() || SecurityType.INDEX_FUTURE == o_.getSecType()) {
            double contractValue = FarmerDataManager.getContractValue(o_.getSymbol());
            double marginRate = FarmerDataManager.getMarginRate(o_.getSymbol());
            if (!o_.isClosePosOrder()) {
                cashNeeded = price * qty * contractValue * (commissionRate + marginRate);
            } else if (o_.isClosePosOrder() && needOrSpent_) {
                //good to go
            } else if (o_.isClosePosOrder() && !needOrSpent_) {
                //compute loss
                double loss = contractValue * (o_.getCumQty() * o_.getAvgPrice() - pe_.getOpenPx() * pe_.getQty());
                if (Direction.LONG == o_.getDirection()) {
                    loss = -loss;
                }

                double margin2 = contractValue * marginRate * pe_.getOpenPx() * pe_.getQty();
                cashNeeded = -loss + price * qty * contractValue * commissionRate - margin2;//Math.max(margin1, margin2);
                //System.out.println("loss=" + loss + ",&&&&&&&&" + cashNeeded + ", price=" + o_.getPrice() + ", avgPrice=" + o_.getAvgPrice() + ", qty=" + o_.getOrderQty() + ", cumQty=" + o_.getCumQty());
            }
        } else {
            throw new UnsupportedOperationException("Unsupported SecurityType: " + o_.getSec().getType());
        }

        return Util.roundQtyNear4Digit(cashNeeded);
    }

    public String getRealTimePLReport(LocalDate d_) {
        FormattedTable table = new FormattedTable();
        List<Object> header = new ArrayList<>();
        header.add("signalType");
        header.add("security");
        header.add("p&l_InCash");
        header.add("p&l_InPct");
        header.add("numOfPositionNet");
        header.add("numOfPositionL");
        header.add("numOfPositionS");
        table.addRow(header);

        Map<String, Position.PositionEntry> pes = positions.get(d_);
        List<SignalType> signalTypes = pes.values().stream()
                .map(e -> e.getSignalType())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        List<Security> securities = pes.values().stream()
                .map(e -> e.getSec())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        for (SignalType st : signalTypes) {
            double plInCashTotal = 0, numOfPositionNetT = 0;
            for (Security sec : securities) {
                List<Position.PositionEntry> pps = pes.values().stream()
                        .filter(e -> e.getSignalType() == st && sec.equals(e.getSec()))
                        .collect(Collectors.toList());
                double plInCash = 0, profit = 0, numOfPositionL = 0, numOfPositionS = 0;

                //TODO:compute profit, could be merge with handleUpdatePosition
                List<Object> row = new ArrayList<>();
                for (Position.PositionEntry pe : pps) {
                    if (pe.isClosed()) {
                        plInCash += pe.getProfit();
                        numOfPositionL += pe.getQty();
                        numOfPositionS += pe.getQty();
                    } else {
                        double contractValue = SecurityType.STOCK == pe.getSecType() ? 1 : FarmerDataManager.getContractValue(pe.getSymbol());
                        double buyCommission = FarmerDataManager.getCommission(pe.getSec(), OrderSide.BUY, false);
                        double sellCommission = FarmerDataManager.getCommission(pe.getSec(), OrderSide.SELL, false);
                        OrderBook ob = FarmerDataManager.getInstance().getOrderBook(pe.getSec());
                        double unclosedQty = pe.getQty() - pe.getClosedQty();
                        if (unclosedQty < 0) {
                            throw new IllegalDataException("Illegal unclosedQty = " + unclosedQty);
                        }

                        if (Direction.LONG == pe.getDirection()) {
                            profit = pe.getClosePx() * pe.getClosedQty() * contractValue * (1 - sellCommission)
                                    - pe.getOpenPx() * pe.getClosedQty() * contractValue * (1 + buyCommission);
                            profit += ob.getLastPx() * unclosedQty * contractValue * (1 - sellCommission)
                                    - pe.getOpenPx() * unclosedQty * contractValue * (1 + buyCommission);
                            numOfPositionL += pe.getQty();
                            numOfPositionS += pe.getClosedQty();
                        } else if (Direction.SHORT == pe.getDirection()) {
                            profit = pe.getOpenPx() * pe.getClosedQty() * contractValue * (1 - sellCommission)
                                    - pe.getClosePx() * pe.getClosedQty() * contractValue * (1 + buyCommission);
                            profit += pe.getOpenPx() * unclosedQty * contractValue * (1 - sellCommission)
                                    - ob.getLastPx() * unclosedQty * contractValue * (1 + buyCommission);
                            numOfPositionS += pe.getQty();
                            numOfPositionL += pe.getClosedQty();
                        }
                        plInCash += profit;
                    }
                    profit = 0;
                }

                row.add(st);
                row.add(sec.getSymbol());
                row.add(Util.roundQtyNear4Digit(plInCash));
                row.add(Util.roundQtyNear4Digit(plInCash / initialCash));
                row.add(numOfPositionL - numOfPositionS);
                row.add(numOfPositionL);
                row.add(numOfPositionS);
                table.addRow(row);

                plInCashTotal += plInCash;
                numOfPositionNetT += numOfPositionL - numOfPositionS;
            }

            List<Object> row = new ArrayList<>();
            row.add(StringUtils.EMPTY);
            row.add("summary");
            row.add(Util.roundQtyNear4Digit(plInCashTotal));
            row.add(Util.roundQtyNear4Digit(plInCashTotal / initialCash));
            row.add(numOfPositionNetT);
            row.add("N/A");
            row.add("N/A");
            table.addRow(row);
        }
        return table.toString();
    }

    @Override
    public String generateReport() {
        Logger.info("Start Generate Trading Report");

        StringBuilder sb = new StringBuilder();
        sb.append("Date,NetValue,NumOfTrades,NumOfSecurity,Cash,DailyPL,Spread\n");
        double numOfWin = 0, numOfLose = 0, numOfOpen = 0;

        List<Double> profits = new ArrayList(), losses = new ArrayList(), daysOfProfit = new ArrayList(), daysOfLoss = new ArrayList();
        LocalDate firstDate = null;
        for (Map.Entry<LocalDate, Map<String, Position.PositionEntry>> e : positions.entrySet()) {
            LocalDate d = e.getKey();
            Map<String, Position.PositionEntry> pes = e.getValue();

            if (d == LocalDate.MIN) {
            } else {
                if (firstDate == null) firstDate = d;
                int sizeOfOpenPos = pes.values().stream()
                        .filter(n -> !n.isClosed())
                        .collect(Collectors.toList())
                        .size();
                double firstDayNetValue = d.equals(dailyNetValue.firstKey()) ? initialCash : dailyNetValue.floorEntry(d.minusDays(1)).getValue();
                sb.append(DateUtil.date2Str(d)).append(",")
                        .append(dailyNetValue.get(d)).append(",")
                        .append(pes.size()).append(",")
                        .append(sizeOfOpenPos).append(",")
                        .append(Util.roundQtyNear4Digit(dailyCash.get(d).getQty())).append(",")
                        .append(Util.roundQtyNear4Digit(dailyNetValue.get(d) - firstDayNetValue)).append(",")
                        .append(Util.roundQtyNear4Digit(dailySpread.get(d).getQty())).append("\n");


                numOfWin += pes.values().stream()
                        .filter(n -> n.isClosed() && n.getProfit() > 0)
                        .count();
                numOfLose += pes.values().stream()
                        .filter(n -> n.isClosed() && n.getProfit() < 0)
                        .count();

                double profit = 0;
                double loss = 0;
                for (Map.Entry<String, Position.PositionEntry> n : pes.entrySet()) {
                    Position.PositionEntry pe = n.getValue();
                    if (pe.isClosed()) {
                        if (pe.getProfit() > 0) {
                            profit += pe.getProfit() / initialCash;
                            //System.out.println(pe.getOpenTime());
                            daysOfProfit.add((double) FarmerDataManager.getInstance().daysBetween(pe.getOpenTime().toLocalDate(), pe.getCloseTime().toLocalDate()));
                        } else {
                            loss += pe.getProfit() / initialCash;
                            daysOfLoss.add((double) FarmerDataManager.getInstance().daysBetween(pe.getOpenTime().toLocalDate(), pe.getCloseTime().toLocalDate()));
                        }
                    }
                }
                profits.add(profit);
                losses.add(loss);
            }
        }

        double avgProfit = profits.stream()
                .mapToDouble(e -> e)
                .average()
                .orElse(0);
        double stdDev = std.evaluate(profits.stream()
                .mapToDouble(e -> e.doubleValue())
                .toArray());
        double avgLoss = losses.stream()
                .mapToDouble(e -> e)
                .average()
                .orElse(0);
        double avgProfitHoldingDays = daysOfProfit.stream()
                .mapToDouble(e -> e)
                .average()
                .orElse(0);
        double avgLossHoldingDays = daysOfLoss.stream()
                .mapToDouble(e -> e)
                .average()
                .orElse(0);

        //compute maxDropDown
        double maxDropDown = 0;
        LocalDate mddStartDate = LocalDate.MIN, mddEndDate = LocalDate.MAX;
        for (Map.Entry<LocalDate, Double> e : dailyNetValue.entrySet()) {
            LocalDate date = e.getKey();
            LocalDate ld = LocalDate.MAX;
            if (date.equals(dailyNetValue.lastKey())) continue;

            double val = e.getValue();
            double minVal = Double.MAX_VALUE;
            for (Map.Entry<LocalDate, Double> n : dailyNetValue.tailMap(date).entrySet()) {
                if (n.getValue() < minVal) {
                    minVal = n.getValue();
                    ld = n.getKey();
                }
            }

            double tMaxDropDown = (minVal - val) / val;
            tMaxDropDown = Util.roundQtyNear4Digit(tMaxDropDown);
            if (tMaxDropDown < maxDropDown) {
                maxDropDown = tMaxDropDown;
                mddStartDate = date;
                mddEndDate = ld;
            }
        }

        numOfOpen += positions.lastEntry().getValue().values().stream()
                .filter(n -> !n.isClosed())
                .count();
        double winningRate = Util.roundQtyNear4Digit(numOfWin / (numOfWin + numOfLose));
        double cumRet = dailyNetValue.lastEntry().getValue() / initialCash - 1;
        cumRet = Util.roundQtyNear4Digit(cumRet);

        //compute average cash usage rate
        List<Double> cashUsageRate = new ArrayList<>(), dailyRet = new ArrayList<>();
        List<Map.Entry<LocalDate, Double>> ll = Lists.newArrayList(dailyNetValue.entrySet());
        for (int i = 0; i < ll.size(); i++) {
            Map.Entry<LocalDate, Double> e = ll.get(i);
            cashUsageRate.add(1 - dailyCash.get(e.getKey()).getQty() / e.getValue());
            double spread = 0;//dailySpread.get(ll.get(i).getKey()).getQty();
            if (i == 0) {
                dailyRet.add((ll.get(i).getValue() + spread) / initialCash - 1);
            } else if (i < ll.size() - 1) {
                dailyRet.add((ll.get(i + 1).getValue() + spread) / ll.get(i).getValue() - 1);
            }
        }
        double avgCashUsageRate = cashUsageRate.stream()
                .mapToDouble(e -> e)
                .average()
                .orElse(0);
        double avgDailyRet = dailyRet.stream()
                .mapToDouble(e -> e)
                .average()
                .orElse(0);
        avgCashUsageRate = Util.roundQtyNear4Digit(avgCashUsageRate);
        double annualRet = Util.roundQtyNear4Digit(avgDailyRet * 240);
        double annualTurnover = totalTurnover / initialCash / dailyCash.size() * 240;
        double spRatio = Util.roundQtyNear4Digit(avgDailyRet / stdDev * Math.sqrt(240));

        StringBuilder s = new StringBuilder();
        s.append("起始时间,").append(DateUtil.date2Str(firstDate))
                .append("\n结束时间,").append(DateUtil.date2Str(positions.lastKey()))
                .append("\n年化收益,").append(annualRet)
                .append("\n累计收益,").append(cumRet)
                .append("\n最大回撤,").append(maxDropDown)
                .append("\n最大回撤区间,[").append(DateUtil.date2Str(mddStartDate)).append("-").append(DateUtil.date2Str(mddEndDate)).append("]")
                .append("\n夏普比例,").append(spRatio)
                .append("\n获利次数,").append(numOfWin)
                .append("\n亏损次数,").append(numOfLose)
                .append("\n未完成交交易,").append(numOfOpen)
                .append("\n胜率,").append(winningRate)
                .append("\n平均盈利,").append(Util.roundQtyNear4Digit(avgProfit))
                .append("\n平均亏损,").append(Util.roundQtyNear4Digit(avgLoss))
                .append("\n平均盈利持仓时间,").append(Util.roundQtyNear4Digit(avgProfitHoldingDays))
                .append("\n平均亏损持仓时间,").append(Util.roundQtyNear4Digit(avgLossHoldingDays))
                .append("\n平均资金使用率,").append(avgCashUsageRate)
                .append("\n换手率,").append(Util.roundQtyNear4Digit(annualTurnover));

        sb.append(s.toString());

        Logger.info("End Generate Trading Report");
        return sb.toString();
    }
}
