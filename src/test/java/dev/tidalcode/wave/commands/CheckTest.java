package dev.tidalcode.wave.commands;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.webelement.ElementFinder.find;


public class CheckTest {


    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/checkbox/checkbox.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void checkboxClickTest() {
        boolean result = find("id:test_checkbox_id").check().isSelected();
        Assert.assertTrue("Radio Button is not checked", result);
    }
}
