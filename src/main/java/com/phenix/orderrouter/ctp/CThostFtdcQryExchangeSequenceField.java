/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryExchangeSequenceField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryExchangeSequenceField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryExchangeSequenceField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryExchangeSequenceField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryExchangeSequenceField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcQryExchangeSequenceField_ExchangeID_get(swigCPtr, this);
  }

  public CThostFtdcQryExchangeSequenceField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryExchangeSequenceField(), true);
  }

  public void CopyTo(CThostFtdcQryExchangeSequenceField r) {
    CTP_wrap4javaJNI.CThostFtdcQryExchangeSequenceField_CopyTo(swigCPtr, this, CThostFtdcQryExchangeSequenceField.getCPtr(r), r);
  }

}