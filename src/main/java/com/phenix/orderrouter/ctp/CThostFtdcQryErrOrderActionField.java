/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryErrOrderActionField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryErrOrderActionField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryErrOrderActionField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryErrOrderActionField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryErrOrderActionField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcQryErrOrderActionField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryErrOrderActionField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcQryErrOrderActionField_InvestorID_get(swigCPtr, this);
  }

  public CThostFtdcQryErrOrderActionField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryErrOrderActionField(), true);
  }

  public void CopyTo(CThostFtdcQryErrOrderActionField r) {
    CTP_wrap4javaJNI.CThostFtdcQryErrOrderActionField_CopyTo(swigCPtr, this, CThostFtdcQryErrOrderActionField.getCPtr(r), r);
  }

}
