package com.tidal.wave.expectations;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.browser.Browser;
import com.tidal.wave.exceptions.ExpectationFailure;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.expectations.Expectation.*;
import static com.tidal.wave.webelement.ElementFinder.find;


public class ExpectationCustomMessageTest {

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
    public void testSoftAssertionInvisibleElement() {
        try {
            find("#invisibleElement").expecting(toBeVisible).orElseFail("The element expected to be visible, but failed to fulfill the condition");
        } catch (ExpectationFailure e) {
            Assert.assertEquals(e.getMessage(), "The element expected to be visible, but failed to fulfill the condition");
        }
    }

    @Test
    public void testSoftAssertionElementToBePresent() {
        try {
            find("#invisibleElement").expecting(toBePresent).orElseFail("The element expected to be present, but failed to fulfill the condition");
        } catch (ExpectationFailure e) {
            Assert.assertEquals(e.getMessage(), "The element expected to be present, but failed to fulfill the condition");
        }
    }

    @Test
    public void testElementToBeInteractable() {
        try {
            find("#invisibleElement").invisibleElement().expecting(toBeInteractable).orElseFail("The element expected to be interactable, but failed to fulfill the condition");
        } catch (ExpectationFailure e) {
            Assert.assertEquals(e.getMessage(), "The element expected to be interactable, but failed to fulfill the condition");
        }
    }
}
