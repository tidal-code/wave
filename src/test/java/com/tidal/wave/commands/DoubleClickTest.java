package com.tidal.wave.commands;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.filehandlers.Finder;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.webelement.ElementFinder.find;

public class DoubleClickTest {

    @Before
    public void initialize() {
         ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/button/button.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void testForErrorMessage() {
        find("#double_click_button").doubleClick();
        Assert.assertEquals("Button Double Clicked", find("#double_click_button").getText());
    }

    @Test
    public void testDoubleClick() {
        find("#double_click_button").doubleClick();
        Assert.assertEquals("Button Double Clicked", find("#double_click_button").getText());
    }

    @Test(expected = ComparisonFailure.class)
    public void testDoubleClickFail() {
        find("#double_click_button").click();
        Assert.assertEquals("Button Double Clicked", find("#double_click_button").getText());
    }
}
