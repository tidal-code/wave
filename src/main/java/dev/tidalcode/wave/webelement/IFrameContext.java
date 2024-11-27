package dev.tidalcode.wave.webelement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IFrameContext {
    private By locator;
    private WebDriver driver;
    private boolean checkVisibility;

    public By getLocator() {
        return locator;
    }

    public void setLocator(By locator) {
        this.locator = locator;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isCheckVisibility() {
        return checkVisibility;
    }

    public void setCheckVisibility(boolean checkVisibility) {
        this.checkVisibility = checkVisibility;
    }
}
