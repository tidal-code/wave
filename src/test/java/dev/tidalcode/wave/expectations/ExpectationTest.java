package dev.tidalcode.wave.expectations;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.exceptions.ExpectationFailure;
import dev.tidalcode.wave.verification.expectations.collections.Expectations;
import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.verification.expectations.Expectation;
import dev.tidalcode.wave.webelement.ElementFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.webelement.ElementFinder.find;


public class ExpectationTest {

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

    @Test(expected = ExpectationFailure.class)
    public void testSoftAssertionInvisibleElement() {
        ElementFinder.find("#invisibleElement").expecting(Expectation.toBeVisible).orElseFail();
    }

    @Test
    public void softAssertWillNotFailWithout_OrElseFail() {
        ElementFinder.find("#invisibleElement").expecting(Expectation.toBeVisible);
    }

    @Test
    public void softAssertForElementToBePresent() {
        ElementFinder.find("#invisibleElementXX").expecting(Expectation.toBePresent);
    }

    @Test
    public void testSoftAssertionElementToBePresent() {
        ElementFinder.find("#invisibleElement").expecting(Expectation.toBePresent).orElseFail();
    }

    @Test
    public void testSATextNotEmpty() {
        ElementFinder.find("#headerid").expecting(Expectation.textNotEmpty);
    }

    @Test
    public void testSATextEqualTo() {
        ElementFinder.find("#headerid").expecting(Expectation.exactText("Header"));
    }

    @Test
    public void testSATextMatching() {
        ElementFinder.find("#headerid").expecting(Expectation.exactText("Head"));
    }

    @Test(expected = ExpectationFailure.class)
    public void testElementToBeInteractable() {
        ElementFinder.find("#invisibleElement").invisibleElement().expecting(Expectation.toBeInteractable).orElseFail();
    }

    @Test
    public void findAllExpectationsTest() {
        ElementFinder.findAll("headerid").waitFor(5).expecting(Expectations.sizeGreaterThan(0));
    }
}
