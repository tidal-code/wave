package com.tidal.wave.actions;

import com.tidal.wave.browser.Driver;
import com.tidal.wave.locator.LocatorMatcher;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import static com.tidal.wave.webelement.Element.element;

/**
 * Provide options to simulate Selenium Action class methods.
 */
public class Interactions {

    private Actions actions;

    public Interactions() {
        actions = new Actions(Driver.getDriver());
    }

    public Interactions keysDown(Keys keys) {
        actions.keyDown(keys);
        return this;
    }

    public Interactions keysUp(Keys keys) {
        actions.keyUp(keys);
        return this;
    }

    public Interactions click(String locator) {
        actions.click(Element.element(LocatorMatcher.getMatchedLocator(locator)));
        return this;
    }

    public Interactions sendKeys(String locator, CharSequence... keys) {
        actions.sendKeys(Element.element(LocatorMatcher.getMatchedLocator("locator")), keys);
        return this;
    }

    public void build() {
        if (null != actions) {
            actions.build().perform();
            actions = null;
        }
    }

}
