/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcRspQueryAccountField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcRspQueryAccountField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcRspQueryAccountField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcRspQueryAccountField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setTradeCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_TradeCode_set(swigCPtr, this, value);
  }

  public String getTradeCode() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_TradeCode_get(swigCPtr, this);
  }

  public void setBankID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankID_set(swigCPtr, this, value);
  }

  public String getBankID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankID_get(swigCPtr, this);
  }

  public void setBankBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankBranchID_set(swigCPtr, this, value);
  }

  public String getBankBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankBranchID_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BrokerID_get(swigCPtr, this);
  }

  public void setBrokerBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BrokerBranchID_set(swigCPtr, this, value);
  }

  public String getBrokerBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BrokerBranchID_get(swigCPtr, this);
  }

  public void setTradeDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_TradeDate_set(swigCPtr, this, value);
  }

  public String getTradeDate() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_TradeDate_get(swigCPtr, this);
  }

  public void setTradeTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_TradeTime_set(swigCPtr, this, value);
  }

  public String getTradeTime() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_TradeTime_get(swigCPtr, this);
  }

  public void setBankSerial(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankSerial_set(swigCPtr, this, value);
  }

  public String getBankSerial() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankSerial_get(swigCPtr, this);
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_TradingDay_get(swigCPtr, this);
  }

  public void setPlateSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_PlateSerial_set(swigCPtr, this, value);
  }

  public int getPlateSerial() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_PlateSerial_get(swigCPtr, this);
  }

  public void setLastFragment(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_LastFragment_set(swigCPtr, this, value);
  }

  public char getLastFragment() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_LastFragment_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_SessionID_get(swigCPtr, this);
  }

  public void setCustomerName(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_CustomerName_set(swigCPtr, this, value);
  }

  public String getCustomerName() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_CustomerName_get(swigCPtr, this);
  }

  public void setIdCardType(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_IdCardType_set(swigCPtr, this, value);
  }

  public char getIdCardType() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_IdCardType_get(swigCPtr, this);
  }

  public void setIdentifiedCardNo(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_IdentifiedCardNo_set(swigCPtr, this, value);
  }

  public String getIdentifiedCardNo() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_IdentifiedCardNo_get(swigCPtr, this);
  }

  public void setCustType(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_CustType_set(swigCPtr, this, value);
  }

  public char getCustType() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_CustType_get(swigCPtr, this);
  }

  public void setBankAccount(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankAccount_set(swigCPtr, this, value);
  }

  public String getBankAccount() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankAccount_get(swigCPtr, this);
  }

  public void setBankPassWord(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankPassWord_set(swigCPtr, this, value);
  }

  public String getBankPassWord() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankPassWord_get(swigCPtr, this);
  }

  public void setAccountID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_AccountID_set(swigCPtr, this, value);
  }

  public String getAccountID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_AccountID_get(swigCPtr, this);
  }

  public void setPassword(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_Password_set(swigCPtr, this, value);
  }

  public String getPassword() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_Password_get(swigCPtr, this);
  }

  public void setFutureSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_FutureSerial_set(swigCPtr, this, value);
  }

  public int getFutureSerial() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_FutureSerial_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_InstallID_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_UserID_get(swigCPtr, this);
  }

  public void setVerifyCertNoFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_VerifyCertNoFlag_set(swigCPtr, this, value);
  }

  public char getVerifyCertNoFlag() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_VerifyCertNoFlag_get(swigCPtr, this);
  }

  public void setCurrencyID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_CurrencyID_set(swigCPtr, this, value);
  }

  public String getCurrencyID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_CurrencyID_get(swigCPtr, this);
  }

  public void setDigest(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_Digest_set(swigCPtr, this, value);
  }

  public String getDigest() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_Digest_get(swigCPtr, this);
  }

  public void setBankAccType(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankAccType_set(swigCPtr, this, value);
  }

  public char getBankAccType() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankAccType_get(swigCPtr, this);
  }

  public void setDeviceID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_DeviceID_set(swigCPtr, this, value);
  }

  public String getDeviceID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_DeviceID_get(swigCPtr, this);
  }

  public void setBankSecuAccType(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankSecuAccType_set(swigCPtr, this, value);
  }

  public char getBankSecuAccType() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankSecuAccType_get(swigCPtr, this);
  }

  public void setBrokerIDByBank(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BrokerIDByBank_set(swigCPtr, this, value);
  }

  public String getBrokerIDByBank() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BrokerIDByBank_get(swigCPtr, this);
  }

  public void setBankSecuAcc(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankSecuAcc_set(swigCPtr, this, value);
  }

  public String getBankSecuAcc() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankSecuAcc_get(swigCPtr, this);
  }

  public void setBankPwdFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankPwdFlag_set(swigCPtr, this, value);
  }

  public char getBankPwdFlag() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankPwdFlag_get(swigCPtr, this);
  }

  public void setSecuPwdFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_SecuPwdFlag_set(swigCPtr, this, value);
  }

  public char getSecuPwdFlag() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_SecuPwdFlag_get(swigCPtr, this);
  }

  public void setOperNo(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_OperNo_set(swigCPtr, this, value);
  }

  public String getOperNo() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_OperNo_get(swigCPtr, this);
  }

  public void setRequestID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_RequestID_set(swigCPtr, this, value);
  }

  public int getRequestID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_RequestID_get(swigCPtr, this);
  }

  public void setTID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_TID_set(swigCPtr, this, value);
  }

  public int getTID() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_TID_get(swigCPtr, this);
  }

  public void setBankUseAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankUseAmount_set(swigCPtr, this, value);
  }

  public double getBankUseAmount() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankUseAmount_get(swigCPtr, this);
  }

  public void setBankFetchAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankFetchAmount_set(swigCPtr, this, value);
  }

  public double getBankFetchAmount() {
    return CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_BankFetchAmount_get(swigCPtr, this);
  }

  public CThostFtdcRspQueryAccountField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcRspQueryAccountField(), true);
  }

  public void CopyTo(CThostFtdcRspQueryAccountField r) {
    CTP_wrap4javaJNI.CThostFtdcRspQueryAccountField_CopyTo(swigCPtr, this, CThostFtdcRspQueryAccountField.getCPtr(r), r);
  }

}