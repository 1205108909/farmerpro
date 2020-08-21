/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcExchangeOrderActionField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcExchangeOrderActionField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcExchangeOrderActionField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcExchangeOrderActionField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ExchangeID_get(swigCPtr, this);
  }

  public void setOrderSysID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_OrderSysID_set(swigCPtr, this, value);
  }

  public String getOrderSysID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_OrderSysID_get(swigCPtr, this);
  }

  public void setActionFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ActionFlag_set(swigCPtr, this, value);
  }

  public char getActionFlag() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ActionFlag_get(swigCPtr, this);
  }

  public void setLimitPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_LimitPrice_set(swigCPtr, this, value);
  }

  public double getLimitPrice() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_LimitPrice_get(swigCPtr, this);
  }

  public void setVolumeChange(int value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_VolumeChange_set(swigCPtr, this, value);
  }

  public int getVolumeChange() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_VolumeChange_get(swigCPtr, this);
  }

  public void setActionDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ActionDate_set(swigCPtr, this, value);
  }

  public String getActionDate() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ActionDate_get(swigCPtr, this);
  }

  public void setActionTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ActionTime_set(swigCPtr, this, value);
  }

  public String getActionTime() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ActionTime_get(swigCPtr, this);
  }

  public void setTraderID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_TraderID_set(swigCPtr, this, value);
  }

  public String getTraderID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_TraderID_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_InstallID_get(swigCPtr, this);
  }

  public void setOrderLocalID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_OrderLocalID_set(swigCPtr, this, value);
  }

  public String getOrderLocalID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_OrderLocalID_get(swigCPtr, this);
  }

  public void setActionLocalID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ActionLocalID_set(swigCPtr, this, value);
  }

  public String getActionLocalID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ActionLocalID_get(swigCPtr, this);
  }

  public void setParticipantID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ParticipantID_set(swigCPtr, this, value);
  }

  public String getParticipantID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ParticipantID_get(swigCPtr, this);
  }

  public void setClientID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ClientID_set(swigCPtr, this, value);
  }

  public String getClientID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_ClientID_get(swigCPtr, this);
  }

  public void setBusinessUnit(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_BusinessUnit_set(swigCPtr, this, value);
  }

  public String getBusinessUnit() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_BusinessUnit_get(swigCPtr, this);
  }

  public void setOrderActionStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_OrderActionStatus_set(swigCPtr, this, value);
  }

  public char getOrderActionStatus() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_OrderActionStatus_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_UserID_get(swigCPtr, this);
  }

  public CThostFtdcExchangeOrderActionField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcExchangeOrderActionField(), true);
  }

  public void CopyTo(CThostFtdcExchangeOrderActionField r) {
    CTP_wrap4javaJNI.CThostFtdcExchangeOrderActionField_CopyTo(swigCPtr, this, CThostFtdcExchangeOrderActionField.getCPtr(r), r);
  }

}
