package com.tidal.wave.shadowdom;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.browser.Driver;
import com.tidal.wave.filehandlers.Finder;
import com.tidal.wave.wait.ThreadSleep;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static com.tidal.wave.webelement.ElementFinder.find;

public class ShadowDomTest {
    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setHeadless(true);
        Browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/nestedshadowdom/index.html"));
    }

    @After
    public void terminate() {
//        Browser.close();
    }

    @Test
    public void testShadowDom(){
        ThreadSleep.forSeconds(2);
        WebDriver driver = Driver.getDriver();
        List<WebElement> shadowRoots = driver.findElements(By.cssSelector("* /deep/ *"));
        System.out.println(shadowRoots.size());
    }
}
