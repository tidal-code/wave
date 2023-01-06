package com.tidal.wave.expectations;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.exceptions.ExpectationFailure;
import com.tidal.wave.filehandlers.Finder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.expectations.collections.Expectations.*;
import static com.tidal.wave.webelement.ElementFinder.findAll;


public class ExpectationCollectionTest {

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        Browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/elements/elements.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test(expected = ExpectationFailure.class)
    public void findAllSizeTestShouldFail() {
        findAll(".test").expecting(size(4)).orElseFail();
    }

    @Test
    public void findAllSizeTestShouldNotFail() {
        findAll(".test").expecting(size(3)).orElseFail();
    }

    @Test(expected = ExpectationFailure.class)
    public void findAllSizeGTTestShouldFail() {
        findAll(".test").expecting(sizeGreaterThan(4)).orElseFail();
    }

    @Test
    public void findAllSizeGTTestShouldNotFail() {
        findAll(".test").expecting(sizeGreaterThan(2)).orElseFail();
    }

    @Test(expected = ExpectationFailure.class)
    public void findAllSizeLTTestShouldFail() {
        findAll(".test").expecting(sizeLessThan(2)).orElseFail();
    }

    @Test
    public void findAllSizeLTTestShouldNotFail() {
        findAll(".test").expecting(sizeLessThan(5)).orElseFail();
    }
}
