/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcExchangeSequenceField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcExchangeSequenceField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcExchangeSequenceField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcExchangeSequenceField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeSequenceField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeSequenceField_ExchangeID_get(swigCPtr, this);
  }

  public void setSequenceNo(int value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeSequenceField_SequenceNo_set(swigCPtr, this, value);
  }

  public int getSequenceNo() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeSequenceField_SequenceNo_get(swigCPtr, this);
  }

  public void setMarketStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcExchangeSequenceField_MarketStatus_set(swigCPtr, this, value);
  }

  public char getMarketStatus() {
    return CTP_wrap4javaJNI.CThostFtdcExchangeSequenceField_MarketStatus_get(swigCPtr, this);
  }

  public CThostFtdcExchangeSequenceField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcExchangeSequenceField(), true);
  }

  public void CopyTo(CThostFtdcExchangeSequenceField r) {
    CTP_wrap4javaJNI.CThostFtdcExchangeSequenceField_CopyTo(swigCPtr, this, CThostFtdcExchangeSequenceField.getCPtr(r), r);
  }

}
