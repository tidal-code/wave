package com.tidal.wave.webelement;

import com.tidal.wave.browser.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class IframeNewHandler {

    public static boolean switchToIframeOfElement(By locator, boolean visibility) {
        WebDriver driver = Driver.getDriver();
        if (driver.findElements(locator).size() > 0) {
            return true;
        }
        return iframeIterator(locator);
    }

    private static boolean iframeIterator(By locator) {
        WebDriver driver = Driver.getDriver();
        List<WebElement> iframes = driver.findElements(By.xpath("//iframe"));
        for (WebElement iframe : iframes) {
            driver.switchTo().frame(iframe);
            if (driver.findElements(locator).size() > 0) {
                return true;
            } else {
                iframeIterator(locator);
            }
        }
        driver.switchTo().parentFrame();
        return false;
    }

}
