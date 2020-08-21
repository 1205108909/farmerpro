/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcTransferBankToFutureRspField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcTransferBankToFutureRspField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcTransferBankToFutureRspField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcTransferBankToFutureRspField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setRetCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_RetCode_set(swigCPtr, this, value);
  }

  public String getRetCode() {
    return CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_RetCode_get(swigCPtr, this);
  }

  public void setRetInfo(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_RetInfo_set(swigCPtr, this, value);
  }

  public String getRetInfo() {
    return CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_RetInfo_get(swigCPtr, this);
  }

  public void setFutureAccount(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_FutureAccount_set(swigCPtr, this, value);
  }

  public String getFutureAccount() {
    return CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_FutureAccount_get(swigCPtr, this);
  }

  public void setTradeAmt(double value) {
    CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_TradeAmt_set(swigCPtr, this, value);
  }

  public double getTradeAmt() {
    return CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_TradeAmt_get(swigCPtr, this);
  }

  public void setCustFee(double value) {
    CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_CustFee_set(swigCPtr, this, value);
  }

  public double getCustFee() {
    return CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_CustFee_get(swigCPtr, this);
  }

  public void setCurrencyCode(String value) {
    CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_CurrencyCode_set(swigCPtr, this, value);
  }

  public String getCurrencyCode() {
    return CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_CurrencyCode_get(swigCPtr, this);
  }

  public CThostFtdcTransferBankToFutureRspField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcTransferBankToFutureRspField(), true);
  }

  public void CopyTo(CThostFtdcTransferBankToFutureRspField r) {
    CTP_wrap4javaJNI.CThostFtdcTransferBankToFutureRspField_CopyTo(swigCPtr, this, CThostFtdcTransferBankToFutureRspField.getCPtr(r), r);
  }

}