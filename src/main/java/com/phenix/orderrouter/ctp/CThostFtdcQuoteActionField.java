/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQuoteActionField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQuoteActionField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQuoteActionField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQuoteActionField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_InvestorID_get(swigCPtr, this);
  }

  public void setQuoteActionRef(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_QuoteActionRef_set(swigCPtr, this, value);
  }

  public int getQuoteActionRef() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_QuoteActionRef_get(swigCPtr, this);
  }

  public void setQuoteRef(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_QuoteRef_set(swigCPtr, this, value);
  }

  public String getQuoteRef() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_QuoteRef_get(swigCPtr, this);
  }

  public void setRequestID(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_RequestID_set(swigCPtr, this, value);
  }

  public int getRequestID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_RequestID_get(swigCPtr, this);
  }

  public void setFrontID(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_FrontID_set(swigCPtr, this, value);
  }

  public int getFrontID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_FrontID_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_SessionID_get(swigCPtr, this);
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ExchangeID_get(swigCPtr, this);
  }

  public void setQuoteSysID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_QuoteSysID_set(swigCPtr, this, value);
  }

  public String getQuoteSysID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_QuoteSysID_get(swigCPtr, this);
  }

  public void setActionFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ActionFlag_set(swigCPtr, this, value);
  }

  public char getActionFlag() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ActionFlag_get(swigCPtr, this);
  }

  public void setActionDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ActionDate_set(swigCPtr, this, value);
  }

  public String getActionDate() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ActionDate_get(swigCPtr, this);
  }

  public void setActionTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ActionTime_set(swigCPtr, this, value);
  }

  public String getActionTime() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ActionTime_get(swigCPtr, this);
  }

  public void setTraderID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_TraderID_set(swigCPtr, this, value);
  }

  public String getTraderID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_TraderID_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_InstallID_get(swigCPtr, this);
  }

  public void setQuoteLocalID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_QuoteLocalID_set(swigCPtr, this, value);
  }

  public String getQuoteLocalID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_QuoteLocalID_get(swigCPtr, this);
  }

  public void setActionLocalID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ActionLocalID_set(swigCPtr, this, value);
  }

  public String getActionLocalID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ActionLocalID_get(swigCPtr, this);
  }

  public void setParticipantID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ParticipantID_set(swigCPtr, this, value);
  }

  public String getParticipantID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ParticipantID_get(swigCPtr, this);
  }

  public void setClientID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ClientID_set(swigCPtr, this, value);
  }

  public String getClientID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_ClientID_get(swigCPtr, this);
  }

  public void setBusinessUnit(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_BusinessUnit_set(swigCPtr, this, value);
  }

  public String getBusinessUnit() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_BusinessUnit_get(swigCPtr, this);
  }

  public void setOrderActionStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_OrderActionStatus_set(swigCPtr, this, value);
  }

  public char getOrderActionStatus() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_OrderActionStatus_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_UserID_get(swigCPtr, this);
  }

  public void setStatusMsg(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_StatusMsg_set(swigCPtr, this, value);
  }

  public String getStatusMsg() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_StatusMsg_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQuoteActionField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcQuoteActionField_InstrumentID_get(swigCPtr, this);
  }

  public CThostFtdcQuoteActionField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQuoteActionField(), true);
  }

}
