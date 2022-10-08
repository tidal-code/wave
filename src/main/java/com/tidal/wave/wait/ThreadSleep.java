package com.tidal.wave.wait;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Duration;

public class ThreadSleep {

    private static final Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;

    private ThreadSleep() {
    }

    public static void forSeconds(double seconds) {
        long millis = (long) seconds * 1000;
        try {
            sleeper.sleep(Duration.ofMillis(millis));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException(e);
        }
    }

    public static void forMilliS(long millis) {
        try {
            sleeper.sleep(Duration.ofMillis(millis));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException(e);
        }
    }
}
