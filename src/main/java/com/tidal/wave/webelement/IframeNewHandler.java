package com.tidal.wave.webelement;

import com.tidal.wave.browser.Driver;
import com.tidal.wave.exceptions.IterationStopper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class IframeNewHandler {

    protected WebElement contextElement;

    public boolean switchToIframeOfElement(By locator, boolean visibility) throws IterationStopper {
        WebDriver driver = Driver.getDriver();

        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            return checkVisibleCondition(elements, visibility);
        }

        driver.switchTo().defaultContent();
        return iframeIterator(locator, visibility);
    }

    private boolean iframeIterator(By locator, boolean visibility) throws IterationStopper{
        WebDriver driver = Driver.getDriver();

        List<WebElement> iframes = driver.findElements(By.xpath("//iframe"));
        for (WebElement iframe : iframes) {
            driver.switchTo().frame(iframe);
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty() && checkVisibleCondition(elements, visibility)) {
                throw new IterationStopper("This is a pseudo code line to exit nested iteration");
            }
            else {
                iframeIterator(locator, visibility);
            }
        }
        driver.switchTo().parentFrame();
        return false;
    }

    private boolean checkVisibleCondition(List<WebElement> elements, boolean visibility) {
        if (visibility) {
            return findDisplayedElement(elements);
        } else {
            if(!elements.isEmpty()) {
                contextElement = elements.get(0);
                return true;
            }
        }
        return false;
    }

    private boolean findDisplayedElement(List<WebElement> elements) {
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                contextElement = element;
                return true;
            }
        }
        return false;
    }

}
