/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryForQuoteField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryForQuoteField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryForQuoteField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryForQuoteField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_InvestorID_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_InstrumentID_get(swigCPtr, this);
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_ExchangeID_get(swigCPtr, this);
  }

  public void setInsertTimeStart(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_InsertTimeStart_set(swigCPtr, this, value);
  }

  public String getInsertTimeStart() {
    return CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_InsertTimeStart_get(swigCPtr, this);
  }

  public void setInsertTimeEnd(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_InsertTimeEnd_set(swigCPtr, this, value);
  }

  public String getInsertTimeEnd() {
    return CTP_wrap4javaJNI.CThostFtdcQryForQuoteField_InsertTimeEnd_get(swigCPtr, this);
  }

  public CThostFtdcQryForQuoteField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryForQuoteField(), true);
  }

}
