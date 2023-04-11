package com.tidal.wave.waiter;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.counter.TimeCounter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.browser.Browser.close;
import static com.tidal.wave.browser.Browser.open;
import static com.tidal.wave.data.GlobalData.getData;
import static com.tidal.wave.data.WaitTime.DEFAULT_WAIT_TIME;
import static com.tidal.wave.verification.conditions.collections.CollectionsCondition.size;
import static com.tidal.wave.verification.expectations.Expectation.toBeNotPresent;
import static com.tidal.wave.webelement.ElementFinder.find;
import static com.tidal.wave.webelement.ElementFinder.findAll;

public class TimeoutTest {

    @Before
    public void testSetUp() {
         ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Browser.withOptions(options).open("http://www.google.com");
    }

    @After
    public void testClose() {
        close();
    }

    /*
     * There is no explicit wait set with setOption(). So the in element wait time of 2 seconds should not
     * affect the next element, and it should default to 5 seconds framework wait time.
     */
    @Test
    public void testDefaultWaitTimeoutFrameworkDefault() {
        find("name:q").waitFor(2).click().sendKeys("Selenium");
        TimeCounter timeCounter = new TimeCounter();
        try {
            find("name:qXXX").click().clear();
        } catch (RuntimeException e) {
            long timeElapsed = timeCounter.timeElapsed().getSeconds();

            if (timeElapsed < 5) {
                throw new RuntimeException(String.format("The wait time should be framework default wait time " +
                        "%s; instead got %s", getData(DEFAULT_WAIT_TIME), timeElapsed));
            }
        }
    }

    /*
     * The test should finish as soon as it finds that the number of elements are zero..
     * So it should finish before it reaches 10 seconds.
     */
    @Test
    public void collectionConditionsSize0TimeOut() {
        open("https://google.co.nz");

        TimeCounter timeCounter = new TimeCounter();
        findAll("//inputXXX").shouldHave(size(0));

        long timeElapsed = timeCounter.timeElapsed().getSeconds();

        if (timeElapsed >= 2) {
            throw new RuntimeException("Non existent element search should take zero seconds");
        }
    }


    /*
     * The test should finish as soon as it finds that the number of elements are zero..
     * So it should finish before it reaches 10 seconds.
     */
    @Test
    public void collectionExpectationsSize0TimeOut() {
        open("https://google.co.nz");
        TimeCounter timeCounter = new TimeCounter();
        find("//inputXXX").expecting(toBeNotPresent).orElseFail();

        long timeElapsed = timeCounter.timeElapsed().getSeconds();

        if (timeElapsed >= 2) {
            throw new RuntimeException("Non existent element search should take zero seconds");
        }
    }
}
