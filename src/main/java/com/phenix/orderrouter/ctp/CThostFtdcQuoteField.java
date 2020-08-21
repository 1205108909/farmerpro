/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQuoteField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQuoteField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQuoteField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQuoteField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_InvestorID_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_InstrumentID_get(swigCPtr, this);
  }

  public void setQuoteRef(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_QuoteRef_set(swigCPtr, this, value);
  }

  public String getQuoteRef() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_QuoteRef_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_UserID_get(swigCPtr, this);
  }

  public void setAskPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_AskPrice_set(swigCPtr, this, value);
  }

  public double getAskPrice() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_AskPrice_get(swigCPtr, this);
  }

  public void setBidPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_BidPrice_set(swigCPtr, this, value);
  }

  public double getBidPrice() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_BidPrice_get(swigCPtr, this);
  }

  public void setAskVolume(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_AskVolume_set(swigCPtr, this, value);
  }

  public int getAskVolume() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_AskVolume_get(swigCPtr, this);
  }

  public void setBidVolume(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_BidVolume_set(swigCPtr, this, value);
  }

  public int getBidVolume() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_BidVolume_get(swigCPtr, this);
  }

  public void setRequestID(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_RequestID_set(swigCPtr, this, value);
  }

  public int getRequestID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_RequestID_get(swigCPtr, this);
  }

  public void setBusinessUnit(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_BusinessUnit_set(swigCPtr, this, value);
  }

  public String getBusinessUnit() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_BusinessUnit_get(swigCPtr, this);
  }

  public void setAskOffsetFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_AskOffsetFlag_set(swigCPtr, this, value);
  }

  public char getAskOffsetFlag() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_AskOffsetFlag_get(swigCPtr, this);
  }

  public void setBidOffsetFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_BidOffsetFlag_set(swigCPtr, this, value);
  }

  public char getBidOffsetFlag() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_BidOffsetFlag_get(swigCPtr, this);
  }

  public void setAskHedgeFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_AskHedgeFlag_set(swigCPtr, this, value);
  }

  public char getAskHedgeFlag() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_AskHedgeFlag_get(swigCPtr, this);
  }

  public void setBidHedgeFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_BidHedgeFlag_set(swigCPtr, this, value);
  }

  public char getBidHedgeFlag() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_BidHedgeFlag_get(swigCPtr, this);
  }

  public void setQuoteLocalID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_QuoteLocalID_set(swigCPtr, this, value);
  }

  public String getQuoteLocalID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_QuoteLocalID_get(swigCPtr, this);
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_ExchangeID_get(swigCPtr, this);
  }

  public void setParticipantID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_ParticipantID_set(swigCPtr, this, value);
  }

  public String getParticipantID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_ParticipantID_get(swigCPtr, this);
  }

  public void setClientID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_ClientID_set(swigCPtr, this, value);
  }

  public String getClientID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_ClientID_get(swigCPtr, this);
  }

  public void setExchangeInstID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_ExchangeInstID_set(swigCPtr, this, value);
  }

  public String getExchangeInstID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_ExchangeInstID_get(swigCPtr, this);
  }

  public void setTraderID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_TraderID_set(swigCPtr, this, value);
  }

  public String getTraderID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_TraderID_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_InstallID_get(swigCPtr, this);
  }

  public void setNotifySequence(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_NotifySequence_set(swigCPtr, this, value);
  }

  public int getNotifySequence() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_NotifySequence_get(swigCPtr, this);
  }

  public void setOrderSubmitStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_OrderSubmitStatus_set(swigCPtr, this, value);
  }

  public char getOrderSubmitStatus() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_OrderSubmitStatus_get(swigCPtr, this);
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_TradingDay_get(swigCPtr, this);
  }

  public void setSettlementID(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_SettlementID_set(swigCPtr, this, value);
  }

  public int getSettlementID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_SettlementID_get(swigCPtr, this);
  }

  public void setQuoteSysID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_QuoteSysID_set(swigCPtr, this, value);
  }

  public String getQuoteSysID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_QuoteSysID_get(swigCPtr, this);
  }

  public void setInsertDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_InsertDate_set(swigCPtr, this, value);
  }

  public String getInsertDate() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_InsertDate_get(swigCPtr, this);
  }

  public void setInsertTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_InsertTime_set(swigCPtr, this, value);
  }

  public String getInsertTime() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_InsertTime_get(swigCPtr, this);
  }

  public void setCancelTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_CancelTime_set(swigCPtr, this, value);
  }

  public String getCancelTime() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_CancelTime_get(swigCPtr, this);
  }

  public void setQuoteStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_QuoteStatus_set(swigCPtr, this, value);
  }

  public char getQuoteStatus() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_QuoteStatus_get(swigCPtr, this);
  }

  public void setClearingPartID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_ClearingPartID_set(swigCPtr, this, value);
  }

  public String getClearingPartID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_ClearingPartID_get(swigCPtr, this);
  }

  public void setSequenceNo(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_SequenceNo_set(swigCPtr, this, value);
  }

  public int getSequenceNo() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_SequenceNo_get(swigCPtr, this);
  }

  public void setAskOrderSysID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_AskOrderSysID_set(swigCPtr, this, value);
  }

  public String getAskOrderSysID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_AskOrderSysID_get(swigCPtr, this);
  }

  public void setBidOrderSysID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_BidOrderSysID_set(swigCPtr, this, value);
  }

  public String getBidOrderSysID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_BidOrderSysID_get(swigCPtr, this);
  }

  public void setFrontID(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_FrontID_set(swigCPtr, this, value);
  }

  public int getFrontID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_FrontID_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_SessionID_get(swigCPtr, this);
  }

  public void setUserProductInfo(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_UserProductInfo_set(swigCPtr, this, value);
  }

  public String getUserProductInfo() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_UserProductInfo_get(swigCPtr, this);
  }

  public void setStatusMsg(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_StatusMsg_set(swigCPtr, this, value);
  }

  public String getStatusMsg() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_StatusMsg_get(swigCPtr, this);
  }

  public void setActiveUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_ActiveUserID_set(swigCPtr, this, value);
  }

  public String getActiveUserID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_ActiveUserID_get(swigCPtr, this);
  }

  public void setBrokerQuoteSeq(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_BrokerQuoteSeq_set(swigCPtr, this, value);
  }

  public int getBrokerQuoteSeq() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_BrokerQuoteSeq_get(swigCPtr, this);
  }

  public void setAskOrderRef(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_AskOrderRef_set(swigCPtr, this, value);
  }

  public String getAskOrderRef() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_AskOrderRef_get(swigCPtr, this);
  }

  public void setBidOrderRef(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_BidOrderRef_set(swigCPtr, this, value);
  }

  public String getBidOrderRef() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_BidOrderRef_get(swigCPtr, this);
  }

  public void setForQuoteSysID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteField_ForQuoteSysID_set(swigCPtr, this, value);
  }

  public String getForQuoteSysID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteField_ForQuoteSysID_get(swigCPtr, this);
  }

  public CThostFtdcQuoteField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQuoteField(), true);
  }

}