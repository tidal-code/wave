package com.tidal.wave.commands;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.browser.Browser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.webelement.ElementFinder.find;


public class UnCheckTest {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Browser.withOptions(options)
                .open("file://" + Finder.findFilePath("components/checkbox/checkbox.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    /*
    Testing an unchecked checkbox.
    The uncheck function should not check it again.
     */
    @Test
    public void checkboxUncheckTest() {
        boolean result = find("id:test_checkbox_id").unCheck().isSelected();
        Assert.assertFalse("Radio button is not unchecked", result);
    }

    /*
    Testing an unchecked checkbox.
    The uncheck function should check it now.
     */
    @Test
    public void checkboxAlreadyCheckedUncheckTest() {
        boolean result = find("id:checked_checkbox").unCheck().isSelected();
        Assert.assertFalse("Radio button is not checked", result);
    }
}
