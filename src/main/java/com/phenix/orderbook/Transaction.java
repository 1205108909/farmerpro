package com.phenix.orderbook;

import com.phenix.data.OrderSide;
import com.phenix.farmer.IEvaluationData;
import com.phenix.farmer.ITimeable;
import com.phenix.data.Security;
import com.phenix.util.Util;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction extends TimeSeriesSecurityData implements IEvaluationData, Serializable, Cloneable, ITimeable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8759352743389620363L;
	@Getter
	@Setter
	private double price;
	@Getter
	@Setter
	private double qty;
	@Setter
	private LocalDateTime dateTime;
	@Getter
	@Setter
	private Security security;
	@Getter
	@Setter
	private int askOrder;
	@Getter
	@Setter
	private int bidOrder;
	@Getter
	@Setter
	private OrderSide side;
	@Getter
	@Setter
	private String functionCode;

	@Override
	public boolean equals(Object obj_) {
		return EqualsBuilder.reflectionEquals(this, obj_);
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException(String.format("%s can not be used as key in hash", Transaction.class));
	}

	@Override
	public Object clone() {
		return Util.deepCopy(this);
	}

	public LocalTime getTime() {
		return dateTime.toLocalTime();
	}

	public LocalDate getDate(){
		return dateTime.toLocalDate();
	}


	private Transaction(double px_, double vol_, LocalDateTime dt_, Security sec_, int bidOrder_, int askOrder_, OrderSide side_, String functionCode_) {
		price = px_;
		qty = vol_;
		dateTime = dt_;
		security = sec_;
		bidOrder = bidOrder_;
		askOrder = askOrder_;
		side = side_;
		functionCode = functionCode_;
	}

	public String getSymbol() {
		return security.getSymbol();
	}

	public static Transaction of(double px_, double vol_, LocalDateTime dt_, Security sec_, int bidOrder_, int askOrder_, OrderSide side_, String functionCode_) {
		return new Transaction(px_, vol_, dt_, sec_, bidOrder_, askOrder_, side_, functionCode_);
	}


	@Override
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public boolean isValid() {
		return security != null && price != 0 && qty != 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName())
				.append("[security=").append(security.getSymbol())
				.append(", time=").append(dateTime.toLocalTime())
				.append(", price=").append(price)
				.append(", qty=").append(qty)
				.append(", bidOrder=").append(bidOrder)
				.append(", askOrder=").append(askOrder)
				.append(", orderSide=").append(side).append("]");
		return sb.toString();
	}
}
