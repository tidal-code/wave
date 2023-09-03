package com.tidal.wave.expectations;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.browser.Browser;
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
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/elements/softassertelements.html"));
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
