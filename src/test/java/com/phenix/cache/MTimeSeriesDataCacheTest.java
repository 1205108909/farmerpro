package com.phenix.cache;

import com.google.common.collect.Lists;
import com.phenix.data.Security;
import com.phenix.data.SecurityType;
import com.phenix.farmer.ITimeable;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Quote;
import com.phenix.util.DateUtil;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MTimeSeriesDataCacheTest {
	List<OrderBook> orderBooks1 = new ArrayList<OrderBook>();
	List<OrderBook> orderBooks2 = new ArrayList<OrderBook>();

	MTimeSeriesDataCache<OrderBook> cache = new MTimeSeriesDataCache<OrderBook>();
	Security sec1 = Security.of("000001.sz", SecurityType.STOCK);
	Security sec2 = Security.of("600000.sh", SecurityType.STOCK);

	@Before
	public void init() {
		cache.clear();

		List<Quote> ask = new ArrayList<Quote>();
		ask.add(new Quote(10.0, 1000));
		ask.add(new Quote(10.1, 2000));
		ask.add(new Quote(10.2, 3000));

		List<Quote> bid = new ArrayList<Quote>();
		bid.add(new Quote(9.9, 4000));
		bid.add(new Quote(9.8, 5000));
		bid.add(new Quote(9.7, 6000));

		LocalDateTime dt1 = DateUtil.getDateTime("20131205 13:03:22");
		LocalDateTime dt2 = DateUtil.getDateTime("20131205 14:03:22");
		LocalDateTime dt3 = DateUtil.getDateTime("20131205 14:03:22");
		LocalDateTime dt4 = DateUtil.getDateTime("20131206 13:03:22");
		LocalDateTime dt5 = DateUtil.getDateTime("20131206 13:03:22");

		OrderBook ob1 = OrderBook.of(dt1);
		ob1.setSecurity(sec1);
		ob1.setAsk(ask);
		ob1.setBid(bid);

		OrderBook ob2 = (OrderBook) ob1.clone();
		ob2.setDateTime(dt2);

		OrderBook ob3 = (OrderBook) ob1.clone();
		ob3.setDateTime(dt3);

		OrderBook ob4 = (OrderBook) ob1.clone();
		ob4.setDateTime(dt4);

		OrderBook ob5 = (OrderBook) ob1.clone();
		ob5.setDateTime(dt5);

		orderBooks1.clear();
		orderBooks1.add(ob1);
		orderBooks1.add(ob2);
		orderBooks1.add(ob3);
		orderBooks1.add(ob4);
		orderBooks1.add(ob5);

		OrderBook ob6 = (OrderBook) ob1.clone();
		ob6.setSecurity(sec2);
		orderBooks2.add(ob6);
	}

	@Test
	public void testPutSecurityOrderBook() {
		cache.put(sec1, orderBooks1.get(0));
		cache.put(sec1, orderBooks1.get(1));

		assertEquals(2, cache.size(sec1));

		cache.putAll(sec2, orderBooks2);
		assertEquals(2, cache.size(sec1));
		assertEquals(1, cache.size(sec2));

		assertEquals(2, cache.size());
	}

	@Test
	public void testGetRange() {

		LocalDateTime dt1 = DateUtil.getDateTime("20121102 13:03:22");
		LocalDateTime dt2 = DateUtil.getDateTime("20121102 14:03:22");
		LocalDateTime dt3 = DateUtil.getDateTime("99991102 14:03:22");
		LocalDateTime dt4 = DateUtil.getDateTime("99991102 14:03:22");

		cache.putAll(sec1, orderBooks1);
		cache.putAll(sec2, orderBooks2);

		// 1.0 get nothing
		List<OrderBook> books = cache.getRange(sec1, OrderBook.of(dt1), OrderBook.of(dt2));
		assertEquals(0, books.size());
		books = cache.getRange(sec1, OrderBook.of(dt3), OrderBook.of(dt4));
		assertEquals(0, books.size());

		// 1.1 get all with date < 1stDate and date > lastDate
		dt1 = dt1.withYear(2000);
		dt2 = dt2.withYear(8888);
		books = cache.getRange(sec1, OrderBook.of(dt1), OrderBook.of(dt2));
		assertEquals(orderBooks1.size(), books.size());
		assertEquals(orderBooks1, books);

		// 1.3 get all with first date and last date
		dt1 = DateUtil.getDateTime("20131205 13:03:22");
		dt2 = DateUtil.getDateTime("20131206 13:03:22");
		books = cache.getRange(sec1, OrderBook.of(dt1), OrderBook.of(dt2));
		assertEquals(5, books.size());
		assertEquals(orderBooks1, books);

		// 1.4 get only part
		dt1 = DateUtil.getDateTime("20131205 13:04:22");
		dt2 = DateUtil.getDateTime("20131205 14:04:22");
		books = cache.getRange(sec1, OrderBook.of(dt1), OrderBook.of(dt2));
		assertEquals(2, books.size());
		assertEquals(Lists.newArrayList(orderBooks1.get(1), orderBooks1.get(2)), books);

		// 1.5 get only 1
		dt1 = DateUtil.getDateTime("20131205 13:03:22");
		dt2 = dt1;
		books = cache.getRange(sec1, OrderBook.of(dt1), OrderBook.of(dt2));
		assertEquals(1, books.size());
		assertEquals(Lists.newArrayList(orderBooks1.get(0)), books);
	}

	@Test
	public void testGet() {
		cache.putAll(sec1, orderBooks1);
		List<OrderBook> obs = cache.get(sec1);
		assertEquals(5, obs.size());
		assertEquals(true, orderBooks1.equals(obs));
	}

	@Test
	public void testRemoveRange() {
		cache.putAll(sec1, orderBooks1);
		cache.putAll(sec2, orderBooks2);

		LocalDateTime dt1 = DateUtil.getDateTime("20121102 13:03:22");
		LocalDateTime dt2 = DateUtil.getDateTime("20121102 14:03:22");
		LocalDateTime dt4 = DateUtil.getDateTime("99991102 14:03:22");
		LocalDateTime dt5 = DateUtil.getDateTime("20131205 13:03:22");

		LocalDateTime dt6 = DateUtil.getDateTime("20131205 14:03:22");
		LocalDateTime dt7 = DateUtil.getDateTime("20131206 13:03:22");

		// 1.0 remove date < 1stDate
		cache.remove(sec1, OrderBook.of(dt1));
		assertEquals(orderBooks1.size(), cache.get(sec1).size());
		assertEquals(orderBooks1, cache.get(sec1));

		cache.removeRange(sec1, OrderBook.of(dt1), OrderBook.of(dt2));
		assertEquals(orderBooks1.size(), cache.get(sec1).size());
		assertEquals(orderBooks1, cache.get(sec1));

		// 1.1 remove date > lastDate
		cache.remove(sec1, OrderBook.of(dt4));
		assertEquals(orderBooks1.size(), cache.get(sec1).size());
		assertEquals(orderBooks1, cache.get(sec1));

		// 1.2 remove the 1st
		cache.remove(sec1, OrderBook.of(dt5));
		assertEquals(orderBooks1.size() - 1, cache.get(sec1).size());
		assertEquals(orderBooks1.subList(1, orderBooks1.size()), cache.get(sec1));

		// 1.3 remove the last 4
		cache.clear();
		cache.putAll(sec1, orderBooks1);
		cache.putAll(sec2, orderBooks2);
		cache.removeRange(sec1, OrderBook.of(dt6), OrderBook.of(dt7));
		assertEquals(1, cache.get(sec1).size());

		// 1.3 remove the 2nd and 3rd
		cache.clear();
		cache.putAll(sec1, orderBooks1);
		cache.putAll(sec2, orderBooks2);
		cache.removeRange(sec1, OrderBook.of(dt6), OrderBook.of(dt6));
		assertEquals(3, cache.get(sec1).size());
		assertEquals(orderBooks1.get(0), cache.get(sec1).get(0));
		assertEquals(orderBooks1.subList(3, orderBooks1.size()), cache.get(sec1).subList(1, 3));
	}

	@Test
	public void testGetSortCounter() {
		cache.putAll(sec1, orderBooks1);
		cache.putAll(sec2, orderBooks1);

		assertEquals(0, cache.getSortCounter());

		LocalDateTime dt1 = DateUtil.getDateTime("20101205 13:03:22");
		OrderBook ob1 = OrderBook.of(dt1);
		ob1.setSecurity(sec1);
		cache.put(sec1, ob1);

		assertEquals(1, cache.getSortCounter());

		OrderBook ob2 = (OrderBook) ob1.clone();
		ob2.setSecurity(sec2);
		cache.put(sec2, ob2);

		assertEquals(2, cache.getSortCounter());
	}

	@Test
	public void testClear() {
		cache.putAll(sec1, orderBooks1);
		cache.putAll(sec2, orderBooks1);

		cache.clear();
		assertEquals(0, cache.size(sec1));
		assertEquals(0, cache.size());
	}

	@Test
	public void testRemove() {
		cache.putAll(sec1, orderBooks1);
		cache.putAll(sec2, orderBooks2);
		assertEquals(2, cache.size());

		cache.remove(sec1);
		assertEquals(1, cache.size());
	}
	
	@Test
	public void testLastBeforeOrEqual(){ 
		cache.putAll(sec1, orderBooks1);
		cache.putAll(sec2, orderBooks2);
		LocalDateTime dt1 = DateUtil.getDateTime("20131207 13:03:22");		
		
		OrderBook ob = cache.getLastBeforeOrEqual(sec1, ITimeable.defaultTimeable(dt1));
		assertEquals(true, ob.equals(cache.get(sec1).get(4)));
		
		LocalDateTime dt2 = DateUtil.getDateTime("20131206 11:03:22");	
		ob = cache.getLastBeforeOrEqual(sec1, ITimeable.defaultTimeable(dt2));
		assertEquals(true, ob.equals(cache.get(sec1).get(2)));
	}
}
