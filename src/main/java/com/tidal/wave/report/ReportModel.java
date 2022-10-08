package com.tidal.wave.report;

public class ReportModel {

    private String functionalFailure;
    private String scriptFailure;
    private String generalFailure;

    public void setFunctionalFailure(String functionalFailure) {
        this.functionalFailure = functionalFailure;
    }

    public void setScriptFailure(String scriptFailure) {
        this.scriptFailure = scriptFailure;
    }

    public void setGeneralFailure(String generalFailure) {
        this.generalFailure = generalFailure;
    }

    public String functionalFailure() {
        return functionalFailure;
    }

    public String scriptFailure() {
        return scriptFailure;
    }

    public String generalFailure() {
        return generalFailure;
    }
}
