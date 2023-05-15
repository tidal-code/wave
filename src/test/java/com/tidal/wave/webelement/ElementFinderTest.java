package com.tidal.wave.webelement;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.browser.Browser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.webelement.ElementFinder.find;
import static com.tidal.wave.webelement.ElementFinder.findAll;

public class ElementFinderTest {

    @Before
    public void testInit(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/elements/elements.html"));
    }

    @Test (expected = NullPointerException.class)
    public void nullPointerCheckFind(){
        String locator = null;
        find(locator).click();
    }

    @Test (expected = NullPointerException.class)
    public void nullPointerCheckFindAll(){
        findAll(null).first().click();
    }

    @After
    public void cleanUp(){
        Browser.close();
    }
}
