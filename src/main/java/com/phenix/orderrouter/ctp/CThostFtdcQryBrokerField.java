/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryBrokerField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryBrokerField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryBrokerField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryBrokerField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryBrokerField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcQryBrokerField_BrokerID_get(swigCPtr, this);
  }

  public CThostFtdcQryBrokerField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryBrokerField(), true);
  }

  public void CopyTo(CThostFtdcQryBrokerField r) {
    CTP_wrap4javaJNI.CThostFtdcQryBrokerField_CopyTo(swigCPtr, this, CThostFtdcQryBrokerField.getCPtr(r), r);
  }

}