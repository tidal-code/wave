package dev.tidalcode.wave.commands;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import org.junit.*;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.webelement.ElementFinder.find;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClickByJSTest {


    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/button/button.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void testClickByJS() {
        find("#test_button_id").clickByJS();
        assertThat("Automation did not work, button cannot be clicked", find("#test_button_id").getText(), equalTo("Button Clicked"));
    }

    @Test(expected = ComparisonFailure.class)
    public void testJSClickCannotDoubleClick() {
        find("#double_click_button").clickByJS();
        Assert.assertEquals("Button Double Clicked", find("#double_click_button").getText());
    }
}
