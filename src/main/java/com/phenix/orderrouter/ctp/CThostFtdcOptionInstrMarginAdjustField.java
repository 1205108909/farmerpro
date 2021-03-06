/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcOptionInstrMarginAdjustField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcOptionInstrMarginAdjustField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcOptionInstrMarginAdjustField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcOptionInstrMarginAdjustField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_InstrumentID_get(swigCPtr, this);
  }

  public void setInvestorRange(char value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_InvestorRange_set(swigCPtr, this, value);
  }

  public char getInvestorRange() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_InvestorRange_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_InvestorID_get(swigCPtr, this);
  }

  public void setSShortMarginRatioByMoney(double value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_SShortMarginRatioByMoney_set(swigCPtr, this, value);
  }

  public double getSShortMarginRatioByMoney() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_SShortMarginRatioByMoney_get(swigCPtr, this);
  }

  public void setSShortMarginRatioByVolume(double value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_SShortMarginRatioByVolume_set(swigCPtr, this, value);
  }

  public double getSShortMarginRatioByVolume() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_SShortMarginRatioByVolume_get(swigCPtr, this);
  }

  public void setHShortMarginRatioByMoney(double value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_HShortMarginRatioByMoney_set(swigCPtr, this, value);
  }

  public double getHShortMarginRatioByMoney() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_HShortMarginRatioByMoney_get(swigCPtr, this);
  }

  public void setHShortMarginRatioByVolume(double value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_HShortMarginRatioByVolume_set(swigCPtr, this, value);
  }

  public double getHShortMarginRatioByVolume() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_HShortMarginRatioByVolume_get(swigCPtr, this);
  }

  public void setAShortMarginRatioByMoney(double value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_AShortMarginRatioByMoney_set(swigCPtr, this, value);
  }

  public double getAShortMarginRatioByMoney() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_AShortMarginRatioByMoney_get(swigCPtr, this);
  }

  public void setAShortMarginRatioByVolume(double value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_AShortMarginRatioByVolume_set(swigCPtr, this, value);
  }

  public double getAShortMarginRatioByVolume() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_AShortMarginRatioByVolume_get(swigCPtr, this);
  }

  public void setIsRelative(int value) {
    CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_IsRelative_set(swigCPtr, this, value);
  }

  public int getIsRelative() {
    return CTP_wrap4javaJNI.CThostFtdcOptionInstrMarginAdjustField_IsRelative_get(swigCPtr, this);
  }

  public CThostFtdcOptionInstrMarginAdjustField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcOptionInstrMarginAdjustField(), true);
  }

}
