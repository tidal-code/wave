package dev.tidalcode.wave.retry;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.retry.RetryCondition.stillPresent;
import static dev.tidalcode.wave.verification.criteria.Criteria.notPresent;
import static dev.tidalcode.wave.webelement.ElementFinder.find;


public class StillPresentRetryTests {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/timeout/stillPresent.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void retryTestIfVisible() {
        find("#textInput").clear().sendKeys("Retry test").clear().sendKeys("QA").retryIf(stillPresent, 3);
        find("#textInput").shouldBe(notPresent);
    }

}
