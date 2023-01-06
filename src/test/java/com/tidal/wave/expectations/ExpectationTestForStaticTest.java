package com.tidal.wave.expectations;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.filehandlers.Finder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.expectations.Expectation.toBeInvisible;
import static com.tidal.wave.verification.expectations.Expectation.toBeVisible;
import static com.tidal.wave.webelement.ElementFinder.find;

public class ExpectationTestForStaticTest {

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        Browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/elements/softassertelements.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void testVisibleExpectation() {
        find("#visibleElement").expecting(toBeVisible).orElseFail();
        find("#invisibleElement").expecting(toBeInvisible).orElseFail();
    }
}
