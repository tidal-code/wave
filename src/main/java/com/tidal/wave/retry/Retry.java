package com.tidal.wave.retry;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.wave.wait.ThreadSleep;

import java.util.List;

public class Retry {

    private Retry() {
    }

    public static void retry(boolean isVisible, boolean isMultiple, List<String> locators, RetryCondition retryCondition, int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            if (retryCondition.retry(isVisible, isMultiple, locators)) {
                break;
            }
            ThreadSleep.forMilliS(500);
        }
    }

    public static void retry(boolean isVisible, boolean isMultiple, List<String> locators, RetryCondition retryCondition, TryUntil tryUntil) {
        TimeCounter timeCounter = new TimeCounter();

        while (!retryCondition.retry(isVisible, isMultiple, locators)) {
            ThreadSleep.forSeconds(tryUntil.getPollingInterval());

            if (timeCounter.timeElapsed(tryUntil.getDuration())) {
                throw new RuntimeException(tryUntil.getFailureMessage());
            }
        }
    }
}
