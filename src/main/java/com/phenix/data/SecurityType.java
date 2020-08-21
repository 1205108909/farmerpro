package com.phenix.data;

import lombok.Getter;

public enum SecurityType {
	UNKNOWN(-1), CASH(0), STOCK(1), ETF(2), INDEX_FUTURE(3), BOND_CONVERTIBLE(4), REPURCHASE(7), FUND_CLOSE(8), FUND_OPEN(9),
	INDEX(10);

	@Getter
	private int value;

	SecurityType(int value_) {
		value = value_;
	}
	
	public static SecurityType parse(String name_) {
		SecurityType t;
		
		try {
			t = SecurityType.valueOf(name_); 
		} catch (IllegalArgumentException e_) {
			t = SecurityType.valueOf(normalize(name_));
		}
		
		return t;
	}
	
	private static String normalize(String name_) {
		String normilizedName = "";
		
		switch (name_.toUpperCase()) {
		case "EQA":
			normilizedName = STOCK.name();
			break;
		case "FTR":
			normilizedName = INDEX_FUTURE.name();
			break;
		case "BDC":
			normilizedName = BOND_CONVERTIBLE.name();
			break;
		case "RPO":
			normilizedName = REPURCHASE.name();
			break;
		case "FDO":
			normilizedName = FUND_OPEN.name();
			break;
		case "FDC":
			normilizedName = FUND_CLOSE.name();
			break;
		default:
			normilizedName = UNKNOWN.name();
			break;
		}
		
		return normilizedName;
	}
}