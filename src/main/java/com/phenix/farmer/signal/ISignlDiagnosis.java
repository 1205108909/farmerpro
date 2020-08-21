package com.phenix.farmer.signal;

import com.google.common.collect.Table;
import com.phenix.data.Security;

import java.util.List;

public interface ISignlDiagnosis {
    Table<Security, String, String> getDiagInfo(List<Security> secs_);
}
