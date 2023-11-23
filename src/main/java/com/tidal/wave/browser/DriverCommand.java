package com.tidal.wave.browser;

import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverCommand {

    private final WebDriver webDriver;

    private static final Logger logger = LoggerFactory.getLogger(DriverCommand.class);

    public DriverCommand(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void closeDriver() {
        webDriver.close();
        webDriver.quit();
    }

    public void navigateTo(String url) {
        try {
            webDriver.navigate().to(url);
        } catch (NoSuchSessionException | UnreachableBrowserException e) {
            logger.warn("After a previous run, WebDriver may not have closed.");
            throw new WebDriverException("Failed to connect to browser: " + e.getMessage());
        }
    }
}
