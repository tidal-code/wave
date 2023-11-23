package dev.tidalcode.wave.webelement;

import dev.tidalcode.wave.browser.Driver;
import dev.tidalcode.wave.wait.Wait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.util.Objects;

public class ElementFinder<T> {

    // Class not to be instantiated.
    private ElementFinder() {
    }

    /**
     * Sets the locator to be found for ui actions
     * Warning: This method is just a collector and no element finding would happen unless an action method is called
     *
     * @param locator is a string to be passed that would be parsed into a By locator
     * @return Instance of the UIElement
     */
    public static UIElement find(String locator) {
        Objects.requireNonNull(locator, "Locator needs to be set to find an element");
        return new UIActions().setProperties(locator).withDefaultWait();
    }

    /**
     * Sets the locator to be found for ui actions
     * Warning: This method is just a collector and no element finding would happen unless an action method is called
     *
     * @param relativeBy the relative locator type implemented from Selenium-4 onwards
     * @return A WebElement that is different from other instances of 'find' methods
     */
    public static WebElement find(RelativeLocator.RelativeBy relativeBy) {
        Objects.requireNonNull(relativeBy, "Locator needs to be set to find an element");
        Wait.setDefaultWait();
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
        Objects.requireNonNull(locator, "Locator needs to be set to find an element");
        return new UIElements().setProperties(locator).withDefaultWait();
    }

}
