package com.tidal.wave.browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class Firefox {
    private static final Logger logger = LoggerFactory.getLogger(Firefox.class);
    public WebDriver getDriver(FirefoxOptions options) {
        logger.info("Test Starting with Firefox Browser");

        WebDriverManager.firefoxdriver().setup();
        if(options == null){
            return new FirefoxDriver();
        }
        return new FirefoxDriver(options);
    }
}
