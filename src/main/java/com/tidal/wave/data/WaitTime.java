package com.tidal.wave.data;

public enum WaitTime implements DataEnum {
    DEFAULT_WAIT_TIME("defaultWaitTime"),
    EXPLICIT_WAIT_TIME("explicitWaitTime"),
    ACTIVITY_WAIT_TIME("activityWaitTime"),
    POLLING_INTERVAL("pollingInterval");

    final String waitTime;

    WaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public String getKey() {
        return waitTime;
    }
}
