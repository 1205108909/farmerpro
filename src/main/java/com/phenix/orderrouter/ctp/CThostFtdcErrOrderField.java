/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcErrOrderField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcErrOrderField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcErrOrderField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcErrOrderField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_InvestorID_get(swigCPtr, this);
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_InstrumentID_get(swigCPtr, this);
  }

  public void setOrderRef(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_OrderRef_set(swigCPtr, this, value);
  }

  public String getOrderRef() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_OrderRef_get(swigCPtr, this);
  }

  public void setUserID(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_UserID_set(swigCPtr, this, value);
  }

  public String getUserID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_UserID_get(swigCPtr, this);
  }

  public void setOrderPriceType(char value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_OrderPriceType_set(swigCPtr, this, value);
  }

  public char getOrderPriceType() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_OrderPriceType_get(swigCPtr, this);
  }

  public void setDirection(char value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_Direction_set(swigCPtr, this, value);
  }

  public char getDirection() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_Direction_get(swigCPtr, this);
  }

  public void setCombOffsetFlag(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_CombOffsetFlag_set(swigCPtr, this, value);
  }

  public String getCombOffsetFlag() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_CombOffsetFlag_get(swigCPtr, this);
  }

  public void setCombHedgeFlag(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_CombHedgeFlag_set(swigCPtr, this, value);
  }

  public String getCombHedgeFlag() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_CombHedgeFlag_get(swigCPtr, this);
  }

  public void setLimitPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_LimitPrice_set(swigCPtr, this, value);
  }

  public double getLimitPrice() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_LimitPrice_get(swigCPtr, this);
  }

  public void setVolumeTotalOriginal(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_VolumeTotalOriginal_set(swigCPtr, this, value);
  }

  public int getVolumeTotalOriginal() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_VolumeTotalOriginal_get(swigCPtr, this);
  }

  public void setTimeCondition(char value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_TimeCondition_set(swigCPtr, this, value);
  }

  public char getTimeCondition() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_TimeCondition_get(swigCPtr, this);
  }

  public void setGTDDate(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_GTDDate_set(swigCPtr, this, value);
  }

  public String getGTDDate() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_GTDDate_get(swigCPtr, this);
  }

  public void setVolumeCondition(char value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_VolumeCondition_set(swigCPtr, this, value);
  }

  public char getVolumeCondition() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_VolumeCondition_get(swigCPtr, this);
  }

  public void setMinVolume(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_MinVolume_set(swigCPtr, this, value);
  }

  public int getMinVolume() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_MinVolume_get(swigCPtr, this);
  }

  public void setContingentCondition(char value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_ContingentCondition_set(swigCPtr, this, value);
  }

  public char getContingentCondition() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_ContingentCondition_get(swigCPtr, this);
  }

  public void setStopPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_StopPrice_set(swigCPtr, this, value);
  }

  public double getStopPrice() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_StopPrice_get(swigCPtr, this);
  }

  public void setForceCloseReason(char value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_ForceCloseReason_set(swigCPtr, this, value);
  }

  public char getForceCloseReason() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_ForceCloseReason_get(swigCPtr, this);
  }

  public void setIsAutoSuspend(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_IsAutoSuspend_set(swigCPtr, this, value);
  }

  public int getIsAutoSuspend() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_IsAutoSuspend_get(swigCPtr, this);
  }

  public void setBusinessUnit(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_BusinessUnit_set(swigCPtr, this, value);
  }

  public String getBusinessUnit() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_BusinessUnit_get(swigCPtr, this);
  }

  public void setRequestID(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_RequestID_set(swigCPtr, this, value);
  }

  public int getRequestID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_RequestID_get(swigCPtr, this);
  }

  public void setUserForceClose(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_UserForceClose_set(swigCPtr, this, value);
  }

  public int getUserForceClose() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_UserForceClose_get(swigCPtr, this);
  }

  public void setErrorID(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_ErrorID_set(swigCPtr, this, value);
  }

  public int getErrorID() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_ErrorID_get(swigCPtr, this);
  }

  public void setErrorMsg(String value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_ErrorMsg_set(swigCPtr, this, value);
  }

  public String getErrorMsg() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_ErrorMsg_get(swigCPtr, this);
  }

  public void setIsSwapOrder(int value) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_IsSwapOrder_set(swigCPtr, this, value);
  }

  public int getIsSwapOrder() {
    return CTP_wrap4javaJNI.CThostFtdcErrOrderField_IsSwapOrder_get(swigCPtr, this);
  }

  public CThostFtdcErrOrderField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcErrOrderField(), true);
  }

  public void CopyTo(CThostFtdcErrOrderField r) {
    CTP_wrap4javaJNI.CThostFtdcErrOrderField_CopyTo(swigCPtr, this, CThostFtdcErrOrderField.getCPtr(r), r);
  }

}
