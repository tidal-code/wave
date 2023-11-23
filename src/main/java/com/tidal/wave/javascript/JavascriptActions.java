package com.tidal.wave.javascript;

import com.tidal.wave.browser.Driver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JavascriptActions {
    //So as not to instantiate
    private JavascriptActions() {
    }

    public static void execute(String jsCode, WebElement... byLocators) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript(jsCode, (Object[]) byLocators);
    }
}
