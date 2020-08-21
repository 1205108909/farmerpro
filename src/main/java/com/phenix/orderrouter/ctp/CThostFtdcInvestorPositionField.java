/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.phenix.orderrouter.ctp;

public class CThostFtdcInvestorPositionField {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CThostFtdcInvestorPositionField(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CThostFtdcInvestorPositionField obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CTP_wrap4javaJNI.delete_CThostFtdcInvestorPositionField(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setInstrumentID(String value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_InstrumentID_set(swigCPtr, this, value);
  }

  public String getInstrumentID() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_InstrumentID_get(swigCPtr, this);
  }

  public void setBrokerID(String value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_BrokerID_set(swigCPtr, this, value);
  }

  public String getBrokerID() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_BrokerID_get(swigCPtr, this);
  }

  public void setInvestorID(String value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_InvestorID_set(swigCPtr, this, value);
  }

  public String getInvestorID() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_InvestorID_get(swigCPtr, this);
  }

  public void setPosiDirection(char value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PosiDirection_set(swigCPtr, this, value);
  }

  public char getPosiDirection() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PosiDirection_get(swigCPtr, this);
  }

  public void setHedgeFlag(char value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_HedgeFlag_set(swigCPtr, this, value);
  }

  public char getHedgeFlag() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_HedgeFlag_get(swigCPtr, this);
  }

  public void setPositionDate(char value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PositionDate_set(swigCPtr, this, value);
  }

  public char getPositionDate() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PositionDate_get(swigCPtr, this);
  }

  public void setYdPosition(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_YdPosition_set(swigCPtr, this, value);
  }

  public int getYdPosition() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_YdPosition_get(swigCPtr, this);
  }

  public void setPosition(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_Position_set(swigCPtr, this, value);
  }

  public int getPosition() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_Position_get(swigCPtr, this);
  }

  public void setLongFrozen(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_LongFrozen_set(swigCPtr, this, value);
  }

  public int getLongFrozen() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_LongFrozen_get(swigCPtr, this);
  }

  public void setShortFrozen(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_ShortFrozen_set(swigCPtr, this, value);
  }

  public int getShortFrozen() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_ShortFrozen_get(swigCPtr, this);
  }

  public void setLongFrozenAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_LongFrozenAmount_set(swigCPtr, this, value);
  }

  public double getLongFrozenAmount() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_LongFrozenAmount_get(swigCPtr, this);
  }

  public void setShortFrozenAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_ShortFrozenAmount_set(swigCPtr, this, value);
  }

  public double getShortFrozenAmount() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_ShortFrozenAmount_get(swigCPtr, this);
  }

  public void setOpenVolume(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_OpenVolume_set(swigCPtr, this, value);
  }

  public int getOpenVolume() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_OpenVolume_get(swigCPtr, this);
  }

  public void setCloseVolume(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CloseVolume_set(swigCPtr, this, value);
  }

  public int getCloseVolume() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CloseVolume_get(swigCPtr, this);
  }

  public void setOpenAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_OpenAmount_set(swigCPtr, this, value);
  }

  public double getOpenAmount() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_OpenAmount_get(swigCPtr, this);
  }

  public void setCloseAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CloseAmount_set(swigCPtr, this, value);
  }

  public double getCloseAmount() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CloseAmount_get(swigCPtr, this);
  }

  public void setPositionCost(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PositionCost_set(swigCPtr, this, value);
  }

  public double getPositionCost() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PositionCost_get(swigCPtr, this);
  }

  public void setPreMargin(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PreMargin_set(swigCPtr, this, value);
  }

  public double getPreMargin() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PreMargin_get(swigCPtr, this);
  }

  public void setUseMargin(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_UseMargin_set(swigCPtr, this, value);
  }

  public double getUseMargin() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_UseMargin_get(swigCPtr, this);
  }

  public void setFrozenMargin(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_FrozenMargin_set(swigCPtr, this, value);
  }

  public double getFrozenMargin() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_FrozenMargin_get(swigCPtr, this);
  }

  public void setFrozenCash(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_FrozenCash_set(swigCPtr, this, value);
  }

  public double getFrozenCash() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_FrozenCash_get(swigCPtr, this);
  }

  public void setFrozenCommission(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_FrozenCommission_set(swigCPtr, this, value);
  }

  public double getFrozenCommission() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_FrozenCommission_get(swigCPtr, this);
  }

  public void setCashIn(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CashIn_set(swigCPtr, this, value);
  }

  public double getCashIn() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CashIn_get(swigCPtr, this);
  }

  public void setCommission(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_Commission_set(swigCPtr, this, value);
  }

  public double getCommission() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_Commission_get(swigCPtr, this);
  }

  public void setCloseProfit(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CloseProfit_set(swigCPtr, this, value);
  }

  public double getCloseProfit() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CloseProfit_get(swigCPtr, this);
  }

  public void setPositionProfit(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PositionProfit_set(swigCPtr, this, value);
  }

  public double getPositionProfit() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PositionProfit_get(swigCPtr, this);
  }

  public void setPreSettlementPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PreSettlementPrice_set(swigCPtr, this, value);
  }

  public double getPreSettlementPrice() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_PreSettlementPrice_get(swigCPtr, this);
  }

  public void setSettlementPrice(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_SettlementPrice_set(swigCPtr, this, value);
  }

  public double getSettlementPrice() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_SettlementPrice_get(swigCPtr, this);
  }

  public void setTradingDay(String value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_TradingDay_set(swigCPtr, this, value);
  }

  public String getTradingDay() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_TradingDay_get(swigCPtr, this);
  }

  public void setSettlementID(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_SettlementID_set(swigCPtr, this, value);
  }

  public int getSettlementID() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_SettlementID_get(swigCPtr, this);
  }

  public void setOpenCost(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_OpenCost_set(swigCPtr, this, value);
  }

  public double getOpenCost() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_OpenCost_get(swigCPtr, this);
  }

  public void setExchangeMargin(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_ExchangeMargin_set(swigCPtr, this, value);
  }

  public double getExchangeMargin() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_ExchangeMargin_get(swigCPtr, this);
  }

  public void setCombPosition(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CombPosition_set(swigCPtr, this, value);
  }

  public int getCombPosition() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CombPosition_get(swigCPtr, this);
  }

  public void setCombLongFrozen(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CombLongFrozen_set(swigCPtr, this, value);
  }

  public int getCombLongFrozen() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CombLongFrozen_get(swigCPtr, this);
  }

  public void setCombShortFrozen(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CombShortFrozen_set(swigCPtr, this, value);
  }

  public int getCombShortFrozen() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CombShortFrozen_get(swigCPtr, this);
  }

  public void setCloseProfitByDate(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CloseProfitByDate_set(swigCPtr, this, value);
  }

  public double getCloseProfitByDate() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CloseProfitByDate_get(swigCPtr, this);
  }

  public void setCloseProfitByTrade(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CloseProfitByTrade_set(swigCPtr, this, value);
  }

  public double getCloseProfitByTrade() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CloseProfitByTrade_get(swigCPtr, this);
  }

  public void setTodayPosition(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_TodayPosition_set(swigCPtr, this, value);
  }

  public int getTodayPosition() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_TodayPosition_get(swigCPtr, this);
  }

  public void setMarginRateByMoney(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_MarginRateByMoney_set(swigCPtr, this, value);
  }

  public double getMarginRateByMoney() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_MarginRateByMoney_get(swigCPtr, this);
  }

  public void setMarginRateByVolume(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_MarginRateByVolume_set(swigCPtr, this, value);
  }

  public double getMarginRateByVolume() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_MarginRateByVolume_get(swigCPtr, this);
  }

  public void setStrikeFrozen(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_StrikeFrozen_set(swigCPtr, this, value);
  }

  public int getStrikeFrozen() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_StrikeFrozen_get(swigCPtr, this);
  }

  public void setStrikeFrozenAmount(double value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_StrikeFrozenAmount_set(swigCPtr, this, value);
  }

  public double getStrikeFrozenAmount() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_StrikeFrozenAmount_get(swigCPtr, this);
  }

  public void setAbandonFrozen(int value) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_AbandonFrozen_set(swigCPtr, this, value);
  }

  public int getAbandonFrozen() {
    return CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_AbandonFrozen_get(swigCPtr, this);
  }

  public CThostFtdcInvestorPositionField() {
    this(CTP_wrap4javaJNI.new_CThostFtdcInvestorPositionField(), true);
  }

  public void CopyTo(CThostFtdcInvestorPositionField r) {
    CTP_wrap4javaJNI.CThostFtdcInvestorPositionField_CopyTo(swigCPtr, this, CThostFtdcInvestorPositionField.getCPtr(r), r);
  }

}
