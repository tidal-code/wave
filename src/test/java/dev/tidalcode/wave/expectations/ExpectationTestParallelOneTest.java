package dev.tidalcode.wave.expectations;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.exceptions.ExpectationFailure;
import dev.tidalcode.wave.verification.expectations.Expectation;
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
        find("#invisibleElementXX").expecting(Expectation.toBePresent);
    }

    @Test
    public void softAssertWillNotFailWithout_OrElseFail() {
        find("#invisibleElement").expecting(Expectation.toBeVisible);
    }

    @Test
    public void testSATextNotEmpty() {
        find("#headerid").expecting(Expectation.textNotEmpty);
    }


    @Test
    public void testSATextMatching() {
        find("#headerid").expecting(Expectation.exactText("Head"));
    }


    @Test
    public void testSATextEqualTo() {
        find("#headerid").expecting(Expectation.exactText("Header"));
    }


    @Test
    public void testSoftAssertionElementToBePresent() {
        find("#invisibleElement").expecting(Expectation.toBePresent).orElseFail();
    }

    @Test
    public void testElementToBeInvisible() {
        find("#invisibleElement").expecting(Expectation.toBeInvisible).orElseFail();
    }

    @Test(expected = ExpectationFailure.class)
    public void testElementToBeInvisibleOrFail() {
        find("name:test_button").expecting(Expectation.toBeInvisible).orElseFail();
    }

    @Test(expected = ExpectationFailure.class)
    public void testElementToBeInteractable() {
        find("#invisibleElement").invisibleElement().expecting(Expectation.toBeInteractable).orElseFail();
    }

    @Test
    public void testElementToBeStale() {
        find("#test_button_id").expecting(Expectation.toBeStale).orElseFail();
    }

    @Test
    public void stateChangeTest() {
        find("#test_button_id").expecting(Expectation.aChangeOfState);
    }
}
