package com.tidal.wave.webelement;

import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.data.ElementData;
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

import static com.tidal.wave.data.GlobalData.getData;
import static com.tidal.wave.data.WaitTimeData.getWaitTime;


public class Element {

    private static final Logger logger = LoggerFactory.getLogger(Element.class);

    private TimeCounter timeCounter;

    public static WebElement element(By byLocator) {
        return element(byLocator, true);
    }

    public static WebElement element(By byLocator, boolean value) {
        return new FindWebElement().webElement(byLocator, value);
    }


    public WebElement getElement(List<By> locatorSet, boolean visibility, boolean isMultiple) {
        final int duration = Integer.parseInt(getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME) : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME));

        if (timeCounter == null) {
            timeCounter = new TimeCounter();
        }

        WebElement element;
        try {
            element = getWebElement(locatorSet, visibility, isMultiple);

            if (!timeCounter.timeElapsed(Duration.ofSeconds(duration))) {
                if (movementDetected(element)) {
                    logger.info("Element not stable, movement detected, retrying to find the element");
                    element = getElement(locatorSet, visibility, isMultiple);
                }
            }

            timeCounter = null;
        } catch (WebDriverException e) {
            logger.error(e.getMessage());
            ThreadSleep.forMilliS(500);
            if (timeCounter.timeElapsed(Duration.ofSeconds(duration))) {
                throw e;
            }
            element = getElement(locatorSet, visibility, isMultiple);
        }
        return element;
    }

    private boolean movementDetected(WebElement element){
        Point one = element.getLocation();
        ThreadSleep.forMilliS(100);
        Point two = element.getLocation();

        int xCordDiff = one.getX() - two.getX();
        int yCordDiff = one.getY() - two.getY();

        return xCordDiff != 0 || yCordDiff != 0;
    }

    private WebElement getWebElement(List<By> locators, boolean visibility, boolean isMultiple){
        WebElement element;
        if (locators.size() > 1) {
            element = getWebElementFromSet(locators, visibility, isMultiple);
        } else if (isMultiple) {
            element = new FindWebElement().webElements(locators.get(0), Integer.parseInt(getData(ElementData.INDEX)), visibility);
        } else if (locators.size() == 1) {
            element = new FindWebElement().webElement(locators.get(0), visibility);
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
     * @param locators list of locators
     * @param visibility decides to find a visible or hidden element
     * @param isMultiple decides to find an element from a list of elements
     * @return WebElement
     */
    private WebElement getWebElementFromSet(List<By> locators, boolean visibility, boolean isMultiple){
        WebElement element = new FindWebElement().webElement(locators.get(0), visibility);

        for (int i = 1; i < locators.size() - 1; i++) {
            element = element.findElement(locators.get(i));
        }
        if (isMultiple) {
            element = element.findElements(locators.get(locators.size() - 1)).get(Integer.parseInt(getData(ElementData.INDEX)));
        } else {
            element = element.findElement(locators.get(locators.size() - 1));
        }
        return element;
    }


    public List<WebElement> getElements(List<By> locatorSet, boolean visibility) {

        int duration = Integer.parseInt(getWaitTime(WaitTime.DEFAULT_WAIT_TIME));

        if (timeCounter == null) {
            timeCounter = new TimeCounter();
        }
        List<WebElement> elements;
        WebElement element;

        try {
            if (locatorSet.size() > 1) {
                List<By> locators = new ArrayList<>(locatorSet);
                element = new FindWebElement().webElement(locators.get(0), visibility);

                for (int i = 1; i < locators.size() - 1; i++) {
                    element = element.findElement(locators.get(i));
                }
                elements = element.findElements(locators.get(locators.size() - 1));
            } else {
                elements = new FindWebElement().webElements(locatorSet.get(0), visibility);
            }
        } catch (WebDriverException e) {
            logger.error(e.getMessage());

            ThreadSleep.forMilliS(500);
            if (timeCounter.timeElapsed(Duration.ofSeconds(duration))) {
                throw e;
            }
            elements = getElements(locatorSet, visibility);
        }

        timeCounter = null;
        return elements;
    }

    public List<WebElement> getElements(List<By> locatorSet) {
        return getElements(locatorSet, false);
    }
}
