package com.tidal.wave.expectations;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.exceptions.ExpectationFailure;
import com.tidal.wave.filehandlers.Finder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.expectations.Expectation.*;
import static com.tidal.wave.webelement.ElementFinder.find;

public class ExpectationTestParallelOneTest {

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
    public void softAssertForElementToBePresent() {
        find("#invisibleElementXX").expecting(toBePresent);
    }

    @Test
    public void softAssertWillNotFailWithout_OrElseFail() {
        find("#invisibleElement").expecting(toBeVisible);
    }

    @Test
    public void testSATextNotEmpty() {
        find("#headerid").expecting(textNotEmpty);
    }


    @Test
    public void testSATextMatching() {
        find("#headerid").expecting(exactText("Head"));
    }


    @Test
    public void testSATextEqualTo() {
        find("#headerid").expecting(exactText("Header"));
    }


    @Test
    public void testSoftAssertionElementToBePresent() {
        find("#invisibleElement").expecting(toBePresent).orElseFail();
    }

    @Test
    public void testElementToBeInvisible() {
        find("#invisibleElement").expecting(toBeInvisible).orElseFail();
    }

    @Test(expected = ExpectationFailure.class)
    public void testElementToBeInvisibleOrFail() {
        find("name:test_button").expecting(toBeInvisible).orElseFail();
    }

    @Test (expected = ExpectationFailure.class)
    public void testElementToBeInteractable() {
        find("#invisibleElement").invisibleElement().expecting(toBeInteractable).orElseFail();
    }

    @Test
    public void testElementToBeStale() {
        find("#test_button_id").expecting(toBeStale).orElseFail();
    }

    @Test
    public void stateChangeTest() {
        find("#test_button_id").expecting(aChangeOfState);
    }
}
