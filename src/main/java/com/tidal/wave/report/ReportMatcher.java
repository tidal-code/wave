package com.tidal.wave.report;

public abstract class ReportMatcher {
    protected ReportMatcher nextReportTypeMatcher;

    public void setNextMatcher(ReportMatcher nextReportTypeMatcher){
        this.nextReportTypeMatcher = nextReportTypeMatcher;
    }

    public abstract ReportModel parse(String typeContent, String errorMessage);
}
