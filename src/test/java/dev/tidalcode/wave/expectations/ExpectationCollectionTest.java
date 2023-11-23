package dev.tidalcode.wave.expectations;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.exceptions.ExpectationFailure;
import dev.tidalcode.wave.verification.expectations.collections.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.webelement.ElementFinder.findAll;


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
        findAll(".test").expecting(Expectations.size(4)).orElseFail();
    }

    @Test
    public void findAllSizeTestShouldNotFail() {
        findAll(".test").expecting(Expectations.size(3)).orElseFail();
    }

    @Test
    public void findAllWaitForShouldGiveOnlyExpectations() {
        findAll(".test").waitFor(3).expecting(Expectations.size(3));
    }

    @Test(expected = ExpectationFailure.class)
    public void findAllSizeGTTestShouldFail() {
        findAll(".test").expecting(Expectations.sizeGreaterThan(4)).orElseFail();
    }

    @Test
    public void findAllSizeGTTestShouldNotFail() {
        findAll(".test").expecting(Expectations.sizeGreaterThan(2)).orElseFail();
    }

    @Test(expected = ExpectationFailure.class)
    public void findAllSizeLTTestShouldFail() {
        findAll(".test").expecting(Expectations.sizeLessThan(2)).orElseFail();
    }

    @Test
    public void findAllSizeLTTestShouldNotFail() {
        findAll(".test").expecting(Expectations.sizeLessThan(5)).orElseFail();
    }
}
