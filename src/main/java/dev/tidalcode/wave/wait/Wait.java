package dev.tidalcode.wave.wait;

import dev.tidalcode.wave.browser.Driver;
import dev.tidalcode.wave.data.WaitTime;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static dev.tidalcode.wave.data.WaitTimeData.getWaitTime;
import static dev.tidalcode.wave.data.WaitTimeData.setWaitTime;

public class Wait {

    private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> backgroundWait = new ThreadLocal<>();

    public static WebDriverWait getWait() {
        if (wait.get() == null) {
            setDefaultWait();
        }
        return wait.get();
    }

    public static void setExplicitWait(int maxWaitTime) {
        removeWait();

        if (maxWaitTime > 600) {
            throw new IllegalArgumentException("Waiting time should not exceed 10 minutes");
        }

        List<Class<? extends Throwable>> ignoredExceptions = new ArrayList<>();
        ignoredExceptions.add(StaleElementReferenceException.class);

        wait.set((WebDriverWait) new WebDriverWait(
                Driver.getDriver(),
                Duration.ofSeconds(maxWaitTime),
                Duration.ofMillis(getDefaultPollingInterval())
        ).ignoreAll(ignoredExceptions));

        setWaitTime(WaitTime.EXPLICIT_WAIT_TIME, String.valueOf(maxWaitTime));
    }

    public static void setDefaultWait() {
        if (getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) != null) {
            setWaitTime(WaitTime.EXPLICIT_WAIT_TIME, null);
            removeWait();
        }

        if (wait.get() == null) {
            List<Class<? extends Throwable>> ignoredExceptions = new ArrayList<>();
            ignoredExceptions.add(StaleElementReferenceException.class);

            wait.set((WebDriverWait) new WebDriverWait(
                    Driver.getDriver(),
                    Duration.ofSeconds(getDefaultWaitTime()),
                    Duration.ofMillis(getDefaultPollingInterval())
            ).ignoreAll(ignoredExceptions));
        }
    }

    public static WebDriverWait getBackgroundMaxWait() {
        if (backgroundWait.get() == null) {
            setActivityWait();
        }
        return backgroundWait.get();
    }

    private static int getDefaultWaitTime() {
        String defaultWaitTime = getWaitTime(WaitTime.DEFAULT_WAIT_TIME);
        if (defaultWaitTime == null) {
            setWaitTime(WaitTime.DEFAULT_WAIT_TIME, "5");
        }
        return Integer.parseInt(getWaitTime(WaitTime.DEFAULT_WAIT_TIME));
    }

    private static void setActivityWait() {
        String activityWaitTime = getWaitTime(WaitTime.ACTIVITY_WAIT_TIME);
        if (activityWaitTime == null) {
            setWaitTime(WaitTime.ACTIVITY_WAIT_TIME, "5");
        }
        if (backgroundWait.get() == null) {
            List<Class<? extends Throwable>> ignoredExceptions = new ArrayList<>();
            ignoredExceptions.add(StaleElementReferenceException.class);

            backgroundWait.set((WebDriverWait) new WebDriverWait(
                    Driver.getDriver(),
                    Duration.ofSeconds(Integer.parseInt(getWaitTime(WaitTime.ACTIVITY_WAIT_TIME))),
                    Duration.ofMillis(getDefaultPollingInterval())
            ).ignoreAll(ignoredExceptions));
        }
    }

    private static int getDefaultPollingInterval() {
        String defaultPollingInterval = getWaitTime(WaitTime.POLLING_INTERVAL);
        if (defaultPollingInterval == null) {
            setWaitTime(WaitTime.POLLING_INTERVAL, String.valueOf(Speed.FIVE.getSpeed()));
        }
        return Integer.parseInt(getWaitTime(WaitTime.POLLING_INTERVAL));
    }

    public static void removeWait() {
        wait.remove();
        backgroundWait.remove();
    }

}
