/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcOpenAccountField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcOpenAccountField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcOpenAccountField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcOpenAccountField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setTradeCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_TradeCode_set(swigCPtr, this, value);
  }

  public String getTradeCode() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_TradeCode_get(swigCPtr, this);
  }

  public void setBankID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankID_set(swigCPtr, this, value);
  }

  public String getBankID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankID_get(swigCPtr, this);
  }

  public void setBankBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankBranchID_set(swigCPtr, this, value);
  }

  public String getBankBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankBranchID_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BrokerID_get(swigCPtr, this);
  }

  public void setBrokerBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BrokerBranchID_set(swigCPtr, this, value);
  }

  public String getBrokerBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BrokerBranchID_get(swigCPtr, this);
  }

  public void setTradeDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_TradeDate_set(swigCPtr, this, value);
  }

  public String getTradeDate() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_TradeDate_get(swigCPtr, this);
  }

  public void setTradeTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_TradeTime_set(swigCPtr, this, value);
  }

  public String getTradeTime() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_TradeTime_get(swigCPtr, this);
  }

  public void setBankSerial(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankSerial_set(swigCPtr, this, value);
  }

  public String getBankSerial() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankSerial_get(swigCPtr, this);
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_TradingDay_get(swigCPtr, this);
  }

  public void setPlateSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_PlateSerial_set(swigCPtr, this, value);
  }

  public int getPlateSerial() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_PlateSerial_get(swigCPtr, this);
  }

  public void setLastFragment(char value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_LastFragment_set(swigCPtr, this, value);
  }

  public char getLastFragment() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_LastFragment_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_SessionID_get(swigCPtr, this);
  }

  public void setCustomerName(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_CustomerName_set(swigCPtr, this, value);
  }

  public String getCustomerName() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_CustomerName_get(swigCPtr, this);
  }

  public void setIdCardType(char value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_IdCardType_set(swigCPtr, this, value);
  }

  public char getIdCardType() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_IdCardType_get(swigCPtr, this);
  }

  public void setIdentifiedCardNo(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_IdentifiedCardNo_set(swigCPtr, this, value);
  }

  public String getIdentifiedCardNo() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_IdentifiedCardNo_get(swigCPtr, this);
  }

  public void setGender(char value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Gender_set(swigCPtr, this, value);
  }

  public char getGender() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Gender_get(swigCPtr, this);
  }

  public void setCountryCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_CountryCode_set(swigCPtr, this, value);
  }

  public String getCountryCode() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_CountryCode_get(swigCPtr, this);
  }

  public void setCustType(char value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_CustType_set(swigCPtr, this, value);
  }

  public char getCustType() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_CustType_get(swigCPtr, this);
  }

  public void setAddress(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Address_set(swigCPtr, this, value);
  }

  public String getAddress() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Address_get(swigCPtr, this);
  }

  public void setZipCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_ZipCode_set(swigCPtr, this, value);
  }

  public String getZipCode() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_ZipCode_get(swigCPtr, this);
  }

  public void setTelephone(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Telephone_set(swigCPtr, this, value);
  }

  public String getTelephone() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Telephone_get(swigCPtr, this);
  }

  public void setMobilePhone(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_MobilePhone_set(swigCPtr, this, value);
  }

  public String getMobilePhone() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_MobilePhone_get(swigCPtr, this);
  }

  public void setFax(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Fax_set(swigCPtr, this, value);
  }

  public String getFax() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Fax_get(swigCPtr, this);
  }

  public void setEMail(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_EMail_set(swigCPtr, this, value);
  }

  public String getEMail() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_EMail_get(swigCPtr, this);
  }

  public void setMoneyAccountStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_MoneyAccountStatus_set(swigCPtr, this, value);
  }

  public char getMoneyAccountStatus() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_MoneyAccountStatus_get(swigCPtr, this);
  }

  public void setBankAccount(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankAccount_set(swigCPtr, this, value);
  }

  public String getBankAccount() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankAccount_get(swigCPtr, this);
  }

  public void setBankPassWord(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankPassWord_set(swigCPtr, this, value);
  }

  public String getBankPassWord() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankPassWord_get(swigCPtr, this);
  }

  public void setAccountID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_AccountID_set(swigCPtr, this, value);
  }

  public String getAccountID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_AccountID_get(swigCPtr, this);
  }

  public void setPassword(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Password_set(swigCPtr, this, value);
  }

  public String getPassword() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Password_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_InstallID_get(swigCPtr, this);
  }

  public void setVerifyCertNoFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_VerifyCertNoFlag_set(swigCPtr, this, value);
  }

  public char getVerifyCertNoFlag() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_VerifyCertNoFlag_get(swigCPtr, this);
  }

  public void setCurrencyID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_CurrencyID_set(swigCPtr, this, value);
  }

  public String getCurrencyID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_CurrencyID_get(swigCPtr, this);
  }

  public void setCashExchangeCode(char value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_CashExchangeCode_set(swigCPtr, this, value);
  }

  public char getCashExchangeCode() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_CashExchangeCode_get(swigCPtr, this);
  }

  public void setDigest(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Digest_set(swigCPtr, this, value);
  }

  public String getDigest() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_Digest_get(swigCPtr, this);
  }

  public void setBankAccType(char value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankAccType_set(swigCPtr, this, value);
  }

  public char getBankAccType() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankAccType_get(swigCPtr, this);
  }

  public void setDeviceID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_DeviceID_set(swigCPtr, this, value);
  }

  public String getDeviceID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_DeviceID_get(swigCPtr, this);
  }

  public void setBankSecuAccType(char value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankSecuAccType_set(swigCPtr, this, value);
  }

  public char getBankSecuAccType() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankSecuAccType_get(swigCPtr, this);
  }

  public void setBrokerIDByBank(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BrokerIDByBank_set(swigCPtr, this, value);
  }

  public String getBrokerIDByBank() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BrokerIDByBank_get(swigCPtr, this);
  }

  public void setBankSecuAcc(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankSecuAcc_set(swigCPtr, this, value);
  }

  public String getBankSecuAcc() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankSecuAcc_get(swigCPtr, this);
  }

  public void setBankPwdFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankPwdFlag_set(swigCPtr, this, value);
  }

  public char getBankPwdFlag() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_BankPwdFlag_get(swigCPtr, this);
  }

  public void setSecuPwdFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_SecuPwdFlag_set(swigCPtr, this, value);
  }

  public char getSecuPwdFlag() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_SecuPwdFlag_get(swigCPtr, this);
  }

  public void setOperNo(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_OperNo_set(swigCPtr, this, value);
  }

  public String getOperNo() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_OperNo_get(swigCPtr, this);
  }

  public void setTID(int value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_TID_set(swigCPtr, this, value);
  }

  public int getTID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_TID_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_UserID_get(swigCPtr, this);
  }

  public void setErrorID(int value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_ErrorID_set(swigCPtr, this, value);
  }

  public int getErrorID() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_ErrorID_get(swigCPtr, this);
  }

  public void setErrorMsg(String value) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_ErrorMsg_set(swigCPtr, this, value);
  }

  public String getErrorMsg() {
    return CTP_wrap4javaJNI.CThostFtdcOpenAccountField_ErrorMsg_get(swigCPtr, this);
  }

  public CThostFtdcOpenAccountField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcOpenAccountField(), true);
  }

  public void CopyTo(CThostFtdcOpenAccountField r) {
    CTP_wrap4javaJNI.CThostFtdcOpenAccountField_CopyTo(swigCPtr, this, CThostFtdcOpenAccountField.getCPtr(r), r);
  }

}
