/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcErrOrderActionField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcErrOrderActionField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcErrOrderActionField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcErrOrderActionField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_InvestorID_get(swigCPtr, this);
  }

  public void setOrderActionRef(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_OrderActionRef_set(swigCPtr, this, value);
  }

  public int getOrderActionRef() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_OrderActionRef_get(swigCPtr, this);
  }

  public void setOrderRef(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_OrderRef_set(swigCPtr, this, value);
  }

  public String getOrderRef() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_OrderRef_get(swigCPtr, this);
  }

  public void setRequestID(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_RequestID_set(swigCPtr, this, value);
  }

  public int getRequestID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_RequestID_get(swigCPtr, this);
  }

  public void setFrontID(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_FrontID_set(swigCPtr, this, value);
  }

  public int getFrontID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_FrontID_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_SessionID_get(swigCPtr, this);
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ExchangeID_get(swigCPtr, this);
  }

  public void setOrderSysID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_OrderSysID_set(swigCPtr, this, value);
  }

  public String getOrderSysID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_OrderSysID_get(swigCPtr, this);
  }

  public void setActionFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ActionFlag_set(swigCPtr, this, value);
  }

  public char getActionFlag() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ActionFlag_get(swigCPtr, this);
  }

  public void setLimitPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_LimitPrice_set(swigCPtr, this, value);
  }

  public double getLimitPrice() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_LimitPrice_get(swigCPtr, this);
  }

  public void setVolumeChange(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_VolumeChange_set(swigCPtr, this, value);
  }

  public int getVolumeChange() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_VolumeChange_get(swigCPtr, this);
  }

  public void setActionDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ActionDate_set(swigCPtr, this, value);
  }

  public String getActionDate() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ActionDate_get(swigCPtr, this);
  }

  public void setActionTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ActionTime_set(swigCPtr, this, value);
  }

  public String getActionTime() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ActionTime_get(swigCPtr, this);
  }

  public void setTraderID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_TraderID_set(swigCPtr, this, value);
  }

  public String getTraderID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_TraderID_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_InstallID_get(swigCPtr, this);
  }

  public void setOrderLocalID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_OrderLocalID_set(swigCPtr, this, value);
  }

  public String getOrderLocalID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_OrderLocalID_get(swigCPtr, this);
  }

  public void setActionLocalID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ActionLocalID_set(swigCPtr, this, value);
  }

  public String getActionLocalID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ActionLocalID_get(swigCPtr, this);
  }

  public void setParticipantID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ParticipantID_set(swigCPtr, this, value);
  }

  public String getParticipantID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ParticipantID_get(swigCPtr, this);
  }

  public void setClientID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ClientID_set(swigCPtr, this, value);
  }

  public String getClientID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ClientID_get(swigCPtr, this);
  }

  public void setBusinessUnit(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_BusinessUnit_set(swigCPtr, this, value);
  }

  public String getBusinessUnit() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_BusinessUnit_get(swigCPtr, this);
  }

  public void setOrderActionStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_OrderActionStatus_set(swigCPtr, this, value);
  }

  public char getOrderActionStatus() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_OrderActionStatus_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_UserID_get(swigCPtr, this);
  }

  public void setStatusMsg(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_StatusMsg_set(swigCPtr, this, value);
  }

  public String getStatusMsg() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_StatusMsg_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_InstrumentID_get(swigCPtr, this);
  }

  public void setErrorID(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ErrorID_set(swigCPtr, this, value);
  }

  public int getErrorID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ErrorID_get(swigCPtr, this);
  }

  public void setErrorMsg(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ErrorMsg_set(swigCPtr, this, value);
  }

  public String getErrorMsg() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_ErrorMsg_get(swigCPtr, this);
  }

  public CThostFtdcErrOrderActionField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcErrOrderActionField(), true);
  }

  public void CopyTo(CThostFtdcErrOrderActionField r) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderActionField_CopyTo(swigCPtr, this, CThostFtdcErrOrderActionField.getCPtr(r), r);
  }

}
