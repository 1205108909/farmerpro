/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcInputExecOrderField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcInputExecOrderField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcInputExecOrderField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcInputExecOrderField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_InvestorID_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_InstrumentID_get(swigCPtr, this);
  }

  public void setExecOrderRef(String value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_ExecOrderRef_set(swigCPtr, this, value);
  }

  public String getExecOrderRef() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_ExecOrderRef_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_UserID_get(swigCPtr, this);
  }

  public void setVolume(int value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_Volume_set(swigCPtr, this, value);
  }

  public int getVolume() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_Volume_get(swigCPtr, this);
  }

  public void setRequestID(int value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_RequestID_set(swigCPtr, this, value);
  }

  public int getRequestID() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_RequestID_get(swigCPtr, this);
  }

  public void setBusinessUnit(String value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_BusinessUnit_set(swigCPtr, this, value);
  }

  public String getBusinessUnit() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_BusinessUnit_get(swigCPtr, this);
  }

  public void setOffsetFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_OffsetFlag_set(swigCPtr, this, value);
  }

  public char getOffsetFlag() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_OffsetFlag_get(swigCPtr, this);
  }

  public void setHedgeFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_HedgeFlag_set(swigCPtr, this, value);
  }

  public char getHedgeFlag() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_HedgeFlag_get(swigCPtr, this);
  }

  public void setActionType(char value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_ActionType_set(swigCPtr, this, value);
  }

  public char getActionType() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_ActionType_get(swigCPtr, this);
  }

  public void setPosiDirection(char value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_PosiDirection_set(swigCPtr, this, value);
  }

  public char getPosiDirection() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_PosiDirection_get(swigCPtr, this);
  }

  public void setReservePositionFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_ReservePositionFlag_set(swigCPtr, this, value);
  }

  public char getReservePositionFlag() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_ReservePositionFlag_get(swigCPtr, this);
  }

  public void setCloseFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_CloseFlag_set(swigCPtr, this, value);
  }

  public char getCloseFlag() {
    return CTP_wrap4javaJNI.CThostFtdcInputExecOrderField_CloseFlag_get(swigCPtr, this);
  }

  public CThostFtdcInputExecOrderField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcInputExecOrderField(), true);
  }

}
