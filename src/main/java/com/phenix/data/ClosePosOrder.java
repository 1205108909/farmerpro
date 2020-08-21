package com.phenix.data;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClosePosOrder extends Order {
	@Getter
	@Setter
	private String rootClOrdId;	
	@Getter
	@Setter
	private CloseOrderType type;
	@Setter
	@Getter
	private boolean closeIntraDay;

	//put here temporarily for bact test purpose
	@Getter
	@Setter
	private double realizedSpread;

	public ClosePosOrder() {
		super(UUID.randomUUID().toString(),null, -1, -1, LocalDateTime.MAX);
		closeIntraDay = false;
		realizedSpread = 0;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean isClosePosOrder() {
		return true;
	}
}
