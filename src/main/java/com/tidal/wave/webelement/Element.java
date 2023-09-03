package com.tidal.wave.webelement;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.ContextException;
import com.tidal.wave.wait.ThreadSleep;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;
import static com.tidal.wave.locator.LocatorMatcher.getMatchedLocator;


public class Element {

    private static final Logger logger = LoggerFactory.getLogger(Element.class);

    private TimeCounter timeCounter;

    public static WebElement element(By byLocator) {
        return element(byLocator, true);
    }

    public static WebElement element(By byLocator, boolean value) {
        return new FindWebElement().webElement(byLocator, value);
    }


    public WebElement getElement(CommandContext context) {

        final int duration = Integer.parseInt(getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME) : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME));

        if (timeCounter == null) {
            timeCounter = new TimeCounter();
        }

        WebElement element;
        try {
            element = getWebElement(context);

            if (!timeCounter.timeElapsed(Duration.ofSeconds(duration))) {
                if (movementDetected(element)) {
                    logger.info("Element not stable, movement detected, retrying to find the element");
                    element = getElement(context);
                }
            }

            timeCounter = null;
        } catch (WebDriverException e) {
            logger.error(e.getMessage());
            ThreadSleep.forMilliS(500);
            if (timeCounter.timeElapsed(Duration.ofSeconds(duration))) {
                throw e;
            }
            element = getElement(context);
            timeCounter = null;
        }
        return element;
    }

    private boolean movementDetected(WebElement element) {
        Point one = element.getLocation();
        ThreadSleep.forMilliS(100);
        Point two = element.getLocation();

        int xCordDiff = one.getX() - two.getX();
        int yCordDiff = one.getY() - two.getY();

        return xCordDiff != 0 || yCordDiff != 0;
    }

    private WebElement getWebElement(CommandContext context) {
        List<String> locators = context.getLocators();
        boolean visibility = context.getVisibility();
        boolean isMultiple = context.isMultiple();

        WebElement element;
        if (locators.size() > 1) {
            element = getWebElementFromSet(context);
        } else if (isMultiple) {
            element = new FindWebElement().webElements(getMatchedLocator(locators.get(0)), context.getElementIndex(), visibility);
        } else if (locators.size() == 1) {
            element = new FindWebElement().webElement(getMatchedLocator(locators.get(0)), visibility);
        } else {
            throw new ContextException("Locator(s) not set. Set locator context with appropriate function");
        }
        return element;
    }

    /**
     * This method returns a single element depending on conditions.
     * The conditions are designed to mimic Selenium's driver.findElement("locator").findElement("locator").... functionality.
     * The very first element is found first. If the list contains information about more than one locator, it is an indication
     * that there needs to be a chain elements to be found.
     *
     * @return WebElement
     */
    private WebElement getWebElementFromSet(CommandContext context) {

        List<String> locators = context.getLocators();
        boolean visibility = context.getVisibility();
        boolean isMultiple = context.isMultiple();

        WebElement element = new FindWebElement().webElement(getMatchedLocator(locators.get(0)), visibility);

        for (int i = 1; i < locators.size() - 1; i++) {
            element = element.findElement(getMatchedLocator(locators.get(i)));
        }
        if (isMultiple) {
            element = element.findElements(getMatchedLocator(locators.get(locators.size() - 1))).get(context.getElementIndex());
        } else {
            element = element.findElement(getMatchedLocator(locators.get(locators.size() - 1)));
        }
        return element;
    }


    public List<WebElement> getElements(CommandContext context) {

        List<String> locatorSet = context.getLocators();
        boolean visibility = context.getVisibility();

        int duration = Integer.parseInt(getWaitTime(WaitTime.DEFAULT_WAIT_TIME));

        if (timeCounter == null) {
            timeCounter = new TimeCounter();
        }
        List<WebElement> elements;
        WebElement element;

        try {
            if (locatorSet.size() > 1) {
                List<String> locators = new ArrayList<>(locatorSet);
                element = new FindWebElement().webElement(getMatchedLocator(locators.get(0)), visibility);

                for (int i = 1; i < locators.size() - 1; i++) {
                    element = element.findElement(getMatchedLocator(locators.get(i)));
                }
                elements = element.findElements(getMatchedLocator(locators.get(locators.size() - 1)));
            } else {
                elements = new FindWebElement().webElements(getMatchedLocator(locatorSet.get(0)), visibility);
            }
        } catch (WebDriverException e) {
            logger.error(e.getMessage());

            ThreadSleep.forMilliS(500);
            if (timeCounter.timeElapsed(Duration.ofSeconds(duration))) {
                throw e;
            }
            elements = getElements(context);
        }

        timeCounter = null;
        return elements;
    }
}
