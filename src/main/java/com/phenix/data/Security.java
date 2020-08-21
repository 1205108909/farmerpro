package com.phenix.data;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Security implements Serializable, Comparable<Security>, Basketable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2174356994537890017L;
	
	public final static Security Cash = Security.of("CASH", SecurityType.CASH, TradeStatus.TRADABLE, Exchange.UNKNOWN, "CASH");
	public final static Comparator<Security> DefaultSecurityComparator = Comparator.comparing(Security::getSymbol);

	public final static Security INDEX_HS300 = Security.of("000300.sh", SecurityType.INDEX, TradeStatus.TRADABLE, Exchange.SS, "hs300");
	public final static Security INDEX_ZZ500 = Security.of("000905.sh", SecurityType.INDEX, TradeStatus.TRADABLE, Exchange.SS, "zz500");
	public final static Security INDEX_SZ50 = Security.of("000016.sh", SecurityType.INDEX, TradeStatus.TRADABLE, Exchange.SS, "sz50");
	public final static Security INDEX_ZZ1000 = Security.of("000852.sh", SecurityType.INDEX, TradeStatus.TRADABLE, Exchange.SS, "zz1000");
	public final static Security STOCK_DEFAULT_SH = Security.of("default.sh", SecurityType.STOCK, TradeStatus.TRADABLE, Exchange.SS, "default.sh");
	public final static Security STOCK_DEFAULT_SZ = Security.of("default.sz", SecurityType.STOCK, TradeStatus.TRADABLE, Exchange.SZ, "default.sz");
	public final static Security UNKNOWN = Security.of("xxxxxx.xx", SecurityType.UNKNOWN, TradeStatus.UNKNOWN, Exchange.UNKNOWN, "UNKNOWN");

	@Getter
	private SecurityType type;
	@Getter
	private String symbol;
	@Getter
	private TradeStatus tradeStatus;
	@Getter
	private String name;
	@Getter
	private Exchange exchange;
	
	public static Security of(String symbol_, SecurityType type_) {
		return of(symbol_, type_, TradeStatus.UNKNOWN, Exchange.UNKNOWN, symbol_);
	}
	
	public static Security of(String symbol_, SecurityType type_, TradeStatus tradeStatus_, Exchange exchange_, String name_) {
		return new Security(symbol_, type_, tradeStatus_, exchange_, name_);
	}
	
	public Security(String symbol_, SecurityType type_, TradeStatus tradeStatus_, Exchange exchange_, String name_) {
		symbol = symbol_;
		type = type_;
		tradeStatus = tradeStatus_;
		exchange = exchange_;
		name = name_;
	}

	public Security() {}

	public String getSimpleSymbol() {
		return StringUtils.substringBefore(symbol, ".");
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, symbol);
	}

	/**
	public final static int hashCode(SecurityType t_, String symbol) {
		return Objects.hash(t_, symbol);
	}
	*/

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
		Security rhs = (Security) obj_;
		return type.equals(rhs.type) && symbol.equals(rhs.symbol);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "@[symbol=" + symbol + ", type=" + type.toString() + "]";  
	}

	@Override
	public int compareTo(Security o) {
		return DefaultSecurityComparator.compare(this, o);
	}

	@Override
	public int getBasketID() {
		return hashCode();
	}

	public boolean isStock(){
		return SecurityType.STOCK == type;
	}

	public boolean isIndex(){
		return SecurityType.INDEX == type;
	}

	public boolean isRepo() {
		return SecurityType.REPURCHASE == type;
	}

	public boolean isExchangeTradedFund() {
		return SecurityType.ETF == type || SecurityType.FUND_OPEN == type || SecurityType.FUND_CLOSE == type;
	}

	public boolean isIndexFuture() {
		return SecurityType.INDEX_FUTURE == type;
	}

	public boolean isConvertibleBond() {
		return type == SecurityType.BOND_CONVERTIBLE ;
	}
}
