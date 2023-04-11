package com.tidal.wave.commands;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.filehandlers.Finder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.webelement.ElementFinder.find;

public class SelectParallelTest {

    @Before
    public void initialize() {
         ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/select/select.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void selectByTextTest() {
        String selectedText = find("#test_select_id").select("Books");
        Assert.assertEquals("Books", selectedText);
    }

    @Test
    public void selectByTextTestMultiple() {
        String selectedText = find("#test_select_id").select("Books");
        Assert.assertEquals("Books", selectedText);

        selectedText = find("#test_select_id").select("CSS");
        Assert.assertEquals("CSS", selectedText);
    }

    @Test
    public void selectByIndexTest() {
        String selectedText = find("#test_select_id").select(2);
        Assert.assertEquals("CSS", selectedText);
    }

    @Test
    public void selectByValueTest() {
        String selectedText = find("#test_select_id").selectByValue("java");
        Assert.assertEquals("Java", selectedText);
    }
}
