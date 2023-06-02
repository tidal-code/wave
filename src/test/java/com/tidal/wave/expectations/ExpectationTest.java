package com.tidal.wave.expectations;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.browser.Browser;
import com.tidal.wave.exceptions.ExpectationFailure;
import com.tidal.wave.verification.expectations.collections.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.expectations.Expectation.*;
import static com.tidal.wave.webelement.ElementFinder.find;
import static com.tidal.wave.webelement.ElementFinder.findAll;


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
        find("#invisibleElement").expecting(toBeVisible).orElseFail();
    }

    @Test
    public void softAssertWillNotFailWithout_OrElseFail() {
        find("#invisibleElement").expecting(toBeVisible);
    }

    @Test
    public void softAssertForElementToBePresent() {
        find("#invisibleElementXX").expecting(toBePresent);
    }

    @Test
    public void testSoftAssertionElementToBePresent() {
        find("#invisibleElement").expecting(toBePresent).orElseFail();
    }

    @Test
    public void testSATextNotEmpty() {
        find("#headerid").expecting(textNotEmpty);
    }

    @Test
    public void testSATextEqualTo() {
        find("#headerid").expecting(exactText("Header"));
    }

    @Test
    public void testSATextMatching() {
        find("#headerid").expecting(exactText("Head"));
    }

    @Test(expected = ExpectationFailure.class)
    public void testElementToBeInteractable() {
        find("#invisibleElement").invisibleElement().expecting(toBeInteractable).orElseFail();
    }

    @Test
    public void findAllExpectationsTest(){
        findAll("headerid").waitFor(5).expecting(Expectations.sizeGreaterThan(0));
    }
}
