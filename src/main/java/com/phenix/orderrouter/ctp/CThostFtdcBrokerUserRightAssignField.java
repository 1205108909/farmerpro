/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcBrokerUserRightAssignField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcBrokerUserRightAssignField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcBrokerUserRightAssignField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcBrokerUserRightAssignField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerUserRightAssignField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerUserRightAssignField_BrokerID_get(swigCPtr, this);
  }

  public void setDRIdentityID(int value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerUserRightAssignField_DRIdentityID_set(swigCPtr, this, value);
  }

  public int getDRIdentityID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerUserRightAssignField_DRIdentityID_get(swigCPtr, this);
  }

  public void setTradeable(int value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerUserRightAssignField_Tradeable_set(swigCPtr, this, value);
  }

  public int getTradeable() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerUserRightAssignField_Tradeable_get(swigCPtr, this);
  }

  public CThostFtdcBrokerUserRightAssignField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcBrokerUserRightAssignField(), true);
  }

  public void CopyTo(CThostFtdcBrokerUserRightAssignField r) {
    CTP_wrap4javaJNI.CThostFtdcBrokerUserRightAssignField_CopyTo(swigCPtr, this, CThostFtdcBrokerUserRightAssignField.getCPtr(r), r);
  }

}
