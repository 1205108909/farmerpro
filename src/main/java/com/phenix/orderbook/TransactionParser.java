package com.phenix.orderbook;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;

import com.phenix.data.OrderSide;
import com.phenix.farmer.FarmerConfigManager;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.phenix.data.Security;
import com.phenix.data.SecurityType;
import com.phenix.exception.IllegalDataException;
import com.phenix.util.DateUtil;

public class TransactionParser {	
	public static Transaction parse(String msg_) {
		SAXBuilder builder = new SAXBuilder();
		
		Document doc = null;
		try {			
			doc = builder.build(new StringReader(msg_));
		} catch (JDOMException | IOException e_) {
			throw new IllegalDataException("Unparsable Data msg_", e_);
		}

		Element record = doc.getRootElement().getChild("body").getChild("record");
		String stkcode = record.getAttributeValue("stkcode");
		String marketid = record.getAttributeValue("marketid");
		String symbol = stkcode + "." + marketid;

		double volume = parseDouble(record.getAttributeValue("donevolume"));		
		double price = parseDouble(record.getAttributeValue("matchprice"));
		String today = FarmerConfigManager.getInstance().getInstanceConfig().getStartTimeAsDate();
		String timeStr = record.getAttributeValue("time");
		if(timeStr.length() == 8) {//padded to 
			timeStr = today + " 0" + timeStr;
		} else {
			timeStr = today + " "  + timeStr;
		}
		LocalDateTime time = DateUtil.getDateTime4(timeStr);
		
		/*if (stkcode.startsWith("8") || stkcode.startsWith("9") || stkcode.startsWith("H")) {
			System.out.println(msg_);
		}*/
		//TODO type check
		Transaction t = Transaction.of(price, volume, time, Security.of(symbol, SecurityType.STOCK), 0, 0, OrderSide.UNKNOWN, "");
		return t;
	}
	
	/*The default */
	public static double parseDouble(String v_) {
		return StringUtils.isEmpty(v_) || v_.equals("-") || v_.equals("-.---")? -1 : Double.parseDouble(v_);
	}
}
