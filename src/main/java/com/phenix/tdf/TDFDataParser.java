package com.phenix.tdf;

import cn.com.wind.td.tdf.*;
import com.phenix.data.OrderSide;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.data.Exchange;
import com.phenix.data.Security;
import com.phenix.orderbook.OrderFlow;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Quote;
import com.phenix.orderbook.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TDFDataParser {
	private final static double Precision = 10000;

	public static Transaction parseTDFTransaction(TDF_TRANSACTION tt_) {
		String symbol = tt_.getWindCode().toLowerCase();
		Security sec = null;
		sec = FarmerDataManager.getInstance().getSecurity(symbol);
		
		if(sec == null) {//Untradable Security
			return null;
		}
		if(sec.getExchange() != Exchange.SS && sec.getExchange() != Exchange.SZ) {//UNKNOWN exchange
			return null;
		}
		
		double px = tt_.getPrice() / Precision;
		double vol = tt_.getVolume();		
		//ignore cancel trade
		if(!Util.isValidLimitPrice(px) || !Util.isValidQty(vol)) {
			return null;
		}
		
		String time = String.valueOf(tt_.getTime());
		if (time.length() == 8)
			time = "0" + time;
		LocalTime lt = DateUtil.getTime2(time);
		LocalDate ld = FarmerDataManager.TODAY;
        String fc = String.valueOf(tt_.getFunctionCode());
		return Transaction.of(px, vol, LocalDateTime.of(ld, lt), sec, tt_.getBidOrder(), tt_.getAskOrder(), OrderSide.UNKNOWN, fc);
	}

	public static OrderFlow parseOrderFlow(TDF_ORDER or_) {
		String symbol = or_.getWindCode().toLowerCase();
		Security sec = FarmerDataManager.getInstance().getSecurity(symbol);
		if(sec == null) {
			return null;
		}
		if(sec.getExchange() != Exchange.SZ) {//currently only shanghai support orderflow
			return null;
		}
		double px = or_.getPrice() / Precision;
		double vol = or_.getVolume();
		String orderKind = String.valueOf(or_.getOrderKind());
		int orderNo_ = or_.getOrder();
		String fuctionCode = String.valueOf(or_.getFunctionCode());
		String time = String.valueOf(or_.getTime());
		if (time.length() == 8)
			time = "0" + time;
		LocalTime lt = DateUtil.getTime2(time);
		LocalDate ld = FarmerDataManager.TODAY;
		return OrderFlow.of(px, vol, orderKind, orderNo_, fuctionCode, LocalDateTime.of(ld, lt), sec);
	}

	public static OrderBook parseTDFIndexOrderBook(TDF_INDEX_DATA md_) {
		String symbol = md_.getWindCode().toLowerCase();
		Security sec = FarmerDataManager.getInstance().getSecurity(symbol);
		if(sec == null) {
			return null;
		}
		double preClose = md_.getPreCloseIndex() / Precision;
		double open = md_.getOpenIndex() / Precision;
		double high = md_.getHighIndex() / Precision;
		double low = md_.getLowIndex() / Precision;
		double last = md_.getLastIndex() / Precision;
		double volume = md_.getTotalVolume() * 100;
		double turnover = md_.getTurnover() * 100;
		String time = String.valueOf(md_.getTime());
		if (time.length() == 8)
			time = "0" + time;
		LocalTime lt = DateUtil.getTime2(time);
		LocalDate ld = FarmerDataManager.TODAY;

		OrderBook ob = OrderBook.of(sec, Collections.emptyList(), Collections.emptyList(), LocalDateTime.of(ld, lt));
		ob.setPreClose(preClose);
		ob.setOpenPx(open);
		ob.setHighPx(high);
		ob.setLowPx(low);
		ob.setLastPx(last);
		ob.setVolume(volume);
		ob.setTurnover(turnover);
		return ob;
	}

	public static OrderBook parseTDFFutureOrderBook(TDF_FUTURE_DATA md_) {
		String symbol = md_.getWindCode().toLowerCase();
		Security sec = FarmerDataManager.getInstance().getSecurity(symbol);
		if (sec == null) {
			return null;
		}
		double preClose = md_.getPreClose() / Precision;
		double open = md_.getOpen() / Precision;
		double high = md_.getHigh() / Precision;
		double low = md_.getLow() / Precision;
		double last = md_.getClose() / Precision;
		double volume = md_.getVolume();
		double turnover = md_.getTurnover();
		double preSettle = md_.getPreSettlePrice() / Precision;
		String time = String.valueOf(md_.getTime());
		if (time.length() == 8)
			time = "0" + time;
		LocalTime lt = DateUtil.getTime2(time);
		LocalDate ld = FarmerDataManager.TODAY;

		List<Quote> ask = new ArrayList<>(1);
		List<Quote> bid = new ArrayList<>(1);
		ask.add(new Quote(md_.getAskPrice()[0] / Precision, md_.getAskVol()[0]));
		bid.add(new Quote(md_.getBidPrice()[0] / Precision, md_.getBidVol()[0]));
		OrderBook ob = OrderBook.of(sec, ask, bid, LocalDateTime.of(ld, lt));
		ob.setPreClose(preClose);
		ob.setOpenPx(open);
		ob.setHighPx(high);
		ob.setLowPx(low);
		ob.setLastPx(last);
		ob.setVolume(volume);
		ob.setTurnover(turnover);
		ob.setPreSettle(preSettle);
//		System.out.println("symbol = " + ob.getSecurity() + ", time = " + ob.getTime() + ",  vol = " + ob.getVol() + ", turnover = " + ob.getTurnover()
//				+ ", " + ob.getPreSettle() + ", " + ob.toEvaluationString());
		return ob;
	}

	public static OrderBook parseTDFOrderBook(TDF_MARKET_DATA md_) {
		String symbol = md_.getWindCode().toLowerCase();
		Security sec = FarmerDataManager.getInstance().getSecurity(symbol);
		if(sec == null) {
			return null;
		}
		double preClose = md_.getPreClose() / Precision;
		double open = md_.getOpen() / Precision;
		double high = md_.getHigh() / Precision;
		double low = md_.getLow() / Precision;
		double last = md_.getMatch() / Precision;
		double highLimit = md_.getHigh() / Precision;
		double lowLimit = md_.getLow() / Precision;
		double volume = md_.getVolume();
		double turnover = md_.getTurnover();

		List<Quote> ask = new ArrayList<Quote>();
		List<Quote> bid = new ArrayList<Quote>();
		long[] bidPrice = md_.getBidPrice();
		long[] askPrice = md_.getAskPrice();
		long[] bidVol = md_.getBidVol();
		long[] askVol = md_.getAskVol();
		for (int i = 0; i < 5; i++) {
			bid.add(new Quote(bidPrice[i] / Precision, bidVol[i]));
			ask.add(new Quote(askPrice[i] / Precision, askVol[i]));
		}

		String time = String.valueOf(md_.getTime());
		if (time.length() == 8)
			time = "0" + time;
		LocalTime lt = DateUtil.getTime2(time);
		LocalDate ld = FarmerDataManager.TODAY;

		OrderBook ob = OrderBook.of(sec, ask, bid, LocalDateTime.of(ld, lt));
		ob.setPreClose(preClose);
		ob.setOpenPx(open);
		ob.setHighPx(high);
		ob.setLowPx(low);
		ob.setLastPx(last);
		ob.setVolume(volume);
		ob.setAccVolume(volume);
		ob.setTurnover(turnover);
		ob.setAccTurnover(turnover);
		ob.setHighLimit(highLimit);
		ob.setLowLimit(lowLimit);
		return ob;
	}
}
