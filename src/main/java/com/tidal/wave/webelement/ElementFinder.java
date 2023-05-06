package com.tidal.wave.webelement;

import com.tidal.wave.browser.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import static com.tidal.wave.wait.Wait.setDefaultWait;

public class ElementFinder {

    // Class not to be instantiated.
    private ElementFinder() {
    }

    /**
     * Sets the locator to be found for ui actions
     * Warning: This method is just a collector and no element finding would happen unless an action method is called
     *
     * @param byLocator is a string to be passed that would be parsed into a By locator
     * @return Instance of the UIElement
     */
    public static UIElement find(String byLocator) {
        return new UIActions().setProperties(byLocator).withDefaultWait();
    }

    /**
     * Sets the locator to be found for ui actions
     * Warning: This method is just a collector and no element finding would happen unless an action method is called
     *
     * @param relativeBy the relative locator type implemented from Selenium-4 onwards
     * @return A WebElement that is different from other instances of 'find' methods
     */
    public static WebElement find(RelativeLocator.RelativeBy relativeBy) {
        setDefaultWait();
        return Driver.getDriver().findElement(relativeBy);
    }

    /**
     * Sets the locator for finding all elements
     * Warning: This method is just a collector and no element finding would happen unless an action method is called
     *
     * @param locator string to be parsed into a By locator
     * @return UIElements instance with properties of a collection
     */
    public static UIElements findAll(String locator) {
        return new UIElements().setProperties(locator).withDefaultWait();
    }

}
