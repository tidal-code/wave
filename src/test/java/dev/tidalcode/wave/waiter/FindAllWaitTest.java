package dev.tidalcode.wave.waiter;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.verification.expectations.collections.Expectations;
import dev.tidalcode.wave.browser.Browser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.utils.data.GlobalData.getData;
import static dev.tidalcode.wave.browser.Browser.close;
import static dev.tidalcode.wave.data.WaitTime.DEFAULT_WAIT_TIME;
import static dev.tidalcode.wave.verification.conditions.collections.CollectionsCondition.*;
import static dev.tidalcode.wave.webelement.ElementFinder.find;
import static dev.tidalcode.wave.webelement.ElementFinder.findAll;

public class FindAllWaitTest {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/elements/elements.html"));
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

    @Test
    public void testExplicitWaitCustomWait() {
        findAll("#testid1000").waitFor(3).expecting(Expectations.sizeGreaterThan(2));
    }
}
