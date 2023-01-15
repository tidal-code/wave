package com.tidal.wave.browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


class Chrome {

    private static final Logger logger = LogManager.getLogger(Chrome.class);

    public WebDriver getDriver(ChromeOptions options) {
        logger.info("Test Starting with Chrome Browser");

        WebDriverManager.chromedriver().setup();
        if(options == null){
            return new ChromeDriver();
        }
        return new ChromeDriver(options);
    }
}
