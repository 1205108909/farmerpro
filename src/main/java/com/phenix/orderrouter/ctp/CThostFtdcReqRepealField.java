/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcReqRepealField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcReqRepealField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcReqRepealField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcReqRepealField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setRepealTimeInterval(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_RepealTimeInterval_set(swigCPtr, this, value);
  }

  public int getRepealTimeInterval() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_RepealTimeInterval_get(swigCPtr, this);
  }

  public void setRepealedTimes(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_RepealedTimes_set(swigCPtr, this, value);
  }

  public int getRepealedTimes() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_RepealedTimes_get(swigCPtr, this);
  }

  public void setBankRepealFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankRepealFlag_set(swigCPtr, this, value);
  }

  public char getBankRepealFlag() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankRepealFlag_get(swigCPtr, this);
  }

  public void setBrokerRepealFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BrokerRepealFlag_set(swigCPtr, this, value);
  }

  public char getBrokerRepealFlag() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BrokerRepealFlag_get(swigCPtr, this);
  }

  public void setPlateRepealSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_PlateRepealSerial_set(swigCPtr, this, value);
  }

  public int getPlateRepealSerial() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_PlateRepealSerial_get(swigCPtr, this);
  }

  public void setBankRepealSerial(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankRepealSerial_set(swigCPtr, this, value);
  }

  public String getBankRepealSerial() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankRepealSerial_get(swigCPtr, this);
  }

  public void setFutureRepealSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_FutureRepealSerial_set(swigCPtr, this, value);
  }

  public int getFutureRepealSerial() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_FutureRepealSerial_get(swigCPtr, this);
  }

  public void setTradeCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_TradeCode_set(swigCPtr, this, value);
  }

  public String getTradeCode() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_TradeCode_get(swigCPtr, this);
  }

  public void setBankID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankID_set(swigCPtr, this, value);
  }

  public String getBankID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankID_get(swigCPtr, this);
  }

  public void setBankBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankBranchID_set(swigCPtr, this, value);
  }

  public String getBankBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankBranchID_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BrokerID_get(swigCPtr, this);
  }

  public void setBrokerBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BrokerBranchID_set(swigCPtr, this, value);
  }

  public String getBrokerBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BrokerBranchID_get(swigCPtr, this);
  }

  public void setTradeDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_TradeDate_set(swigCPtr, this, value);
  }

  public String getTradeDate() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_TradeDate_get(swigCPtr, this);
  }

  public void setTradeTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_TradeTime_set(swigCPtr, this, value);
  }

  public String getTradeTime() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_TradeTime_get(swigCPtr, this);
  }

  public void setBankSerial(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankSerial_set(swigCPtr, this, value);
  }

  public String getBankSerial() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankSerial_get(swigCPtr, this);
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_TradingDay_get(swigCPtr, this);
  }

  public void setPlateSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_PlateSerial_set(swigCPtr, this, value);
  }

  public int getPlateSerial() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_PlateSerial_get(swigCPtr, this);
  }

  public void setLastFragment(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_LastFragment_set(swigCPtr, this, value);
  }

  public char getLastFragment() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_LastFragment_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_SessionID_get(swigCPtr, this);
  }

  public void setCustomerName(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_CustomerName_set(swigCPtr, this, value);
  }

  public String getCustomerName() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_CustomerName_get(swigCPtr, this);
  }

  public void setIdCardType(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_IdCardType_set(swigCPtr, this, value);
  }

  public char getIdCardType() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_IdCardType_get(swigCPtr, this);
  }

  public void setIdentifiedCardNo(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_IdentifiedCardNo_set(swigCPtr, this, value);
  }

  public String getIdentifiedCardNo() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_IdentifiedCardNo_get(swigCPtr, this);
  }

  public void setCustType(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_CustType_set(swigCPtr, this, value);
  }

  public char getCustType() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_CustType_get(swigCPtr, this);
  }

  public void setBankAccount(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankAccount_set(swigCPtr, this, value);
  }

  public String getBankAccount() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankAccount_get(swigCPtr, this);
  }

  public void setBankPassWord(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankPassWord_set(swigCPtr, this, value);
  }

  public String getBankPassWord() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankPassWord_get(swigCPtr, this);
  }

  public void setAccountID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_AccountID_set(swigCPtr, this, value);
  }

  public String getAccountID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_AccountID_get(swigCPtr, this);
  }

  public void setPassword(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_Password_set(swigCPtr, this, value);
  }

  public String getPassword() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_Password_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_InstallID_get(swigCPtr, this);
  }

  public void setFutureSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_FutureSerial_set(swigCPtr, this, value);
  }

  public int getFutureSerial() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_FutureSerial_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_UserID_get(swigCPtr, this);
  }

  public void setVerifyCertNoFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_VerifyCertNoFlag_set(swigCPtr, this, value);
  }

  public char getVerifyCertNoFlag() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_VerifyCertNoFlag_get(swigCPtr, this);
  }

  public void setCurrencyID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_CurrencyID_set(swigCPtr, this, value);
  }

  public String getCurrencyID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_CurrencyID_get(swigCPtr, this);
  }

  public void setTradeAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_TradeAmount_set(swigCPtr, this, value);
  }

  public double getTradeAmount() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_TradeAmount_get(swigCPtr, this);
  }

  public void setFutureFetchAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_FutureFetchAmount_set(swigCPtr, this, value);
  }

  public double getFutureFetchAmount() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_FutureFetchAmount_get(swigCPtr, this);
  }

  public void setFeePayFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_FeePayFlag_set(swigCPtr, this, value);
  }

  public char getFeePayFlag() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_FeePayFlag_get(swigCPtr, this);
  }

  public void setCustFee(double value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_CustFee_set(swigCPtr, this, value);
  }

  public double getCustFee() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_CustFee_get(swigCPtr, this);
  }

  public void setBrokerFee(double value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BrokerFee_set(swigCPtr, this, value);
  }

  public double getBrokerFee() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BrokerFee_get(swigCPtr, this);
  }

  public void setMessage(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_Message_set(swigCPtr, this, value);
  }

  public String getMessage() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_Message_get(swigCPtr, this);
  }

  public void setDigest(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_Digest_set(swigCPtr, this, value);
  }

  public String getDigest() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_Digest_get(swigCPtr, this);
  }

  public void setBankAccType(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankAccType_set(swigCPtr, this, value);
  }

  public char getBankAccType() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankAccType_get(swigCPtr, this);
  }

  public void setDeviceID(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_DeviceID_set(swigCPtr, this, value);
  }

  public String getDeviceID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_DeviceID_get(swigCPtr, this);
  }

  public void setBankSecuAccType(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankSecuAccType_set(swigCPtr, this, value);
  }

  public char getBankSecuAccType() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankSecuAccType_get(swigCPtr, this);
  }

  public void setBrokerIDByBank(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BrokerIDByBank_set(swigCPtr, this, value);
  }

  public String getBrokerIDByBank() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BrokerIDByBank_get(swigCPtr, this);
  }

  public void setBankSecuAcc(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankSecuAcc_set(swigCPtr, this, value);
  }

  public String getBankSecuAcc() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankSecuAcc_get(swigCPtr, this);
  }

  public void setBankPwdFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankPwdFlag_set(swigCPtr, this, value);
  }

  public char getBankPwdFlag() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_BankPwdFlag_get(swigCPtr, this);
  }

  public void setSecuPwdFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_SecuPwdFlag_set(swigCPtr, this, value);
  }

  public char getSecuPwdFlag() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_SecuPwdFlag_get(swigCPtr, this);
  }

  public void setOperNo(String value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_OperNo_set(swigCPtr, this, value);
  }

  public String getOperNo() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_OperNo_get(swigCPtr, this);
  }

  public void setRequestID(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_RequestID_set(swigCPtr, this, value);
  }

  public int getRequestID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_RequestID_get(swigCPtr, this);
  }

  public void setTID(int value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_TID_set(swigCPtr, this, value);
  }

  public int getTID() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_TID_get(swigCPtr, this);
  }

  public void setTransferStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_TransferStatus_set(swigCPtr, this, value);
  }

  public char getTransferStatus() {
    return CTP_wrap4javaJNI.CThostFtdcReqRepealField_TransferStatus_get(swigCPtr, this);
  }

  public CThostFtdcReqRepealField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcReqRepealField(), true);
  }

  public void CopyTo(CThostFtdcReqRepealField r) {
    CTP_wrap4javaJNI.CThostFtdcReqRepealField_CopyTo(swigCPtr, this, CThostFtdcReqRepealField.getCPtr(r), r);
  }

}
