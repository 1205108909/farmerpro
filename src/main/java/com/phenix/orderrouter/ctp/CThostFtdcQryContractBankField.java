/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcQryContractBankField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcQryContractBankField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcQryContractBankField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcQryContractBankField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryContractBankField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcQryContractBankField_BrokerID_get(swigCPtr, this);
  }

  public void setBankID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryContractBankField_BankID_set(swigCPtr, this, value);
  }

  public String getBankID() {
    return CTP_wrap4javaJNI.CThostFtdcQryContractBankField_BankID_get(swigCPtr, this);
  }

  public void setBankBrchID(String value) {
    CTP_wrap4javaJNI.CThostFtdcQryContractBankField_BankBrchID_set(swigCPtr, this, value);
  }

  public String getBankBrchID() {
    return CTP_wrap4javaJNI.CThostFtdcQryContractBankField_BankBrchID_get(swigCPtr, this);
  }

  public CThostFtdcQryContractBankField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcQryContractBankField(), true);
  }

  public void CopyTo(CThostFtdcQryContractBankField r) {
    CTP_wrap4javaJNI.CThostFtdcQryContractBankField_CopyTo(swigCPtr, this, CThostFtdcQryContractBankField.getCPtr(r), r);
  }

}
