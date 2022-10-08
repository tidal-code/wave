package com.tidal.wave.browser;

import com.tidal.wave.data.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.util.Objects;

public class Driver {
    private final Logger logger = LogManager.getLogger(Driver.class);

    private DriverCommand driverCommand;

    public DriverCommand create(String browserType, AbstractDriverOptions<?> options){
        WebDriver webDriver = new BrowserFactory().getBrowser(browserType, options);
        Store.objectType("Driver", webDriver);
        driverCommand = new DriverCommand(webDriver);
        return driverCommand;
    }

    public void close(){
        logger.info("Quitting Driver");
        Objects.requireNonNull(driverCommand, "Attempting to close a browser which is not initiated");
        driverCommand.closeDriver();
    }

    public static WebDriver getDriver(){
        return (WebDriver) Store.getTypeObject("Driver");
    }
}
