/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcMarketDataAveragePriceField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcMarketDataAveragePriceField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcMarketDataAveragePriceField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcMarketDataAveragePriceField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setAveragePrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataAveragePriceField_AveragePrice_set(swigCPtr, this, value);
  }

  public double getAveragePrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataAveragePriceField_AveragePrice_get(swigCPtr, this);
  }

  public CThostFtdcMarketDataAveragePriceField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcMarketDataAveragePriceField(), true);
  }

  public void CopyTo(CThostFtdcMarketDataAveragePriceField r) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataAveragePriceField_CopyTo(swigCPtr, this, CThostFtdcMarketDataAveragePriceField.getCPtr(r), r);
  }

}
