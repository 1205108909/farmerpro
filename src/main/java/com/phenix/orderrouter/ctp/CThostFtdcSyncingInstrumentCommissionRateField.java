/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcSyncingInstrumentCommissionRateField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcSyncingInstrumentCommissionRateField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcSyncingInstrumentCommissionRateField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcSyncingInstrumentCommissionRateField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_InstrumentID_get(swigCPtr, this);
  }

  public void setInvestorRange(char value) {
    CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_InvestorRange_set(swigCPtr, this, value);
  }

  public char getInvestorRange() {
    return CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_InvestorRange_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_InvestorID_get(swigCPtr, this);
  }

  public void setOpenRatioByMoney(double value) {
    CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_OpenRatioByMoney_set(swigCPtr, this, value);
  }

  public double getOpenRatioByMoney() {
    return CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_OpenRatioByMoney_get(swigCPtr, this);
  }

  public void setOpenRatioByVolume(double value) {
    CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_OpenRatioByVolume_set(swigCPtr, this, value);
  }

  public double getOpenRatioByVolume() {
    return CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_OpenRatioByVolume_get(swigCPtr, this);
  }

  public void setCloseRatioByMoney(double value) {
    CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_CloseRatioByMoney_set(swigCPtr, this, value);
  }

  public double getCloseRatioByMoney() {
    return CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_CloseRatioByMoney_get(swigCPtr, this);
  }

  public void setCloseRatioByVolume(double value) {
    CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_CloseRatioByVolume_set(swigCPtr, this, value);
  }

  public double getCloseRatioByVolume() {
    return CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_CloseRatioByVolume_get(swigCPtr, this);
  }

  public void setCloseTodayRatioByMoney(double value) {
    CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_CloseTodayRatioByMoney_set(swigCPtr, this, value);
  }

  public double getCloseTodayRatioByMoney() {
    return CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_CloseTodayRatioByMoney_get(swigCPtr, this);
  }

  public void setCloseTodayRatioByVolume(double value) {
    CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_CloseTodayRatioByVolume_set(swigCPtr, this, value);
  }

  public double getCloseTodayRatioByVolume() {
    return CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_CloseTodayRatioByVolume_get(swigCPtr, this);
  }

  public CThostFtdcSyncingInstrumentCommissionRateField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcSyncingInstrumentCommissionRateField(), true);
  }

  public void CopyTo(CThostFtdcSyncingInstrumentCommissionRateField r) {
    CTP_wrap4javaJNI.CThostFtdcSyncingInstrumentCommissionRateField_CopyTo(swigCPtr, this, CThostFtdcSyncingInstrumentCommissionRateField.getCPtr(r), r);
  }

}
