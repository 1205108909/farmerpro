/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcMarketDataStaticField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcMarketDataStaticField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcMarketDataStaticField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcMarketDataStaticField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setOpenPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_OpenPrice_set(swigCPtr, this, value);
  }

  public double getOpenPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_OpenPrice_get(swigCPtr, this);
  }

  public void setHighestPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_HighestPrice_set(swigCPtr, this, value);
  }

  public double getHighestPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_HighestPrice_get(swigCPtr, this);
  }

  public void setLowestPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_LowestPrice_set(swigCPtr, this, value);
  }

  public double getLowestPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_LowestPrice_get(swigCPtr, this);
  }

  public void setClosePrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_ClosePrice_set(swigCPtr, this, value);
  }

  public double getClosePrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_ClosePrice_get(swigCPtr, this);
  }

  public void setUpperLimitPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_UpperLimitPrice_set(swigCPtr, this, value);
  }

  public double getUpperLimitPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_UpperLimitPrice_get(swigCPtr, this);
  }

  public void setLowerLimitPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_LowerLimitPrice_set(swigCPtr, this, value);
  }

  public double getLowerLimitPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_LowerLimitPrice_get(swigCPtr, this);
  }

  public void setSettlementPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_SettlementPrice_set(swigCPtr, this, value);
  }

  public double getSettlementPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_SettlementPrice_get(swigCPtr, this);
  }

  public void setCurrDelta(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_CurrDelta_set(swigCPtr, this, value);
  }

  public double getCurrDelta() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_CurrDelta_get(swigCPtr, this);
  }

  public CThostFtdcMarketDataStaticField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcMarketDataStaticField(), true);
  }

  public void CopyTo(CThostFtdcMarketDataStaticField r) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataStaticField_CopyTo(swigCPtr, this, CThostFtdcMarketDataStaticField.getCPtr(r), r);
  }

}