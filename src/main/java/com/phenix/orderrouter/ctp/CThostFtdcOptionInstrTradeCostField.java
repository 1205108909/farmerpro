/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcOptionInstrTradeCostField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcOptionInstrTradeCostField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcOptionInstrTradeCostField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcOptionInstrTradeCostField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_InvestorID_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_InstrumentID_get(swigCPtr, this);
  }

  public void setHedgeFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_HedgeFlag_set(swigCPtr, this, value);
  }

  public char getHedgeFlag() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_HedgeFlag_get(swigCPtr, this);
  }

  public void setFixedMargin(double value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_FixedMargin_set(swigCPtr, this, value);
  }

  public double getFixedMargin() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_FixedMargin_get(swigCPtr, this);
  }

  public void setMiniMargin(double value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_MiniMargin_set(swigCPtr, this, value);
  }

  public double getMiniMargin() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_MiniMargin_get(swigCPtr, this);
  }

  public void setRoyalty(double value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_Royalty_set(swigCPtr, this, value);
  }

  public double getRoyalty() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_Royalty_get(swigCPtr, this);
  }

  public void setExchFixedMargin(double value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_ExchFixedMargin_set(swigCPtr, this, value);
  }

  public double getExchFixedMargin() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_ExchFixedMargin_get(swigCPtr, this);
  }

  public void setExchMiniMargin(double value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_ExchMiniMargin_set(swigCPtr, this, value);
  }

  public double getExchMiniMargin() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrTradeCostField_ExchMiniMargin_get(swigCPtr, this);
  }

  public CThostFtdcOptionInstrTradeCostField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcOptionInstrTradeCostField(), true);
  }

}
