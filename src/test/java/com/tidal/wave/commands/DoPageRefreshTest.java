package com.tidal.wave.commands;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.data.IntervalTime;
import com.tidal.wave.data.MaxTime;
import com.tidal.wave.webelement.ElementFinder;
import com.tidal.wave.browser.Browser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.webelement.ElementFinder.find;

public class DoPageRefreshTest {

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


    @Test
    public void testDoPageRefresh() {
        try {
            find("id:test_button_idX").doPageRefresh(MaxTime._10_SECONDS, IntervalTime._4_SECONDS).click();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Element 'id:test_button_idX' not found within the maximum time of 10 seconds"));
        }

        try {
            find("id:test_button_idX").doPageRefresh(MaxTime._10_SECONDS, IntervalTime._4_SECONDS).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
