package dev.tidalcode.wave.wait;

import dev.tidalcode.wave.browser.Driver;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.data.WaitTimeData;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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

        WaitTimeData.setWaitTime(WaitTime.EXPLICIT_WAIT_TIME, String.valueOf(maxWaitTime));
    }

    public static void setDefaultWait() {
        if (WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) != null) {
            WaitTimeData.setWaitTime(WaitTime.EXPLICIT_WAIT_TIME, null);
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
        String defaultWaitTime = WaitTimeData.getWaitTime(WaitTime.DEFAULT_WAIT_TIME);
        if (defaultWaitTime == null) {
            WaitTimeData.setWaitTime(WaitTime.DEFAULT_WAIT_TIME, "5");
        }
        return Integer.parseInt(WaitTimeData.getWaitTime(WaitTime.DEFAULT_WAIT_TIME));
    }

    private static void setActivityWait() {
        String activityWaitTime = WaitTimeData.getWaitTime(WaitTime.ACTIVITY_WAIT_TIME);
        if (activityWaitTime == null) {
            WaitTimeData.setWaitTime(WaitTime.ACTIVITY_WAIT_TIME, "5");
        }
        if (backgroundWait.get() == null) {
            List<Class<? extends Throwable>> ignoredExceptions = new ArrayList<>();
            ignoredExceptions.add(StaleElementReferenceException.class);

            backgroundWait.set((WebDriverWait) new WebDriverWait(
                    Driver.getDriver(),
                    Duration.ofSeconds(Integer.parseInt(WaitTimeData.getWaitTime(WaitTime.ACTIVITY_WAIT_TIME))),
                    Duration.ofMillis(getDefaultPollingInterval())
            ).ignoreAll(ignoredExceptions));
        }
    }

    private static int getDefaultPollingInterval() {
        String defaultPollingInterval = WaitTimeData.getWaitTime(WaitTime.POLLING_INTERVAL);
        if (defaultPollingInterval == null) {
            WaitTimeData.setWaitTime(WaitTime.POLLING_INTERVAL, String.valueOf(Speed.FIVE.getSpeed()));
        }
        return Integer.parseInt(WaitTimeData.getWaitTime(WaitTime.POLLING_INTERVAL));
    }

    public static void removeWait() {
        wait.remove();
        backgroundWait.remove();
    }

}
