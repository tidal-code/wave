package dev.tidalcode.wave.retry;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.webelement.ElementFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.retry.RetryCondition.notVisible;
import static dev.tidalcode.wave.verification.criteria.Criteria.visible;
import static dev.tidalcode.wave.webelement.ElementFinder.find;


public class StillNotVisibleRetryTests {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/timeout/notVisible.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void retryTestIfVisible() {
        ElementFinder.find("#textInput2").clear().sendKeys("Retry test").clear().sendKeys("QA").retryIf(notVisible("#buttonId"), 3);
        ElementFinder.find("#buttonId").shouldBe(visible);
    }

}
