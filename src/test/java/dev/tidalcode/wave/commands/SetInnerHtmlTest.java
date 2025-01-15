package dev.tidalcode.wave.commands;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static dev.tidalcode.wave.webelement.ElementFinder.find;


public class SetInnerHtmlTest {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Browser.withOptions(options)
                .withWaitTime(Duration.ofSeconds(7))
                .open("file://" + Finder.findFilePath("components/textinput/textinput.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void textInputText() {
        String textInputLocator = "id:innerHtmlP";
        find(textInputLocator).setInnerHtml("Tidal-Wave");
        Assert.assertEquals("Tidal-Wave", find(textInputLocator).getText());
    }

}
