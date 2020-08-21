package com.phenix.farmer.config;

import lombok.Getter;

public enum Environment {
    DEV(1), QA(2), PROD(3);

    @Getter
    private int value;

    Environment(int value_) {
        value = value_;
    }
}
