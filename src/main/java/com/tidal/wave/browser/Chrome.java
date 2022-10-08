package com.tidal.wave.browser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static io.github.bonigarcia.wdm.WebDriverManager.getInstance;


class Chrome {

    private static final Logger logger = LogManager.getLogger(Chrome.class);

    public WebDriver getDriver(ChromeOptions options) {
        getInstance(ChromeDriver.class).setup();
        if(options == null){
            return new ChromeDriver();
        }
        logger.info("Test Starting with ChromeDriver");
        return new ChromeDriver(options);
    }
}
