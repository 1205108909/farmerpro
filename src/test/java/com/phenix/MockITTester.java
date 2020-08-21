package com.phenix;

import com.google.common.collect.Lists;
import mockit.*;
import org.junit.Test;

import java.util.List;

public class MockITTester {
    @Tested
    private DataHolder holder;
    @Injectable
    @Mocked
    private List<String> sValue1 = Lists.newArrayList("a", "b", "c");
    @Injectable
    private List<String> sValue_ = Lists.newArrayList("a2", "b2", "c2");
    @Injectable
    private int value = 4;

    @Test
    public void testConstructor(@Injectable("3") int value_) {
        holder.print();
    }

    @Test
    public void testConstructor2() {
        holder.print();
    }

    @Test
    public void test3(@Injectable final MethodTest methodTest) {
        //below will throw an exception
//        new Expectations() {
//            {
//                methodTest.getValue2();
//                result = -1000;
//            }
//        };
//        System.out.println(methodTest.getValue());
    }

    @Test
    public void test4() {
        MethodTest mt = new MethodTest();
        new MockUp<MethodTest>() {
            @Mock
            public int getValue2() {
                return -2000;
            }
        };
        //System.out.println(mt.getValue2());
    }


    public static class DataHolder {
        private int value;
        private List<String> sValue;
        public DataHolder(int value_, List<String> sValue_) {
            this.value = value_;
            this.sValue = sValue_;
        }
        public void print() {
            //System.out.println("*" + getValue());
            //System.out.println(Arrays.toString(sValue.toArray()));
        }

        public int getValue() {
            return value;
        }
    }

    public static class MethodTest {
        public int getValue() {
            return getValue2();
        }

        public int getValue2() {
            return  -1;
        }
    }
}
