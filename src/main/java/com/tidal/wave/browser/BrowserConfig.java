package com.tidal.wave.browser;

import com.tidal.wave.data.DataCleaner;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.wait.Activity;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.time.Duration;
import java.util.HashMap;

import static com.tidal.wave.data.GlobalData.addData;
import static com.tidal.wave.data.WaitTimeData.setWaitTime;

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

    public BrowserConfig pageBackGroundActivity(Activity activity, boolean enable){
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

    private void setOptionsAndCreateNewDriver(String url){
        setExplicitWaitTime();
        storePageBackGroundControlOptions();
        driver.create(browserType, options).navigateTo(url);
    }

    private void setExplicitWaitTime(){
        if(explicitWait != null){
            setWaitTime(WaitTime.DEFAULT_WAIT_TIME, String.valueOf(explicitWait.getSeconds()));
        }
    }

    public void storePageBackGroundControlOptions(){
        if(!activityMap.isEmpty()){
            activityMap.forEach((k, v) -> addData(k.getActivityType(), String.valueOf(v)));
        }
    }


}


