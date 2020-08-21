/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcInputCombActionField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcInputCombActionField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcInputCombActionField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcInputCombActionField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcInputCombActionField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcInputCombActionField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcInputCombActionField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcInputCombActionField_InvestorID_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcInputCombActionField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcInputCombActionField_InstrumentID_get(swigCPtr, this);
  }

  public void setCombActionRef(String value) {
    CTP_wrap4javaJNI.CThostFtdcInputCombActionField_CombActionRef_set(swigCPtr, this, value);
  }

  public String getCombActionRef() {
    return CTP_wrap4javaJNI.CThostFtdcInputCombActionField_CombActionRef_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcInputCombActionField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcInputCombActionField_UserID_get(swigCPtr, this);
  }

  public void setDirection(char value) {
    CTP_wrap4javaJNI.CThostFtdcInputCombActionField_Direction_set(swigCPtr, this, value);
  }

  public char getDirection() {
    return CTP_wrap4javaJNI.CThostFtdcInputCombActionField_Direction_get(swigCPtr, this);
  }

  public void setVolume(int value) {
    CTP_wrap4javaJNI.CThostFtdcInputCombActionField_Volume_set(swigCPtr, this, value);
  }

  public int getVolume() {
    return CTP_wrap4javaJNI.CThostFtdcInputCombActionField_Volume_get(swigCPtr, this);
  }

  public void setCombDirection(char value) {
    CTP_wrap4javaJNI.CThostFtdcInputCombActionField_CombDirection_set(swigCPtr, this, value);
  }

  public char getCombDirection() {
    return CTP_wrap4javaJNI.CThostFtdcInputCombActionField_CombDirection_get(swigCPtr, this);
  }

  public void setHedgeFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcInputCombActionField_HedgeFlag_set(swigCPtr, this, value);
  }

  public char getHedgeFlag() {
    return CTP_wrap4javaJNI.CThostFtdcInputCombActionField_HedgeFlag_get(swigCPtr, this);
  }

  public CThostFtdcInputCombActionField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcInputCombActionField(), true);
  }

}
