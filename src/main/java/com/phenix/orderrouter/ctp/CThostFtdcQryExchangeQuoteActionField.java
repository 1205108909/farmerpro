/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryExchangeQuoteActionField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryExchangeQuoteActionField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryExchangeQuoteActionField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryExchangeQuoteActionField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setParticipantID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryExchangeQuoteActionField_ParticipantID_set(swigCPtr, this, value);
  }

  public String getParticipantID() {
    return CTP_wrap4javaJNI.CThostFtdcQryExchangeQuoteActionField_ParticipantID_get(swigCPtr, this);
  }

  public void setClientID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryExchangeQuoteActionField_ClientID_set(swigCPtr, this, value);
  }

  public String getClientID() {
    return CTP_wrap4javaJNI.CThostFtdcQryExchangeQuoteActionField_ClientID_get(swigCPtr, this);
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryExchangeQuoteActionField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcQryExchangeQuoteActionField_ExchangeID_get(swigCPtr, this);
  }

  public void setTraderID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryExchangeQuoteActionField_TraderID_set(swigCPtr, this, value);
  }

  public String getTraderID() {
    return CTP_wrap4javaJNI.CThostFtdcQryExchangeQuoteActionField_TraderID_get(swigCPtr, this);
  }

  public CThostFtdcQryExchangeQuoteActionField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryExchangeQuoteActionField(), true);
  }

}
