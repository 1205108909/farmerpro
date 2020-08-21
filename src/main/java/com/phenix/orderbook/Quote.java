package com.phenix.orderbook;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Quote implements Cloneable, Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8022093555923998701L;
	@Getter
	@Setter
	private double price;
	@Getter
	@Setter
	private double qty;

	public Quote(double price_, double qty_) {
		price = price_;
		qty = qty_;
	}

	@Override
	public String toString() {
		return String.format("%s@%s", price, qty);
	}

	@Override
	public Object clone() {
		return new Quote(price, qty);
	}
	
	@Override
	public boolean equals(Object obj_) {
		if (obj_ == null) {
			return false;
		}
		if (obj_ == this) {
			return true;
		}
		if (obj_.getClass() != getClass()) {
			return false;
		}
		Quote rhs = (Quote) obj_;

		return price == rhs.price && qty == rhs.qty;
	}
}
