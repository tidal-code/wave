package com.tidal.wave.expectations;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.exceptions.ExpectationFailure;
import com.tidal.wave.browser.Browser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.expectations.collections.Expectations.*;
import static com.tidal.wave.webelement.ElementFinder.findAll;


public class ExpectationCollectionTest {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/elements/elements.html"));
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

    @Test
    public void findAllWaitForShouldGiveOnlyExpectations() {
        findAll(".test").waitFor(3).expecting(size(3));
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
