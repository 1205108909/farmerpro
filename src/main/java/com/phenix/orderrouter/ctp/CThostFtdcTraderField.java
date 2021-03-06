/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcTraderField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcTraderField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcTraderField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcTraderField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcTraderField_ExchangeID_get(swigCPtr, this);
  }

  public void setTraderID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderField_TraderID_set(swigCPtr, this, value);
  }

  public String getTraderID() {
    return CTP_wrap4javaJNI.CThostFtdcTraderField_TraderID_get(swigCPtr, this);
  }

  public void setParticipantID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderField_ParticipantID_set(swigCPtr, this, value);
  }

  public String getParticipantID() {
    return CTP_wrap4javaJNI.CThostFtdcTraderField_ParticipantID_get(swigCPtr, this);
  }

  public void setPassword(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderField_Password_set(swigCPtr, this, value);
  }

  public String getPassword() {
    return CTP_wrap4javaJNI.CThostFtdcTraderField_Password_get(swigCPtr, this);
  }

  public void setInstallCount(int value) {
    CTP_wrap4javaJNI.CThostFtdcTraderField_InstallCount_set(swigCPtr, this, value);
  }

  public int getInstallCount() {
    return CTP_wrap4javaJNI.CThostFtdcTraderField_InstallCount_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTraderField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcTraderField_BrokerID_get(swigCPtr, this);
  }

  public CThostFtdcTraderField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcTraderField(), true);
  }

  public void CopyTo(CThostFtdcTraderField r) {
    CTP_wrap4javaJNI.CThostFtdcTraderField_CopyTo(swigCPtr, this, CThostFtdcTraderField.getCPtr(r), r);
  }

}
