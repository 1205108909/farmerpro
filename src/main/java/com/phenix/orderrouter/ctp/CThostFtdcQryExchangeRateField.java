/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryExchangeRateField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryExchangeRateField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryExchangeRateField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryExchangeRateField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryExchangeRateField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcQryExchangeRateField_BrokerID_get(swigCPtr, this);
  }

  public void setFromCurrencyID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryExchangeRateField_FromCurrencyID_set(swigCPtr, this, value);
  }

  public String getFromCurrencyID() {
    return CTP_wrap4javaJNI.CThostFtdcQryExchangeRateField_FromCurrencyID_get(swigCPtr, this);
  }

  public void setToCurrencyID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryExchangeRateField_ToCurrencyID_set(swigCPtr, this, value);
  }

  public String getToCurrencyID() {
    return CTP_wrap4javaJNI.CThostFtdcQryExchangeRateField_ToCurrencyID_get(swigCPtr, this);
  }

  public CThostFtdcQryExchangeRateField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryExchangeRateField(), true);
  }

}
