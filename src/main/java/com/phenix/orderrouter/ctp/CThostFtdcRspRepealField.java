/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcRspRepealField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcRspRepealField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcRspRepealField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcRspRepealField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setRepealTimeInterval(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_RepealTimeInterval_set(swigCPtr, this, value);
  }

  public int getRepealTimeInterval() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_RepealTimeInterval_get(swigCPtr, this);
  }

  public void setRepealedTimes(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_RepealedTimes_set(swigCPtr, this, value);
  }

  public int getRepealedTimes() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_RepealedTimes_get(swigCPtr, this);
  }

  public void setBankRepealFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankRepealFlag_set(swigCPtr, this, value);
  }

  public char getBankRepealFlag() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankRepealFlag_get(swigCPtr, this);
  }

  public void setBrokerRepealFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BrokerRepealFlag_set(swigCPtr, this, value);
  }

  public char getBrokerRepealFlag() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BrokerRepealFlag_get(swigCPtr, this);
  }

  public void setPlateRepealSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_PlateRepealSerial_set(swigCPtr, this, value);
  }

  public int getPlateRepealSerial() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_PlateRepealSerial_get(swigCPtr, this);
  }

  public void setBankRepealSerial(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankRepealSerial_set(swigCPtr, this, value);
  }

  public String getBankRepealSerial() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankRepealSerial_get(swigCPtr, this);
  }

  public void setFutureRepealSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_FutureRepealSerial_set(swigCPtr, this, value);
  }

  public int getFutureRepealSerial() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_FutureRepealSerial_get(swigCPtr, this);
  }

  public void setTradeCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_TradeCode_set(swigCPtr, this, value);
  }

  public String getTradeCode() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_TradeCode_get(swigCPtr, this);
  }

  public void setBankID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankID_set(swigCPtr, this, value);
  }

  public String getBankID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankID_get(swigCPtr, this);
  }

  public void setBankBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankBranchID_set(swigCPtr, this, value);
  }

  public String getBankBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankBranchID_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BrokerID_get(swigCPtr, this);
  }

  public void setBrokerBranchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BrokerBranchID_set(swigCPtr, this, value);
  }

  public String getBrokerBranchID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BrokerBranchID_get(swigCPtr, this);
  }

  public void setTradeDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_TradeDate_set(swigCPtr, this, value);
  }

  public String getTradeDate() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_TradeDate_get(swigCPtr, this);
  }

  public void setTradeTime(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_TradeTime_set(swigCPtr, this, value);
  }

  public String getTradeTime() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_TradeTime_get(swigCPtr, this);
  }

  public void setBankSerial(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankSerial_set(swigCPtr, this, value);
  }

  public String getBankSerial() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankSerial_get(swigCPtr, this);
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_TradingDay_get(swigCPtr, this);
  }

  public void setPlateSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_PlateSerial_set(swigCPtr, this, value);
  }

  public int getPlateSerial() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_PlateSerial_get(swigCPtr, this);
  }

  public void setLastFragment(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_LastFragment_set(swigCPtr, this, value);
  }

  public char getLastFragment() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_LastFragment_get(swigCPtr, this);
  }

  public void setSessionID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_SessionID_set(swigCPtr, this, value);
  }

  public int getSessionID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_SessionID_get(swigCPtr, this);
  }

  public void setCustomerName(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_CustomerName_set(swigCPtr, this, value);
  }

  public String getCustomerName() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_CustomerName_get(swigCPtr, this);
  }

  public void setIdCardType(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_IdCardType_set(swigCPtr, this, value);
  }

  public char getIdCardType() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_IdCardType_get(swigCPtr, this);
  }

  public void setIdentifiedCardNo(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_IdentifiedCardNo_set(swigCPtr, this, value);
  }

  public String getIdentifiedCardNo() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_IdentifiedCardNo_get(swigCPtr, this);
  }

  public void setCustType(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_CustType_set(swigCPtr, this, value);
  }

  public char getCustType() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_CustType_get(swigCPtr, this);
  }

  public void setBankAccount(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankAccount_set(swigCPtr, this, value);
  }

  public String getBankAccount() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankAccount_get(swigCPtr, this);
  }

  public void setBankPassWord(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankPassWord_set(swigCPtr, this, value);
  }

  public String getBankPassWord() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankPassWord_get(swigCPtr, this);
  }

  public void setAccountID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_AccountID_set(swigCPtr, this, value);
  }

  public String getAccountID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_AccountID_get(swigCPtr, this);
  }

  public void setPassword(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_Password_set(swigCPtr, this, value);
  }

  public String getPassword() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_Password_get(swigCPtr, this);
  }

  public void setInstallID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_InstallID_set(swigCPtr, this, value);
  }

  public int getInstallID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_InstallID_get(swigCPtr, this);
  }

  public void setFutureSerial(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_FutureSerial_set(swigCPtr, this, value);
  }

  public int getFutureSerial() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_FutureSerial_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_UserID_get(swigCPtr, this);
  }

  public void setVerifyCertNoFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_VerifyCertNoFlag_set(swigCPtr, this, value);
  }

  public char getVerifyCertNoFlag() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_VerifyCertNoFlag_get(swigCPtr, this);
  }

  public void setCurrencyID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_CurrencyID_set(swigCPtr, this, value);
  }

  public String getCurrencyID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_CurrencyID_get(swigCPtr, this);
  }

  public void setTradeAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_TradeAmount_set(swigCPtr, this, value);
  }

  public double getTradeAmount() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_TradeAmount_get(swigCPtr, this);
  }

  public void setFutureFetchAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_FutureFetchAmount_set(swigCPtr, this, value);
  }

  public double getFutureFetchAmount() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_FutureFetchAmount_get(swigCPtr, this);
  }

  public void setFeePayFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_FeePayFlag_set(swigCPtr, this, value);
  }

  public char getFeePayFlag() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_FeePayFlag_get(swigCPtr, this);
  }

  public void setCustFee(double value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_CustFee_set(swigCPtr, this, value);
  }

  public double getCustFee() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_CustFee_get(swigCPtr, this);
  }

  public void setBrokerFee(double value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BrokerFee_set(swigCPtr, this, value);
  }

  public double getBrokerFee() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BrokerFee_get(swigCPtr, this);
  }

  public void setMessage(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_Message_set(swigCPtr, this, value);
  }

  public String getMessage() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_Message_get(swigCPtr, this);
  }

  public void setDigest(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_Digest_set(swigCPtr, this, value);
  }

  public String getDigest() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_Digest_get(swigCPtr, this);
  }

  public void setBankAccType(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankAccType_set(swigCPtr, this, value);
  }

  public char getBankAccType() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankAccType_get(swigCPtr, this);
  }

  public void setDeviceID(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_DeviceID_set(swigCPtr, this, value);
  }

  public String getDeviceID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_DeviceID_get(swigCPtr, this);
  }

  public void setBankSecuAccType(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankSecuAccType_set(swigCPtr, this, value);
  }

  public char getBankSecuAccType() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankSecuAccType_get(swigCPtr, this);
  }

  public void setBrokerIDByBank(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BrokerIDByBank_set(swigCPtr, this, value);
  }

  public String getBrokerIDByBank() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BrokerIDByBank_get(swigCPtr, this);
  }

  public void setBankSecuAcc(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankSecuAcc_set(swigCPtr, this, value);
  }

  public String getBankSecuAcc() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankSecuAcc_get(swigCPtr, this);
  }

  public void setBankPwdFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankPwdFlag_set(swigCPtr, this, value);
  }

  public char getBankPwdFlag() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_BankPwdFlag_get(swigCPtr, this);
  }

  public void setSecuPwdFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_SecuPwdFlag_set(swigCPtr, this, value);
  }

  public char getSecuPwdFlag() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_SecuPwdFlag_get(swigCPtr, this);
  }

  public void setOperNo(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_OperNo_set(swigCPtr, this, value);
  }

  public String getOperNo() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_OperNo_get(swigCPtr, this);
  }

  public void setRequestID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_RequestID_set(swigCPtr, this, value);
  }

  public int getRequestID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_RequestID_get(swigCPtr, this);
  }

  public void setTID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_TID_set(swigCPtr, this, value);
  }

  public int getTID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_TID_get(swigCPtr, this);
  }

  public void setTransferStatus(char value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_TransferStatus_set(swigCPtr, this, value);
  }

  public char getTransferStatus() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_TransferStatus_get(swigCPtr, this);
  }

  public void setErrorID(int value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_ErrorID_set(swigCPtr, this, value);
  }

  public int getErrorID() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_ErrorID_get(swigCPtr, this);
  }

  public void setErrorMsg(String value) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_ErrorMsg_set(swigCPtr, this, value);
  }

  public String getErrorMsg() {
    return CTP_wrap4javaJNI.CThostFtdcRspRepealField_ErrorMsg_get(swigCPtr, this);
  }

  public CThostFtdcRspRepealField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcRspRepealField(), true);
  }

  public void CopyTo(CThostFtdcRspRepealField r) {
    CTP_wrap4javaJNI.CThostFtdcRspRepealField_CopyTo(swigCPtr, this, CThostFtdcRspRepealField.getCPtr(r), r);
  }

}
