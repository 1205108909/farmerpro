package com.phenix.orderrouter;

import com.google.common.base.Joiner;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.data.*;
import com.phenix.util.Alert;
import com.phenix.util.Alert.Severity;
import com.phenix.util.Util;
import com.phenix.message.ExecutionReport;
import com.phenix.orderrouter.ctp.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CTPOrderRouterManager extends CThostFtdcTraderSpi {	
	private final static transient Logger Logger = LoggerFactory.getLogger(CTPOrderRouterManager.class);	
	
	private ExecutorService es = Executors.newSingleThreadExecutor();
	private Map<String, String> orderMapping = new ConcurrentHashMap<>();
	private Map<String, String> orderMappingInverse = new ConcurrentHashMap<>();
			
	private String brokerId = "0268";
	private String userId = "02067";
	private String password = "qwer1234";
	
	@Getter
	private int frontId;
	@Getter
	private int sessionId;		
	private AtomicInteger orderRef = new AtomicInteger(0);
	private AtomicInteger reqId = new AtomicInteger(0);

	@Getter
	private final static CTPOrderRouterManager Instance = new CTPOrderRouterManager();	

	CThostFtdcTraderApi traderApi;
	private String seqPath = "";
	
	public int getRequestId() {
		return reqId.get();
	}
	
	public void init() {
		es.submit(() -> {
			_init();
		});
	}

	private void _init() {
		traderApi = CThostFtdcTraderApi.CreateFtdcTraderApi(seqPath);
		if (traderApi == null) {
			// TODO error handling
		}
		// traderApi.ReqUserLogin(pReqUserLoginField, nRequestID)

		traderApi.RegisterSpi(this);
		traderApi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESUME);
		traderApi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESUME);
		traderApi.RegisterFront("tcp://180.168.146.181:10200");
		traderApi.Init();
		traderApi.Join();
	}

	@Override
	public void OnFrontConnected() {
		CThostFtdcReqUserLoginField loginField = new CThostFtdcReqUserLoginField();
		loginField.setBrokerID(brokerId);
		loginField.setUserID(userId);
		loginField.setPassword(password);

		int res = traderApi.ReqUserLogin(loginField, reqId.incrementAndGet());
		if (res != 0) {
			// TODO error handling
		}
	}
	
	@Override
	public void OnFrontDisconnected(int nReason) {
		Logger.error(String.format("OnFrontDisconnected - Logged in failed with errorCode : [%s]", nReason));
	};

	@Override
	public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin_, CThostFtdcRspInfoField pRspInfo_, int nRequestID_, boolean bIsLast_) {		
		if (pRspInfo_ != null && pRspInfo_.getErrorID() != 0) {

		}
		
		orderRef.set(Integer.parseInt(pRspUserLogin_.getMaxOrderRef()));
		frontId = pRspUserLogin_.getFrontID();
		sessionId = pRspUserLogin_.getSessionID();
		
		Logger.info(String.format("OnRspUserLogin - logged in with frontId[%s], sessionId[%s], maxOrderRef[%s]", frontId, sessionId, orderRef.get()));
		
		CThostFtdcSettlementInfoConfirmField field = new CThostFtdcSettlementInfoConfirmField();
		field.setBrokerID(brokerId);
		field.setInvestorID(userId);
		field.setConfirmDate(traderApi.GetTradingDay());		
		
		Logger.info("ReqSettlementInfoConfirm");
		traderApi.ReqSettlementInfoConfirm(field, reqId.incrementAndGet());
	}
	
	@Override
	public void OnRspSettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField pSettlementInfoConfirm, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		Logger.info(String.format("OnRspSettlementInfoConfirm - confirmDate = [%s]", pSettlementInfoConfirm.getConfirmDate()));
	}
	
	@Override
	public void OnRtnOrder(CThostFtdcOrderField pOrder_) {
		char submitStatus = pOrder_.getOrderSubmitStatus();
		char orderStatus = pOrder_.getOrderStatus();
		Logger.info(String.format("OnRtnOrder submitStatus = [%s], orderStatus[%s]", submitStatus, orderStatus));
		
		OrderStatus orderStatus2 = CTPOrderRouterManager.convertOrderStatus(submitStatus, orderStatus);		
		String orderRef = pOrder_.getOrderRef();		
		String exchangeOrderId = generateExchangeOrderId(frontId, sessionId, Integer.parseInt(orderRef));		
		String clOrdId = orderMappingInverse.get(exchangeOrderId);
		if(clOrdId == null) {
			String msg = String.format("No_ClOrdId_Found for ctpOrder[%s]", exchangeOrderId);
			Alert.fireAlert(Severity.Critical, msg, msg);
		}
		
		ExecutionReport report = new ExecutionReport();
		report.setOrderStatus(orderStatus2);	
		report.setSecurity(Security.of(pOrder_.getInstrumentID(), SecurityType.INDEX_FUTURE));
		report.setCumQty(pOrder_.getVolumeTraded());
		report.setLeavesQty(pOrder_.getVolumeTotal());
		Logger.info(report.toString());	
		
		/*if(orderStatus2 != OrderStatus.PartialFilled) {//PartialFilled and Filled are handled by OnRtnTrade
			FarmerController.getInstance().enqueueEvent(new ExecutionReportEvent(report));
		}		*/
	}
	
	@Override
	public void OnRtnTrade(CThostFtdcTradeField pTrade_) {	
		Logger.info("OnRtnTrade");
		String orderRef = pTrade_.getOrderRef();
		String exchangeOrderId = generateExchangeOrderId(frontId, sessionId, Integer.parseInt(orderRef));		
		String clOrdId = orderMappingInverse.get(exchangeOrderId);
		if(clOrdId == null) {
			String msg = String.format("No_ClOrdId_Found for ctpOrder[%s]", exchangeOrderId);
			Alert.fireAlert(Severity.Critical, msg, msg);
		}
		
		String instrument = pTrade_.getInstrumentID();
		double ticksize = FarmerDataManager.getInstance().getTickSize(instrument);
		double price = Util.roundPriceNearestTick(pTrade_.getPrice(), ticksize);
		int volume = pTrade_.getVolume();

		ExecutionReport report = new ExecutionReport();		
		report.setLastPx(price);
		report.setLastShares(volume);
		report.setOrderStatus(OrderStatus.PARTIAL_FILLED);//PARTIAL_FILL for filled and partiallfilled
		report.setClOrdId(clOrdId);
		
		Logger.info(report.toString());
		//FarmerController.getInstance().enqueueEvent(new ExecutionReportEvent(report));
	}
	
	@Override
	public void OnRspOrderInsert(CThostFtdcInputOrderField pInputOrder, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		Logger.error(String.format("OnRspOrderInsert - errorCode[%s], errorMsg[%s]", pRspInfo.getErrorID(), pRspInfo.getErrorMsg()));
	};
	
	@Override
	public void OnErrRtnOrderInsert(CThostFtdcInputOrderField pInputOrder, CThostFtdcRspInfoField pRspInfo) {
		Logger.error(String.format("OnErrRtnOrderInsert - errorCode[%s], errorMsg[%s]", pRspInfo.getErrorID(), pRspInfo.getErrorMsg()));
	};

	public void placeOrder(Order order_) {
		CThostFtdcInputOrderField order = createCTPOrder(order_);		
		traderApi.ReqOrderInsert(order, reqId.incrementAndGet());		
	}
	
	public void cancelOrder(Order order_) {
		//TODO
	}
	
	//Performance enhancement, init them from start
	public CThostFtdcInputOrderField createCTPOrder(Order order_) {
		CThostFtdcInputOrderField ctpOrder = new CThostFtdcInputOrderField();
		ctpOrder.setBrokerID(brokerId);
		ctpOrder.setInvestorID(userId);		
		ctpOrder.setInstrumentID(order_.getSymbol());
		ctpOrder.setOrderRef(String.valueOf(orderRef.incrementAndGet()));	
		ctpOrder.setUserID(userId);		
		
		/*System.out.println(ctpOrder.getBrokerID());
		System.out.println(ctpOrder.getUserID());
		System.out.println(ctpOrder.getInstrumentID());
		System.out.println(ctpOrder.getOrderRef());
		System.out.println(ctpOrder.getInvestorID());	*/	
		
		ctpOrder.setDirection(order_.getOrderSide() == OrderSide.BUY ? CTP_wrap4javaConstants.THOST_FTDC_D_Buy :CTP_wrap4javaConstants.THOST_FTDC_D_Sell);
		ctpOrder.setCombOffsetFlag(String.valueOf(order_.isOpenPosOrder() ? CTP_wrap4javaConstants.THOST_FTDC_OF_Open : CTP_wrap4javaConstants.THOST_FTDC_OF_Close));
		ctpOrder.setCombHedgeFlag(String.valueOf(CTP_wrap4javaConstants.THOST_FTDC_HF_Speculation));
		ctpOrder.setVolumeTotalOriginal((int) order_.getOrderQty());
		ctpOrder.setVolumeCondition(CTP_wrap4javaConstants.THOST_FTDC_VC_AV);
		ctpOrder.setMinVolume(0);
		ctpOrder.setForceCloseReason(CTP_wrap4javaConstants.THOST_FTDC_FCC_NotForceClose);
		ctpOrder.setIsAutoSuspend(0);
		ctpOrder.setUserForceClose(0);
		
		/*System.out.println(ctpOrder.getDirection());
		System.out.println(ctpOrder.getCombOffsetFlag());
		System.out.println(ctpOrder.getCombHedgeFlag());
		System.out.println(ctpOrder.getVolumeTotalOriginal());
		System.out.println(ctpOrder.getVolumeCondition());
		System.out.println(ctpOrder.getMinVolume());
		System.out.println(ctpOrder.getForceCloseReason());
		System.out.println(ctpOrder.getIsAutoSuspend());
		System.out.println(ctpOrder.getUserForceClose());*/
		
		
		ctpOrder.setOrderPriceType(CTP_wrap4javaConstants.THOST_FTDC_OPT_LimitPrice);
		ctpOrder.setLimitPrice(order_.getPrice());
		ctpOrder.setTimeCondition(CTP_wrap4javaConstants.THOST_FTDC_TC_IOC);//Immediate or cancel		
		ctpOrder.setStopPrice(0);
		ctpOrder.setContingentCondition(CTP_wrap4javaConstants.THOST_FTDC_CC_Immediately);
		/*System.out.println(ctpOrder.getOrderPriceType());
		System.out.println(ctpOrder.getLimitPrice());
		System.out.println(ctpOrder.getTimeCondition());
		System.out.println(ctpOrder.getStopPrice());*/
		
		
		createOrderMapping(order_, generateExchangeOrderId(frontId, sessionId, orderRef.get()));
		
		return ctpOrder;
	}
	
	private String generateExchangeOrderId(int frontId_, int sessionId_, int orderRef_) {
		return Joiner.on('_').join(frontId_, sessionId_, orderRef_);
	}
	
	private void createOrderMapping(Order order_, String ctpOrderId_) {
		orderMapping.put(order_.getClOrdId(), ctpOrderId_);
		orderMappingInverse.put(ctpOrderId_, order_.getClOrdId());
	}
	
	
	/**
	 * 1. after market sending order : '0'_'a' with '4'_'5'
	 * 2. market open sending limit order without fill : '0'_'a' with '3'_'3', submit cancel then '3'_'3' with '3'_'5'
	 * 3. market open sending a limit order with 2 fill : '0'_'a', '0'_'a', '0'_'1'|execution, '0'_'0'|execution
	 * 4. market open sending a limit order with 2 fill
	 *    '0'_'a', '3'_'3', then '3'_'3', '3'_'1'|execution, '3'_'1', '3'_'1'|execution
	 *    submit cancel
	 *    '3'_'1', '3'_'5'
	 * 5. market open sending a limit order without fill(IOC)
	 *    '0'_'a' with '0'_'5'
	 * 6. market open sending a limit order with fill(IOC)
	 *    '0'_'a', '0'_'a' with '0'_'0'
	 * --------- submit status ---------------
	#define THOST_FTDC_OSS_InsertSubmitted '0'
	#define THOST_FTDC_OSS_CancelSubmitted '1'
	#define THOST_FTDC_OSS_ModifySubmitted '2'
	#define THOST_FTDC_OSS_Accepted '3'
	#define THOST_FTDC_OSS_InsertRejected '4'
	#define THOST_FTDC_OSS_CancelRejected '5'
	#define THOST_FTDC_OSS_ModifyRejected '6'
	
	* --------- order status ---------------
	#define THOST_FTDC_OST_AllTraded '0'
	#define THOST_FTDC_OST_PartTradedQueueing '1'
	#define THOST_FTDC_OST_PartTradedNotQueueing '2'
	#define THOST_FTDC_OST_NoTradeQueueing '3'
	#define THOST_FTDC_OST_NoTradeNotQueueing '4'
	#define THOST_FTDC_OST_Canceled '5'
	#define THOST_FTDC_OST_Unknown 'a'
	#define THOST_FTDC_OST_NotTouched 'b'
	#define THOST_FTDC_OST_Touched 'c'
	 */
	public static OrderStatus convertOrderStatus(char ctpOrderSubmitStatus_, char ctpOrderStatus_) {
		OrderStatus type = null;		
		
		switch (ctpOrderSubmitStatus_) {
			case CTP_wrap4javaConstants.THOST_FTDC_OSS_Accepted://'3':				
			case CTP_wrap4javaConstants.THOST_FTDC_OSS_InsertSubmitted://'0':
				type = OrderStatus.NEW;
				//(ctpOrderStatus_ == '1' || ctpOrderStatus_ == '2')
				if(ctpOrderStatus_ == CTP_wrap4javaConstants.THOST_FTDC_OST_PartTradedQueueing || ctpOrderStatus_ == CTP_wrap4javaConstants.THOST_FTDC_OST_PartTradedNotQueueing) {
					type = OrderStatus.PARTIAL_FILLED;
				} else if(ctpOrderStatus_ == CTP_wrap4javaConstants.THOST_FTDC_OST_AllTraded) {//'0') {
					type = OrderStatus.FILLED;
				} else if(ctpOrderStatus_ == CTP_wrap4javaConstants.THOST_FTDC_OST_Canceled) {//'5') {
					type = OrderStatus.CANCELED;
				}
				break;
			case CTP_wrap4javaConstants.THOST_FTDC_OSS_InsertRejected://'4':			//after-market 
			case CTP_wrap4javaConstants.THOST_FTDC_OSS_CancelRejected://'5':
			case CTP_wrap4javaConstants.THOST_FTDC_OSS_ModifyRejected://'6':
				type = OrderStatus.REJECTED;
				break;
			case CTP_wrap4javaConstants.THOST_FTDC_OSS_CancelSubmitted://'1':
				type = OrderStatus.PENDING_CANCEL;
				//if(ctpOrderStatus_ == '5' || ctpOrderStatus_ == '2' || ctpOrderStatus_ == '4') {
				if(ctpOrderStatus_ == CTP_wrap4javaConstants.THOST_FTDC_OST_Canceled || ctpOrderStatus_ == CTP_wrap4javaConstants.THOST_FTDC_OST_PartTradedNotQueueing || ctpOrderStatus_ == CTP_wrap4javaConstants.THOST_FTDC_OST_NoTradeNotQueueing) {
					type = OrderStatus.CANCELED;
				}
				break;
			case CTP_wrap4javaConstants.THOST_FTDC_OSS_ModifySubmitted://'2':
				type = OrderStatus.PENDING_REPLACE;
				break;
			default:
				throw new UnsupportedOperationException(String.format("unsupported submit status[%s] and orderstatus[%s]", ctpOrderSubmitStatus_, ctpOrderStatus_));
		}
		
		return type;
	}
}
