/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcTraderOfferField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcTraderOfferField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcTraderOfferField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcTraderOfferField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ExchangeID_get(swigCPtr, this);
  }

  public void setTraderID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_TraderID_set(swigCPtr, this, value);
  }

  public String getTraderID() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_TraderID_get(swigCPtr, this);
  }

  public void setParticipantID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ParticipantID_set(swigCPtr, this, value);
  }

  public String getParticipantID() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ParticipantID_get(swigCPtr, this);
  }

  public void setPassword(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_Password_set(swigCPtr, this, value);
  }

  public String getPassword() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_Password_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_InstallID_get(swigCPtr, this);
  }

  public void setOrderLocalID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_OrderLocalID_set(swigCPtr, this, value);
  }

  public String getOrderLocalID() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_OrderLocalID_get(swigCPtr, this);
  }

  public void setTraderConnectStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_TraderConnectStatus_set(swigCPtr, this, value);
  }

  public char getTraderConnectStatus() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_TraderConnectStatus_get(swigCPtr, this);
  }

  public void setConnectRequestDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ConnectRequestDate_set(swigCPtr, this, value);
  }

  public String getConnectRequestDate() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ConnectRequestDate_get(swigCPtr, this);
  }

  public void setConnectRequestTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ConnectRequestTime_set(swigCPtr, this, value);
  }

  public String getConnectRequestTime() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ConnectRequestTime_get(swigCPtr, this);
  }

  public void setLastReportDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_LastReportDate_set(swigCPtr, this, value);
  }

  public String getLastReportDate() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_LastReportDate_get(swigCPtr, this);
  }

  public void setLastReportTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_LastReportTime_set(swigCPtr, this, value);
  }

  public String getLastReportTime() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_LastReportTime_get(swigCPtr, this);
  }

  public void setConnectDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ConnectDate_set(swigCPtr, this, value);
  }

  public String getConnectDate() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ConnectDate_get(swigCPtr, this);
  }

  public void setConnectTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ConnectTime_set(swigCPtr, this, value);
  }

  public String getConnectTime() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_ConnectTime_get(swigCPtr, this);
  }

  public void setStartDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_StartDate_set(swigCPtr, this, value);
  }

  public String getStartDate() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_StartDate_get(swigCPtr, this);
  }

  public void setStartTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_StartTime_set(swigCPtr, this, value);
  }

  public String getStartTime() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_StartTime_get(swigCPtr, this);
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_TradingDay_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_BrokerID_get(swigCPtr, this);
  }

  public void setMaxTradeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_MaxTradeID_set(swigCPtr, this, value);
  }

  public String getMaxTradeID() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_MaxTradeID_get(swigCPtr, this);
  }

  public void setMaxOrderMessageReference(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_MaxOrderMessageReference_set(swigCPtr, this, value);
  }

  public String getMaxOrderMessageReference() {
    return CTP_wrap4javaJNI.CThostFtdcTraderOfferField_MaxOrderMessageReference_get(swigCPtr, this);
  }

  public CThostFtdcTraderOfferField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcTraderOfferField(), true);
  }

  public void CopyTo(CThostFtdcTraderOfferField r) {
    CTP_wrap4javaJNI.CThostFtdcTraderOfferField_CopyTo(swigCPtr, this, CThostFtdcTraderOfferField.getCPtr(r), r);
  }

}
