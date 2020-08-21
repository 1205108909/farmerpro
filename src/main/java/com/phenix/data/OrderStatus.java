package com.phenix.data;

import lombok.Getter;

public enum OrderStatus {
	NEW(0), PARTIAL_FILLED(1), FILLED(2), DONE_FOR_DAY(3), CANCELED(4), REPLACED(5), PENDING_CANCEL(6), STOPPED(7), REJECTED(8), SUSPENDED(9), PENDING_NEW(0xa), CALCULATED(0xb), EXPIRED(0xc), ACCEPTED_FOR_BIDDING(0xd), PENDING_REPLACE(0xe);

	@Getter
	private int value;

	OrderStatus(int value_) {
		value = value_;
	}
}