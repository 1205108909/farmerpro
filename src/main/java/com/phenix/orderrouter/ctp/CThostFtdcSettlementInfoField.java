/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcSettlementInfoField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcSettlementInfoField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcSettlementInfoField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcSettlementInfoField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_TradingDay_get(swigCPtr, this);
  }

  public void setSettlementID(int value) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_SettlementID_set(swigCPtr, this, value);
  }

  public int getSettlementID() {
    return CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_SettlementID_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_InvestorID_get(swigCPtr, this);
  }

  public void setSequenceNo(int value) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_SequenceNo_set(swigCPtr, this, value);
  }

  public int getSequenceNo() {
    return CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_SequenceNo_get(swigCPtr, this);
  }

  public void setContent(String value) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_Content_set(swigCPtr, this, value);
  }

  public String getContent() {
    return CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_Content_get(swigCPtr, this);
  }

  public CThostFtdcSettlementInfoField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcSettlementInfoField(), true);
  }

  public void CopyTo(CThostFtdcSettlementInfoField r) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoField_CopyTo(swigCPtr, this, CThostFtdcSettlementInfoField.getCPtr(r), r);
  }

}