/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcTransferSerialField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcTransferSerialField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcTransferSerialField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcTransferSerialField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setPlateSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_PlateSerial_set(swigCPtr, this, value);
  }

  public int getPlateSerial() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_PlateSerial_get(swigCPtr, this);
  }

  public void setTradeDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_TradeDate_set(swigCPtr, this, value);
  }

  public String getTradeDate() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_TradeDate_get(swigCPtr, this);
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_TradingDay_get(swigCPtr, this);
  }

  public void setTradeTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_TradeTime_set(swigCPtr, this, value);
  }

  public String getTradeTime() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_TradeTime_get(swigCPtr, this);
  }

  public void setTradeCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_TradeCode_set(swigCPtr, this, value);
  }

  public String getTradeCode() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_TradeCode_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_SessionID_get(swigCPtr, this);
  }

  public void setBankID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankID_set(swigCPtr, this, value);
  }

  public String getBankID() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankID_get(swigCPtr, this);
  }

  public void setBankBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankBranchID_set(swigCPtr, this, value);
  }

  public String getBankBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankBranchID_get(swigCPtr, this);
  }

  public void setBankAccType(char value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankAccType_set(swigCPtr, this, value);
  }

  public char getBankAccType() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankAccType_get(swigCPtr, this);
  }

  public void setBankAccount(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankAccount_set(swigCPtr, this, value);
  }

  public String getBankAccount() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankAccount_get(swigCPtr, this);
  }

  public void setBankSerial(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankSerial_set(swigCPtr, this, value);
  }

  public String getBankSerial() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankSerial_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BrokerID_get(swigCPtr, this);
  }

  public void setBrokerBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BrokerBranchID_set(swigCPtr, this, value);
  }

  public String getBrokerBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BrokerBranchID_get(swigCPtr, this);
  }

  public void setFutureAccType(char value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_FutureAccType_set(swigCPtr, this, value);
  }

  public char getFutureAccType() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_FutureAccType_get(swigCPtr, this);
  }

  public void setAccountID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_AccountID_set(swigCPtr, this, value);
  }

  public String getAccountID() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_AccountID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_InvestorID_get(swigCPtr, this);
  }

  public void setFutureSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_FutureSerial_set(swigCPtr, this, value);
  }

  public int getFutureSerial() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_FutureSerial_get(swigCPtr, this);
  }

  public void setIdCardType(char value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_IdCardType_set(swigCPtr, this, value);
  }

  public char getIdCardType() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_IdCardType_get(swigCPtr, this);
  }

  public void setIdentifiedCardNo(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_IdentifiedCardNo_set(swigCPtr, this, value);
  }

  public String getIdentifiedCardNo() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_IdentifiedCardNo_get(swigCPtr, this);
  }

  public void setCurrencyID(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_CurrencyID_set(swigCPtr, this, value);
  }

  public String getCurrencyID() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_CurrencyID_get(swigCPtr, this);
  }

  public void setTradeAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_TradeAmount_set(swigCPtr, this, value);
  }

  public double getTradeAmount() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_TradeAmount_get(swigCPtr, this);
  }

  public void setCustFee(double value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_CustFee_set(swigCPtr, this, value);
  }

  public double getCustFee() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_CustFee_get(swigCPtr, this);
  }

  public void setBrokerFee(double value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BrokerFee_set(swigCPtr, this, value);
  }

  public double getBrokerFee() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BrokerFee_get(swigCPtr, this);
  }

  public void setAvailabilityFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_AvailabilityFlag_set(swigCPtr, this, value);
  }

  public char getAvailabilityFlag() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_AvailabilityFlag_get(swigCPtr, this);
  }

  public void setOperatorCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_OperatorCode_set(swigCPtr, this, value);
  }

  public String getOperatorCode() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_OperatorCode_get(swigCPtr, this);
  }

  public void setBankNewAccount(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankNewAccount_set(swigCPtr, this, value);
  }

  public String getBankNewAccount() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_BankNewAccount_get(swigCPtr, this);
  }

  public void setErrorID(int value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_ErrorID_set(swigCPtr, this, value);
  }

  public int getErrorID() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_ErrorID_get(swigCPtr, this);
  }

  public void setErrorMsg(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_ErrorMsg_set(swigCPtr, this, value);
  }

  public String getErrorMsg() {
    return CTP_wrap4javaJNI.CThostFtdcTransferSerialField_ErrorMsg_get(swigCPtr, this);
  }

  public CThostFtdcTransferSerialField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcTransferSerialField(), true);
  }

  public void CopyTo(CThostFtdcTransferSerialField r) {
    CTP_wrap4javaJNI.CThostFtdcTransferSerialField_CopyTo(swigCPtr, this, CThostFtdcTransferSerialField.getCPtr(r), r);
  }

}
