/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcTradingAccountPasswordUpdateField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcTradingAccountPasswordUpdateField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcTradingAccountPasswordUpdateField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcTradingAccountPasswordUpdateField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTradingAccountPasswordUpdateField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcTradingAccountPasswordUpdateField_BrokerID_get(swigCPtr, this);
  }

  public void setAccountID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTradingAccountPasswordUpdateField_AccountID_set(swigCPtr, this, value);
  }

  public String getAccountID() {
    return CTP_wrap4javaJNI.CThostFtdcTradingAccountPasswordUpdateField_AccountID_get(swigCPtr, this);
  }

  public void setOldPassword(String value) {
    CTP_wrap4javaJNI.CThostFtdcTradingAccountPasswordUpdateField_OldPassword_set(swigCPtr, this, value);
  }

  public String getOldPassword() {
    return CTP_wrap4javaJNI.CThostFtdcTradingAccountPasswordUpdateField_OldPassword_get(swigCPtr, this);
  }

  public void setNewPassword(String value) {
    CTP_wrap4javaJNI.CThostFtdcTradingAccountPasswordUpdateField_NewPassword_set(swigCPtr, this, value);
  }

  public String getNewPassword() {
    return CTP_wrap4javaJNI.CThostFtdcTradingAccountPasswordUpdateField_NewPassword_get(swigCPtr, this);
  }

  public void setCurrencyID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTradingAccountPasswordUpdateField_CurrencyID_set(swigCPtr, this, value);
  }

  public String getCurrencyID() {
    return CTP_wrap4javaJNI.CThostFtdcTradingAccountPasswordUpdateField_CurrencyID_get(swigCPtr, this);
  }

  public CThostFtdcTradingAccountPasswordUpdateField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcTradingAccountPasswordUpdateField(), true);
  }

  public void CopyTo(CThostFtdcTradingAccountPasswordUpdateField r) {
    CTP_wrap4javaJNI.CThostFtdcTradingAccountPasswordUpdateField_CopyTo(swigCPtr, this, CThostFtdcTradingAccountPasswordUpdateField.getCPtr(r), r);
  }

}
