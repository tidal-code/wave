package com.tidal.wave.commands;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.browser.Browser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

import static com.tidal.wave.webelement.ElementFinder.findAll;

public class FindAllTextDataTest {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/elements/elements.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void findAllTextDataTest() {
        List<String> allTextData = findAll("css:#testid3 p").getAllText();
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("Tester");
        expectedValues.add("Of");
        expectedValues.add("Automation");
        Assert.assertEquals(expectedValues, allTextData);
    }

    @Test
    public void findAllTextDataTestWithValue() {
        List<String> allTextData = findAll("css:#testid5 p").getAllText();
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("Automation");
        expectedValues.add("Tester");
        Assert.assertEquals(expectedValues, allTextData);
    }

    @Test(expected = AssertionError.class)
    public void findAllTextDataTestFail() {
        List<String> allTextData = findAll("css:#testid3 p").getAllText();
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("Tester");
        expectedValues.add("Automation");
        Assert.assertEquals(expectedValues, allTextData);
    }

    @Test
    public void findAllTextDataTestNPTest() {

        List<String> testList = new ArrayList<>();
        testList.add("hello");
        testList.add("world");
        testList.add("java");

        testList.forEach(e -> {
            List<String> allTextData = findAll("css:#testid3 p").getAllText();
            System.out.println(allTextData.size());
            List<String> expectedValues = new ArrayList<>();
            expectedValues.add("Tester");
            expectedValues.add("Of");
            expectedValues.add("Automation");
            Assert.assertEquals(expectedValues, allTextData);
        });

//        List<String> allTextData = findAll("css:#testid3 p").getAllText();
//        System.out.println(allTextData.size());
//        List<String> expectedValues = new ArrayList<>();
//        expectedValues.add("Tester");
//        expectedValues.add("Automation");
//        Assert.assertEquals(expectedValues, allTextData);
    }
}
