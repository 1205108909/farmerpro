/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcBrokerUserFunctionField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcBrokerUserFunctionField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcBrokerUserFunctionField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcBrokerUserFunctionField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerUserFunctionField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerUserFunctionField_BrokerID_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerUserFunctionField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerUserFunctionField_UserID_get(swigCPtr, this);
  }

  public void setBrokerFunctionCode(char value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerUserFunctionField_BrokerFunctionCode_set(swigCPtr, this, value);
  }

  public char getBrokerFunctionCode() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerUserFunctionField_BrokerFunctionCode_get(swigCPtr, this);
  }

  public CThostFtdcBrokerUserFunctionField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcBrokerUserFunctionField(), true);
  }

  public void CopyTo(CThostFtdcBrokerUserFunctionField r) {
    CTP_wrap4javaJNI.CThostFtdcBrokerUserFunctionField_CopyTo(swigCPtr, this, CThostFtdcBrokerUserFunctionField.getCPtr(r), r);
  }

}