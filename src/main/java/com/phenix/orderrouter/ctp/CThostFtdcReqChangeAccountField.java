/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcReqChangeAccountField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcReqChangeAccountField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcReqChangeAccountField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcReqChangeAccountField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setTradeCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_TradeCode_set(swigCPtr, this, value);
  }

  public String getTradeCode() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_TradeCode_get(swigCPtr, this);
  }

  public void setBankID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankID_set(swigCPtr, this, value);
  }

  public String getBankID() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankID_get(swigCPtr, this);
  }

  public void setBankBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankBranchID_set(swigCPtr, this, value);
  }

  public String getBankBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankBranchID_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BrokerID_get(swigCPtr, this);
  }

  public void setBrokerBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BrokerBranchID_set(swigCPtr, this, value);
  }

  public String getBrokerBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BrokerBranchID_get(swigCPtr, this);
  }

  public void setTradeDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_TradeDate_set(swigCPtr, this, value);
  }

  public String getTradeDate() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_TradeDate_get(swigCPtr, this);
  }

  public void setTradeTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_TradeTime_set(swigCPtr, this, value);
  }

  public String getTradeTime() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_TradeTime_get(swigCPtr, this);
  }

  public void setBankSerial(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankSerial_set(swigCPtr, this, value);
  }

  public String getBankSerial() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankSerial_get(swigCPtr, this);
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_TradingDay_get(swigCPtr, this);
  }

  public void setPlateSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_PlateSerial_set(swigCPtr, this, value);
  }

  public int getPlateSerial() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_PlateSerial_get(swigCPtr, this);
  }

  public void setLastFragment(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_LastFragment_set(swigCPtr, this, value);
  }

  public char getLastFragment() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_LastFragment_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_SessionID_get(swigCPtr, this);
  }

  public void setCustomerName(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_CustomerName_set(swigCPtr, this, value);
  }

  public String getCustomerName() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_CustomerName_get(swigCPtr, this);
  }

  public void setIdCardType(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_IdCardType_set(swigCPtr, this, value);
  }

  public char getIdCardType() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_IdCardType_get(swigCPtr, this);
  }

  public void setIdentifiedCardNo(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_IdentifiedCardNo_set(swigCPtr, this, value);
  }

  public String getIdentifiedCardNo() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_IdentifiedCardNo_get(swigCPtr, this);
  }

  public void setGender(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Gender_set(swigCPtr, this, value);
  }

  public char getGender() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Gender_get(swigCPtr, this);
  }

  public void setCountryCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_CountryCode_set(swigCPtr, this, value);
  }

  public String getCountryCode() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_CountryCode_get(swigCPtr, this);
  }

  public void setCustType(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_CustType_set(swigCPtr, this, value);
  }

  public char getCustType() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_CustType_get(swigCPtr, this);
  }

  public void setAddress(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Address_set(swigCPtr, this, value);
  }

  public String getAddress() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Address_get(swigCPtr, this);
  }

  public void setZipCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_ZipCode_set(swigCPtr, this, value);
  }

  public String getZipCode() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_ZipCode_get(swigCPtr, this);
  }

  public void setTelephone(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Telephone_set(swigCPtr, this, value);
  }

  public String getTelephone() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Telephone_get(swigCPtr, this);
  }

  public void setMobilePhone(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_MobilePhone_set(swigCPtr, this, value);
  }

  public String getMobilePhone() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_MobilePhone_get(swigCPtr, this);
  }

  public void setFax(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Fax_set(swigCPtr, this, value);
  }

  public String getFax() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Fax_get(swigCPtr, this);
  }

  public void setEMail(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_EMail_set(swigCPtr, this, value);
  }

  public String getEMail() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_EMail_get(swigCPtr, this);
  }

  public void setMoneyAccountStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_MoneyAccountStatus_set(swigCPtr, this, value);
  }

  public char getMoneyAccountStatus() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_MoneyAccountStatus_get(swigCPtr, this);
  }

  public void setBankAccount(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankAccount_set(swigCPtr, this, value);
  }

  public String getBankAccount() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankAccount_get(swigCPtr, this);
  }

  public void setBankPassWord(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankPassWord_set(swigCPtr, this, value);
  }

  public String getBankPassWord() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankPassWord_get(swigCPtr, this);
  }

  public void setNewBankAccount(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_NewBankAccount_set(swigCPtr, this, value);
  }

  public String getNewBankAccount() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_NewBankAccount_get(swigCPtr, this);
  }

  public void setNewBankPassWord(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_NewBankPassWord_set(swigCPtr, this, value);
  }

  public String getNewBankPassWord() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_NewBankPassWord_get(swigCPtr, this);
  }

  public void setAccountID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_AccountID_set(swigCPtr, this, value);
  }

  public String getAccountID() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_AccountID_get(swigCPtr, this);
  }

  public void setPassword(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Password_set(swigCPtr, this, value);
  }

  public String getPassword() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Password_get(swigCPtr, this);
  }

  public void setBankAccType(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankAccType_set(swigCPtr, this, value);
  }

  public char getBankAccType() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankAccType_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_InstallID_get(swigCPtr, this);
  }

  public void setVerifyCertNoFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_VerifyCertNoFlag_set(swigCPtr, this, value);
  }

  public char getVerifyCertNoFlag() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_VerifyCertNoFlag_get(swigCPtr, this);
  }

  public void setCurrencyID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_CurrencyID_set(swigCPtr, this, value);
  }

  public String getCurrencyID() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_CurrencyID_get(swigCPtr, this);
  }

  public void setBrokerIDByBank(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BrokerIDByBank_set(swigCPtr, this, value);
  }

  public String getBrokerIDByBank() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BrokerIDByBank_get(swigCPtr, this);
  }

  public void setBankPwdFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankPwdFlag_set(swigCPtr, this, value);
  }

  public char getBankPwdFlag() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_BankPwdFlag_get(swigCPtr, this);
  }

  public void setSecuPwdFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_SecuPwdFlag_set(swigCPtr, this, value);
  }

  public char getSecuPwdFlag() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_SecuPwdFlag_get(swigCPtr, this);
  }

  public void setTID(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_TID_set(swigCPtr, this, value);
  }

  public int getTID() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_TID_get(swigCPtr, this);
  }

  public void setDigest(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Digest_set(swigCPtr, this, value);
  }

  public String getDigest() {
    return CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_Digest_get(swigCPtr, this);
  }

  public CThostFtdcReqChangeAccountField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcReqChangeAccountField(), true);
  }

  public void CopyTo(CThostFtdcReqChangeAccountField r) {
    CTP_wrap4javaJNI.CThostFtdcReqChangeAccountField_CopyTo(swigCPtr, this, CThostFtdcReqChangeAccountField.getCPtr(r), r);
  }

}
