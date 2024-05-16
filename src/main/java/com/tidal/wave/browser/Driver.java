package com.tidal.wave.browser;

import com.tidal.wave.data.Store;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Manages the creation and destruction of WebDriver instance. <br>
 * The driver output types would be: <br>
 * -- Chrome <br>
 * -- Firefox <br>
 * -- Edge <br>
 * Safari creates its own browser instance. <br>
 * IE is not supported.
 *
 */
public class Driver {
    private final Logger logger = LoggerFactory.getLogger(Driver.class);

    private DriverCommand driverCommand;

    /**
     * Creates the Driver(Browser) instance with the given parameters.
     * Gets the driver from the stored data if it is already created.
     *
     * @param browserType Choose the browser type.
     * @param options The browser options to be supplied.
     * @return DriverCommand instance to navigate and terminate the browser sessions
     */
    protected DriverCommand create(String browserType, AbstractDriverOptions<?> options) {
        WebDriver webDriver = getDriver();
        if (null == webDriver) {
            webDriver = new BrowserFactory().getBrowser(browserType, options);
        }
        Store.objectType("Driver", webDriver);
        driverCommand = new DriverCommand(webDriver);
        return driverCommand;
    }

    /**
     * Terminates the Driver instance.
     *
     */
    public void close() {
        logger.info("Quitting Driver");
        Objects.requireNonNull(driverCommand, "Driver is null. Attempting to close a browser which was not initiated or already closed?");
        driverCommand.closeDriver();
    }

    /**
     *
     * @return The Driver instance.
     */
    public static WebDriver getDriver() {
        return (WebDriver) Store.getTypeObject("Driver");
    }
}
