package com.phenix.farmer.signal;

import lombok.Getter;

public enum PatternLevel {
    UNKNOWN(-1), NORMAL(0), MIDDLE(1), BIG(2);

    @Getter
    private int value;//

    PatternLevel(int value_) {
        this.value = value_;
    }
}
