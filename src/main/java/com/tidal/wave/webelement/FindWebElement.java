package com.tidal.wave.webelement;

import com.tidal.utils.loggers.LoggerUtil;
import com.tidal.wave.browser.Driver;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.data.WaitTimeData;
import com.tidal.wave.exceptions.IterationStopper;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.Activity;
import com.tidal.wave.wait.ActivityWaiter;
import com.tidal.wave.wait.Wait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

import static com.tidal.utils.data.GlobalData.getData;
import static com.tidal.utils.loggers.LoggerUtil.getLogger;


public class FindWebElement extends IframeIterator {

    private static final LoggerUtil log = getLogger(FindWebElement.class);

    private final ActivityWaiter activityWaiter = (ActivityWaiter) ObjectSupplier.instanceOf(ActivityWaiter.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait backgroundActivityWait;

    public WebElement webElement(By locator) {
        return webElement(locator, true);
    }

    public WebElement webElement(By locator, boolean ensureVisibilityOfElement) {
        wait = Wait.getWait();
        driver = Driver.getDriver();
        backgroundActivityWait = Wait.getBackgroundMaxWait();

        if (getBGCheck()) {
            waitForPageLoad(driver, backgroundActivityWait);
        }

        log.elementsLog("Searching for element " + locator);

        findFrameOfElement(locator, ensureVisibilityOfElement);

        log.elementsLog("Found Element " + locator);
        return foundElement(driver);
    }

    public WebElement webElements(By locator, int index, boolean visibility) {
        wait = Wait.getWait();
        driver = Driver.getDriver();
        backgroundActivityWait = Wait.getBackgroundMaxWait();

        if (getBGCheck()) {
            waitForPageLoad(driver, backgroundActivityWait);
        }

        log.elementsLog("Searching for " + (index + 1) + " elements with locator " + locator);

        try {
            switchToIframeOfElement(locator, visibility);
        } catch (IterationStopper ignored) { //Iteration Exception is thrown when an element is found
            //ignored
        }

        try {
            wait.until(d -> (d.findElements(locator).size() - 1 >= index));
        } catch (TimeoutException e) {
            throw new TimeoutException(
                    String.format("Expected %d elements, but found only %d element[s] in %d seconds",
                            index + 1,
                            driver.findElements(locator).size(),
                            getWaitTime()));
        }

        List<WebElement> elements = wait.until(d -> d.findElements(locator));

        if (elements.size() - 1 >= index) {
            return elements.get(index);
        } else {
            throw new TimeoutException(String.format("Expected %d elements, but found only %d element[s]", index + 1, elements.size()));
        }
    }

    public List<WebElement> webElements(By locator, boolean visibility) {
        wait = Wait.getWait();
        driver = Driver.getDriver();
        backgroundActivityWait = Wait.getBackgroundMaxWait();

        log.elementsLog("Searching for elements " + locator);

        if (getBGCheck()) {
            waitForPageLoad(driver, backgroundActivityWait);
        }

        try {
            switchToIframeOfElement(locator, visibility);
        } catch (IterationStopper ignored) { //Iteration Exception is thrown when an element is found
            //ignored
        }

        List<WebElement> elements = wait.until(d -> d.findElements(locator));

        if (!elements.isEmpty()) {
            log.elementsLog("Elements found " + locator + " is [" + elements.size() + "]");
        } else {
            log.elementsLog("No elements found " + locator);
        }
        return elements;
    }

    private void findFrameOfElement(By locator, boolean visibility) {

        Function<WebDriver, Boolean> findElement = d -> {
            if (getBGCheck()) {
                waitForPageLoad(d, backgroundActivityWait);
            }
            try {
                return switchToIframeOfElement(locator, visibility);
            } catch (IterationStopper ignored) { //Iteration Exception is thrown when an element is found
                return true;
            }
        };

        try {
            wait.until(findElement);
        } catch (TimeoutException e) {
            throw new TimeoutException(String.format("Element not found using %s in %d seconds \n %s", locator, getWaitTime(), e.getMessage()));
        }
    }


    private void waitForPageLoad(WebDriver driver, WebDriverWait wait) {
        try {
            activityWaiter.waitUntilDocReady(driver, wait);

            if (getJQueryCheck()) {
                activityWaiter.waitUntilJQueryReady(driver, wait);
            }

            if (getAngularCheck()) {
                activityWaiter.waitUntilAngularReady(driver, wait);
            }

            if (getAngular5Check()) {
                activityWaiter.waitUntilAngular5Ready(driver, wait);
            }

        } catch (Exception e) {
            log.elementsLog("Activity Waiter Exception Occurred");
        }
    }

    private WebElement foundElement(WebDriver driver) {
        WebElement element = contextElement;

        if (element.isDisplayed()) {
            String jsHighLighter = "arguments[0].style.border='1px dotted green'";
            ((JavascriptExecutor) driver).executeScript(jsHighLighter, element);
            return element;
        }
        return element;
    }

    private int getWaitTime() {
        String time = WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? WaitTimeData.getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        return Integer.parseInt(time);
    }

    protected boolean getBGCheck() {
        if (getData(Activity.ALL_ACTIVITIES_CHECK.getActivityType()) == null) {
            return true;
        }
        return Boolean.parseBoolean(getData(Activity.ALL_ACTIVITIES_CHECK.getActivityType()));
    }

    protected boolean getJQueryCheck() {
        if (getData(Activity.JQUERY_LOAD_WAITER.getActivityType()) == null) {
            return false;
        }
        return Boolean.parseBoolean(getData(Activity.JQUERY_LOAD_WAITER.getActivityType()));
    }

    protected boolean getAngularCheck() {
        if (getData(Activity.ANGULAR_CHECK.getActivityType()) == null) {
            return false;
        }
        return Boolean.parseBoolean(getData(Activity.ANGULAR_CHECK.getActivityType()));
    }

    protected boolean getAngular5Check() {
        if (getData(Activity.ANGULAR_5_CHECK.getActivityType()) == null) {
            return false;
        }
        return Boolean.parseBoolean(getData(Activity.ANGULAR_CHECK.getActivityType()));
    }
}
