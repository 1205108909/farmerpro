/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryCFMMCTradingAccountKeyField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryCFMMCTradingAccountKeyField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryCFMMCTradingAccountKeyField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryCFMMCTradingAccountKeyField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryCFMMCTradingAccountKeyField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcQryCFMMCTradingAccountKeyField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryCFMMCTradingAccountKeyField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcQryCFMMCTradingAccountKeyField_InvestorID_get(swigCPtr, this);
  }

  public CThostFtdcQryCFMMCTradingAccountKeyField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryCFMMCTradingAccountKeyField(), true);
  }

  public void CopyTo(CThostFtdcQryCFMMCTradingAccountKeyField r) {
    CTP_wrap4javaJNI.CThostFtdcQryCFMMCTradingAccountKeyField_CopyTo(swigCPtr, this, CThostFtdcQryCFMMCTradingAccountKeyField.getCPtr(r), r);
  }

}
