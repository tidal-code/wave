package dev.tidalcode.wave.retry;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.wait.ThreadSleep;

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
