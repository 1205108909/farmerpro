package com.phenix.farmer.signal;

import com.phenix.data.*;
import com.phenix.farmer.Matcher;
import com.phenix.farmer.TestUtil;
import com.phenix.farmer.config.PositionBalanceConfig;
import com.phenix.message.ExecType;
import com.phenix.util.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PositionBalanceManagerTest extends BaseSignalTest{
    private PositionBalanceManager pbManager;

    @Before
    public void setUp() {
        pbManager = new PositionBalanceManager(1, PositionBalanceConfig.DEFAULT);
    }
    @Test
    public void computeCashNeeded() {
        Security hs300 = Security.of("000300.sh", SecurityType.INDEX);

        //1.0 index buy(short sell)
        Order o = Order.of(hs300, 2999, 2, TIME_NOW);
        o.setDirection(Direction.SHORT);
        o.setOrderSide(OrderSide.SELL);
        o.setLowPx(2999);
        o.setHighPx(3001);
        o.setRefPx(3000);
        o.setPlacementReason("test short");
        //open order
        Assert.assertEquals(179983.1856, Util.roundQtyNear4Digit(pbManager.computeCashNeeded(o)), TestUtil.DELTA);

        //order filled
        o.handleExecution(Matcher.createExecutionReport(o, ExecType.FILL));
        o.setAvgPrice(2998.8);
        Assert.assertEquals(179971.1827, Util.roundQtyNear4Digit(pbManager.computeCashSpent(o)), TestUtil.DELTA);

        //1.1 index sell(buy long)1979771.856
        //open order
        Order co = o.createCloseOrder(TIME_NOW.plusMinutes(2), 2990.6);
        co.setRefPx(2989);
        co.setPlacementReason("long and close order");
        Assert.assertEquals(0, Util.roundQtyNear4Digit(pbManager.computeCashNeeded(co)), TestUtil.DELTA);

        //close order
        co.handleExecution(Matcher.createExecutionReport(co, ExecType.FILL));
        co.setAvgPrice(2989.6);
        Position.PositionEntry pe = new Position.PositionEntry(o.getSec(), o.getOrderQty(), o.getClOrdId(), o.getDirection());
        pe.setOpenPx(o.getAvgPrice());
        pe.setQty(o.getCumQty());
        Assert.assertEquals(-185404.9498, Util.roundQtyNear4Digit(pbManager.computeCashSpent(co, pe)), TestUtil.DELTA);

        //2.0 stock buy
        Security sh6000000 = Security.of("600000.sh", SecurityType.STOCK);
        //2.1 stock buy - 20181211
        o = Order.of(sh6000000, 10.72, 1000, TIME_NOW);
        o.setDirection(Direction.LONG);
        o.setOrderSide(OrderSide.BUY);
        o.setLowPx(10.69);
        o.setHighPx(10.90);
        o.setRefPx(10.70);
        o.setPlacementReason("test buy");
        //open order
        Assert.assertEquals(10722.6800, Util.roundQtyNear4Digit(pbManager.computeCashNeeded(o)), TestUtil.DELTA);

        //order filled
        o.handleExecution(Matcher.createExecutionReport(o, ExecType.FILL));
        o.setAvgPrice(10.71);
        Assert.assertEquals(10712.6775, Util.roundQtyNear4Digit(pbManager.computeCashSpent(o)), TestUtil.DELTA);

        //2.2 stock sell
        //open order
        co = o.createCloseOrder(TIME_NOW.plusMinutes(2), 10.8);
        co.setRefPx(10.81);
        co.setPlacementReason("sell and close order");
        Assert.assertEquals(0, Util.roundQtyNear4Digit(pbManager.computeCashNeeded(co)), TestUtil.DELTA);

        //close order
        co.handleExecution(Matcher.createExecutionReport(co, ExecType.FILL));
        co.setAvgPrice(10.79);
        Assert.assertEquals(-10776.5125, Util.roundQtyNear4Digit(pbManager.computeCashSpent(co)), TestUtil.DELTA);
    }

}