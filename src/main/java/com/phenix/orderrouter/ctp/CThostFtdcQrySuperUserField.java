/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQrySuperUserField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQrySuperUserField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQrySuperUserField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQrySuperUserField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQrySuperUserField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcQrySuperUserField_UserID_get(swigCPtr, this);
  }

  public CThostFtdcQrySuperUserField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQrySuperUserField(), true);
  }

  public void CopyTo(CThostFtdcQrySuperUserField r) {
    CTP_wrap4javaJNI.CThostFtdcQrySuperUserField_CopyTo(swigCPtr, this, CThostFtdcQrySuperUserField.getCPtr(r), r);
  }

}
