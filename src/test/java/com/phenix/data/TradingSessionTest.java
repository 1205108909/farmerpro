package com.phenix.data;

import com.phenix.farmer.TestUtil;
import com.phenix.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TradingSessionTest {
	private SessionGroup sgSh = TestUtil.buildSessionGroup(Exchange.SS);
	private SessionGroup sgSz = TestUtil.buildSessionGroup(Exchange.SZ);
	private LocalDate date20160101 = DateUtil.getDate(TestUtil.DEFAULT_DATE);

	@Test
	public void testIsAMAuction() {
		LocalDateTime dt1 = LocalDateTime.of(date20160101, DateUtil.getTime("09:15:00000"));
		LocalDateTime dt2 = LocalDateTime.of(date20160101, DateUtil.getTime("09:15:01000"));
		LocalDateTime dt3 = LocalDateTime.of(date20160101, DateUtil.getTime("09:23:00000"));
		LocalDateTime dt4 = LocalDateTime.of(date20160101, DateUtil.getTime("09:25:00000"));

		boolean actual1 = sgSh.isAMAuctionSession(dt1, IntervalType.End);
		boolean actual2 = sgSz.isAMAuctionSession(dt2, IntervalType.End);
		boolean actual3 = sgSz.isAMAuctionSession(dt3, IntervalType.End);
		boolean actual4 = sgSz.isAMAuctionSession(dt4, IntervalType.End);
		boolean actual5 = sgSz.isAMAuctionSession(dt4, IntervalType.Start);

		Assert.assertEquals(false, actual1);
		Assert.assertEquals(true, actual2);
		Assert.assertEquals(true, actual3);
		Assert.assertEquals(true, actual4);
		Assert.assertEquals(false, actual5);
	}

	@Test
	public void testIsPMAuction() {
		LocalDateTime dt1 = LocalDateTime.of(date20160101, DateUtil.getTime( "12:00:00000"));
		LocalDateTime dt2 = LocalDateTime.of(date20160101, DateUtil.getTime( "07:00:00000"));
		LocalDateTime dt3 = LocalDateTime.of(date20160101, DateUtil.getTime( "14:23:00000"));
		LocalDateTime dt4 = LocalDateTime.of(date20160101, DateUtil.getTime( "14:27:00000"));
		LocalDateTime dt5 = LocalDateTime.of(date20160101, DateUtil.getTime( "15:00:00000"));

		boolean actual1 = sgSh.isPMAuctionSession(dt1,IntervalType.End);
		boolean actual2 = sgSh.isPMAuctionSession(dt2,IntervalType.End);
		boolean actual3 = sgSh.isPMAuctionSession(dt3,IntervalType.End);
		boolean actual4 = sgSz.isPMAuctionSession(dt4,IntervalType.End);
		boolean actual5 = sgSz.isPMAuctionSession(dt5,IntervalType.End);
		boolean actual6 = sgSz.isPMAuctionSession(dt5,IntervalType.Start);

		Assert.assertEquals(false, actual1);
		Assert.assertEquals(false, actual2);
		Assert.assertEquals(false, actual3);
		Assert.assertEquals(false, actual4);
		Assert.assertEquals(false, actual5);
		Assert.assertEquals(false, actual6);
	}

	@Test
	public void testIsClsAuction() {
		LocalDateTime dt1 = LocalDateTime.of(date20160101, DateUtil.getTime( "12:00:00000"));
		LocalDateTime dt2 = LocalDateTime.of(date20160101, DateUtil.getTime( "07:00:00000"));
		LocalDateTime dt3 = LocalDateTime.of(date20160101, DateUtil.getTime( "14:23:00000"));
		LocalDateTime dt4 = LocalDateTime.of(date20160101, DateUtil.getTime( "14:27:00000"));
		LocalDateTime dt5 = LocalDateTime.of(date20160101, DateUtil.getTime( "15:00:00000"));

		boolean actual1 = sgSh.isClsAuctionSession(dt1,IntervalType.End);
		boolean actual2 = sgSh.isClsAuctionSession(dt2,IntervalType.End);
		boolean actual3 = sgSh.isClsAuctionSession(dt3,IntervalType.End);
		boolean actual4 = sgSz.isClsAuctionSession(dt4,IntervalType.End);
		boolean actual5 = sgSz.isClsAuctionSession(dt5,IntervalType.End);
		boolean actual6 = sgSz.isClsAuctionSession(dt5,IntervalType.Start);

		Assert.assertEquals(false, actual1);
		Assert.assertEquals(false, actual2);
		Assert.assertEquals(false, actual3);
		Assert.assertEquals(false, actual4);
		Assert.assertEquals(true, actual5);
		Assert.assertEquals(false, actual6);
	}

	@Test
	public void testIsTradable() {
		LocalDateTime dt1 = LocalDateTime.of(date20160101, DateUtil.getTime("07:30:22000"));
		LocalDateTime dt2 = LocalDateTime.of(date20160101, DateUtil.getTime("15:44:20000"));
		LocalDateTime dt3 = LocalDateTime.of(date20160101, DateUtil.getTime("12:00:22000"));
		LocalDateTime dt4 = LocalDateTime.of(date20160101, DateUtil.getTime("09:16:00000"));
		LocalDateTime dt5 = LocalDateTime.of(date20160101, DateUtil.getTime("09:20:00000"));
		LocalDateTime dt6 = LocalDateTime.of(date20160101, DateUtil.getTime("09:30:00000"));
		LocalDateTime dt7 = LocalDateTime.of(date20160101, DateUtil.getTime("15:00:00000"));

		boolean actual1 = sgSh.isTradable(dt1, IntervalType.End, AuctionType.All);
		boolean actual2 = sgSh.isTradable(dt2, IntervalType.End, AuctionType.All);
		boolean actual3 = sgSz.isTradable(dt3, IntervalType.End, AuctionType.All);
		Assert.assertEquals(false, actual1);
		Assert.assertEquals(false, actual2);
		Assert.assertEquals(false, actual3);

		boolean actual4 = sgSh.isTradable(dt4, IntervalType.End, AuctionType.All);
		boolean actual5 = sgSz.isTradable(dt5, IntervalType.End, AuctionType.All);
		boolean actual6 = sgSz.isTradable(dt6, IntervalType.End, AuctionType.All);
		boolean actual61 = sgSz.isTradable(dt6, IntervalType.Start, AuctionType.All);
		boolean actual7 = sgSz.isTradable(dt7, IntervalType.End, AuctionType.All);

		Assert.assertEquals(true, actual4);
		Assert.assertEquals(true, actual5);
		Assert.assertEquals(false, actual6);
		Assert.assertEquals(true, actual61);
		Assert.assertEquals(true, actual7);

		boolean actual8 = sgSh.isTradable(dt5, IntervalType.End, AuctionType.None);
		boolean actual81 = sgSh.isTradable(dt5, IntervalType.End, AuctionType.AMAuction);
		boolean actual82 = sgSh.isTradable(dt5, IntervalType.End, AuctionType.PMAuction);
		boolean actual83 = sgSh.isTradable(dt5, IntervalType.Start, AuctionType.AMAuction);
		Assert.assertEquals(false, actual8);
		Assert.assertEquals(true, actual81);
		Assert.assertEquals(false, actual82);
		Assert.assertEquals(true, actual83);

		boolean actual9 = sgSz.isTradable(dt7, IntervalType.End, AuctionType.None);
		Assert.assertEquals(false, actual9);

		boolean actual10 = sgSz.isTradable(dt7, IntervalType.End, AuctionType.None);
		boolean actual101 = sgSz.isTradable(dt7, IntervalType.Start, AuctionType.None);
		boolean actual102 = sgSz.isTradable(dt7, IntervalType.End, AuctionType.CloseAuction);
		boolean actual103 = sgSz.isTradable(dt7, IntervalType.Start, AuctionType.CloseAuction);
		Assert.assertEquals(false, actual10);
		Assert.assertEquals(false, actual101);
		Assert.assertEquals(true, actual102);
		Assert.assertEquals(false, actual103);
	}

	@Test
	public void testGetOpenTime() {
		LocalDateTime marketOpen1 = sgSh.getMarketOpen(AuctionType.AMAuction);
		LocalDateTime marketOpen2 = sgSz.getMarketOpen(AuctionType.None);
		LocalDateTime marketOpen3 = sgSz.getMarketOpen(AuctionType.PMAuction);
		LocalDateTime marketOpen4 = sgSz.getMarketOpen(AuctionType.All);

		LocalDateTime expected1 = LocalDateTime.of(date20160101, DateUtil.getTime( "09:15:00000"));
		LocalDateTime expected2 = LocalDateTime.of(date20160101, DateUtil.getTime( "09:30:00000"));

		Assert.assertEquals(expected1, marketOpen1);
		Assert.assertEquals(expected2, marketOpen2);
		Assert.assertEquals(expected2, marketOpen3);
		Assert.assertEquals(expected1, marketOpen4);
	}

	@Test
	public void testGetCloseTime() {
		LocalDateTime marketClose1 = sgSh.getMarketClose(AuctionType.CloseAuction);
		LocalDateTime marketClose2 = sgSz.getMarketClose(AuctionType.None);
		LocalDateTime marketClose3 = sgSz.getMarketClose(AuctionType.All);
		LocalDateTime marketClose4 = sgSz.getMarketClose(AuctionType.AMAuction);
		LocalDateTime marketClose5 = sgSz.getMarketClose(AuctionType.None);
		LocalDateTime expected1 = LocalDateTime.of(date20160101, DateUtil.getTime( "15:00:00000"));
		LocalDateTime expected2 = LocalDateTime.of(date20160101, DateUtil.getTime( "14:57:00000"));

		Assert.assertEquals(expected1, marketClose1);
		Assert.assertEquals(expected2, marketClose2);
		Assert.assertEquals(expected1, marketClose3);
		Assert.assertEquals(expected2, marketClose4);
		Assert.assertEquals(expected2, marketClose5);
	}

	@Test
	public void testEquals() {
		TradingSession ts1 = sgSh.getSessions().get(0);
		TradingSession ts2 = new TradingSession(ts1.getDate(),
				ts1.getSessionGroup(),
				ts1.getSessionType(),
				ts1.getStartTime(),
				ts1.getEndTime(),
				new ArrayList<>(ts1.getOperations()),
				ts1.getAuctionType(),
				ts1.isTradable());

		TradingSession ts3 = new TradingSession(ts1.getDate(),
				ts1.getSessionGroup(),
				ts1.getSessionType(),
				ts1.getStartTime(),
				ts1.getEndTime(),
				new ArrayList<>(ts1.getOperations()),
				ts1.getAuctionType(),
				!ts1.isTradable());

		Assert.assertEquals(true, ts1.equals(ts2));
		Assert.assertEquals(false, ts1.equals(ts3));
	}

	@Test
	public void testGetSessionByTime() {
		LocalDateTime dt1 = LocalDateTime.of(date20160101, DateUtil.getTime( "09:15:00000"));

		TradingSession preMarketOpen = sgSh.getSessionByType(SessionType.PreMarketOpen);
		TradingSession amAuction = sgSh.getSessionByType(SessionType.AMAuction);
		TradingSession session1 = sgSh.getSessionByTime(dt1, IntervalType.End);
		TradingSession session2 = sgSh.getSessionByTime(dt1, IntervalType.Start);

		Assert.assertEquals(preMarketOpen, session1);
		Assert.assertEquals(amAuction, session2);
	}

	@Test
	public void testGetNextSession() {
		LocalDateTime dt1 = LocalDateTime.of(date20160101, DateUtil.getTime("09:15:00000"));

		TradingSession amAuction = sgSh.getSessionByType(SessionType.AMAuction);
		TradingSession amMatching = sgSh.getSessionByType(SessionType.AMMatching);
		TradingSession session1 = sgSh.getNextSession(dt1, IntervalType.End);
		TradingSession session2 = sgSh.getNextSession(dt1, IntervalType.Start);

		Assert.assertEquals(amAuction, session1);
		Assert.assertEquals(amMatching, session2);
	}

	@Test
	public void testGetNextTradableSession() {
		LocalDateTime dt1 = LocalDateTime.of(date20160101, DateUtil.getTime(  "09:15:00000"));
		LocalDateTime dt2 = LocalDateTime.of(date20160101, DateUtil.getTime(  "09:40:00000"));
		LocalDateTime dt3 = LocalDateTime.of(date20160101, DateUtil.getTime(  "16:40:00000"));

		TradingSession pmContinuous = sgSz.getSessionByType(SessionType.PMContinuous);
		TradingSession amMatching = sgSh.getSessionByType(SessionType.AMMatching);
		TradingSession amAuction = sgSh.getSessionByType(SessionType.AMAuction);
		TradingSession session1 = sgSh.getNextTradableSession(dt1, IntervalType.End, AuctionType.All);
		TradingSession session2 = sgSh.getNextTradableSession(dt1, IntervalType.Start, AuctionType.All);
		TradingSession session3 = sgSz.getNextTradableSession(dt2, IntervalType.End, AuctionType.All);
		System.out.println("dt3:"+dt3);

		TradingSession session4 = sgSz.getNextTradableSession(dt3, IntervalType.End, AuctionType.All);
		System.out.println("dt3:"+session4);
		Assert.assertEquals(amAuction, session1);
		Assert.assertEquals(amMatching, session2);
		Assert.assertEquals(pmContinuous, session3);
		Assert.assertEquals(true,session4==null);

		TradingSession amContinuous = sgSz.getSessionByType(SessionType.AMContinuous);
		TradingSession session5 = sgSz.getNextTradableSession(dt1, IntervalType.Start, AuctionType.None);
		TradingSession session6 = sgSz.getNextTradableSession(dt1, IntervalType.Start, AuctionType.CloseAuction);
		TradingSession session7 = sgSh.getNextTradableSession(dt1, IntervalType.Start, AuctionType.All);
		TradingSession session8 = sgSh.getNextTradableSession(dt1, IntervalType.Start, AuctionType.AMAuction);
		Assert.assertEquals(amContinuous, session5);
		Assert.assertEquals(amContinuous, session6);
		Assert.assertEquals(amMatching, session7);
		Assert.assertEquals(amMatching, session8);

		LocalDateTime dt4 = LocalDateTime.of(date20160101, DateUtil.getTime( "14:00:00000"));
		TradingSession szPmNext = sgSz.getNextTradableSession(dt4, IntervalType.Start, AuctionType.None);
		Assert.assertNull(szPmNext);

		TradingSession szPmAuction = sgSz.getSessionByType(SessionType.CloseAuction);
		szPmNext = sgSz.getNextTradableSession(dt4, IntervalType.Start, AuctionType.All);
		Assert.assertEquals(szPmAuction, szPmNext);

		szPmNext = sgSz.getNextTradableSession(dt4, IntervalType.Start, AuctionType.CloseAuction);
		Assert.assertEquals(szPmAuction, szPmNext);

		szPmNext = sgSz.getNextTradableSession(dt4, IntervalType.Start, AuctionType.AMAuction);
		Assert.assertNull(szPmNext);

		szPmNext = sgSz.getNextTradableSession(dt4, IntervalType.Start, AuctionType.PMAuction);
		Assert.assertNull(szPmNext);
	}

	@Test
	public void testGetPrevSession() {
		LocalDateTime dt1 = LocalDateTime.of(date20160101, DateUtil.getTime(  "09:15:00000"));
		TradingSession preMarketOpen = sgSh.getSessionByType(SessionType.PreMarketOpen);
		TradingSession session1 = sgSh.getPrevSession(dt1, IntervalType.End);
		TradingSession session2 = sgSh.getPrevSession(dt1, IntervalType.Start);

		Assert.assertNull(session1);
		Assert.assertEquals(preMarketOpen, session2);
	}

	@Test
	public void testGetPrevTradableSession() {
		LocalDateTime dt1 = LocalDateTime.of(date20160101, DateUtil.getTime( "09:15:00000"));
		LocalDateTime dt2 = LocalDateTime.of(date20160101, DateUtil.getTime( "09:40:00000"));
		LocalDateTime dt3 = LocalDateTime.of(date20160101, DateUtil.getTime( "16:40:00000"));
		LocalDateTime dt4 = LocalDateTime.of(date20160101, DateUtil.getTime( "13:00:00000"));

		TradingSession closeAunction = sgSz.getSessionByType(SessionType.CloseAuction);
		TradingSession amMatching = sgSz.getSessionByType(SessionType.AMMatching);
		TradingSession amContinuous = sgSz.getSessionByType(SessionType.AMContinuous);
		TradingSession pmContinuous = sgSz.getSessionByType(SessionType.PMContinuous);
		TradingSession clsAuction = sgSz.getSessionByType(SessionType.CloseAuction);
		TradingSession session1 = sgSh.getPrevTradableSession(dt1, IntervalType.End, AuctionType.All);
		TradingSession session2 = sgSh.getPrevTradableSession(dt1, IntervalType.Start, AuctionType.All);
		TradingSession session3 = sgSz.getPrevTradableSession(dt2, IntervalType.End, AuctionType.All);
		TradingSession session4 = sgSz.getPrevTradableSession(dt3, IntervalType.End, AuctionType.All);
		TradingSession session5 = sgSz.getPrevTradableSession(dt4, IntervalType.Start, AuctionType.All);
		TradingSession session6 = sgSz.getPrevTradableSession(dt3, IntervalType.Start, AuctionType.AMAuction);
		TradingSession session7 = sgSz.getPrevTradableSession(dt3, IntervalType.Start, AuctionType.All);
		TradingSession session8 = sgSz.getPrevTradableSession(dt2, IntervalType.Start, AuctionType.None);

		Assert.assertNull(session1);
		Assert.assertNull(session2);
		Assert.assertEquals(amMatching, session3);
		Assert.assertEquals(closeAunction, session4);
		Assert.assertEquals(amContinuous, session5);
		Assert.assertEquals(pmContinuous, session6);
		Assert.assertEquals(clsAuction, session7);
		Assert.assertNull(session8);
	}

	@Test
	public void testGetFirstTradableSession() {
		TradingSession amAuction = sgSh.getSessionByType(SessionType.AMAuction);
		TradingSession amContinuous = sgSh.getSessionByType(SessionType.AMContinuous);

		Assert.assertEquals(amAuction, sgSh.getFirstTradableSession(AuctionType.All));
		Assert.assertEquals(amAuction, sgSh.getFirstTradableSession(AuctionType.AMAuction));
		Assert.assertEquals(amContinuous, sgSh.getFirstTradableSession(AuctionType.PMAuction));
		Assert.assertEquals(amContinuous, sgSh.getFirstTradableSession(AuctionType.None));
	}

	@Test
	public void testGetLastTradableSession() {
		TradingSession closeAuction = sgSz.getSessionByType(SessionType.CloseAuction);
		TradingSession pmContinuous = sgSz.getSessionByType(SessionType.PMContinuous);

		Assert.assertEquals(closeAuction, sgSz.getLastTradableSession(AuctionType.All));
		Assert.assertEquals(pmContinuous, sgSz.getLastTradableSession(AuctionType.AMAuction));
		Assert.assertEquals(closeAuction, sgSz.getLastTradableSession(AuctionType.CloseAuction));
		Assert.assertEquals(pmContinuous, sgSz.getLastTradableSession(AuctionType.None));
	}

	@Test
	public void testSessionGroup()  throws Exception {
		LocalDateTime time = LocalDateTime.of(date20160101, DateUtil.getTime("09:30:00000"));
		TradingSession tradingSession = sgSh.getSessionByTime(time, IntervalType.Start);
		Assert.assertEquals(true, tradingSession.isTradable());

		boolean isAuctionMatch = tradingSession.isAuctionMatch(AuctionType.All);
		Assert.assertEquals(true, isAuctionMatch);

		isAuctionMatch = tradingSession.isAuctionMatch(AuctionType.None);
		Assert.assertEquals(true, isAuctionMatch);

		isAuctionMatch = tradingSession.isAuctionMatch(AuctionType.CloseAuction);
		Assert.assertEquals(true, isAuctionMatch);

		time = LocalDateTime.of(date20160101, DateUtil.getTime("09:15:00000"));
		tradingSession = sgSh.getSessionByTime(time, IntervalType.Start);
		isAuctionMatch = tradingSession.isAuctionMatch(AuctionType.AMAuction);
		Assert.assertEquals(true, isAuctionMatch);

		isAuctionMatch = tradingSession.isAuctionMatch(AuctionType.CloseAuction);
		Assert.assertEquals(false, isAuctionMatch);

		tradingSession = sgSh.getSessionByTime(time, IntervalType.End);
		Assert.assertEquals(false, tradingSession.isTradable());

		time = LocalDateTime.of(date20160101, DateUtil.getTime("09:15:00000"));
		tradingSession = sgSh.getNextTradableSession(time, IntervalType.Start, AuctionType.All);
		Assert.assertEquals(LocalDateTime.of(date20160101, DateUtil.getTime("09:20:00000")), tradingSession.getStartTime());

		time = LocalDateTime.of(date20160101, DateUtil.getTime("10:15:00000"));
		tradingSession = sgSh.getNextTradableSession(time, IntervalType.Start, AuctionType.CloseAuction);
		Assert.assertEquals(LocalDateTime.of(date20160101, DateUtil.getTime("13:00:00000")), tradingSession.getStartTime());

		time = LocalDateTime.of(date20160101, DateUtil.getTime("13:15:00000"));
		tradingSession = sgSz.getNextTradableSession(time, IntervalType.Start, AuctionType.CloseAuction);
		Assert.assertEquals(LocalDateTime.of(date20160101, DateUtil.getTime("14:57:00000")), tradingSession.getStartTime());

		time = LocalDateTime.MAX;
		tradingSession = sgSh.getSessionByTime(time, IntervalType.Start);
		Assert.assertEquals(true, (tradingSession == null));

		time = LocalDateTime.MIN;
		tradingSession = sgSh.getSessionByTime(time, IntervalType.End);
		Assert.assertEquals(true, (tradingSession == null));

		LocalDateTime time1 = LocalDateTime.of(date20160101, DateUtil.getTime("09:35:00000"));
		LocalDateTime time2 = LocalDateTime.of(date20160101, DateUtil.getTime("10:35:00000"));
		TradingSession session1 = sgSh.getSessionByTime(time1, IntervalType.Start);
		TradingSession session2 = sgSh.getSessionByTime(time2, IntervalType.Start);
		Assert.assertEquals(true, (session1.equals(session2)));
	}

	@Test
	public void testSymbol2SessionGroup() {
		String symbol1 = "600000.sh";
		String symbol2 = "000001.sz";

		String name1 = TradingSession.symbol2SessionGroup(symbol1);
		String name2 = TradingSession.symbol2SessionGroup(symbol2);

		Assert.assertEquals("default.sh", name1);
		Assert.assertEquals("default.sz", name2);
	}
}
