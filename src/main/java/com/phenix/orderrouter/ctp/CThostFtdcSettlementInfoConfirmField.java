/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcSettlementInfoConfirmField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcSettlementInfoConfirmField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcSettlementInfoConfirmField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcSettlementInfoConfirmField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoConfirmField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcSettlementInfoConfirmField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoConfirmField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcSettlementInfoConfirmField_InvestorID_get(swigCPtr, this);
  }

  public void setConfirmDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoConfirmField_ConfirmDate_set(swigCPtr, this, value);
  }

  public String getConfirmDate() {
    return CTP_wrap4javaJNI.CThostFtdcSettlementInfoConfirmField_ConfirmDate_get(swigCPtr, this);
  }

  public void setConfirmTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoConfirmField_ConfirmTime_set(swigCPtr, this, value);
  }

  public String getConfirmTime() {
    return CTP_wrap4javaJNI.CThostFtdcSettlementInfoConfirmField_ConfirmTime_get(swigCPtr, this);
  }

  public CThostFtdcSettlementInfoConfirmField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcSettlementInfoConfirmField(), true);
  }

  public void CopyTo(CThostFtdcSettlementInfoConfirmField r) {
    CTP_wrap4javaJNI.CThostFtdcSettlementInfoConfirmField_CopyTo(swigCPtr, this, CThostFtdcSettlementInfoConfirmField.getCPtr(r), r);
  }

}
