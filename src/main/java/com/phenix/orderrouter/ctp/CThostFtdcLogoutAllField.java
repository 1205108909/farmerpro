/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcLogoutAllField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcLogoutAllField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcLogoutAllField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcLogoutAllField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setFrontID(int value) {
    CTP_wrap4javaJNI.CThostFtdcLogoutAllField_FrontID_set(swigCPtr, this, value);
  }

  public int getFrontID() {
    return CTP_wrap4javaJNI.CThostFtdcLogoutAllField_FrontID_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcLogoutAllField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcLogoutAllField_SessionID_get(swigCPtr, this);
  }

  public void setSystemName(String value) {
    CTP_wrap4javaJNI.CThostFtdcLogoutAllField_SystemName_set(swigCPtr, this, value);
  }

  public String getSystemName() {
    return CTP_wrap4javaJNI.CThostFtdcLogoutAllField_SystemName_get(swigCPtr, this);
  }

  public CThostFtdcLogoutAllField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcLogoutAllField(), true);
  }

  public void CopyTo(CThostFtdcLogoutAllField r) {
    CTP_wrap4javaJNI.CThostFtdcLogoutAllField_CopyTo(swigCPtr, this, CThostFtdcLogoutAllField.getCPtr(r), r);
  }

}
