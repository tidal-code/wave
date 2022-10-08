package com.tidal.wave.commands;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.webelement.ElementFinder.find;

public class SendKeysTest {

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
    public void inputUsingSetText() {
        find("id:myText1").click().clear().setText("Watercare");
        find("id:text_submit_button").click();
        String textAfter = find("id:result").getText();
        Assert.assertEquals("", "Watercare QA Team", textAfter);
    }

    @Test (expected = RuntimeException.class)
    public void inputUsingSetTextShouldFail() {
        String textInputLocator ="id:myText1";
        find(textInputLocator).waitFor(6).click().clear().setText("Watercare" + Keys.TAB);
    }
}
