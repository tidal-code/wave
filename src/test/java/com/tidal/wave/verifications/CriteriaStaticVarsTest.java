package com.tidal.wave.verifications;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.exceptions.TimeoutException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.criteria.Criteria.*;
import static com.tidal.wave.webelement.ElementFinder.find;


public class CriteriaStaticVarsTest {


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

    @After
    public void testCleanUp() {
        Browser.close();
    }

    @Test
    public void shouldHaveVisibleComponent() {
        find("name:q").shouldBe(visible);
    }

    @Test(expected = TimeoutException.class)
    public void elementPresentConditionTest() {
        find("id:gbwXXX").shouldBe(present);
    }

    @Test
    public void elementNotPresentConditionTest() {
        find("id:gbwXXX").waitFor(10).shouldBe(notPresent);
    }

    @Test
    public void elementEnabledTest() {
        find("name:btnK").shouldBe(enabled);
    }

    @Test(expected = TimeoutException.class)
    public void elementNotEnabledTest() {
        find("name:btnK").shouldBe(notEnabled);
    }

}
