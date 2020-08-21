/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcExchangeQuoteField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcExchangeQuoteField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcExchangeQuoteField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcExchangeQuoteField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setAskPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_AskPrice_set(swigCPtr, this, value);
  }

  public double getAskPrice() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_AskPrice_get(swigCPtr, this);
  }

  public void setBidPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BidPrice_set(swigCPtr, this, value);
  }

  public double getBidPrice() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BidPrice_get(swigCPtr, this);
  }

  public void setAskVolume(int value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_AskVolume_set(swigCPtr, this, value);
  }

  public int getAskVolume() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_AskVolume_get(swigCPtr, this);
  }

  public void setBidVolume(int value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BidVolume_set(swigCPtr, this, value);
  }

  public int getBidVolume() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BidVolume_get(swigCPtr, this);
  }

  public void setRequestID(int value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_RequestID_set(swigCPtr, this, value);
  }

  public int getRequestID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_RequestID_get(swigCPtr, this);
  }

  public void setBusinessUnit(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BusinessUnit_set(swigCPtr, this, value);
  }

  public String getBusinessUnit() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BusinessUnit_get(swigCPtr, this);
  }

  public void setAskOffsetFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_AskOffsetFlag_set(swigCPtr, this, value);
  }

  public char getAskOffsetFlag() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_AskOffsetFlag_get(swigCPtr, this);
  }

  public void setBidOffsetFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BidOffsetFlag_set(swigCPtr, this, value);
  }

  public char getBidOffsetFlag() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BidOffsetFlag_get(swigCPtr, this);
  }

  public void setAskHedgeFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_AskHedgeFlag_set(swigCPtr, this, value);
  }

  public char getAskHedgeFlag() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_AskHedgeFlag_get(swigCPtr, this);
  }

  public void setBidHedgeFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BidHedgeFlag_set(swigCPtr, this, value);
  }

  public char getBidHedgeFlag() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BidHedgeFlag_get(swigCPtr, this);
  }

  public void setQuoteLocalID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_QuoteLocalID_set(swigCPtr, this, value);
  }

  public String getQuoteLocalID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_QuoteLocalID_get(swigCPtr, this);
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ExchangeID_get(swigCPtr, this);
  }

  public void setParticipantID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ParticipantID_set(swigCPtr, this, value);
  }

  public String getParticipantID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ParticipantID_get(swigCPtr, this);
  }

  public void setClientID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ClientID_set(swigCPtr, this, value);
  }

  public String getClientID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ClientID_get(swigCPtr, this);
  }

  public void setExchangeInstID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ExchangeInstID_set(swigCPtr, this, value);
  }

  public String getExchangeInstID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ExchangeInstID_get(swigCPtr, this);
  }

  public void setTraderID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_TraderID_set(swigCPtr, this, value);
  }

  public String getTraderID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_TraderID_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_InstallID_get(swigCPtr, this);
  }

  public void setNotifySequence(int value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_NotifySequence_set(swigCPtr, this, value);
  }

  public int getNotifySequence() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_NotifySequence_get(swigCPtr, this);
  }

  public void setOrderSubmitStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_OrderSubmitStatus_set(swigCPtr, this, value);
  }

  public char getOrderSubmitStatus() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_OrderSubmitStatus_get(swigCPtr, this);
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_TradingDay_get(swigCPtr, this);
  }

  public void setSettlementID(int value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_SettlementID_set(swigCPtr, this, value);
  }

  public int getSettlementID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_SettlementID_get(swigCPtr, this);
  }

  public void setQuoteSysID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_QuoteSysID_set(swigCPtr, this, value);
  }

  public String getQuoteSysID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_QuoteSysID_get(swigCPtr, this);
  }

  public void setInsertDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_InsertDate_set(swigCPtr, this, value);
  }

  public String getInsertDate() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_InsertDate_get(swigCPtr, this);
  }

  public void setInsertTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_InsertTime_set(swigCPtr, this, value);
  }

  public String getInsertTime() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_InsertTime_get(swigCPtr, this);
  }

  public void setCancelTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_CancelTime_set(swigCPtr, this, value);
  }

  public String getCancelTime() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_CancelTime_get(swigCPtr, this);
  }

  public void setQuoteStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_QuoteStatus_set(swigCPtr, this, value);
  }

  public char getQuoteStatus() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_QuoteStatus_get(swigCPtr, this);
  }

  public void setClearingPartID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ClearingPartID_set(swigCPtr, this, value);
  }

  public String getClearingPartID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ClearingPartID_get(swigCPtr, this);
  }

  public void setSequenceNo(int value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_SequenceNo_set(swigCPtr, this, value);
  }

  public int getSequenceNo() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_SequenceNo_get(swigCPtr, this);
  }

  public void setAskOrderSysID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_AskOrderSysID_set(swigCPtr, this, value);
  }

  public String getAskOrderSysID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_AskOrderSysID_get(swigCPtr, this);
  }

  public void setBidOrderSysID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BidOrderSysID_set(swigCPtr, this, value);
  }

  public String getBidOrderSysID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_BidOrderSysID_get(swigCPtr, this);
  }

  public void setForQuoteSysID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ForQuoteSysID_set(swigCPtr, this, value);
  }

  public String getForQuoteSysID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeQuoteField_ForQuoteSysID_get(swigCPtr, this);
  }

  public CThostFtdcExchangeQuoteField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcExchangeQuoteField(), true);
  }

}
