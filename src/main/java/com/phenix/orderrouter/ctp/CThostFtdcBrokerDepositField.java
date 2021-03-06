/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcBrokerDepositField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcBrokerDepositField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcBrokerDepositField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcBrokerDepositField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_TradingDay_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_BrokerID_get(swigCPtr, this);
  }

  public void setParticipantID(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_ParticipantID_set(swigCPtr, this, value);
  }

  public String getParticipantID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_ParticipantID_get(swigCPtr, this);
  }

  public void setExchangeID(String value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_ExchangeID_set(swigCPtr, this, value);
  }

  public String getExchangeID() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_ExchangeID_get(swigCPtr, this);
  }

  public void setPreBalance(double value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_PreBalance_set(swigCPtr, this, value);
  }

  public double getPreBalance() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_PreBalance_get(swigCPtr, this);
  }

  public void setCurrMargin(double value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_CurrMargin_set(swigCPtr, this, value);
  }

  public double getCurrMargin() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_CurrMargin_get(swigCPtr, this);
  }

  public void setCloseProfit(double value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_CloseProfit_set(swigCPtr, this, value);
  }

  public double getCloseProfit() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_CloseProfit_get(swigCPtr, this);
  }

  public void setBalance(double value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_Balance_set(swigCPtr, this, value);
  }

  public double getBalance() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_Balance_get(swigCPtr, this);
  }

  public void setDeposit(double value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_Deposit_set(swigCPtr, this, value);
  }

  public double getDeposit() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_Deposit_get(swigCPtr, this);
  }

  public void setWithdraw(double value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_Withdraw_set(swigCPtr, this, value);
  }

  public double getWithdraw() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_Withdraw_get(swigCPtr, this);
  }

  public void setAvailable(double value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_Available_set(swigCPtr, this, value);
  }

  public double getAvailable() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_Available_get(swigCPtr, this);
  }

  public void setReserve(double value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_Reserve_set(swigCPtr, this, value);
  }

  public double getReserve() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_Reserve_get(swigCPtr, this);
  }

  public void setFrozenMargin(double value) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_FrozenMargin_set(swigCPtr, this, value);
  }

  public double getFrozenMargin() {
    return CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_FrozenMargin_get(swigCPtr, this);
  }

  public CThostFtdcBrokerDepositField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcBrokerDepositField(), true);
  }

  public void CopyTo(CThostFtdcBrokerDepositField r) {
    CTP_wrap4javaJNI.CThostFtdcBrokerDepositField_CopyTo(swigCPtr, this, CThostFtdcBrokerDepositField.getCPtr(r), r);
  }

}
