package com.phenix.data;

import com.phenix.exception.UnknownValueException;
import lombok.Getter;

public enum OpenClose { 
    Open(1), Close(2);
    
    @Getter
    private int value;

    OpenClose(int value_) {
    	value = value_;
    }
    
    public static OpenClose of(int value_) {
    	if(value_ == 1 || value_ == 2)
    		return value_ == 1 ? Open : Close;
    	else {
			throw new UnknownValueException();
		}
    }
}