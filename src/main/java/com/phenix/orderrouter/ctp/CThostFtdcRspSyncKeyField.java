/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcRspSyncKeyField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcRspSyncKeyField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcRspSyncKeyField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcRspSyncKeyField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setTradeCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_TradeCode_set(swigCPtr, this, value);
  }

  public String getTradeCode() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_TradeCode_get(swigCPtr, this);
  }

  public void setBankID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BankID_set(swigCPtr, this, value);
  }

  public String getBankID() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BankID_get(swigCPtr, this);
  }

  public void setBankBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BankBranchID_set(swigCPtr, this, value);
  }

  public String getBankBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BankBranchID_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BrokerID_get(swigCPtr, this);
  }

  public void setBrokerBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BrokerBranchID_set(swigCPtr, this, value);
  }

  public String getBrokerBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BrokerBranchID_get(swigCPtr, this);
  }

  public void setTradeDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_TradeDate_set(swigCPtr, this, value);
  }

  public String getTradeDate() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_TradeDate_get(swigCPtr, this);
  }

  public void setTradeTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_TradeTime_set(swigCPtr, this, value);
  }

  public String getTradeTime() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_TradeTime_get(swigCPtr, this);
  }

  public void setBankSerial(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BankSerial_set(swigCPtr, this, value);
  }

  public String getBankSerial() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BankSerial_get(swigCPtr, this);
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_TradingDay_get(swigCPtr, this);
  }

  public void setPlateSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_PlateSerial_set(swigCPtr, this, value);
  }

  public int getPlateSerial() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_PlateSerial_get(swigCPtr, this);
  }

  public void setLastFragment(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_LastFragment_set(swigCPtr, this, value);
  }

  public char getLastFragment() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_LastFragment_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_SessionID_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_InstallID_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_UserID_get(swigCPtr, this);
  }

  public void setMessage(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_Message_set(swigCPtr, this, value);
  }

  public String getMessage() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_Message_get(swigCPtr, this);
  }

  public void setDeviceID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_DeviceID_set(swigCPtr, this, value);
  }

  public String getDeviceID() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_DeviceID_get(swigCPtr, this);
  }

  public void setBrokerIDByBank(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BrokerIDByBank_set(swigCPtr, this, value);
  }

  public String getBrokerIDByBank() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_BrokerIDByBank_get(swigCPtr, this);
  }

  public void setOperNo(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_OperNo_set(swigCPtr, this, value);
  }

  public String getOperNo() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_OperNo_get(swigCPtr, this);
  }

  public void setRequestID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_RequestID_set(swigCPtr, this, value);
  }

  public int getRequestID() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_RequestID_get(swigCPtr, this);
  }

  public void setTID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_TID_set(swigCPtr, this, value);
  }

  public int getTID() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_TID_get(swigCPtr, this);
  }

  public void setErrorID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_ErrorID_set(swigCPtr, this, value);
  }

  public int getErrorID() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_ErrorID_get(swigCPtr, this);
  }

  public void setErrorMsg(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_ErrorMsg_set(swigCPtr, this, value);
  }

  public String getErrorMsg() {
    return CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_ErrorMsg_get(swigCPtr, this);
  }

  public CThostFtdcRspSyncKeyField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcRspSyncKeyField(), true);
  }

  public void CopyTo(CThostFtdcRspSyncKeyField r) {
    CTP_wrap4javaJNI.CThostFtdcRspSyncKeyField_CopyTo(swigCPtr, this, CThostFtdcRspSyncKeyField.getCPtr(r), r);
  }

}
