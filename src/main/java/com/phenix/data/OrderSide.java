package com.phenix.data;

import lombok.Getter;

import com.phenix.exception.UnknownValueException;

public enum OrderSide {
    BUY(1), SELL(2), UNKNOWN(-1);
    
    @Getter
    private int value;

    OrderSide(int value_) {
    	value = value_;
    }
    
    public static OrderSide of(int value_) {
    	if(value_ == 1 || value_ == 2)
    		return value_ == 1 ? BUY : SELL;
    	else {
			throw new UnknownValueException();
		}
    }

    public static OrderSide parse(String s_) {
        switch (s_) {
            case "S" : return OrderSide.SELL;
            case "B" : return OrderSide.BUY;
            default: return OrderSide.UNKNOWN;
        }
    }

    public OrderSide opposite() {
        return BUY == this ? SELL : BUY;
    }
}