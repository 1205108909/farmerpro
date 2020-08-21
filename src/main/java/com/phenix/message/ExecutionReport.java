package com.phenix.message;

import com.phenix.data.OrderStatus;
import com.phenix.data.Security;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class ExecutionReport extends Message implements Serializable {
	@Getter
	@Setter
	private String execId;	
	@Getter
	@Setter
	private String clOrdId;
	@Getter
	@Setter
	private String origClOrdId;
	@Getter
	@Setter
	private Security security;
	@Getter
	@Setter
	private OrderStatus orderStatus;
	@Getter
	@Setter
	private double cumQty;
	@Getter
	@Setter
	private double leavesQty;
	@Getter
	@Setter
	private double avgPrice;
	@Getter
	@Setter
	private double lastShares;
	@Getter
	@Setter
	private double lastPx;
	@Getter
	@Setter
	private ExecType execType;
	@Getter
	@Setter
	private LocalDateTime transactionTime;
	@Getter
	@Setter
	private FixMessageType msgType;

	public String getSymbol() {
		return security.getSymbol();
	}
	
	public ExecutionReport() {
		execId = UUID.randomUUID().toString();
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
