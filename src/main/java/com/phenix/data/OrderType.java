package com.phenix.data;

import com.phenix.exception.UnknownValueException;
import lombok.Getter;

public enum OrderType {
    MARKET(1), LIMIT(2), UNKNOWN(-1);

    @Getter
    private int value;

    OrderType(int value_) {
        value = value_;
    }

    public static OrderType of(int value_) {
        if(value_ == 1 || value_ == 2)
            return value_ == 1 ? MARKET : LIMIT;
        else {
            throw new UnknownValueException();
        }
    }

    public static OrderType parse(String s_) {
        switch (s_) {
            case "1": return OrderType.MARKET;
            case "0": return OrderType.LIMIT;
            default: return OrderType.UNKNOWN;
        }
    }
}