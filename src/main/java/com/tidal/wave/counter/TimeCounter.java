package com.tidal.wave.counter;

import java.time.Duration;
import java.time.Instant;

public class TimeCounter {
    private Instant startTime = Instant.now();

    public void restart() {
        startTime = Instant.now();
    }

    /**
     * method timeElapsed checks if a time duration of 'n' seconds is elapsed from the moment it is invoked
     *
     * @param duration is the time duration to This expected condition would wait till the condition is true
     * @return true if the time is elapsed
     */
    public boolean timeElapsed(Duration duration) {
        if (duration.getSeconds() > 600) {
            throw new IllegalArgumentException("Duration should not exceed 600 seconds");
        }
        Duration timeElapsed = Duration.between(startTime, Instant.now());
        return timeElapsed.getSeconds() >= duration.getSeconds();
    }

    /**
     * Gets the time elapsed in Duration
     *
     * @return Duration of time elapsed from the time of object instantiation
     */

    public Duration timeElapsed() {
        return Duration.between(startTime, Instant.now());
    }
}




