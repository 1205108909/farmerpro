package com.phenix.tdf;

import cn.com.wind.td.tdf.*;
import com.google.common.collect.ImmutableList;
import com.phenix.exception.TDFRuntimeException;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.config.TDFConfig;
import com.phenix.farmer.event.OrderBookUpdateEvent;
import com.phenix.farmer.event.OrderFlowUpdateEvent;
import com.phenix.orderbook.OrderFlow;
import com.phenix.util.Alert;
import com.phenix.util.Alert.Severity;
import com.phenix.orderbook.OrderBook;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class TDFClient {
	private final static transient Logger Logger = LoggerFactory.getLogger(TDFClient.class);

	@Getter
	public final static TDFClient CLIENT = new TDFClient(); // Instance of
															// TdfClient.

	private cn.com.wind.td.tdf.TDFClient client = new cn.com.wind.td.tdf.TDFClient();
	private final TDF_OPEN_SETTING tdfSettings = new TDF_OPEN_SETTING();

	// Some default values for Tdf client settings.
	private static final int HEART_BEAT_INTERVAL = 0;//Heart Beat间隔（秒数）, 若值为0则表示默认值10秒钟
	private static final int MISSED_BEART_COUNT = 0;//如果没有收到心跳次数超过这个值，且没收到其他任何数据，则判断为掉线，若值0为默认次数2次
	private static final int OPEN_TIME_OUT = 0;//在调TDF_Open期间，接收每一个数据包的超时时间（秒数，不是TDF_Open调用总的最大等待时间），若值为0则默认30秒

	// DATA_TYPE_FLAG.DATA_TYPE_INDEX is always subscribed by default.DATA_TYPE_FLAG.DATA_TYPE_TRANSACTION |
	private static final long DEFAULT_TYPE_FLAGS = DATA_TYPE_FLAG.DATA_TYPE_ORDER;
	private static final long DEFAULT_CONNECTION_ID = 0;
	private static final int DEFAULT_WAIT_TIME = 10;
	private static final String DEFAULT_MARKETS = "SH-2-0;SZ-2-0;CF-1-0"; // ALl MARKET. Don't change this
	public final static ImmutableList<String> L2_MARKETS = ImmutableList.copyOf(Arrays.asList(DEFAULT_MARKETS.split(";")));

	private volatile boolean exit = false;

	public void init(TDFConfig config_) {
		tdfSettings.setIp(config_.getIp());//"172.10.10.13");
		tdfSettings.setPort(config_.getPort());//"5000");
		tdfSettings.setUser(config_.getUserName());//"test");
		tdfSettings.setPwd(config_.getPassword());//"test");
		tdfSettings.setTime(Integer.parseInt(config_.getTime()));//-1);//DEFAULT_TDF_DATA_RESUME_TIME); // 0 from now, -1 from market_open
		tdfSettings.setMarkets(config_.getMarket());//"SH-2-0;SZ-2-0;CF-1-0"

		tdfSettings.setTypeFlags(DEFAULT_TYPE_FLAGS);
		tdfSettings.setConnectionID(DEFAULT_CONNECTION_ID);
		tdfSettings.setSubScriptions("");//代码订阅,例如"600000.sh;ag.shf;000001.sz"，需要订阅的股票(单个股票格式为原始Code+.+市场，如999999.SH)，以“;”分割，为空则订阅全市场

		client.setEnv(TDF_ENVIRON_SETTING.TDF_ENVIRON_HEART_BEAT_INTERVAL, HEART_BEAT_INTERVAL);
		client.setEnv(TDF_ENVIRON_SETTING.TDF_ENVIRON_MISSED_BEART_COUNT, MISSED_BEART_COUNT);
		client.setEnv(TDF_ENVIRON_SETTING.TDF_ENVIRON_OPEN_TIME_OUT, OPEN_TIME_OUT);
	}

	public void connect() {
		Logger.info("Connecting TDF...");
		int err = client.open(tdfSettings);

		verifyErrorCode(err);
		Logger.info("Successfully connect to Tdf server: " + tdfSettings.getIp() + ":" + tdfSettings.getPort());
	}

	public void start() {
		receive();
	}

	private void receive() {
		try {
			Alert.fireAlert(Severity.Info, "Start receive", "Start receive");
			Logger.info("Start to receive message");

			while (!exit) {
				TDF_MSG message = client.getMessage(DEFAULT_WAIT_TIME);
				handleMessage(message);
			}
		} catch (Exception e_) {
			Logger.error(e_.getMessage(), e_);
			Alert.fireAlert(Severity.Major, "Error on receiving orderbook", e_.getStackTrace().toString());
		}
		finally {
			Logger.info("TDF is going to cleanup");
			client.close();
			System.out.println("TDFClient exit done");
		}
	}

	private void handleMessage(TDF_MSG msg_) {
		if(msg_ == null)
			return;
		
		int itemCount = msg_.getAppHead().getItemCount();
		int dataType = msg_.getDataType();
		
		//Logger.info("received***" + itemCount);
		for (int i = 0; i < itemCount; i++) {
			TDF_MSG_DATA md = cn.com.wind.td.tdf.TDFClient.getMessageData(msg_, i);
			if (dataType == TDF_MSG_ID.MSG_DATA_TRANSACTION ) {				
				TDF_TRANSACTION t =  md.getTransaction();
				try {
					//Transaction tr = TDFDataParser.parseTDFTransaction(t);
					//if(tr == null) continue;
					//FarmerController.getInstance().enqueueEvent(new TransactionUpdateEvent(tr));
				} catch (Exception ex_) {
					Logger.error(ex_.getMessage(), ex_);
					String id = "TDFClient -> Honghui: error on parsing transaction:" + t.getWindCode();
					Alert.fireAlert(Severity.Major, id, ex_.toString());
				}
			} else if (dataType == TDF_MSG_ID.MSG_DATA_MARKET) {
				TDF_MARKET_DATA o = md.getMarketData();
				try {
					OrderBook ob = TDFDataParser.parseTDFOrderBook(o);
					if(ob == null) return;
					FarmerController.getInstance().enqueueEvent(new OrderBookUpdateEvent(ob));
				} catch (Exception ex_) {
					Logger.error(ex_.getMessage(), ex_);
					String id = "TDFClient -> Honghui: error on parsing orderbook:" + o.getWindCode();
					Alert.fireAlert(Severity.Major, id, ex_.toString());
				}
			} else if (dataType == TDF_MSG_ID.MSG_DATA_INDEX) {
				TDF_INDEX_DATA io = md.getIndexData();
				try {
					OrderBook ob = TDFDataParser.parseTDFIndexOrderBook(io);
					if(ob == null) return;
					FarmerController.getInstance().enqueueEvent(new OrderBookUpdateEvent(ob));
				} catch (Exception ex_) {
					Logger.error(ex_.getMessage(), ex_);
					String id = "TDFClient -> Honghui: error on parsing index orderbook:" + io.getWindCode();
					Alert.fireAlert(Severity.Major, id, ex_.toString());
				}
			} else if (dataType == TDF_MSG_ID.MSG_DATA_FUTURE) {
				TDF_FUTURE_DATA io = md.getFutureData();
				try {
					OrderBook ob = TDFDataParser.parseTDFFutureOrderBook(io);
					if (ob == null) return;
					FarmerController.getInstance().enqueueEvent(new OrderBookUpdateEvent(ob));
				} catch (Exception ex_) {
					Logger.error(ex_.getMessage(), ex_);
					String id = "TDFClient -> Honghui: error on parsing future orderbook:" + io.getWindCode();
					Alert.fireAlert(Severity.Major, id, ex_.toString());
				}
			} else if (dataType == TDF_MSG_ID.MSG_DATA_ORDER) {
				TDF_ORDER order = md.getOrder();
				try {
					OrderFlow ofl = TDFDataParser.parseOrderFlow(order);
					if(ofl == null) return;
					FarmerController.getInstance().enqueueEvent(new OrderFlowUpdateEvent(ofl));
				} catch (Exception ex_) {
					Logger.error(ex_.getMessage(), ex_);
					String id = "TDFClient -> Honghui: error on parsing order orderbook:" + order.getWindCode();
					Alert.fireAlert(Severity.Major, id, ex_.toString());
				}
			} else if (dataType == TDF_MSG_ID.MSG_SYS_DISCONNECT_NETWORK) {
				//TODO: fire alert
			} else if(dataType == TDF_MSG_ID.MSG_SYS_LOGIN_RESULT) {
				//TODO: fire alert
			}
		}
	}

	/**
	 * @param errorCode
	 *            the given error code from Tdf client.
	 */
	public void verifyErrorCode(int errorCode) {
		TdfErrorEnum error = TdfErrorEnum.getErrorByErrorCode(errorCode);
		if (error != TdfErrorEnum.SUCCESS) {
			Alert.fireAlert(Severity.Major, "TdfError", "TdfError");
			throw new TDFRuntimeException(error.toString());
		}
	}

	public void cleanup() {
		exit = true;
		Logger.info("TDF is cleaned up");
	}
}
