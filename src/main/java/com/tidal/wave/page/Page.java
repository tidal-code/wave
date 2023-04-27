package com.tidal.wave.page;

import com.tidal.wave.browser.Driver;
import com.tidal.wave.wait.Wait;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Page {

    private static final Logger logger = LoggerFactory.getLogger(Page.class);

    private Page() {
    }

    public static void navigateTo(String url){
        Driver.getDriver().navigate().to(url);
    }

    public static void refresh() {
        Driver.getDriver().navigate().refresh();
    }

    public static String title() {
        return Driver.getDriver().getTitle();
    }

    public static String currentUrl() {
        return Driver.getDriver().getCurrentUrl();
    }

    public static Set<String> getWindowHandles() {
        return Driver.getDriver().getWindowHandles();
    }

    public static String getWindowHandle() {
        return Driver.getDriver().getWindowHandle();
    }

    public static String getPageSource() {
        return Driver.getDriver().getPageSource();
    }

    /**
     * Method to switch to an open alert. It will wait for the default wait time for the alert to appear.
     *
     * @return Instance of the Alert
     */
    public static Alert switchToAlert() {
        WebDriverWait wait = Wait.getWait();
        wait.until(ExpectedConditions.alertIsPresent());
        return Driver.getDriver().switchTo().alert();
    }

    /**
     * Method to dismiss the alert. It will wait for the default wait time for the alert to appear and then will perform the cancel/dismiss action.
     * Equivalent to pressing the cancel button on the alert.
     */
    public static void dismissAlert() {
        switchToAlert().dismiss();
    }


    /**
     * Method to accept the alert. It will wait for the default wait time for the alert to appear.
     * It is the same as clicking on the OK button on the alert.
     */
    public static void acceptAlert() {
        switchToAlert().accept();
    }

    /**
     * Method to send text to the alert. It will wait for the default wait time for the alert to appear.
     *
     * @param text Text to be sent to the alert
     * @return Instance of the Alert
     */
    public static Alert sendTextToAlert(String text) {
        Alert alert = switchToAlert();
        alert.sendKeys(text);
        return alert;
    }

    /**
     * Method to grad the text value of an alert.
     *
     * @return Text value of the alert.
     */
    public static String getAlertText() {
        return switchToAlert().getText();
    }

    /**
     * Method to get the browser console logs as a list.
     *
     * @return A list of log entries
     */
    public static List<String> getConsoleLog() {
        LogEntries logEntries = Driver.getDriver().manage().logs().get(LogType.BROWSER);
        List<String> consoleLog = new ArrayList<>();
        for (LogEntry entry : logEntries) {
            logger.info(entry.getLevel() + " " + entry.getMessage());

            consoleLog.add(entry.getMessage());
        }
        return consoleLog;
    }

    /**
     * Method to get the browser console logs as log entries.
     *
     * @return Browser console log entries
     */
    public static LogEntries getConsoleLogEntries() {
        return Driver.getDriver().manage().logs().get(LogType.BROWSER);
    }

    /**
     * Method to retrieve the desired capabilities
     *
     * @return Desired capability
     */
    public static String getCapability(String capability) {
        Capabilities caps = ((RemoteWebDriver) Driver.getDriver()).getCapabilities();
        if (caps.asMap().get(capability) != null) {
            return caps.asMap().get(capability).toString();
        }
        return String.format("No such capability existing/set for the given %s browser", StringUtils.capitalize(caps.asMap().get("browserName").toString()));
    }


    public static Map<String, Object> getCapabilities() {
        return ((RemoteWebDriver) Driver.getDriver()).getCapabilities().asMap();
    }
}
