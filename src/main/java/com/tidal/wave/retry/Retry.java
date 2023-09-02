package com.tidal.wave.retry;

import com.tidal.wave.command.Executor;
import com.tidal.wave.wait.ThreadSleep;

import java.util.List;

public class Retry {

    private Retry() {
    }

    public static void retry(Executor executor, RetryCondition retryCondition, int numberOfTimes) {
        for (int i = 0; i < numberOfTimes; i++) {
            if (retryCondition.retry(executor)) {
                break;
            }
            ThreadSleep.forMilliS(500);
        }
    }
}
