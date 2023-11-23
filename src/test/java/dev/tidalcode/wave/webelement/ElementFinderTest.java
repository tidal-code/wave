package dev.tidalcode.wave.webelement;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.webelement.ElementFinder.find;

public class ElementFinderTest {

    @Before
    public void testInit() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/elements/elements.html"));
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerCheckFind() {
        String locator = null;
        ElementFinder.find(locator).click();
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerCheckFindAll() {
        ElementFinder.findAll(null).first().click();
    }

    @After
    public void cleanUp() {
        Browser.close();
    }
}
