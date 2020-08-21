package com.phenix.data;

import lombok.Getter;

public enum IndexFutureType {
	CURRENT_MONTH(1), NEXT_MONTH(2), CURRENT_QUARTER(3), NEXT_QUARTER(4);

	@Getter
	private int value;

	IndexFutureType(int value_) {
		value = value_;
	}
}