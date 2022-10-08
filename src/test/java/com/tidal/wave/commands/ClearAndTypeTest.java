package com.tidal.wave.commands;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.webelement.ElementFinder.find;


public class ClearAndTypeTest {


    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        Browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/textinput/textinput.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void clearAndTypeTest() {
        String textInputLocator = "id:myText1";
        find(textInputLocator).setText("ProvaTest");
        Assert.assertEquals("ProvaTest", find(textInputLocator).getText());

        find(textInputLocator).clearAndType("Hello World");
        Assert.assertEquals("Hello World", find(textInputLocator).getText());
    }
}
