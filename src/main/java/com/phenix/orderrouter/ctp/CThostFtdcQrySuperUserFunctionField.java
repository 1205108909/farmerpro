/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQrySuperUserFunctionField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQrySuperUserFunctionField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQrySuperUserFunctionField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQrySuperUserFunctionField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQrySuperUserFunctionField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcQrySuperUserFunctionField_UserID_get(swigCPtr, this);
  }

  public CThostFtdcQrySuperUserFunctionField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQrySuperUserFunctionField(), true);
  }

  public void CopyTo(CThostFtdcQrySuperUserFunctionField r) {
    CTP_wrap4javaJNI.CThostFtdcQrySuperUserFunctionField_CopyTo(swigCPtr, this, CThostFtdcQrySuperUserFunctionField.getCPtr(r), r);
  }

}
