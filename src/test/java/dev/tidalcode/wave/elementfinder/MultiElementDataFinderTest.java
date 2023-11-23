package dev.tidalcode.wave.elementfinder;

import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.webelement.ElementFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeoutException;

import static dev.tidalcode.wave.verification.conditions.collections.CollectionsCondition.size;
import static dev.tidalcode.wave.webelement.ElementFinder.find;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class MultiElementDataFinderTest {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("https://google.co.nz");
    }

    @After
    public void terminate() {
        Browser.close();
    }


    @Test
    public void testFindAllWithClick() {
        ElementFinder.find("name:q").click().sendKeys("Selenium");
    }

    @Test
    public void testFindAllWithSetText() {
        ElementFinder.findAll("name:q").get(0).click().setText("Selenium");
    }

    @Test
    public void testFindAllShouldNotThrowError() {
        int size = ElementFinder.findAll("name:xxxx").size();
        System.out.println(size);
    }

    @Test
    public void testFindAllShouldHave() {
//        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
//        Browser 
//        Browser.withOptions(options).open("https://google.co.nz");
        ElementFinder.findAll("name:q").shouldHave(size(1));
//        Browser.close();
    }

//    public static void main(String[] args) {
//        new MultiElementDataFinderTest().testFindAllShouldHave();
//    }

    @Test(expected = TimeoutException.class)
    public void testFindAllShouldHaveThrowError() {
        ElementFinder.findAll("name:q").shouldHave(size(5));
    }

    @Test
    public void testFindAllSize() {
        int size = ElementFinder.findAll("name:q").size();
        assertThat(1, equalTo(size));
    }
}
