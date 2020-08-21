/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcExchangeMarginRateField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcExchangeMarginRateField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcExchangeMarginRateField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcExchangeMarginRateField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_BrokerID_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_InstrumentID_get(swigCPtr, this);
  }

  public void setHedgeFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_HedgeFlag_set(swigCPtr, this, value);
  }

  public char getHedgeFlag() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_HedgeFlag_get(swigCPtr, this);
  }

  public void setLongMarginRatioByMoney(double value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_LongMarginRatioByMoney_set(swigCPtr, this, value);
  }

  public double getLongMarginRatioByMoney() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_LongMarginRatioByMoney_get(swigCPtr, this);
  }

  public void setLongMarginRatioByVolume(double value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_LongMarginRatioByVolume_set(swigCPtr, this, value);
  }

  public double getLongMarginRatioByVolume() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_LongMarginRatioByVolume_get(swigCPtr, this);
  }

  public void setShortMarginRatioByMoney(double value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_ShortMarginRatioByMoney_set(swigCPtr, this, value);
  }

  public double getShortMarginRatioByMoney() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_ShortMarginRatioByMoney_get(swigCPtr, this);
  }

  public void setShortMarginRatioByVolume(double value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_ShortMarginRatioByVolume_set(swigCPtr, this, value);
  }

  public double getShortMarginRatioByVolume() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_ShortMarginRatioByVolume_get(swigCPtr, this);
  }

  public CThostFtdcExchangeMarginRateField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcExchangeMarginRateField(), true);
  }

  public void CopyTo(CThostFtdcExchangeMarginRateField r) {
    CTP_wrap4javaJNI.CThostFtdcExchangeMarginRateField_CopyTo(swigCPtr, this, CThostFtdcExchangeMarginRateField.getCPtr(r), r);
  }

}
