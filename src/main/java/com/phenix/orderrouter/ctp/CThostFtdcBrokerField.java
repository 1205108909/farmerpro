/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcBrokerField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcBrokerField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcBrokerField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcBrokerField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerField_BrokerID_get(swigCPtr, this);
  }

  public void setBrokerAbbr(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerField_BrokerAbbr_set(swigCPtr, this, value);
  }

  public String getBrokerAbbr() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerField_BrokerAbbr_get(swigCPtr, this);
  }

  public void setBrokerName(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerField_BrokerName_set(swigCPtr, this, value);
  }

  public String getBrokerName() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerField_BrokerName_get(swigCPtr, this);
  }

  public void setIsActive(int value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerField_IsActive_set(swigCPtr, this, value);
  }

  public int getIsActive() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerField_IsActive_get(swigCPtr, this);
  }

  public CThostFtdcBrokerField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcBrokerField(), true);
  }

  public void CopyTo(CThostFtdcBrokerField r) {
    CTP_wrap4javaJNI.CThostFtdcBrokerField_CopyTo(swigCPtr, this, CThostFtdcBrokerField.getCPtr(r), r);
  }

}
