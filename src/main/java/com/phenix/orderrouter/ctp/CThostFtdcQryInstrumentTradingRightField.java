/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryInstrumentTradingRightField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryInstrumentTradingRightField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryInstrumentTradingRightField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryInstrumentTradingRightField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryInstrumentTradingRightField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcQryInstrumentTradingRightField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryInstrumentTradingRightField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcQryInstrumentTradingRightField_InvestorID_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryInstrumentTradingRightField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcQryInstrumentTradingRightField_InstrumentID_get(swigCPtr, this);
  }

  public CThostFtdcQryInstrumentTradingRightField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryInstrumentTradingRightField(), true);
  }

  public void CopyTo(CThostFtdcQryInstrumentTradingRightField r) {
    CTP_wrap4javaJNI.CThostFtdcQryInstrumentTradingRightField_CopyTo(swigCPtr, this, CThostFtdcQryInstrumentTradingRightField.getCPtr(r), r);
  }

}