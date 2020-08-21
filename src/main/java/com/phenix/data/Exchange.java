package com.phenix.data;

import lombok.Getter;

import com.phenix.exception.UnknownValueException;

public enum Exchange {
	SS(1), SZ(2), CFFEX(3), SI(4), HK(5), UNKNOWN(9999);
    
    @Getter
    private int value;

    Exchange(int value_) {
    	value = value_;
    }
    
    public static Exchange of(int value_) {
    	if(value_ == 1 || value_ == 2)
    		return value_ == 1 ? SS : SZ;
    	else {
			throw new UnknownValueException();
		}
    }
    
    public static Exchange parse(String name_) {
    	Exchange t;
		
		try {
			t = Exchange.valueOf(name_); 
		} catch (IllegalArgumentException e_) {
			t = Exchange.valueOf(normalize(name_));
		}
		
		return t;
	}
	
	private static String normalize(String name_) {
		String normilizedName = "";
		
		switch (name_.toUpperCase()) {
			case "SH":
				normilizedName = SS.name();
				break;
			case "SZ":
				normilizedName = SZ.name();
				break;
			case "CFE":
				normilizedName = CFFEX.name();
				break;
			case "IF":
				normilizedName = CFFEX.name();
				break;
			case "SI":
				normilizedName = SI.name();
				break;
			case "HK":
				normilizedName = HK.name();
				break;
			default:
				normilizedName = UNKNOWN.name();
				break;
		}
		
		return normilizedName;
	}
}
