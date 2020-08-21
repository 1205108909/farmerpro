package com.phenix.orderrouter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.phenix.farmer.FarmerDataManager;
import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.phenix.data.Security;
import com.phenix.data.SecurityType;
import com.phenix.util.DateUtil;
import com.phenix.util.Util;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.Quote;
import com.phenix.orderrouter.ctp.CThostFtdcDepthMarketDataField;
import com.phenix.orderrouter.ctp.CThostFtdcMdApi;
import com.phenix.orderrouter.ctp.CThostFtdcMdSpi;
import com.phenix.orderrouter.ctp.CThostFtdcReqUserLoginField;
import com.phenix.orderrouter.ctp.CThostFtdcRspInfoField;
import com.phenix.orderrouter.ctp.CThostFtdcRspUserLoginField;
import com.phenix.orderrouter.ctp.CThostFtdcSpecificInstrumentField;

public class CTPOrderBookManager extends CThostFtdcMdSpi {
	private final static transient Logger Logger = LoggerFactory.getLogger(CTPOrderBookManager.class);
	
	CThostFtdcMdApi mdApi;
	private String seqPath = "";
	private int requestId = 0;

	private ExecutorService es = Executors.newSingleThreadExecutor();
	
	private List<String> instrument = Lists.newArrayList("IF1507", "IH1507", "IC1507");
	
	private LocalDate tradingDay = Util.InvalidTradingDay;

	@Getter
	private final static CTPOrderBookManager Instance = new CTPOrderBookManager();
	
	public void init() {
		es.submit(() -> {
			_init();
		});
	}
	
	public LocalDate getTradingDate() {
		return tradingDay;
	}
	
	private void _init() {		
		mdApi = CThostFtdcMdApi.CreateFtdcMdApi(seqPath, true);
		if (mdApi == null) {
			// TODO error handling
		}

		mdApi.RegisterSpi(this);
		mdApi.RegisterFront("tcp://180.168.146.181:10210");
		mdApi.Init();
		mdApi.Join();
	}

	public void cleanup() {
		mdApi.Release();
	}

	@Override
	public void OnFrontConnected() {	
		Logger.info("OnFrontConnected - connected");
		
		CThostFtdcReqUserLoginField loginField = new CThostFtdcReqUserLoginField();
		loginField.setBrokerID("0268");
		loginField.setUserID("02067");
		loginField.setPassword("qwer1234");

		int res = mdApi.ReqUserLogin(loginField, requestId++);
		if (res != 0) {
			// TODO error handling
		}
	}
	
	@Override
	public void OnFrontDisconnected(int nReason) {
		Logger.error(String.format("OnFrontDisconnected - Logged in failed with errorCode : [%s]", nReason));
	};

	@Override
	public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		if (pRspInfo != null && pRspInfo.getErrorID() != 0) {

		}
		
		int frontId = pRspUserLogin.getFrontID();
		int sessionId = pRspUserLogin.getSessionID();		
		
		Logger.info(String.format("OnRspUserLogin - logged in with frontId[%s], sessionId[%s]", frontId, sessionId));
		Logger.info(String.format("Going to subscribe orderbook for [%s]", instrument));
		
		mdApi.SubscribeMarketData(instrument.toArray(new String[] {}), instrument.size());
		tradingDay = DateUtil.getDate(pRspUserLogin.getTradingDay());
	}
	
	@Override
	public void OnRspUserLogout(com.phenix.orderrouter.ctp.CThostFtdcUserLogoutField pUserLogout, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		//TODO
	};

	@Override
	public void OnRspSubMarketData(CThostFtdcSpecificInstrumentField pSpecificInstrument, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		Logger.error(String.format("OnRspSubMarketData - errorCode[%s], errorMsg[%s]", pRspInfo.getErrorID(), pRspInfo.getErrorMsg()));
	}
	
	@Override
	public void OnRspUnSubMarketData(CThostFtdcSpecificInstrumentField pSpecificInstrument, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		Logger.error(String.format("OnRspUnSubMarketData - errorCode[%s], errorMsg[%s]", pRspInfo.getErrorID(), pRspInfo.getErrorMsg()));
	};
	
	/**
	@Override
	public void OnRspSubForQuoteRsp(CThostFtdcSpecificInstrumentField pSpecificInstrument, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		//TODO 订阅询价应答
	};
	
	@Override
	public void OnRspUnSubForQuoteRsp(CThostFtdcSpecificInstrumentField pSpecificInstrument, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {		
		//TODO 取消订阅询价应答
	};
	
	@Override
	public void OnRtnForQuoteRsp(CThostFtdcForQuoteRspField pForQuoteRsp) {
		//TODO 询价通知
	};*/

	@Override
	public void OnRtnDepthMarketData(CThostFtdcDepthMarketDataField pDepthMarketData) {		
		String instrument = pDepthMarketData.getInstrumentID();		
		double ticksize = FarmerDataManager.getInstance().getTickSize(instrument);//FarmerDataManager.getInstance().getTickSize(sec);
		
		List<Quote> asks = new ArrayList<Quote>();
		List<Quote> bids = new ArrayList<Quote>();
		asks.add(new Quote(Util.roundPriceNearestTick(pDepthMarketData.getAskPrice1(), ticksize), pDepthMarketData.getAskVolume1()));
		bids.add(new Quote(Util.roundPriceNearestTick(pDepthMarketData.getBidPrice1(), ticksize), pDepthMarketData.getBidVolume1()));	
		OrderBook ob = OrderBook.of(asks, bids);		
		
		int mills = pDepthMarketData.getUpdateMillisec();		
		LocalTime time = DateUtil.getTime(pDepthMarketData.getUpdateTime() + (mills == 0 ? "000" : String.valueOf(mills)));		
		ob.setDateTime(LocalDateTime.of(tradingDay, time));
		
		Security sec = Security.of(instrument, SecurityType.INDEX_FUTURE);
		ob.setSecurity(sec);		
		ob.setLastPx(Util.roundPriceNearestTick(pDepthMarketData.getLastPrice(), ticksize));
		
		Logger.info(sec.toString() + " *** " + ob.toEvaluationString());	
		
		//FarmerController.getInstance().enqueueEvent(new OrderBookUpdateEvent(ob));
	}	
	
	@Override
	public void OnRspError(CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		Logger.error(String.format("OnRspError - errorCode[%s], errorMsg[%s]", pRspInfo.getErrorID(), pRspInfo.getErrorMsg()));
	}	
	
	@Override
	public void OnHeartBeatWarning(int nTimeLapse) {
		Logger.info(String.format("OnHeartBeatWarning - nTimeLapse[%s]", nTimeLapse));
	}
}
