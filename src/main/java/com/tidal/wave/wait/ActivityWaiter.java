package com.tidal.wave.wait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

public class ActivityWaiter {

    private final Logger logger = LogManager.getLogger(ActivityWaiter.class);

    public void waitAllRequest(WebDriver driver, WebDriverWait wait) {
        waitUntilDocReady(driver, wait);
        waitUntilJQueryReady(driver, wait);
        waitUntilAngularReady(driver, wait);
        waitUntilAngular5Ready(driver, wait);
    }

    public void waitUntilDocReady(WebDriver driver, WebDriverWait wait) {
        try {
            Function<WebDriver, Boolean> jsLoad = d -> ((JavascriptExecutor) d).executeScript("return document.readyState").toString().equals("complete");

            boolean jsReady = ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");

            if (!jsReady) {
                logger.warn("Document not in ready state, Waiting for background process to be completed");
                wait.until(jsLoad);
            }
        } catch (WebDriverException e) {
            logger.error(e.getMessage());
        }
    }

    public void waitUntilJQueryReady(WebDriver driver, WebDriverWait wait) {
        boolean jQueryDefined = (boolean) ((JavascriptExecutor) driver).executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined) {
            waitForJQueryLoad(driver, wait);
        }
    }

    private void waitForJQueryLoad(WebDriver driver, WebDriverWait wait) {
        Function<WebDriver, Boolean> jQueryLoad = d -> ((Long) ((JavascriptExecutor) d).executeScript("return jQuery.active") == 0);
        boolean jqueryReady = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active==0");

        if (!jqueryReady) {
            logger.warn("Document not in ready state, Waiting for JQUERY loading to finish");
            wait.until(jQueryLoad);
        }
    }

    public void waitUntilAngularReady(WebDriver driver, WebDriverWait wait) {
        JavascriptExecutor jsExecDriver = (JavascriptExecutor) driver;
        try {
            boolean angularUnDefined = (boolean) jsExecDriver.executeScript("return window.angular === undefined");
            if (!angularUnDefined) {
                logger.warn("Document not in ready state, Waiting for Angular Activities to finish");
                boolean angularInjectorUnDefined = (boolean) jsExecDriver.executeScript("return angular.element(document).injector() === undefined");
                if (!angularInjectorUnDefined) {
                    waitForAngularLoad(driver, wait);
                }
            }
        } catch (WebDriverException e) {
            logger.error(e.getMessage());
        }
    }

    private void waitForAngularLoad(WebDriver driver, WebDriverWait wait) {
        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
        angularLoads(angularReadyScript, driver, wait);
    }

    public void waitUntilAngular5Ready(WebDriver driver, WebDriverWait wait) {
        JavascriptExecutor jsExecDriver = (JavascriptExecutor) driver;
        try {
            Object angular5Check = jsExecDriver.executeScript("return getAllAngularRootElements()[0].attributes['ng-version']");
            if (angular5Check != null) {
                logger.warn("Document not in ready state, Waiting for Angular5 activities to finish");
                boolean angularPageLoaded = (boolean) jsExecDriver.executeScript("return window.getAllAngularTestabilities().findIndex(x=>!x.isStable()) === -1");
                if (!angularPageLoaded) {
                    waitForAngular5Load(driver, wait);
                }
            }
        } catch (WebDriverException e) {
            logger.error(e.getMessage());
        }
    }

    private void waitForAngular5Load(WebDriver driver, WebDriverWait wait) {
        String angularReadyScript = "return window.getAllAngularTestabilities().findIndex(x=>!x.isStable()) === -1";
        angularLoads(angularReadyScript, driver, wait);
    }

    private void angularLoads(String angularReadyScript, WebDriver driver, WebDriverWait wait) {
        try {
            Function<WebDriver, Boolean> angularLoad = d -> Boolean.valueOf(((JavascriptExecutor) d).executeScript(angularReadyScript).toString());
            boolean angularReady = Boolean.parseBoolean(((JavascriptExecutor) driver).executeScript(angularReadyScript).toString());

            if (!angularReady) {
                wait.until(angularLoad);
            }
        } catch (WebDriverException e) {
            logger.error(e.getMessage());
        }
    }

}