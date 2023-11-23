package dev.tidalcode.wave.expectations;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.exceptions.ExpectationFailure;
import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.verification.expectations.Expectation;
import dev.tidalcode.wave.webelement.ElementFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.webelement.ElementFinder.find;

public class ExpectationTestParallelOneTest {

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
    public void softAssertForElementToBePresent() {
        ElementFinder.find("#invisibleElementXX").expecting(Expectation.toBePresent);
    }

    @Test
    public void softAssertWillNotFailWithout_OrElseFail() {
        ElementFinder.find("#invisibleElement").expecting(Expectation.toBeVisible);
    }

    @Test
    public void testSATextNotEmpty() {
        ElementFinder.find("#headerid").expecting(Expectation.textNotEmpty);
    }


    @Test
    public void testSATextMatching() {
        ElementFinder.find("#headerid").expecting(Expectation.exactText("Head"));
    }


    @Test
    public void testSATextEqualTo() {
        ElementFinder.find("#headerid").expecting(Expectation.exactText("Header"));
    }


    @Test
    public void testSoftAssertionElementToBePresent() {
        ElementFinder.find("#invisibleElement").expecting(Expectation.toBePresent).orElseFail();
    }

    @Test
    public void testElementToBeInvisible() {
        ElementFinder.find("#invisibleElement").expecting(Expectation.toBeInvisible).orElseFail();
    }

    @Test(expected = ExpectationFailure.class)
    public void testElementToBeInvisibleOrFail() {
        ElementFinder.find("name:test_button").expecting(Expectation.toBeInvisible).orElseFail();
    }

    @Test(expected = ExpectationFailure.class)
    public void testElementToBeInteractable() {
        ElementFinder.find("#invisibleElement").invisibleElement().expecting(Expectation.toBeInteractable).orElseFail();
    }

    @Test
    public void testElementToBeStale() {
        ElementFinder.find("#test_button_id").expecting(Expectation.toBeStale).orElseFail();
    }

    @Test
    public void stateChangeTest() {
        ElementFinder.find("#test_button_id").expecting(Expectation.aChangeOfState);
    }
}
