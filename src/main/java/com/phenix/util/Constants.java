package com.phenix.util;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class Constants {
	public final static double MiusOne = -1;
	
	public final static String DEFAULT = "default";
	
	public final static String MQ_RETROACTIVE = "?consumer.retroactive=true";
	
	public final static String MQConnection_OrderBook = "OrderBook";
	//public final static List<String> MQTopic_OrderBooks = ImmutableList.of("quotahq");
	
	public final static String MQConnection_Transaction = "Transaction";
	public final static List<String> MQTopic_Transactions = ImmutableList.of("quotatransactiondataSZ", "quotatransactiondataSH");
	
	public final static String Date_YYYYMMDD = "yyyyMMdd";
	
	public final static String DataRequestQueue = "TickData.Request.Queue";
	
	public final static String AlphaRequestQueue = "Alpha.Request.Queue";
	
	public final static String DataResponse = "TickData.Response";
	
	public final static String AlphaResponse = "Alpha.Response";
	
	public final static String Force = "Force";
		
	public final static Map<String, String> ACK_MAP = ImmutableMap.of("Status", "Ack");
	
	public final static String SignalDuration = "SignalDuration";
	
	public final static String SignalCount= "SignalCount";
	
	public final static String SignalID = "SignalID";
	
	public final static String NA = "N/A";
	
	public final static String Symbol = "Symbol";
	
	public final static String DelimiterComma = ",";
	
	public final static String DelimiterEnter = "\n";
	
	public final static String DB_WIND = "Wande";
	
	public final static String DB_JYDB = "JYDB";

	public final static String DB_DATASERVICE = "DataService";

	public final static LocalTime LOCAL_TIME_1450 = LocalTime.of(14, 50);

	public final static LocalTime LOCAL_TIME_1430 = LocalTime.of(14, 30);

	public final static LocalTime LOCAL_TIME_1445 = LocalTime.of(14, 45);
}

