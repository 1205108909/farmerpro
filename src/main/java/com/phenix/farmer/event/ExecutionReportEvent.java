package com.phenix.farmer.event;

import com.phenix.farmer.FarmerController;
import com.phenix.message.ExecutionReport;
import lombok.Getter;

public class ExecutionReportEvent extends AbstractEngineEvent {
    @Getter
    private ExecutionReport report;

    public ExecutionReportEvent(ExecutionReport report_) {
        report = report_;
        consumer = FarmerController.getInstance().getExecutionReportEventConsumer();
    }

    public static ExecutionReportEvent of(ExecutionReport report_) {
        return new ExecutionReportEvent(report_);
    }

    @Override
    public int hashCode() {
        return report.getSecurity().getBasketID();
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", getClass().getSimpleName(), report.toString());
    }
}
