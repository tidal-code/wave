package com.tidal.wave.commands;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.exceptions.TestAssertionError;
import com.tidal.wave.filehandlers.Finder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.data.GlobalData.addData;
import static com.tidal.wave.webelement.ElementFinder.find;

public class ClickTest {


    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        
        Browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/button/button.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    /*
     * For this test two work add an exception to throw in click method
     *  Paste the below code in the click try block
     *
            if(getData("test").equals("throw")) {
                throw new ElementClickInterceptedException("test");
            }
     */
    //@Test(expected = TestAssertionError.class)
    public void clickTimeOutTest() {
        String locator = "id:test_button_id";
        addData("test", "do not throw");
        find(locator).click();
        addData("test", "throw");
        TimeCounter timeCounter = new TimeCounter();
        try {
            find(locator).waitFor(4).click();
        } catch (RuntimeException e) {
            if (timeCounter.timeElapsed().getSeconds() < 10) {
                throw new TestAssertionError("Click time out did not work propertly");
            }
        }
    }

    /*
   * For this test two work add an exception to throw in click method
   *  Paste the below code in the click try block
   *
          if(getData("test").equals("throw")) {
              throw new ElementClickInterceptedException("test");
          }
   */
    @Test
    public void clickTimeOutTestDoNotThrows() {
        String locator = "id:test_button_id";
        addData("test", "do not throw");
        find(locator).click();
        addData("test", "throw");
        TimeCounter timeCounter = new TimeCounter();
        try {
            find(locator).waitFor(7).click();
        } catch (RuntimeException e) {
            if (timeCounter.timeElapsed().getSeconds() < 7) {
                throw new TestAssertionError("Click time out did not work propertly");
            }
        }
    }
}
