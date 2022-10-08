package com.tidal.wave.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

import static io.github.bonigarcia.wdm.WebDriverManager.getInstance;

class Opera {
    public WebDriver getDriver(OperaOptions options) {
        getInstance(OperaDriver.class).setup();
        if(options == null){
            return new OperaDriver();
        }
        return new OperaDriver(options);
    }
}
