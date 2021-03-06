/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcMarketDataField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcMarketDataField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcMarketDataField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcMarketDataField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_TradingDay_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_InstrumentID_get(swigCPtr, this);
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_ExchangeID_get(swigCPtr, this);
  }

  public void setExchangeInstID(String value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_ExchangeInstID_set(swigCPtr, this, value);
  }

  public String getExchangeInstID() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_ExchangeInstID_get(swigCPtr, this);
  }

  public void setLastPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_LastPrice_set(swigCPtr, this, value);
  }

  public double getLastPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_LastPrice_get(swigCPtr, this);
  }

  public void setPreSettlementPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_PreSettlementPrice_set(swigCPtr, this, value);
  }

  public double getPreSettlementPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_PreSettlementPrice_get(swigCPtr, this);
  }

  public void setPreClosePrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_PreClosePrice_set(swigCPtr, this, value);
  }

  public double getPreClosePrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_PreClosePrice_get(swigCPtr, this);
  }

  public void setPreOpenInterest(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_PreOpenInterest_set(swigCPtr, this, value);
  }

  public double getPreOpenInterest() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_PreOpenInterest_get(swigCPtr, this);
  }

  public void setOpenPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_OpenPrice_set(swigCPtr, this, value);
  }

  public double getOpenPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_OpenPrice_get(swigCPtr, this);
  }

  public void setHighestPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_HighestPrice_set(swigCPtr, this, value);
  }

  public double getHighestPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_HighestPrice_get(swigCPtr, this);
  }

  public void setLowestPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_LowestPrice_set(swigCPtr, this, value);
  }

  public double getLowestPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_LowestPrice_get(swigCPtr, this);
  }

  public void setVolume(int value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_Volume_set(swigCPtr, this, value);
  }

  public int getVolume() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_Volume_get(swigCPtr, this);
  }

  public void setTurnover(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_Turnover_set(swigCPtr, this, value);
  }

  public double getTurnover() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_Turnover_get(swigCPtr, this);
  }

  public void setOpenInterest(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_OpenInterest_set(swigCPtr, this, value);
  }

  public double getOpenInterest() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_OpenInterest_get(swigCPtr, this);
  }

  public void setClosePrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_ClosePrice_set(swigCPtr, this, value);
  }

  public double getClosePrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_ClosePrice_get(swigCPtr, this);
  }

  public void setSettlementPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_SettlementPrice_set(swigCPtr, this, value);
  }

  public double getSettlementPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_SettlementPrice_get(swigCPtr, this);
  }

  public void setUpperLimitPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_UpperLimitPrice_set(swigCPtr, this, value);
  }

  public double getUpperLimitPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_UpperLimitPrice_get(swigCPtr, this);
  }

  public void setLowerLimitPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_LowerLimitPrice_set(swigCPtr, this, value);
  }

  public double getLowerLimitPrice() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_LowerLimitPrice_get(swigCPtr, this);
  }

  public void setPreDelta(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_PreDelta_set(swigCPtr, this, value);
  }

  public double getPreDelta() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_PreDelta_get(swigCPtr, this);
  }

  public void setCurrDelta(double value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_CurrDelta_set(swigCPtr, this, value);
  }

  public double getCurrDelta() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_CurrDelta_get(swigCPtr, this);
  }

  public void setUpdateTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_UpdateTime_set(swigCPtr, this, value);
  }

  public String getUpdateTime() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_UpdateTime_get(swigCPtr, this);
  }

  public void setUpdateMillisec(int value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_UpdateMillisec_set(swigCPtr, this, value);
  }

  public int getUpdateMillisec() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_UpdateMillisec_get(swigCPtr, this);
  }

  public void setActionDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_ActionDay_set(swigCPtr, this, value);
  }

  public String getActionDay() {
    return CTP_wrap4javaJNI.CThostFtdcMarketDataField_ActionDay_get(swigCPtr, this);
  }

  public CThostFtdcMarketDataField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcMarketDataField(), true);
  }

  public void CopyTo(CThostFtdcMarketDataField r) {
    CTP_wrap4javaJNI.CThostFtdcMarketDataField_CopyTo(swigCPtr, this, CThostFtdcMarketDataField.getCPtr(r), r);
  }

}
