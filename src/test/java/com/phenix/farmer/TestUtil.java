package com.phenix.farmer;

import com.phenix.data.*;
import com.phenix.exception.IllegalDataException;
import com.phenix.math.*;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Quote;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestUtil {
	public final static double DELTA = 0.0_000_000_001; //1/billion
	public final static String DEFAULT_DATE = "20160101";
	private final static DateTimeFormatter Date_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

	public static Element str2Element(String xmlStr_) {
		SAXBuilder builder = new SAXBuilder();

		Document doc = null;
		try {
			doc = builder.build(new StringReader(xmlStr_));
		} catch (JDOMException | IOException e_) {
			throw new IllegalDataException("Unparsable Data msg_", e_);
		}

		return doc.getRootElement();// .getAttributes()
	}

	public static Order createOrder(Security sec_, OrderStatus status_, OrderSide side_, double orderQty_, double leavesQty_, double cumQty_, double price_) {
		Order order = Order.of();

		order.setSec(sec_);
		order.setOrderQty(orderQty_);
		order.setCumQty(cumQty_);
		order.setLeavesQty(leavesQty_);
		order.setOrderStatus(status_);
		order.setOrderSide(side_);
		order.setPrice(price_);

		return order;
	}

	public static ClosePosOrder createOrder(String rootOrderId_, Security sec_, OrderStatus status_, OrderSide side_, double orderQty_, double leavesQty_, double cumQty_, double price_) {
		ClosePosOrder order = new ClosePosOrder();

		order.setRootClOrdId(rootOrderId_);
		order.setSec(sec_);
		order.setOrderQty(orderQty_);
		order.setCumQty(cumQty_);
		order.setLeavesQty(leavesQty_);
		order.setOrderStatus(status_);
		order.setOrderSide(side_);
		order.setPrice(price_);

		return order;
	}

	public static Path getDir(@SuppressWarnings("rawtypes") Class clazz_) {
		Path path = null;
		try {
			path = Paths.get(clazz_.getResource(".").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return path;
	}

	public static List<OrderBook> createOrderBook(Security sec_) {
		List<OrderBook> obs = new ArrayList<>();
		List<Quote> ask = new ArrayList<Quote>();
		ask.add(new Quote(10.0, 1000));
		ask.add(new Quote(10.1, 2000));
		ask.add(new Quote(10.2, 3000));
		List<Quote> bid = new ArrayList<Quote>();
		bid.add(new Quote(9.9, 4000));
		bid.add(new Quote(9.8, 5000));
		bid.add(new Quote(9.7, 6000));

		LocalDateTime dt1 = DateUtil.getDateTime("20131206 09:25:03");
		LocalDateTime dt2 = DateUtil.getDateTime("20131206 09:30:03");
		LocalDateTime dt3 = DateUtil.getDateTime("20131206 09:30:58");
		LocalDateTime dt4 = DateUtil.getDateTime("20131206 09:31:03");
		LocalDateTime dt5 = DateUtil.getDateTime("20131206 11:29:59");
		LocalDateTime dt6 = DateUtil.getDateTime("20131206 11:30:03");
		LocalDateTime dt7 = DateUtil.getDateTime("20131206 13:00:01");
		LocalDateTime dt8 = DateUtil.getDateTime("20131206 14:56:59");
		LocalDateTime dt9 = DateUtil.getDateTime("20131206 14:57:05");
		LocalDateTime dt10 = DateUtil.getDateTime("20131206 15:00:03");
		LocalDateTime dt11 = DateUtil.getDateTime("20131206 09:25:59");

		OrderBook ob1 = OrderBook.of(dt1);
		ob1.setSecurity(sec_);
		ob1.setAsk(ask);
		ob1.setBid(bid);
		ob1.setLastPx(10.0);

		OrderBook ob2 = (OrderBook)ob1.clone();
		ob2.setDateTime(dt2);
		OrderBook ob3 = (OrderBook)ob1.clone();
		ob3.setDateTime(dt3);
		OrderBook ob4 = (OrderBook)ob1.clone();
		ob4.setDateTime(dt4);
		OrderBook ob5 = (OrderBook)ob1.clone();
		ob5.setDateTime(dt5);
		OrderBook ob6 = (OrderBook)ob1.clone();
		ob6.setDateTime(dt6);
		OrderBook ob7 = (OrderBook)ob1.clone();
		ob7.setDateTime(dt7);
		OrderBook ob8 = (OrderBook)ob1.clone();
		ob8.setDateTime(dt8);
		OrderBook ob9 = (OrderBook)ob1.clone();
		ob9.setDateTime(dt9);
		OrderBook ob10 = (OrderBook)ob1.clone();
		ob10.setDateTime(dt10);
		OrderBook ob11 = (OrderBook)ob1.clone();
		ob11.setDateTime(dt11);
		obs.clear();
		obs.add(ob1);
		obs.add(ob2);
		obs.add(ob3);
		obs.add(ob4);
		obs.add(ob5);
		obs.add(ob6);
		obs.add(ob7);
		obs.add(ob8);
		obs.add(ob9);
		obs.add(ob10);
		obs.add(ob11);

		Collections.sort(obs, OrderBook.TimeComparator);
		return obs;
	}

	public static SessionGroup buildSessionGroup(Exchange exch_) {
		if (exch_.equals(Exchange.SZ)) {
			String date = "default";
			String sessionGroup = "default.sz";
			List<TradingSession> sessions = new ArrayList<>();
			sessions.add(buildSession(date, sessionGroup, "PreMarketOpen", "00:00:00", "09:15:00", "NONE", 0, 0));
			sessions.add(buildSession(date, sessionGroup, "AMAuction", "09:15:00", "09:20:00", "NEW_LIMIT,CANCEL", 1, 1));
			sessions.add(buildSession(date, sessionGroup, "AMMatching", "09:20:00", "09:25:00", "NEW_LIMIT", 1, 1));
			sessions.add(buildSession(date, sessionGroup, "AMBlocking", "09:25:00", "09:30:00", "NONE", 0, 0));
			sessions.add(buildSession(date, sessionGroup, "AMContinuous", "09:30:00", "11:30:00", "NEW_LIMIT,NEW_MARKET,CANCEL", 0, 1));
			sessions.add(buildSession(date, sessionGroup, "LunchBreak", "11:30:00", "13:00:00", "NONE", 0, 0));
			sessions.add(buildSession(date, sessionGroup, "PMContinuous", "13:00:00", "14:57:00", "NEW_LIMIT,NEW_MARKET,CANCEL", 0, 1));
			sessions.add(buildSession(date, sessionGroup, "CloseAuction", "14:57:00", "15:00:00", "NEW_LIMIT", 3, 1));
			sessions.add(buildSession(date, sessionGroup, "MarketClose", "15:00:00", "23:59:59", "NONE", 0, 0));
			SessionGroup group = new SessionGroup(sessionGroup, sessions);
			return group;
		} else if (exch_.equals(Exchange.SS)) {
			String date = "default";
			String sessionGroup = "default.sh";
			List<TradingSession> sessions = new ArrayList<>();
			sessions.add(buildSession(date, sessionGroup, "PreMarketOpen", "00:00:00", "09:15:00", "NONE", 0, 0));
			sessions.add(buildSession(date, sessionGroup, "AMAuction", "09:15:00", "09:20:00", "NEW_LIMIT,CANCEL", 1, 1));
			sessions.add(buildSession(date, sessionGroup, "AMMatching", "09:20:00", "09:25:00", "NEW_LIMIT", 1, 1));
			sessions.add(buildSession(date, sessionGroup, "AMBlocking", "09:25:00", "09:30:00", "NONE", 0, 0));
			sessions.add(buildSession(date, sessionGroup, "AMContinuous", "09:30:00", "11:30:00", "NEW_LIMIT,NEW_MARKET,CANCEL", 0, 1));
			sessions.add(buildSession(date, sessionGroup, "LunchBreak", "11:30:00", "13:00:00", "NONE", 0, 0));
			sessions.add(buildSession(date, sessionGroup, "PMContinuous", "13:00:00", "15:00:00", "NEW_LIMIT,NEW_MARKET,CANCEL", 0, 1));
			sessions.add(buildSession(date, sessionGroup, "MarketClose", "15:00:00", "23:59:59", "NONE", 0, 0));
			SessionGroup group = new SessionGroup(sessionGroup, sessions);
			return group;
		} else {
			throw new UnsupportedOperationException("Unsupported Exchange: " + exch_);
		}
	}
	private static TradingSession buildSession(String date, String sessionGroup, String sessionType, String startTime, String endTime, String operations, int auctionFlag, int isTradable) {
		startTime = DEFAULT_DATE + " " + startTime;
		endTime = DEFAULT_DATE + " " + endTime;
		SessionType type = SessionType.valueOf(sessionType);
		LocalDateTime start = LocalDateTime.parse(startTime, Date_YYYYMMDDHHMMSS);
		LocalDateTime end = LocalDateTime.parse(endTime, Date_YYYYMMDDHHMMSS);
		AuctionType aucType = AuctionType.getAucType(auctionFlag);

		return new TradingSession(date, sessionGroup, type, start, end, operations, aucType, isTradable == 1);
	}
	public static SessionGroup buildSessionGroupMissingSession() {
		String date = "default";
		String sessionGroup = "default.sz";
		List<TradingSession> sessions = new ArrayList<>();

		sessions.add(buildSession(date, sessionGroup, "PreMarketOpen", "00:00:00", "09:15:00", "NONE", 0, 0));
		sessions.add(buildSession(date, sessionGroup, "AMAuction", "09:15:00", "09:20:00", "NEW_LIMIT,CANCEL", 1, 1));
		sessions.add(buildSession(date, sessionGroup, "AMMatching", "09:20:00", "09:25:00", "NEW_LIMIT", 1, 1));
		sessions.add(buildSession(date, sessionGroup, "AMBlocking", "09:25:00", "09:30:00", "NONE", 0, 0));
		sessions.add(buildSession(date, sessionGroup, "AMContinuous", "09:30:00", "11:30:00", "NEW_LIMIT,NEW_MARKET,CANCEL", 0, 1));
		sessions.add(buildSession(date, sessionGroup, "LunchBreak", "11:30:00", "13:00:00", "NONE", 0, 0));
		sessions.add(buildSession(date, sessionGroup, "PMContinuous", "13:00:00", "14:57:00", "NEW_LIMIT,NEW_MARKET,CANCEL", 0, 1));
		sessions.add(buildSession(date, sessionGroup, "MarketClose", "15:00:00", "23:59:59", "NONE", 0, 0));

		SessionGroup group = new SessionGroup(sessionGroup, sessions);
		return group;
	}
	public static SessionGroup buildSessionGroupInvalidStartEndTime(boolean isStartTimeInvalid) {
		String date = "default";
		String sessionGroup = "default.sz";
		List<TradingSession> sessions = new ArrayList<>();

		sessions.add(buildSession(date, sessionGroup, "PreMarketOpen", "00:00:00", "09:15:00", "NONE", 0, 0));
		sessions.add(buildSession(date, sessionGroup, "AMAuction", "09:15:00", "09:20:00", "NEW_LIMIT,CANCEL", 1, 1));
		sessions.add(buildSession(date, sessionGroup, "AMMatching", "09:20:00", "09:25:00", "NEW_LIMIT", 1, 1));
		sessions.add(buildSession(date, sessionGroup, "AMBlocking", "09:25:00", "09:30:00", "NONE", 0, 0));
		sessions.add(buildSession(date, sessionGroup, "AMContinuous", "09:30:00", "11:30:00", "NEW_LIMIT,NEW_MARKET,CANCEL", 0, 1));
		sessions.add(buildSession(date, sessionGroup, "LunchBreak", "11:30:00", "13:00:00", "NONE", 0, 0));
		sessions.add(buildSession(date, sessionGroup, "PMContinuous", "13:00:00", "14:57:00", "NEW_LIMIT,NEW_MARKET,CANCEL", 0, 1));
		sessions.add(buildSession(date, sessionGroup, "CloseAuction", "14:57:00", "15:00:00", "NEW_LIMIT", 3, 1));
		sessions.add(buildSession(date, sessionGroup, "MarketClose", "15:00:00", "23:59:59", "NONE", 0, 0));

		if (isStartTimeInvalid) {
			sessions.remove(0);
		} else {
			sessions.remove(sessions.size() - 1);
			sessions.remove(sessions.size() - 1);
		}

		SessionGroup group = new SessionGroup(sessionGroup, sessions);
		return group;
	}

	public static double computePct(double px_, double base_) {
		return Util.roundQtyNear4Digit(px_ / base_ - 1);
	}

	public static List<OrderBook> createOrderBook(List<LocalDateTime> lts_, double []px_) {
		if (lts_.size() != px_.length) {
			throw new IllegalDataException("size mismatch: size1=" + lts_.size() + " but size2=" + px_.length);
		}

		List<OrderBook> obs = new ArrayList<>(lts_.size());
		for(int i = 0; i < lts_.size(); i++) {
			OrderBook b = OrderBook.of(lts_.get(i));
			b.setLastPx(px_[i]);
			obs.add(b);
		}
		return obs;
	}
}
