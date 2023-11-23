package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.exceptions.TestAssertionError;
import dev.tidalcode.wave.browser.Browser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.utils.data.GlobalData.addData;
import static dev.tidalcode.wave.webelement.ElementFinder.find;

public class ClickTest {


    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/button/button.html"));
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
