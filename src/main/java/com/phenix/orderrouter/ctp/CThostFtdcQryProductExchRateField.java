/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryProductExchRateField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryProductExchRateField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryProductExchRateField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryProductExchRateField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setProductID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryProductExchRateField_ProductID_set(swigCPtr, this, value);
  }

  public String getProductID() {
    return CTP_wrap4javaJNI.CThostFtdcQryProductExchRateField_ProductID_get(swigCPtr, this);
  }

  public CThostFtdcQryProductExchRateField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryProductExchRateField(), true);
  }

}
