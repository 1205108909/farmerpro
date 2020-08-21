package com.phenix.util;

import com.phenix.farmer.FarmerConfigManager;
import com.phenix.farmer.FarmerController;
import com.phenix.farmer.FarmerDataManager;
import com.phenix.farmer.signal.SignalMode;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Alert {
	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Alert.class);

	public enum Severity {
		Clear,
		Critical,
		Major, 
		Minor,
		Fatal,
		Info
	}

	public static void fireAlert(Severity severity_, String key_, String msg_) {
		if(SignalMode.BACK_TEST == FarmerController.getInstance().getSignalMode())
			return;

		String alertID = key_ + "_" + FarmerConfigManager.getInstance().getAdminConfig().getAdminStr();
		LOGGER.info("Alert [ " + severity_.toString() + "] - [" + alertID + "]: " + msg_);

		Map<String, String> alertData = new HashMap<>(4);
		alertData.put("Severity", severity_.toString());
		alertData.put("AlertID", alertID);
		alertData.put("Message", msg_);
		alertData.put("Time", DateUtil.time2Str2(FarmerDataManager.getInstance().getTimeNow()));

		String topic = "ENG.ALERT.BUSINESS." + alertID;

		try {
			FarmerDataManager.getInstance().publishMessage(topic, alertData);
		} catch (Exception e_) {
			LOGGER.error(e_.getMessage(), e_);
		}
	}
	
	public static void clearAlert(String key_) {
		String alertID = key_ + "_" + FarmerConfigManager.getInstance().getAdminConfig().getAdminStr();
		LOGGER.info("Clear alert [" + alertID + "]");

		Map<String, String> alertData = new HashMap<>(2);
		alertData.put("Severity", Severity.Clear.toString());
		alertData.put("AlertID", alertID);

		String topic = "ENG.ALERT,BUSINESS." + alertID;

		try {
			FarmerDataManager.getInstance().publishMessage(topic, alertData);
		} catch (Exception e_) {
			LOGGER.error(e_.getMessage(), e_);
		}
	}
}
