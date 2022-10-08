package com.tidal.wave.commands;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.conditions.Condition.exactText;
import static com.tidal.wave.webelement.ElementFinder.find;


public class ClearTest {


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
    public void textInputText() {
        String textInputLocator = "id:myText1";
        find(textInputLocator).setText("ProvaTest");
        Assert.assertEquals("ProvaTest", find(textInputLocator).getText());

        find(textInputLocator).clear();
        Assert.assertEquals("", find(textInputLocator).getText());
    }

    @Test
    public void testLocatorStringBuilderContains() {
        find("input.contains.data-id.dataid123").setText("ProvaTest");
        find("input.contains.data-id.dataid123").shouldHave(exactText("ProvaTest"));
    }

    @Test
    public void testLocatorStringBuilderWith() {
        find("input.with.id.myText1").setText("ProvaTest");
        find("input.with.id.myText1").shouldHave(exactText("ProvaTest"));
    }

}
