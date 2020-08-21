package com.phenix.data;

import lombok.Getter;

import com.phenix.exception.UnknownValueException;

public enum PlacementCategory {
	AGGRESSIVE(1), PASSIVE(2), ULTRA_PASSIVE(3);

	@Getter
	private int value;

	PlacementCategory(int value_) {
		value = value_;
	}

	public static PlacementCategory of(int value_) {
		if (value_ == 1 || value_ == 2 || value_ == 3)
			return value_ == 1 ? AGGRESSIVE : PASSIVE;
		else {
			throw new UnknownValueException();
		}
	}
}