package com.tidal.wave.elementfinder;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.browser.Driver;
import com.tidal.wave.filehandlers.Finder;
import com.tidal.wave.webelement.UIElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.conditions.Condition.exactText;
import static com.tidal.wave.verification.conditions.collections.CollectionsCondition.size;
import static com.tidal.wave.verification.criteria.Criteria.visible;
import static com.tidal.wave.webelement.ElementFinder.find;
import static com.tidal.wave.webelement.ElementFinder.findAll;

public class TheFindAllTest {

    @Before
    public void initialize() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        
        Browser.withOptions(chromeOptions).open("file://" + Finder.findFilePath("components/elements/elements.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }


    @Test
    public void findAllTest() {
        findAll("tagName:div").get(3).shouldHave(exactText("QA"));
    }

    @Test
    public void findAllShouldBeVisible() {
        findAll("tagName:div").get(3).shouldBe(visible);
    }

    @Test
    public void testThenandThenFindClick() {
        UIElement element = find("id:testid1");
        element.thenFind("id:testid2").thenFind("tagName:div").thenFind("tagName:p").click();
    }

    @Test
    public void findAndFindAll() {
        find("#testid1").thenFindAll("tagName:div").get(3).shouldBe(visible).click();
    }

    @Test
    public void findAllShouldBeVisibleToClick() {
        findAll("tagName:div").get(3).shouldBe(visible).click();
    }

    @Test
    public void findAllCountTest() {
        int count = findAll("tagName:div").size();
        Assert.assertEquals(10, count);
    }

    @Test
    public void testThenandThenFindP() {
        find("id:testid1").thenFind("id:testid2").thenFind("tagName:div").thenFind("tagName:p").click();
        String text = find("id:testid1").thenFind("id:testid2").thenFind("tagName:div").thenFind("tagName:p").getText();
        Assert.assertEquals("Tester", text);
    }

    private UIElement testUIElementChaining() {
        UIElement element = find("id:testid1");
        element = element.thenFind("id:testid2");
        return element;
    }

    private UIElement testUIElementChaining2(UIElement uiElement) {
        return uiElement.thenFind("tagName:div");
    }

    private String testUIElementChaining3(UIElement uiElement) {
        UIElement element = uiElement.thenFind("tagName:p");
        return element.getText();
    }

    @Test
    public void testUIElementChaining4() {
        String text = testUIElementChaining3(testUIElementChaining2(testUIElementChaining()));
        Assert.assertEquals("Tester", text);
    }

    @Test
    public void testThenandThenFindPx() {
        String text = find("id:testid1").thenFind("id:testid2").thenFind("tagName:div").thenFindAll("tagName:p").get(0).getText();
        Assert.assertEquals("Tester", text);
    }

    @Test
    public void testThenAndThenFindAllP() {
        int number = find("id:testid1").thenFind("id:testid2").thenFind("tagName:div").thenFindAll("tagName:p").size();
        Assert.assertEquals(3, number);
    }


    @Test
    public void testUIElementThenTest() {
        UIElement element = find("id:testid1");
        int number = element.thenFind("id:testid2").thenFind("tagName:div").thenFindAll("tagName:p").size();
        Assert.assertEquals(3, number);


        int number2 = find("id:testid1").thenFind("id:testid2").thenFind("tagName:div").thenFindAll("tagName:p").size();
        Assert.assertEquals(3, number);

        WebElement element2 = Driver.getDriver().findElement(By.id("testid1"));
        int number3 = element2.findElement(By.id("testid2")).findElement(By.tagName("div")).findElements(By.tagName("p")).size();
        Assert.assertEquals(3, number2);
    }

    @Test
    public void testForEach() {
        findAll("class:test").forEach(e -> System.out.println(e.getText()));
    }

    @Test
    public void testGetSize() {
        findAll("xxxxxxx").shouldHave(size(0));
    }

}
