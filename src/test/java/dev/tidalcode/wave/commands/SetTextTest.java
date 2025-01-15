package dev.tidalcode.wave.commands;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static dev.tidalcode.wave.webelement.ElementFinder.find;

public class SetTextTest {


    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options)
                .withWaitTime(Duration.ofSeconds(6))
                .open("file://" + Finder.findFilePath("components/textinput/textinput.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void inputUsingSetText() {
        String textInputLocator = "id:myText1";
        find(textInputLocator).click().clear().setText("Tidal-Wave");
        String buttonLocator = "id:text_submit_button";
        find(buttonLocator).click();

        String textAfter = find("id:result").getText();
        Assert.assertEquals("", "Tidal-Wave QA Team", textAfter);
    }

    @Test
    public void inputUsingSetTextWithoutValue() {
        String textInputLocator = "id:myText4";
        find(textInputLocator).click().clear().setText("Extra");
        String buttonLocator = "id:text_submit_button";
        find(buttonLocator).click();

        String textAfter = find("id:result").getText();
        Assert.assertEquals("", "Tidal Wave QA Team Extra", textAfter);
    }

    @Test(expected = RuntimeException.class)
    public void inputUsingSetTextShouldFail() {
        find("id:myText1").waitFor(6).click().clear().setText("Tidal-Wave" + Keys.TAB);
    }
}
