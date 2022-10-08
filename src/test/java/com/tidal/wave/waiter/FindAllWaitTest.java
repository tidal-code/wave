package com.tidal.wave.waiter;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.filehandlers.Finder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.browser.Browser.close;
import static com.tidal.wave.data.GlobalData.getData;
import static com.tidal.wave.data.WaitTime.DEFAULT_WAIT_TIME;
import static com.tidal.wave.verification.conditions.collections.CollectionsCondition.*;
import static com.tidal.wave.webelement.ElementFinder.find;
import static com.tidal.wave.webelement.ElementFinder.findAll;

public class FindAllWaitTest {

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);

        Browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/elements/elements.html"));
    }

    @After
    public void terminate() {
        close();
    }


    @Test
    public void testForFindElementsAfterSettingExpWait() {
        find("#testid1").waitFor(8).click();
        TimeCounter timeCounter = new TimeCounter();

        try {
            findAll("name:qXX").shouldHave(size(0));
        } catch (RuntimeException e) {
            long timeElapsed = timeCounter.timeElapsed().getSeconds();

            if (timeElapsed >= 2) {
                throw new RuntimeException(String.format("Explicit wait time overwrites default wait time, " +
                        "expected is %s seconds but got %d", getData(DEFAULT_WAIT_TIME), timeElapsed));
            }
        }
    }

    @Test
    public void testForShouldHaveSizeNonZero() {
        find("#testid1").waitFor(8).click();

        TimeCounter timeCounter = new TimeCounter();
        try {
            findAll("name:qXX").shouldHave(size(1));
        } catch (RuntimeException e) {
            long timeElapsed = timeCounter.timeElapsed().getSeconds();

            if (timeElapsed > 2) {
                throw new RuntimeException(String.format("Explicit wait time overwrites default wait time, " +
                        "expected is %s seconds but got %d", getData(DEFAULT_WAIT_TIME), timeElapsed));
            }
        }
    }

    @Test
    public void testForShouldHaveSizeGreaterThan() {
        find("#testid1").waitFor(8).click();

        TimeCounter timeCounter = new TimeCounter();
        try {
            findAll("name:qXX").shouldHave(sizeGreaterThan(1));
        } catch (RuntimeException e) {
            long timeElapsed = timeCounter.timeElapsed().getSeconds();

            if (timeElapsed > 2) {
                throw new RuntimeException(String.format("Explicit wait time overwrites default wait time, " +
                        "expected is %s seconds but got %d", getData(DEFAULT_WAIT_TIME), timeElapsed));
            }
        }
    }

    @Test
    public void testWaitForShouldHaveSizeLessThan() {
        find("#testid1").waitFor(8).click();

        TimeCounter timeCounter = new TimeCounter();
        try {
            findAll("name:#testid1").shouldHave(sizeLessThan(1));
        } catch (RuntimeException e) {
            long timeElapsed = timeCounter.timeElapsed().getSeconds();

            if (timeElapsed >= 2) {
                throw new RuntimeException(String.format("Explicit wait time overwrites default wait time, " +
                        "expected is %s seconds but got %d", getData(DEFAULT_WAIT_TIME), timeElapsed));
            }
        }
    }


    @Test
    public void testExplicitWaitForShouldHaveSizeLessThan() {
        find("#testid1").waitFor(8).click();

        TimeCounter timeCounter = new TimeCounter();
        try {
            findAll("name:#testid1").shouldHave(sizeLessThan(1));
        } catch (RuntimeException e) {
            long timeElapsed = timeCounter.timeElapsed().getSeconds();

            if (timeElapsed <= 3) {
                throw new RuntimeException(String.format("Explicit wait time overwrites default wait time, " +
                        "expected is %s seconds but got %d", getData(DEFAULT_WAIT_TIME), timeElapsed));
            }
        }
    }
}
