package com.phenix.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UtilTest {
    @Test
    public void testMerge() {
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();

        l1.add("a,b,c,d");
        l1.add("1,2,3,4");
        l1.add("7,8,9,10");
        l2.add("h,i,j");

        List<String> l3 = Util.merge(l1, l2, true);
        List<String> expected = new ArrayList<>();
        expected.add("a,b,c,d,h,i,j");
        expected.add("1,2,3,4,,,");
        expected.add("7,8,9,10,,,");
        Assert.assertEquals(expected, l3);
    }
}
