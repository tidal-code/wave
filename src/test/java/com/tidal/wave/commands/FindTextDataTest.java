package com.tidal.wave.commands;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.browser.Browser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.webelement.ElementFinder.find;

public class FindTextDataTest {

    @Before
    public void initialize() {
         ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/elements/texttypes.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }



    @Test
    public void findTextBygetText() {
        String text = find("#pOne").getText();
        Assert.assertEquals("Text Value", text);
    }

    @Test
    public void findTextBygetValue() {
        String text = find("#pTwo").getText();
        Assert.assertEquals(find("#pTwo").getAttribute("value"), text);
    }

    @Test
    public void findTextByInnerHtml() {
        find("#pThree").clickByJS();
        String text = find("#pThree").getText();
        Assert.assertEquals(find("#pThree").getAttribute("innerHTML"), text);
    }

}
