package com.tidal.wave.verifications;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.verification.conditions.Condition;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.browser.Browser.open;
import static com.tidal.wave.verification.conditions.collections.CollectionsCondition.*;
import static com.tidal.wave.verification.criteria.Criteria.visible;
import static com.tidal.wave.webelement.ElementFinder.find;
import static com.tidal.wave.webelement.ElementFinder.findAll;


public class ConditionsTest {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("https://www.google.com");
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void shouldHaveExactTextTest() {
        open("https://google.co.nz");

        find("name:q")
                .sendKeys("Selenium")
                .click()
                .clear()
                .sendKeys("Junit")
                .clear()
                .sendKeys("Cucumber")
                .shouldHave(Condition.exactText("Cucumber"));
    }

    @Test
    public void shouldHaveMatchingTextTest() {
        open("https://google.co.nz");

        find("name:q")
                .sendKeys("Selenium")
                .click()
                .clear()
                .sendKeys("Junit")
                .clear()
                .sendKeys("Cucumber")
                .shouldHave(Condition.matchingText("Cucumber"));

    }

    @Test
    public void shouldHaveIgnoringCaseTextTest() {
        open("https://google.co.nz");

        find("name:q")
                .sendKeys("Selenium")
                .click()
                .clear()
                .sendKeys("Junit")
                .clear()
                .sendKeys("Cucumber")
                .shouldHave(Condition.ignoreCaseExactText("CUCUMBER"));
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveExactTextTesThrowsErrort() {
        open("https://google.co.nz");

        find("name:q")
                .sendKeys("Selenium")
                .click()
                .clear()
                .sendKeys("Junit")
                .clear()
                .sendKeys("Cucumber").waitFor(10)
                .shouldHave(Condition.exactText("CucumberX"));

    }

    @Test(expected = AssertionError.class)
    public void noMatchingShouldThrowError() {
        open("https://google.co.nz");

        find("name:q")
                .sendKeys("Selenium")
                .click()
                .clear()
                .sendKeys("Junit")
                .clear()
                .sendKeys("Cucumber")
                .shouldHave(Condition.matchingText("Selenium"));
    }

    @Test(expected = AssertionError.class)
    public void shouldHaveIgnoringCaseTextThrowsError() {
        open("https://google.co.nz");

        find("name:q")
                .sendKeys("Selenium")
                .click()
                .clear()
                .sendKeys("Junit")
                .clear()
                .sendKeys("Cucumber")
                .shouldHave(Condition.ignoreCaseExactText("CCUMBER"));
    }

    @Test
    @Ignore("This value is never a constant")
    public void collectionSizeEqualsTest() {
        open("https://google.co.nz");
        findAll("//input").shouldHave(size(13));
    }

    @Test
    public void collectionConditionsMix() {
        open("https://google.co.nz");
        findAll("//input").shouldHave(sizeLessThan(20), sizeGreaterThan(6));
    }

    @Test
    public void collectionConditionsSize0() {
        open("https://google.co.nz");
        findAll("//inputXXX").shouldHave(size(0));
    }


    @Test
    public void collectionSizeGreaterThan() {
        open("https://google.co.nz");
        findAll("//input").shouldHave(sizeGreaterThan(7));
    }

    @Test
    public void collectionSizeLessThan() {
        open("https://google.co.nz");
        findAll("//input").shouldHave(sizeLessThan(15));
    }

    @Test
    public void elementDisplayedTest() {
        open("https://google.co.nz");
        find("//input").shouldBe(visible);
    }

}
