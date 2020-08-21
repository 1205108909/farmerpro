package com.phenix.farmer.signal;

import com.phenix.data.Basket;
import com.phenix.data.Exchange;
import com.phenix.data.Order;
import com.phenix.data.SessionGroup;
import com.phenix.farmer.*;
import com.phenix.farmer.config.Environment;
import com.phenix.farmer.config.InstanceConfig;
import mockit.Mock;
import mockit.MockUp;
import org.junit.BeforeClass;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Consumer;

public class BaseSignalTest {
    public final static LocalDateTime TIME_NOW = LocalDateTime.now();
    private final static InstanceConfig config = new InstanceConfig(Environment.DEV, TIME_NOW, TIME_NOW,
            3, SignalMode.BACK_TEST, 0.001, LocalTime.MAX, 4096 * 8,
            "d:/work/model_prod/pattern_universe.csv", null,
            null, null, null, RunningMode.LOCAL, null);

    /*
     * Note: All Singleton should be mocked in a static manner
     *
     */
    @BeforeClass
    public static void setUpClass() {
        new MockUp<FarmerDataManager>() {
            @Mock
            public SessionGroup getSessionGroup(Exchange exch_) {
                if (exch_ == Exchange.SZ) {
                    return TestUtil.buildSessionGroup(Exchange.SZ);
                } else if (exch_ == Exchange.SS) {
                    return TestUtil.buildSessionGroup(Exchange.SZ);
                } else if (exch_ == Exchange.CFFEX) {
                    return TestUtil.buildSessionGroup(Exchange.SZ);
                } else {
                    throw new UnsupportedOperationException("Unsupported Exchange: " + exch_);
                }
            }
        };

        new MockUp<FarmerConfigManager>() {
            @Mock
            public InstanceConfig getInstanceConfig() {
                return config;
            }
        };

        new MockUp<FarmerController>() {
            @Mock
            public Consumer getOrderBookUpdateEventConsumer() {
                return (e) -> {
                    //System.out.println(e + " was accepted");
                };
            }
        };

        new MockUp<PositionBalanceManager>() {
            @Mock
            public boolean handleOpenPosition(SignalType st_, Basket<? extends Order> basket_, LocalDateTime dt_) {
                return true;
            }
            @Mock
            public void handleUpdatePosition(Order o_) {
                return;
            }
        };
    }
}
