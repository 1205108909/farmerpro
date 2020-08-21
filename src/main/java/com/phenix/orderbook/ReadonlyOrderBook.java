package com.phenix.orderbook;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.phenix.data.OrderSide;
import com.phenix.data.Security;

public class ReadonlyOrderBook extends OrderBook implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4329230122806461899L;
	private OrderBook book;
	
	public ReadonlyOrderBook(OrderBook book_) {
		book = book_;
	}

	@Override
	public List<Quote> getAsk() {
		return book.getAsk();
	}

	@Override
	public void setAsk(List<Quote> ask) {
		throw new UnsupportedOperationException();
	} 

	@Override
	public List<Quote> getBid() {
		return book.getBid();
	}

	@Override
	public void setBid(List<Quote> bid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getLastPx() {
		return book.getLastPx();
	}

	@Override
	public void setLastPx(double lastPx_) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Security getSecurity() {
		return book.getSecurity();
	}

	@Override
	public void setSecurity(Security security_) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getTurnover() {
		return book.getTurnover();
	}

	@Override
	public void setTurnover(double turnover) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getVolume() {
		return book.getVolume();
	}

	@Override
	public void setVolume(double volume) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getPreClose() {
		return book.getPreClose();
	}

	@Override
	public void setPreClose(double preClose) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getOpenPx() {
		return book.getOpenPx();
	}

	@Override
	public void setOpenPx(double openPx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getClosePx() {
		return book.getClosePx();
	}

	@Override
	public void setClosePx(double closePx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getHighPx() {
		return book.getHighPx();
	}

	@Override
	public void setHighPx(double highPx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getLowPx() {
		return book.getLowPx();
	}

	@Override
	public void setLowPx(double lowPx) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public LocalDateTime getDateTime() {
		return book.getDateTime();
	}
	
	@Override
	public void setDateTime(LocalDateTime timeNow_) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean isSingleSided() {
		return book.isSingleSided();
	}

	@Override
	public double getFarPrice(OrderSide orderSide_, int level_) {
		return book.getFarPrice(orderSide_, level_);
	}

	@Override
	public double getFarPrice(OrderSide orderSide_) {
		return book.getFarPrice(orderSide_);
	}

	@Override
	public double getNearPrice(OrderSide orderSide_, int level_) {
		return book.getNearPrice(orderSide_, level_);
	}

	@Override
	public double getNearPrice(OrderSide orderSide_) {
		return book.getNearPrice(orderSide_);
	}

	@Override
	public double getFarSize(OrderSide orderSide_, int level_) {
		return book.getFarSize(orderSide_, level_);
	}

	@Override
	public double getFarSize(OrderSide orderSide_) {
		return book.getFarSize(orderSide_);
	}

	@Override
	public double getNearSize(OrderSide orderSide_, int level_) {
		return book.getNearPrice(orderSide_, level_);
	}

	@Override
	public double getNearSize(OrderSide orderSide_) {
		return book.getNearSize(orderSide_);
	}

	@Override
	public Quote getAsk(int level_) {
		return book.getAsk(level_);
	}

	@Override
	public Quote getBid(int level_) {
		return book.getBid(level_);
	}

	@Override
	public double getAskPrice(int level_) {
		return book.getAskPrice(level_);
	}

	@Override
	public double getAskQty(int level_) {
		return book.getAskQty(level_);
	}

	@Override
	public double getBidPrice(int level_) {
		return book.getBidPrice(level_);
	}

	@Override
	public double getBidQty(int level_) {
		return book.getBidQty(level_);
	}

	@Override
	public String toEvaluationString() {
		return book.toEvaluationString();
	}

	@Override
	public double getSpread() {
		return book.getSpread();
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
		ReadonlyOrderBook ob = (ReadonlyOrderBook) obj_;
		return book.equals(ob.book);
	}

	@Override
	public int hashCode() {
		return book.hashCode();
	}

	@Override
	public Object clone() {
		OrderBook bookNew = (OrderBook) book.clone();
		return new ReadonlyOrderBook(bookNew);
	}
}
