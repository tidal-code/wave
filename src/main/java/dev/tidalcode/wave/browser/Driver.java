package dev.tidalcode.wave.browser;

import dev.tidalcode.wave.data.Store;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Driver {
    private final Logger logger = LoggerFactory.getLogger(Driver.class);

    private DriverCommand driverCommand;

    public DriverCommand create(String browserType, AbstractDriverOptions<?> options) {
        WebDriver webDriver = getDriver();
        if (null == webDriver) {
            webDriver = new BrowserFactory().getBrowser(browserType, options);
        }
        Store.objectType("Driver", webDriver);
        driverCommand = new DriverCommand(webDriver);
        return driverCommand;
    }

    public void close() {
        logger.info("Quitting Driver");
        Objects.requireNonNull(driverCommand, "Attempting to close a browser which was not initiated or already closed");
        driverCommand.closeDriver();
    }

    public static WebDriver getDriver() {
        return (WebDriver) Store.getTypeObject("Driver");
    }
}
