package com.phenix.util;

import org.junit.Test;

import java.time.LocalTime;

import static java.time.LocalTime.of;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class DateUtilTest {
    @Test
    public void testRoundTimeDown() {
        LocalTime t = of(9, 30, 0);

        assertEquals(of(9, 30, 0), DateUtil.roundTimeDown(t, 30));
        assertEquals(of(9, 30, 0), DateUtil.roundTimeDown(t.plusSeconds(1), 30));
        assertEquals(of(9, 30, 0), DateUtil.roundTimeDown(t.plusSeconds(29), 30));
        assertEquals(of(9, 30, 30), DateUtil.roundTimeDown(t.plusSeconds(30), 30));
        assertEquals(of(9, 29, 30), DateUtil.roundTimeDown(t.plusSeconds(-1), 30));
    }

    @Test
    public void testGetTimeResidual() {
        LocalTime t = of(9, 30, 0);
        assertEquals(0, DateUtil.getTimeResidual(t, 30));
        assertEquals(1, DateUtil.getTimeResidual(t.plusSeconds(1), 30));
        assertEquals(29, DateUtil.getTimeResidual(t.plusSeconds(29), 30));
        assertEquals(0, DateUtil.getTimeResidual(t.plusSeconds(30), 30));
        assertEquals(57, DateUtil.getTimeResidual(of(9, 25, 57), 60));
        assertEquals(3, DateUtil.getTimeResidual(of(9, 30, 3), 60));
    }

    @Test
    public void testTime2Int() {
        LocalTime t = of(9, 30, 32);
        assertEquals(93032, DateUtil.time2Int(t));
    }

    @Test
    public void testRoundTimeDownInSecs() {
        LocalTime t1 = of(9, 29, 57);
        LocalTime t2 = of(9, 30, 01);
        LocalTime t1e = of(9, 29, 00);
        LocalTime t2e = of(9, 30, 00);
        assertEquals(t1e.toSecondOfDay(), DateUtil.roundTimeDownInSecs(t1, 60));
        assertEquals(t2e.toSecondOfDay(), DateUtil.roundTimeDownInSecs(t2, 60));
        assertEquals(t2e.toSecondOfDay(), DateUtil.roundTimeDownInSecs(t2e, 60));
    }

    @Test
    public void testTradingTimeDiffInSecs() {
        LocalTime t1 = of(9, 25, 00);
        LocalTime t2 = of(9, 30, 00);
        LocalTime t3 = of(11, 31, 00);
        LocalTime t4 = of(12, 31, 00);
        LocalTime t5 = of(13, 31, 00);
        LocalTime t6 = of(14, 59, 00);
        LocalTime t7 = of(14, 30, 00);
        LocalTime t8 = of(9, 25, 10);

        assertEquals(1, DateUtil.tradingTimeDiffInSecs(t1.toSecondOfDay(), t2.toSecondOfDay()));
        assertEquals(2 * 60 * 60, DateUtil.tradingTimeDiffInSecs(t2.toSecondOfDay(), t3.toSecondOfDay()));
        assertEquals(0, DateUtil.tradingTimeDiffInSecs(t3.toSecondOfDay(), t4.toSecondOfDay()));
        assertEquals(31 * 60, DateUtil.tradingTimeDiffInSecs(t4.toSecondOfDay(), t5.toSecondOfDay()));
        assertEquals(4 * 60 * 60 - 1 * 60, DateUtil.tradingTimeDiffInSecs(t1.toSecondOfDay(), t6.toSecondOfDay()));
        assertEquals(29 * 60, DateUtil.tradingTimeDiffInSecs(t7.toSecondOfDay(), t6.toSecondOfDay()));
        assertEquals(1, DateUtil.tradingTimeDiffInSecs(t8.toSecondOfDay(), t2.toSecondOfDay()));
    }

    @Test
    public void testMinusSeconds() {
        LocalTime t1 = of(9, 25, 00);
        LocalTime t2 = of(11, 25, 00);
        LocalTime t3 = of(13, 05, 00);
        LocalTime t4 = of(13, 42, 00);

        assertEquals(LocalTime.of(9, 00, 00), DateUtil.minusSeconds(t1, 60 * 25));
        assertEquals(LocalTime.of(11, 00, 00), DateUtil.minusSeconds(t2, 60 * 25));
        assertEquals(LocalTime.of(11, 10, 00), DateUtil.minusSeconds(t3, 60 * 25));
        assertEquals(LocalTime.of(13, 17, 00), DateUtil.minusSeconds(t4, 60 * 25));
    }
}