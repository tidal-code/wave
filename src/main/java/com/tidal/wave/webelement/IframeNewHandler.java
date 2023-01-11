package com.tidal.wave.webelement;

import com.tidal.wave.browser.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class IframeNewHandler {

    public static boolean switchToIframeOfElement(By locator, boolean visibility) {
        WebDriver driver = Driver.getDriver();
        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            return checkVisibleCondition(elements, visibility);
        }
        return iframeIterator(locator, visibility);
    }

    private static boolean iframeIterator(By locator, boolean visibility) {
        WebDriver driver = Driver.getDriver();
        List<WebElement> iframes = driver.findElements(By.xpath("//iframe"));
        for (WebElement iframe : iframes) {
            driver.switchTo().frame(iframe);
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                return checkVisibleCondition(elements, visibility);
            } else {
                iframeIterator(locator, visibility);
            }
        }
        driver.switchTo().parentFrame();
        return false;
    }

    private static boolean checkVisibleCondition(List<WebElement> elements, boolean visibility){
            if (visibility) {
                return findDisplayedElement(elements);
            } else {
                return true;
            }
    }

    private static boolean findDisplayedElement(List<WebElement> elements) {
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                return true;
            }
        }
        return false;
    }

}
