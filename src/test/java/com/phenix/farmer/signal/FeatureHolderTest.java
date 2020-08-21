package com.phenix.farmer.signal;

import com.phenix.data.*;
import com.phenix.data.IntervalFeature;
import com.phenix.farmer.TestUtil;
import mockit.Deencapsulation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by LI JT on 2016/7/8.
 * Description:
 */
public class FeatureHolderTest {
    private static final int DEFAULT_BUCKET_DURATION_IN_SEC = 10;
    private FeatureHolder<IntervalFeature> holder;

    @Before
    public void setUp() {
        this.holder = new FeatureHolder<>(DEFAULT_BUCKET_DURATION_IN_SEC, IntervalFeature.DefaultFactory);
        Exchange exchange = Exchange.SS;
        this.holder.addTimeSlots(exchange, TestUtil.buildSessionGroup(exchange));
        exchange = Exchange.SZ;
        this.holder.addTimeSlots(exchange, TestUtil.buildSessionGroup(exchange));
    }

    @After
    public void tearDown() {
        this.holder.cleanup();
    }

    @Test
    public void testAddTimeSlots() throws Exception {
        Map<Exchange, TimeSlot> timeSlots = Deencapsulation.getField(this.holder, "timeSlots");
        Assert.assertEquals(2, timeSlots.keySet().size());
        Assert.assertTrue(timeSlots.containsKey(Exchange.SS));
        Assert.assertTrue(timeSlots.containsKey(Exchange.SZ));
    }

    /**
     * This method test getOrCreate(Security sec_, int time_, int lookBackPeriodInSecs_) when a input security does not exist
     * int the Map<Security, List<T>> features. It should be created and a subList of the newly created list is returned.
     * @throws Exception
     */
    @Test
    public void testGetOrCreateNotExist() throws Exception {
        Security security = createSecurity("000002", Exchange.SZ);
        int time = 94801;
        int lookBackPeriod = 60;
        List<IntervalFeature> featureList = this.holder.getOrCreateRange(security, time, lookBackPeriod);
        Assert.assertNotNull(featureList);
        Assert.assertEquals(60 / DEFAULT_BUCKET_DURATION_IN_SEC + 1, featureList.size());
        Assert.assertTrue(featureList.get(0).getClass().equals(IntervalFeature.class));
    }

    @Test
    public void testGetBucketIndex() {
        validateBucketIndex("000001", Exchange.SZ, 90805, 0);
        validateBucketIndex("000001", Exchange.SZ, 91505, 0);
        validateBucketIndex("000001", Exchange.SZ, 91510, 1);
        validateBucketIndex("000001", Exchange.SZ, 91520, 2);
        validateBucketIndex("000001", Exchange.SZ, 92520, 59);
        validateBucketIndex("000001", Exchange.SZ, 93000, 60);
        validateBucketIndex("000001", Exchange.SZ, 93010, 61);

        validateBucketIndex("000001", Exchange.SZ, 112959, 779);
        validateBucketIndex("000001", Exchange.SZ, 113000, 779);
        validateBucketIndex("000001", Exchange.SZ, 113559, 779);

        validateBucketIndex("000001", Exchange.SZ, 130005, 780);
        validateBucketIndex("000001", Exchange.SZ, 145659, 1481);
        validateBucketIndex("000001", Exchange.SZ, 145700, 1482);

        validateBucketIndex("000001", Exchange.SZ, 145949, 1498);
        validateBucketIndex("000001", Exchange.SZ, 145959, 1499);
        validateBucketIndex("000001", Exchange.SZ, 170001, 1499);
    }

    private void validateBucketIndex(String symbol, Exchange exchange, int time, int expected) {
        Security security = createSecurity(symbol, exchange);
        //int actual = Deencapsulation.invoke(this.holder, "getBucketIndex", security, time);
        int actual = this.holder.getBucketIndex(security, time);
        Assert.assertEquals(expected, actual);
    }

    private Security createSecurity(String symbol, Exchange exchange) {
        Security security = Security.of(symbol, SecurityType.STOCK, TradeStatus.TRADABLE, exchange, symbol);
        return security;
    }
}