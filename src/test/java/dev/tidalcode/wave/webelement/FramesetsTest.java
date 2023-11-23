package dev.tidalcode.wave.webelement;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static dev.tidalcode.wave.webelement.ElementFinder.find;

public class FramesetsTest {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withWaitTime(Duration.ofSeconds(6))
                .withOptions(options)
                .open("file://" + Finder.findFilePath("framesets/index.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void findElementFromRelatedFrame() {
        find("h2 with text 'Related Articles'").getText();
    }

    @Test
    public void findElementFromContentFrame() {
        find("h1 with text 'Welcome to Our Website'").getText();
    }

    @Test
    public void findInnerFrameTest() {
        //this is from the menu
        find("linkText:Services").getText();
    }
}
