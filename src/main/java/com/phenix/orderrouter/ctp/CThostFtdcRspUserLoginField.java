/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcRspUserLoginField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcRspUserLoginField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcRspUserLoginField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcRspUserLoginField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_TradingDay_get(swigCPtr, this);
  }

  public void setLoginTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_LoginTime_set(swigCPtr, this, value);
  }

  public String getLoginTime() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_LoginTime_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_BrokerID_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_UserID_get(swigCPtr, this);
  }

  public void setSystemName(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_SystemName_set(swigCPtr, this, value);
  }

  public String getSystemName() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_SystemName_get(swigCPtr, this);
  }

  public void setFrontID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_FrontID_set(swigCPtr, this, value);
  }

  public int getFrontID() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_FrontID_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_SessionID_get(swigCPtr, this);
  }

  public void setMaxOrderRef(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_MaxOrderRef_set(swigCPtr, this, value);
  }

  public String getMaxOrderRef() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_MaxOrderRef_get(swigCPtr, this);
  }

  public void setSHFETime(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_SHFETime_set(swigCPtr, this, value);
  }

  public String getSHFETime() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_SHFETime_get(swigCPtr, this);
  }

  public void setDCETime(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_DCETime_set(swigCPtr, this, value);
  }

  public String getDCETime() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_DCETime_get(swigCPtr, this);
  }

  public void setCZCETime(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_CZCETime_set(swigCPtr, this, value);
  }

  public String getCZCETime() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_CZCETime_get(swigCPtr, this);
  }

  public void setFFEXTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_FFEXTime_set(swigCPtr, this, value);
  }

  public String getFFEXTime() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_FFEXTime_get(swigCPtr, this);
  }

  public void setINETime(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_INETime_set(swigCPtr, this, value);
  }

  public String getINETime() {
    return CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_INETime_get(swigCPtr, this);
  }

  public CThostFtdcRspUserLoginField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcRspUserLoginField(), true);
  }

  public void CopyTo(CThostFtdcRspUserLoginField r) {
    CTP_wrap4javaJNI.CThostFtdcRspUserLoginField_CopyTo(swigCPtr, this, CThostFtdcRspUserLoginField.getCPtr(r), r);
  }

}
