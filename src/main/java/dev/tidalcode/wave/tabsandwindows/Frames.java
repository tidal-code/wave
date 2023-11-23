package dev.tidalcode.wave.tabsandwindows;

import dev.tidalcode.wave.wait.Wait;
import dev.tidalcode.wave.webelement.Element;
import dev.tidalcode.wave.locator.LocatorMatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Frames {
    private Frames() {
    }

    public static void switchToFrame(String locator) {
        switchToFrame(locator, false);
    }

    public static void switchToFrame(String locator, boolean frameVisibility) {
        WebElement iframeElement = Element.element(LocatorMatcher.getMatchedLocator(locator), frameVisibility);
        ExpectedCondition<WebDriver> availableFrame = ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeElement);
        Wait.getWait().until(availableFrame);
    }
}
