/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryDepthMarketDataField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryDepthMarketDataField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryDepthMarketDataField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryDepthMarketDataField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryDepthMarketDataField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcQryDepthMarketDataField_InstrumentID_get(swigCPtr, this);
  }

  public CThostFtdcQryDepthMarketDataField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryDepthMarketDataField(), true);
  }

  public void CopyTo(CThostFtdcQryDepthMarketDataField r) {
    CTP_wrap4javaJNI.CThostFtdcQryDepthMarketDataField_CopyTo(swigCPtr, this, CThostFtdcQryDepthMarketDataField.getCPtr(r), r);
  }

}
