package com.tidal.wave.elementfinder;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.verification.expectations.collections.Expectations;
import com.tidal.wave.webelement.UIElements;
import com.tidal.wave.browser.Browser;
import com.tidal.wave.webelement.UIElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.expectations.collections.Expectations.sizeGreaterThan;
import static com.tidal.wave.webelement.ElementFinder.findAll;

public class FindAllIterationTest {
    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/table/table.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void thenFindAllIteratorTest() {
        findAll("//table//tr").expecting(sizeGreaterThan(0));
        UIElements rows = findAll("//table//tr");
        rows.first();
        rows.last();
        rows.skipFirst();
        rows.skipLast();
        rows.remove(rows.get(1));


        for (UIElement row : rows) {
            System.out.println(row.getText());
        }
    }
}
