package com.phenix.farmer.feature;

import lombok.Getter;

public enum BarColor {
    White(0), Red(1), Green(2), UNKNOWN(-1);

    @Getter
    private int value;

    BarColor(int value_) {
        value = value_;
    }
}
