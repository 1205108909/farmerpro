package com.phenix.data;

public enum Direction {
	LONG, SHORT, UNKNOWN;

	public Direction opposite() {
		if(UNKNOWN == this) {
			return UNKNOWN;
		}
		return LONG == this ? SHORT : LONG;
	}
}
