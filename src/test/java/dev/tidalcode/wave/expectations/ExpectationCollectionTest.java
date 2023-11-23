package dev.tidalcode.wave.expectations;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.exceptions.ExpectationFailure;
import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.webelement.ElementFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.verification.expectations.collections.Expectations.*;


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
        ElementFinder.findAll(".test").expecting(size(4)).orElseFail();
    }

    @Test
    public void findAllSizeTestShouldNotFail() {
        ElementFinder.findAll(".test").expecting(size(3)).orElseFail();
    }

    @Test
    public void findAllWaitForShouldGiveOnlyExpectations() {
        ElementFinder.findAll(".test").waitFor(3).expecting(size(3));
    }

    @Test(expected = ExpectationFailure.class)
    public void findAllSizeGTTestShouldFail() {
        ElementFinder.findAll(".test").expecting(sizeGreaterThan(4)).orElseFail();
    }

    @Test
    public void findAllSizeGTTestShouldNotFail() {
        ElementFinder.findAll(".test").expecting(sizeGreaterThan(2)).orElseFail();
    }

    @Test(expected = ExpectationFailure.class)
    public void findAllSizeLTTestShouldFail() {
        ElementFinder.findAll(".test").expecting(sizeLessThan(2)).orElseFail();
    }

    @Test
    public void findAllSizeLTTestShouldNotFail() {
        ElementFinder.findAll(".test").expecting(sizeLessThan(5)).orElseFail();
    }
}
