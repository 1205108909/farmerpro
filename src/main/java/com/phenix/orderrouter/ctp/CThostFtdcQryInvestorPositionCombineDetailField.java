/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryInvestorPositionCombineDetailField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryInvestorPositionCombineDetailField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryInvestorPositionCombineDetailField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryInvestorPositionCombineDetailField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryInvestorPositionCombineDetailField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcQryInvestorPositionCombineDetailField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryInvestorPositionCombineDetailField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcQryInvestorPositionCombineDetailField_InvestorID_get(swigCPtr, this);
  }

  public void setCombInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryInvestorPositionCombineDetailField_CombInstrumentID_set(swigCPtr, this, value);
  }

  public String getCombInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcQryInvestorPositionCombineDetailField_CombInstrumentID_get(swigCPtr, this);
  }

  public CThostFtdcQryInvestorPositionCombineDetailField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryInvestorPositionCombineDetailField(), true);
  }

  public void CopyTo(CThostFtdcQryInvestorPositionCombineDetailField r) {
    CTP_wrap4javaJNI.CThostFtdcQryInvestorPositionCombineDetailField_CopyTo(swigCPtr, this, CThostFtdcQryInvestorPositionCombineDetailField.getCPtr(r), r);
  }

}
