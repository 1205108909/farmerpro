/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcCommPhaseField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcCommPhaseField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcCommPhaseField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcCommPhaseField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcCommPhaseField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcCommPhaseField_TradingDay_get(swigCPtr, this);
  }

  public void setCommPhaseNo(short value) {
    CTP_wrap4javaJNI.CThostFtdcCommPhaseField_CommPhaseNo_set(swigCPtr, this, value);
  }

  public short getCommPhaseNo() {
    return CTP_wrap4javaJNI.CThostFtdcCommPhaseField_CommPhaseNo_get(swigCPtr, this);
  }

  public void setSystemID(String value) {
    CTP_wrap4javaJNI.CThostFtdcCommPhaseField_SystemID_set(swigCPtr, this, value);
  }

  public String getSystemID() {
    return CTP_wrap4javaJNI.CThostFtdcCommPhaseField_SystemID_get(swigCPtr, this);
  }

  public CThostFtdcCommPhaseField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcCommPhaseField(), true);
  }

  public void CopyTo(CThostFtdcCommPhaseField r) {
    CTP_wrap4javaJNI.CThostFtdcCommPhaseField_CopyTo(swigCPtr, this, CThostFtdcCommPhaseField.getCPtr(r), r);
  }

}
