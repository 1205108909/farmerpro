package com.phenix.farmer.config;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.phenix.data.IEngineConfig;
import com.phenix.exception.NotImplementedException;
import com.phenix.farmer.RunningMode;
import com.phenix.farmer.signal.SignalMode;
import com.phenix.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.jdom2.Element;

public class InstanceConfig implements IEngineConfig {
    @Getter
    private Environment environment;

    @Getter
    private LocalDateTime startTime;

    @Getter
    @Setter
    private LocalDateTime dataDate;//use this to specify the date on which the data is used for computing

    @Getter
    private String startTimeAsDate;//cached to save the parse time

    @Getter
    private SignalMode signalMode;

    @Getter
    private int numOfWorker;

    @Getter
    private double commissionRate;

    @Getter
    private LocalTime dailyShutDownTime;

    @Getter
    private int eventBufferSize;

    @Getter
    private String universePath;

    @Getter
    private String signalSecurityMappingPath;

    @Getter
    private String indexFutureMappingPath;

    @Getter
    private String tradingDayPath;

	@Getter
	private String indexComponentPath;

	@Getter
    private RunningMode runningMode;

	@Getter
    private String persistPath;

    public InstanceConfig(Environment env_, LocalDateTime startTime_, LocalDateTime dataDate_, int numOfWorker_,
                          SignalMode mode_, double commissionRate_, LocalTime dailyShutDownTime_, int eventBufferSize_,
                          String universePath_, String signalSecurityMappingPath_, String indexFutureMappingPath_,
                          String tradingDayPath_, String indexComponentPath_, RunningMode runningMode_, String persistPath_) {
        environment = env_;
        startTime = startTime_;
        dataDate = dataDate_;
        startTimeAsDate = DateUtil.date2Str(startTime_);
        numOfWorker = numOfWorker_ == -1 ? Runtime.getRuntime().availableProcessors() : numOfWorker_;
        signalMode = mode_;
        commissionRate = commissionRate_;
        dailyShutDownTime = dailyShutDownTime_;
        eventBufferSize = eventBufferSize_;
        universePath = universePath_;
        signalSecurityMappingPath = signalSecurityMappingPath_;
        indexFutureMappingPath = indexFutureMappingPath_;
        tradingDayPath = tradingDayPath_;
		indexComponentPath = indexComponentPath_;
		runningMode = runningMode_;
		persistPath = persistPath_;
    }

    public long getShutDownDelayInSecs() {
        return dailyShutDownTime.toSecondOfDay() - startTime.toLocalTime().toSecondOfDay();
    }

    @Override
    public boolean readConfig(Element e_) {
        throw new NotImplementedException("not implemented yet");
    }
}
