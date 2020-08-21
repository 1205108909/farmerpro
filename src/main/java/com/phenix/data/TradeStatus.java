package com.phenix.data;

import lombok.Getter;

public enum TradeStatus {
	//general daily status
	UNKNOWN(-1), UNTRADABLE(0), TRADABLE(1),
	//intra day status
	AUCT(11), OBTR(12), BREAK(13), CLOSE(14), SUSP(15), HALT(16);
	@Getter
	private int value;
	TradeStatus(int Value_) {
		value = Value_;
	}
	
	public static TradeStatus parse(int val_) {		
		for(TradeStatus ts : TradeStatus.values()) {
			if(ts.getValue() == val_) {
				return ts;
			}
		}
		
		return TradeStatus.UNKNOWN;
	}
	
	public static boolean isTradable(TradeStatus ts_) {
		return ts_ == AUCT || ts_ == TRADABLE || ts_ == OBTR;
	}
}
