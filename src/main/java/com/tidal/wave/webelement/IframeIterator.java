package com.tidal.wave.webelement;

import com.tidal.wave.browser.Driver;
import com.tidal.wave.exceptions.IterationStopper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class IframeIterator {

    protected WebElement contextElement;

    public boolean switchToIframeOfElement(By locator, boolean visibility) throws IterationStopper {
        WebDriver driver = Driver.getDriver();

        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            return checkVisibleCondition(elements, visibility);
        }

        driver.switchTo().defaultContent();
        return findElementsIframe(locator, visibility);
    }

    private boolean findElementsIframe(By locator, boolean visibility) throws IterationStopper{
        WebDriver driver = Driver.getDriver();

        List<WebElement> iframes = driver.findElements(By.xpath("//iframe"));
        for (WebElement iframe : iframes) {
            driver.switchTo().frame(iframe);
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty() && checkVisibleCondition(elements, visibility)) {
                throw new IterationStopper("This exception is thrown to stop iteration after the element is found. " +
                        "Unless an exception is thrown, the iteration will continue the full cycle. " +
                        "So if this exception is thrown, it means the element is found");
            }
            else {
                findElementsIframe(locator, visibility);
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
