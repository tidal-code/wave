package dev.tidalcode.wave.browser;

import dev.tidalcode.wave.data.DataCleaner;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.data.WaitTimeData;
import dev.tidalcode.wave.wait.Activity;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.time.Duration;
import java.util.HashMap;

import static com.tidal.utils.data.GlobalData.addData;

public final class BrowserConfig {

    private String browserType;
    private AbstractDriverOptions<?> options;
    private Duration explicitWait;

    private final HashMap<Activity, Boolean> activityMap = new HashMap<>();
    private final Driver driver = new Driver();

    public BrowserConfig type(String browserType) {
        this.browserType = browserType;
        return this;
    }

    public BrowserConfig type(BrowserTypes browserType) {
        this.browserType = browserType.getBrowserName();
        return this;
    }

    public BrowserConfig withWaitTime(Duration explicitWait) {
        this.explicitWait = explicitWait;
        return this;
    }

    public BrowserConfig pageBackGroundActivity(Activity activity, boolean enable) {
        activityMap.put(activity, enable);
        return this;
    }

    public BrowserConfig withOptions(AbstractDriverOptions<?> options) {
        this.options = options;
        return this;
    }

    public void open(String url) {
        setOptionsAndCreateNewDriver(url);
    }

    public void close() {
        try {
            driver.close();
        } finally {
            DataCleaner.cleanData();
        }
    }

    private void setOptionsAndCreateNewDriver(String url) {
        setExplicitWaitTime();
        storePageBackGroundControlOptions();
        driver.create(browserType, options).navigateTo(url);
    }

    private void setExplicitWaitTime() {
        if (explicitWait != null) {
            WaitTimeData.setWaitTime(WaitTime.DEFAULT_WAIT_TIME, String.valueOf(explicitWait.getSeconds()));
        }
    }

    private void storePageBackGroundControlOptions() {
        if (!activityMap.isEmpty()) {
            activityMap.forEach((k, v) -> addData(k.getActivityType(), String.valueOf(v)));
        }
    }
}


