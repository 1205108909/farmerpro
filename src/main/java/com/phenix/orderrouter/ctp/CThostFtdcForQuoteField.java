/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcForQuoteField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcForQuoteField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcForQuoteField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcForQuoteField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_InvestorID_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_InstrumentID_get(swigCPtr, this);
  }

  public void setForQuoteRef(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_ForQuoteRef_set(swigCPtr, this, value);
  }

  public String getForQuoteRef() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_ForQuoteRef_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_UserID_get(swigCPtr, this);
  }

  public void setForQuoteLocalID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_ForQuoteLocalID_set(swigCPtr, this, value);
  }

  public String getForQuoteLocalID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_ForQuoteLocalID_get(swigCPtr, this);
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_ExchangeID_get(swigCPtr, this);
  }

  public void setParticipantID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_ParticipantID_set(swigCPtr, this, value);
  }

  public String getParticipantID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_ParticipantID_get(swigCPtr, this);
  }

  public void setClientID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_ClientID_set(swigCPtr, this, value);
  }

  public String getClientID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_ClientID_get(swigCPtr, this);
  }

  public void setExchangeInstID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_ExchangeInstID_set(swigCPtr, this, value);
  }

  public String getExchangeInstID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_ExchangeInstID_get(swigCPtr, this);
  }

  public void setTraderID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_TraderID_set(swigCPtr, this, value);
  }

  public String getTraderID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_TraderID_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_InstallID_get(swigCPtr, this);
  }

  public void setInsertDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_InsertDate_set(swigCPtr, this, value);
  }

  public String getInsertDate() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_InsertDate_get(swigCPtr, this);
  }

  public void setInsertTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_InsertTime_set(swigCPtr, this, value);
  }

  public String getInsertTime() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_InsertTime_get(swigCPtr, this);
  }

  public void setForQuoteStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_ForQuoteStatus_set(swigCPtr, this, value);
  }

  public char getForQuoteStatus() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_ForQuoteStatus_get(swigCPtr, this);
  }

  public void setFrontID(int value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_FrontID_set(swigCPtr, this, value);
  }

  public int getFrontID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_FrontID_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_SessionID_get(swigCPtr, this);
  }

  public void setStatusMsg(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_StatusMsg_set(swigCPtr, this, value);
  }

  public String getStatusMsg() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_StatusMsg_get(swigCPtr, this);
  }

  public void setActiveUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_ActiveUserID_set(swigCPtr, this, value);
  }

  public String getActiveUserID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_ActiveUserID_get(swigCPtr, this);
  }

  public void setBrokerForQutoSeq(int value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteField_BrokerForQutoSeq_set(swigCPtr, this, value);
  }

  public int getBrokerForQutoSeq() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteField_BrokerForQutoSeq_get(swigCPtr, this);
  }

  public CThostFtdcForQuoteField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcForQuoteField(), true);
  }

}