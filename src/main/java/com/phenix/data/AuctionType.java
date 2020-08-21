package com.phenix.data;

import com.phenix.exception.UnknownValueException;
import lombok.Getter;

public enum AuctionType {
    None(0), // not an auction
    AMAuction(1), PMAuction(2), CloseAuction(3), All(4);
    @Getter
    private int value;

    AuctionType(int value_) {
        value = value_;
    }

    public static AuctionType getAucType(int value) {
        switch (value) {
            case 0:
                return None;
            case 1:
                return AMAuction;
            case 2:
                return PMAuction;
            case 3:
                return CloseAuction;
            case 4:
                return All;
            default:
                throw new UnknownValueException(value + " not valid for AuctionType");
        }
    }
}
