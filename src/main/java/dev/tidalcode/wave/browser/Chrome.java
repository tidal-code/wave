package dev.tidalcode.wave.browser;

import dev.tidalcode.wave.config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class Chrome {

    private static final Logger logger = LoggerFactory.getLogger(Chrome.class);

    public WebDriver getDriver(ChromeOptions options) {
        logger.info("Test Starting with Chrome Browser");

        if(Config.DRIVER_MANAGER){
            logger.info("Browser (Chrome) setup completed by WebDriver Manager");
            WebDriverManager.chromedriver().setup();
        }

        if (options == null) {
            return new ChromeDriver();
        }
        return new ChromeDriver(options);
    }
}
