package com.tidal.wave.waiter;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.utils.propertieshandler.PropertiesFinder;
import com.tidal.wave.verification.conditions.Condition;
import com.tidal.wave.browser.Browser;
import com.tidal.wave.data.WaitTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static com.tidal.utils.data.GlobalData.getData;
import static com.tidal.wave.browser.Browser.close;
import static com.tidal.wave.data.WaitTime.DEFAULT_WAIT_TIME;
import static com.tidal.wave.data.WaitTimeData.getWaitTime;
import static com.tidal.wave.webelement.ElementFinder.find;
import static com.tidal.wave.webelement.ElementFinder.findAll;


public class GlobalWaitTest {

    @Before
    public void testSetUp() {
        System.setProperty("local.timeout", "10");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Browser
                .withOptions(options)
                .withWaitTime(Duration.ofSeconds(Integer.parseInt(PropertiesFinder.getProperty("local.timeout"))))
                .open("http://www.google.com");
    }

    @After
    public void testClose() {
        close();
    }

    @Test
    public void testDefaultWaitTimeout() {
        find("name:q").sendKeys("Selenium");
        TimeCounter timeCounter = new TimeCounter();
        try {
            find("name:qXXX").clear();
        } catch (RuntimeException e) {
            long timeElapsed = timeCounter.timeElapsed().getSeconds();

            if (timeElapsed < 5) {
                throw new RuntimeException(String.format("Default wait time is not working properly, " +
                        "expected is %s seconds but got %d", getData(DEFAULT_WAIT_TIME), timeElapsed));
            }
        }
    }

    @Test
    public void testExplicitWaitTimeOut() {
        find("name:q").sendKeys("Selenium");
        TimeCounter timeCounter = new TimeCounter();

        try {
            find("name:qXXX").clear();
        } catch (RuntimeException e) {
            long timeElapsed = timeCounter.timeElapsed().getSeconds();

            if (timeElapsed < 10) {
                throw new RuntimeException(String.format("Explicit wait time is not working properly, " +
                        "expected is %s seconds but got %d", getWaitTime(WaitTime.EXPLICIT_WAIT_TIME), timeElapsed));
            }
        }
    }


    @Test
    public void testExplicitWaitTimeOutDoNotOverWriteDefaultWaitTime() {
        find("name:q").waitFor(10).sendKeys("Selenium");
        TimeCounter timeCounter = new TimeCounter();

        try {
            find("name:qXXX").clear();
        } catch (RuntimeException e) {
            long timeElapsed = timeCounter.timeElapsed().getSeconds();

            if (timeElapsed < 5) {
                throw new RuntimeException(String.format("Explicit wait time overwrites default wait time, " +
                        "expected is %s seconds but got %d", getData(DEFAULT_WAIT_TIME), timeElapsed));
            }
        }
    }

    @Test
    public void shouldHaveTextExplicitWait() {
        find("name:q").sendKeys("Selenium");
        findAll("name:q").get(0).sendKeys("Selenium");

        TimeCounter timeCounter = new TimeCounter();

        try {
            find("name:q").waitFor(10).shouldHave(Condition.exactText("Cucumber"));
        } catch (RuntimeException e) {
            long timeElapsed = timeCounter.timeElapsed().getSeconds();

            if (!(timeElapsed <= 10)) {
                throw new RuntimeException(String.format("Explicit wait time overwrites default wait time, " +
                        "expected is %s seconds but got %d", getData(DEFAULT_WAIT_TIME), timeElapsed));
            }
        }
    }


}
