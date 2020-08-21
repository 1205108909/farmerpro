package com.phenix.farmer.config;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotImplementedException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BackTestConfig implements IEngineConfig {
    @Getter
    private LocalDate startDate;
    @Getter
    private LocalDate endDate;
    @Getter
    private List<String> indexName;

    public BackTestConfig(LocalDate startDate_, LocalDate endDate_, String indexName_) {
        startDate = startDate_;
        endDate = endDate_;
        indexName = StringUtils.isBlank(indexName_) ? Collections.emptyList() : Arrays.asList(StringUtils.split(indexName_, "|"));
    }

    @Override
    public boolean readConfig(Element e_) {
        throw new NotImplementedException("not implemented yet");
    }
}
