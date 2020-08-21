package com.phenix.farmer.signal;

import lombok.Getter;

public enum SignalMode {
    BACK_TEST(0), PAPER_TEST(1), PRECISE_BACK_TEST(2), PROD(3), MANUAL(4), KLINE(5), ALPHA(6);

    public final static boolean isTestingMode(SignalMode m_) {
        return BACK_TEST == m_ || PAPER_TEST == m_ || PRECISE_BACK_TEST == m_;
    }

    public final static boolean isBackTestMode(SignalMode m_){
        return BACK_TEST == m_ || PRECISE_BACK_TEST == m_;
    }

    @Getter
    private int value;
    SignalMode(int type_) {
        this.value = type_;
    }
}
