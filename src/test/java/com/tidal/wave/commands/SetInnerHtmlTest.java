package com.tidal.wave.commands;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static com.tidal.wave.webelement.ElementFinder.find;


public class SetInnerHtmlTest {

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        
        Browser.withOptions(chromeOptions)
                .withWaitTime(Duration.ofSeconds(7))
                .open("file://" + Finder.findFilePath("components/textinput/textinput.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void textInputText() {
        String textInputLocator ="id:innerHtmlP";
        find(textInputLocator).setInnerHtml("Watercare");
        Assert.assertEquals(find(textInputLocator).getText(), "Watercare");
    }

}
