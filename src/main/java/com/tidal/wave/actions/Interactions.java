package com.tidal.wave.actions;

import com.tidal.wave.browser.Driver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import static com.tidal.wave.locator.LocatorMatcher.getMatchedLocator;
import static com.tidal.wave.webelement.Element.element;

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
        actions.click(element(getMatchedLocator(locator)));
        return this;
    }

    public Interactions sendKeys(String locator, CharSequence... keys) {
        actions.sendKeys(element(getMatchedLocator("locator")), keys);
        return this;
    }

    public void build() {
        if (null != actions) {
            actions.build().perform();
            actions = null;
        }
    }

}
