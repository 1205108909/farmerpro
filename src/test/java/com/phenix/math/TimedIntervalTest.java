package com.phenix.math;

import org.junit.Assert;
import org.junit.Test;

import static java.time.LocalTime.of;

public class TimedIntervalTest {

    @Test
    public void contains() {
        TimedInterval ti = new TimedInterval( of(10, 0, 0), of(10, 10, 0));

        Assert.assertEquals(true, ti.contains(of(10, 0, 0)));
        Assert.assertEquals(true, ti.contains(of(10, 10, 0)));
        Assert.assertEquals(false, ti.contains(of(9, 0, 0)));
        Assert.assertEquals(false, ti.contains(of(10, 11, 0)));

        Assert.assertEquals(true, ti.contains(of(10, 0, 0), true, false));
        Assert.assertEquals(true, ti.contains(of(10, 10, 0), false, true));
        Assert.assertEquals(false, ti.contains(of(10, 0, 0), false, false));
        Assert.assertEquals(false, ti.contains(of(10, 10, 0), false, false));

        Assert.assertEquals(false, ti.contains(of(9, 0, 0), false, false));
        Assert.assertEquals(false, ti.contains(of(10, 11, 0), false, false));

    }
}