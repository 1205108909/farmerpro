/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcForQuoteParamField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcForQuoteParamField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcForQuoteParamField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcForQuoteParamField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteParamField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteParamField_BrokerID_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteParamField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteParamField_InstrumentID_get(swigCPtr, this);
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteParamField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteParamField_ExchangeID_get(swigCPtr, this);
  }

  public void setLastPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteParamField_LastPrice_set(swigCPtr, this, value);
  }

  public double getLastPrice() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteParamField_LastPrice_get(swigCPtr, this);
  }

  public void setPriceInterval(double value) {
    CTP_wrap4javaJNI.CThostFtdcForQuoteParamField_PriceInterval_set(swigCPtr, this, value);
  }

  public double getPriceInterval() {
    return CTP_wrap4javaJNI.CThostFtdcForQuoteParamField_PriceInterval_get(swigCPtr, this);
  }

  public CThostFtdcForQuoteParamField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcForQuoteParamField(), true);
  }

}
