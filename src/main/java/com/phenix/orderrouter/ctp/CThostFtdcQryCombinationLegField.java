/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryCombinationLegField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryCombinationLegField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryCombinationLegField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryCombinationLegField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setCombInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryCombinationLegField_CombInstrumentID_set(swigCPtr, this, value);
  }

  public String getCombInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcQryCombinationLegField_CombInstrumentID_get(swigCPtr, this);
  }

  public void setLegID(int value) {
    CTP_wrap4javaJNI.CThostFtdcQryCombinationLegField_LegID_set(swigCPtr, this, value);
  }

  public int getLegID() {
    return CTP_wrap4javaJNI.CThostFtdcQryCombinationLegField_LegID_get(swigCPtr, this);
  }

  public void setLegInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryCombinationLegField_LegInstrumentID_set(swigCPtr, this, value);
  }

  public String getLegInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcQryCombinationLegField_LegInstrumentID_get(swigCPtr, this);
  }

  public CThostFtdcQryCombinationLegField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryCombinationLegField(), true);
  }

  public void CopyTo(CThostFtdcQryCombinationLegField r) {
    CTP_wrap4javaJNI.CThostFtdcQryCombinationLegField_CopyTo(swigCPtr, this, CThostFtdcQryCombinationLegField.getCPtr(r), r);
  }

}