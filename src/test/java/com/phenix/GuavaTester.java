package com.phenix;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GuavaTester {
    private Multimap<String, String> mMap;

    @Before
    public void setUp() {
        mMap = LinkedListMultimap.create();
    }
    @Test
    public void testMultiMap() {
        Assert.assertNotNull(mMap.get("1"));

        mMap.put("1", "11");
        mMap.put("1", "12");
        mMap.put("1", "13");
        mMap.put("2", "21");

        Assert.assertEquals("21", Lists.newArrayList(mMap.get("2")).get(0));
        Assert.assertEquals(3, Lists.newArrayList(mMap.get("1")).size());

        mMap.remove("1", "11");
        Assert.assertEquals(2, Lists.newArrayList(mMap.get("1")).size());
        Assert.assertEquals("12", Lists.newArrayList(mMap.get("1")).get(0));
        Assert.assertEquals("13", Lists.newArrayList(mMap.get("1")).get(1));

        mMap.removeAll("1");
        Assert.assertEquals(1, mMap.size());
        Assert.assertEquals(0, mMap.get("1").size());
        Assert.assertEquals(0, mMap.get("100").size());
    }

    @After
    public void tearDown() {
        mMap.clear();
    }

}
