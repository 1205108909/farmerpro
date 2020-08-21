package com.phenix.orderbook;

import com.google.common.base.MoreObjects;
import com.phenix.data.OrderSide;
import com.phenix.data.Security;
import com.phenix.data.SecurityType;
import com.phenix.farmer.IEvaluationData;
import com.phenix.farmer.ITimeable;
import com.phenix.util.DateUtil;
import com.phenix.util.FormattedTable;
import com.phenix.util.Util;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderBook extends TimeSeriesSecurityData implements Cloneable, Serializable, IEvaluationData, ITimeable, Comparable<OrderBook> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5661552489004666908L;

	public final static Comparator<OrderBook> ORDER_BOOK_COMPARATOR_SYMBOL_FIRST = Comparator.comparing(OrderBook::getSecurity);
	public final static Comparator<OrderBook> ORDER_BOOK_COMPARATOR_DATATIME_FIRST = (e1, e2) -> {
		int res = e1.getDateTime().compareTo(e2.getDateTime());
		if(res != 0 ) return res;
		if(SecurityType.INDEX_FUTURE == e1.getSecurityType() && SecurityType.INDEX_FUTURE != e2.getSecurityType())
			res = -1;
		else if(SecurityType.INDEX_FUTURE != e1.getSecurityType() && SecurityType.INDEX_FUTURE == e2.getSecurityType())
			res = 1;
		else
			res = e1.getSecurity().compareTo(e2.getSecurity());
		return res;
	};

	public final static Comparator<OrderBook> ORDER_BOOK_COMPARATOR_DATATIME_FIRST_STOCK_FIRST = (e1, e2) -> {
		int res = e1.getDateTime().compareTo(e2.getDateTime());
		if(res != 0 ) return res;
		if(SecurityType.STOCK == e1.getSecurityType() && SecurityType.STOCK != e2.getSecurityType())
			res = -1;
		else if(SecurityType.STOCK != e1.getSecurityType() && SecurityType.STOCK == e2.getSecurityType())
			res = 1;
		else
			res = e1.getSecurity().compareTo(e2.getSecurity());
		return res;
	};

	@Getter
	@Setter
	private List<Quote> ask;
	@Getter
	@Setter
	private List<Quote> bid;
	@Getter
	@Setter
	private double lastPx;
	@Getter
	@Setter
	private Security security;
	@Getter
	@Setter
	private double turnover;
	@Getter
	@Setter
	private double volume;
	@Getter
	@Setter
	private double accTurnover;
	@Getter
	@Setter
	private double accVolume;
	@Getter
	@Setter
	private double preClose;
	@Getter
	@Setter
	private double openPx;
	@Getter
	@Setter
	private double closePx;
	@Getter
	@Setter
	private double highPx;
	@Getter
	@Setter
	private double lowPx;
	@Getter
	@Setter
	private double preSettle;

	@Getter
	@Setter
	private long totalAskVolume;

	@Getter
	@Setter
	private long totalBidVolume;

	@Getter
	@Setter
	private LocalDateTime dateTime;

	@Getter
	@Setter
	private double highLimit;

	@Getter
	@Setter
	private double lowLimit;

	public OrderBook() {
		this(null, new ArrayList<>(), new ArrayList<>(), LocalDateTime.MIN);
	}
	
	private OrderBook(Security sec_, List<Quote> ask_, List<Quote> bid_, LocalDateTime dt_) {
		this(ask_, bid_, -1, sec_, -1, -1, -1, -1, -1, -1, -1, dt_, -1, -1);
	}
	
	private OrderBook (List<Quote>ask_, List<Quote>bid_, double lastPrice_, Security sec_, double turnover_, double volume_,
			double preClose_, double openPrice_, double closePrice_, double hp_, double lp_, LocalDateTime bookTime_, long totalAskVolume_, long totalBidVolume_) {
		this.ask = ask_;
		this.bid = bid_;
		this.lastPx = lastPrice_;
		this.security = sec_;
		this.turnover = turnover_;
		this.volume = volume_;
		this.preClose = preClose_;
		this.openPx = openPrice_;
		this.closePx = closePrice_;
		this.highPx = hp_;
		this.lowPx = lp_;
		this.dateTime = bookTime_;
		this.preSettle = this.closePx;
		this.totalAskVolume = totalAskVolume_;
		this.totalBidVolume = totalBidVolume_;
	}
	
	public static OrderBook of(List<Quote>ask_, List<Quote>bid_, double lastPrice_, Security sec_, double turnover_, double volume_,
			double preClose_, double op_, double cp_, double hp_, double lp_, LocalDateTime bookTime_) {
		return new OrderBook(ask_, bid_, lastPrice_, sec_, turnover_, volume_, preClose_, op_, cp_, hp_, lp_, bookTime_, -1, -1);
	}

	public static OrderBook of(List<Quote>ask_, List<Quote>bid_, double lastPrice_, Security sec_, double turnover_, double volume_,
							   double preClose_, double op_, double cp_, double hp_, double lp_, LocalDateTime bookTime_, long totalAskVolume_, long totalBidVolume_) {
		return new OrderBook(ask_, bid_, lastPrice_, sec_, turnover_, volume_, preClose_, op_, cp_, hp_, lp_, bookTime_, totalAskVolume_, totalBidVolume_);
	}
	
	public static OrderBook of() {
		return of(LocalDateTime.MIN);
	}
	
	public static OrderBook of(LocalDateTime dt_) {
		return of(new ArrayList<Quote>(), new ArrayList<Quote>(), dt_);
	}
	
	public static OrderBook of(List<Quote>ask_, List<Quote>bid_, LocalDateTime dt_) {
		return of(null, ask_, bid_, dt_);
	}
	
	public static OrderBook of(Security sec_, List<Quote>ask_, List<Quote>bid_, LocalDateTime dt_) {
		return new OrderBook(sec_, ask_, bid_, dt_);
	}
	
	public static OrderBook of(List<Quote>ask_, List<Quote>bid_) {
		return of(ask_, bid_, LocalDateTime.MIN);
	}
	
	public boolean isSingleSided() {
		return ask.size() + bid.size() > 0 && (ask.size() == 0 || bid.size() == 0);
	}

	public int getQuoteSize() {
		return bid.size();
	}

	public double getFarPrice(OrderSide orderSide_, int level_) {
		List<Quote> quotes = orderSide_ == OrderSide.BUY ? ask : bid;

		if(SecurityType.INDEX == security.getType() && Util.isValidLimitPrice(lastPx))
			return lastPx;
		else if (quotes.size() <= level_ || !Util.isValidPrice(quotes.get(level_).getPrice()))
			return Util.defaultPrice(orderSide_);
		else
			return quotes.get(level_).getPrice();
	}

	public double getFarPrice(OrderSide orderSide_) {
		return getFarPrice(orderSide_, 0);
	}

	public double getNearPrice(OrderSide orderSide_, int level_) {
		List<Quote> quotes = orderSide_ == OrderSide.BUY ? bid : ask;

		if(SecurityType.INDEX == security.getType() && Util.isValidLimitPrice(lastPx))
			return lastPx;
		else if (quotes.size() <= level_ || !Util.isValidPrice(quotes.get(level_).getPrice()))
			return Util.defaultPrice(orderSide_);
		else
			return quotes.get(level_).getPrice();
	}

	public double getNearPrice(OrderSide orderSide_) {
		return getNearPrice(orderSide_, 0);
	}

	public double getFarSize(OrderSide orderSide_, int level_) {
		List<Quote> quotes = orderSide_ == OrderSide.BUY ? ask : bid;

		if (quotes.size() <= level_ || !Util.isValidPrice(quotes.get(level_).getQty()))
			return Util.defaultQty(orderSide_);
		else
			return quotes.get(level_).getQty();
	}

	public double getFarSize(OrderSide orderSide_) {
		return getFarSize(orderSide_, 0);
	}

	public double getNearSize(OrderSide orderSide_, int level_) {
		List<Quote> quotes = orderSide_ == OrderSide.BUY ? bid : ask;

		if (quotes.size() <= level_ || !Util.isValidPrice(quotes.get(level_).getQty()))
			return Util.defaultQty(orderSide_);
		else
			return quotes.get(level_).getQty();
	}

	public double getNearSize(OrderSide orderSide_) {
		return getNearSize(orderSide_, 0);
	}

	public Quote getAsk(int level_) {
		if (level_ >= ask.size() || level_ < 0) {
			return new Quote(Util.DefaultSellPrice, Util.DefaultSellQty);
		} else {
			return (Quote) ask.get(level_).clone();
		}
	}

	public Quote getBid(int level_) {
		if (level_ >= bid.size() || level_ < 0) {
			return new Quote(Util.DefaultBuyPrice, Util.DefaultBuyQty);
		} else {

			return (Quote) bid.get(level_).clone();
		}
	}

	public double getAskPrice(int level_) {
		return getAsk(level_).getPrice();
	}

	public double getAskQty(int level_) {
		return getAsk(level_).getQty();
	}

	public double getBidPrice(int level_) {
		return getBid(level_).getPrice();
	}

	public double getBidQty(int level_) {
		return getBid(level_).getQty();
	}

	public String toEvaluationString() {
		StringBuilder strBuilder = new StringBuilder();

		//last
		strBuilder.append("last:").append(lastPx).append("|");

		// bid
		strBuilder.append("bid:");
		for (Quote q : bid) {
			strBuilder.append(String.format("%s,", q.toString()));
		}

		// remove the trailing ','
		if (strBuilder.charAt(strBuilder.length() - 1) == ',') {
			strBuilder.setCharAt(strBuilder.length() - 1, '|');
		}

		// ask
		strBuilder.append("ask:");
		for (int i = ask.size() - 1; i >= 0; i--) {
			strBuilder.append(String.format("%s,", ask.get(i).toString()));
		}

		if (strBuilder.charAt(strBuilder.length() - 1) == ',') {
			strBuilder.deleteCharAt(strBuilder.length() - 1);
		}

		return strBuilder.toString();
	}

	public String toIndexEvaluationString() {
		return MoreObjects.toStringHelper(this)
				.add("time", getDateTime())
				.add("sec", security.getSymbol())
				.add("cp", lastPx)
				.add("op", openPx)
				.add("hp", highPx)
				.add("lp", lowPx)
				.add("vol", volume)
				.add("turnover", turnover)
				.toString();
	}

	public double getSpread() {
		return getAskPrice(0) - getBidPrice(0);
	}
	
	public boolean isValid() {
		return security != null && ask.size() + bid.size() > 0; 
	}

	@Override
	public boolean equals(Object obj_) {
		return EqualsBuilder.reflectionEquals(this, obj_);
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException(String.format("%s can not be used as key in hash", OrderBook.class));
	}

	@Override
	public Object clone() {
		return Util.deepCopy(this);
	}

	@Override
	public String toString() {
		FormattedTable table = new FormattedTable();
		List<Object> row = new ArrayList<Object>();
		row.add("as");
		row.add("ap");
		row.add("|");
		row.add("bp");
		row.add("bs");
		table.addRow(row);

		// ask side
		int count = ask.size();
		for (int i = count - 1; i >= 0; --i) {
			row = new ArrayList<>();
			row.add(ask.get(i).getQty());
			row.add(ask.get(i).getPrice());
			row.add("|");
			row.add(" ");
			row.add(" ");
			table.addRow(row);
		}

		// bid side
		count = bid.size();
		for (int i = 0; i < count; ++i) {
			row = new ArrayList<>();
			row.add(" ");
			row.add(" ");
			row.add("|");
			row.add(bid.get(i).getPrice());
			row.add(bid.get(i).getQty());
			table.addRow(row);
		}

		return table.toString();
	}

	public String toResearchString(String delimiter_) {
		StringBuilder sb = new StringBuilder();
		return sb.append(DateUtil.time2str(getTime(), DateUtil.TIME_HHMMSS_SSS2)).append(delimiter_)
				.append(lastPx).append(delimiter_)
				.append(-1).append(delimiter_)
				.append(-1).append(delimiter_)
				.append(volume).append(delimiter_)
				.append(turnover).append(delimiter_)
				.append(openPx).append(delimiter_)
				.append(highPx).append(delimiter_)
				.append(lowPx).append(delimiter_)
				.append(preClose)
				.toString();
	}

	public String toResearchString() {
		return toResearchString(",");
	}

	private void writeObject(java.io.ObjectOutputStream s_) throws java.io.IOException {
		s_.defaultWriteObject();
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream s_) throws java.io.IOException, ClassNotFoundException {
		s_.defaultReadObject();
	}

	public ReadonlyOrderBook asReadOnly() {
		return new ReadonlyOrderBook((OrderBook) clone());
	}
	
	public LocalTime getTime() {
		return dateTime.toLocalTime();
	}

	public int getTimeInSecs() {
		return dateTime.toLocalTime().toSecondOfDay();
	}

	public LocalDate getDate() {
		return dateTime.toLocalDate();
	}

	public String getSymbol() {
		return security.getSymbol();
	}

	public SecurityType getSecurityType() {
		return security.getType();
	}

	@Override
	public int compareTo(OrderBook other_) {
		int res = other_.dateTime.compareTo(other_.dateTime);
		return res != 0 ? res : security.compareTo(other_.security);
	}
}
