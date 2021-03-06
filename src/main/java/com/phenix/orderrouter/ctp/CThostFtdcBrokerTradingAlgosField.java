/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcBrokerTradingAlgosField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcBrokerTradingAlgosField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcBrokerTradingAlgosField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcBrokerTradingAlgosField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_BrokerID_get(swigCPtr, this);
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_ExchangeID_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_InstrumentID_get(swigCPtr, this);
  }

  public void setHandlePositionAlgoID(char value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_HandlePositionAlgoID_set(swigCPtr, this, value);
  }

  public char getHandlePositionAlgoID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_HandlePositionAlgoID_get(swigCPtr, this);
  }

  public void setFindMarginRateAlgoID(char value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_FindMarginRateAlgoID_set(swigCPtr, this, value);
  }

  public char getFindMarginRateAlgoID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_FindMarginRateAlgoID_get(swigCPtr, this);
  }

  public void setHandleTradingAccountAlgoID(char value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_HandleTradingAccountAlgoID_set(swigCPtr, this, value);
  }

  public char getHandleTradingAccountAlgoID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_HandleTradingAccountAlgoID_get(swigCPtr, this);
  }

  public CThostFtdcBrokerTradingAlgosField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcBrokerTradingAlgosField(), true);
  }

  public void CopyTo(CThostFtdcBrokerTradingAlgosField r) {
    CTP_wrap4javaJNI.CThostFtdcBrokerTradingAlgosField_CopyTo(swigCPtr, this, CThostFtdcBrokerTradingAlgosField.getCPtr(r), r);
  }

}
