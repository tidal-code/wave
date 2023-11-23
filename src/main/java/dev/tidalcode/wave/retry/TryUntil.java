package dev.tidalcode.wave.retry;

import java.time.Duration;

public class TryUntil {

    private final Duration duration;
    private final int pollingInterval;
    private final String failureMessage;

    private TryUntil(Duration duration, int pollingInterval, String failureMessage) {
        this.duration = duration;
        this.pollingInterval = pollingInterval;
        this.failureMessage = failureMessage;
    }

    public static TryUntil with(Duration maxTimeDuration, int pollingInterval, String failureMessage) {
        return new TryUntil(maxTimeDuration, pollingInterval, failureMessage);
    }

    public Duration getDuration() {
        return duration;
    }

    public int getPollingInterval() {
        return pollingInterval;
    }

    public String getFailureMessage() {
        return failureMessage;
    }
}
