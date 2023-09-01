package com.tidal.wave.retry;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.browser.Browser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;


import static com.tidal.wave.retry.RetryCondition.stillPresent;
import static com.tidal.wave.retry.RetryCondition.stillVisible;
import static com.tidal.wave.verification.criteria.Criteria.*;
import static com.tidal.wave.webelement.ElementFinder.find;


public class RetryTests {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/timeout/timeoutElements.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void retryTestIfVisible() {
        TimeCounter timeCounter = new TimeCounter();
        find("#textInput").clear().sendKeys("Retry test").clear().sendKeys("QA").retryIf(stillVisible, 3);
        find("#textInput").shouldBe(notVisible);

        if (timeCounter.timeElapsed().toMillis() > 7000) {
            throw new RuntimeException("Retry Condition did not exited in time (took more than 7000 milli seconds, when the element became invisible in 6000 milli seconds");
        }
    }

    @Test
    public void retryTestIfNotVisible() {
        TimeCounter timeCounter = new TimeCounter();
        find("#textInput2").clear().click().sendKeys("Retry test").retryIf(RetryCondition.notVisible("#buttonId"), 8);
        find("#buttonId").shouldBe(visible);

        if (timeCounter.timeElapsed().toMillis() > 7000) {
            throw new RuntimeException("Retry Condition did exited in time (took more than 7000 milli seconds, when the element was visible in 6000 milli seconds");
        }
    }

    @Test
    public void retryTestIfStillPresent() {
        TimeCounter timeCounter = new TimeCounter();
        find("#textInput").clear().sendKeys("Retry test").clear().sendKeys("QA").retryIf(stillPresent, 3);
        find("#textInput").shouldBe(notPresent);

        if (timeCounter.timeElapsed().toMillis() > 7000) {
            throw new RuntimeException("Retry Condition did not exited in time (took more than 7000 milli seconds, when the element became invisible in 6000 milli seconds");
        }
    }
}
