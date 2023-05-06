package com.tidal.wave.commands;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.browser.Browser;
import com.tidal.wave.exceptions.ExpectationFailure;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.expectations.Expectation.toBeInteractable;
import static com.tidal.wave.webelement.ElementFinder.find;
import static com.tidal.wave.webelement.ElementFinder.findAll;

public class IsVisibleTest {

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

    @Test
    public void isPresentTest() {
        Assert.assertTrue(findAll("title:hidden").isPresent());
    }

    @Test (expected = ExpectationFailure.class)
    public void interactionExpectationShouldFail() {
        find("title:hidden").invisibleElement().expecting(toBeInteractable).orElseFail();
    }

    @Test(expected = AssertionError.class)
    public void displayTestShouldFail(){
        Assert.assertTrue(find("name:display_test").isDisplayed());
    }


}
