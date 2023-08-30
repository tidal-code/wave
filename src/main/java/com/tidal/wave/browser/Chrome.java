package com.tidal.wave.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class Chrome {

    private static final Logger logger = LoggerFactory.getLogger(Chrome.class);

    public WebDriver getDriver(ChromeOptions options) {
        logger.info("Test Starting with Chrome Browser");

        if(options == null){
            return new ChromeDriver();
        }
        return new ChromeDriver(options);
    }
}
