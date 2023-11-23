package dev.tidalcode.wave.commands;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.verification.conditions.Condition;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.webelement.ElementFinder.find;


public class ClearTest {


    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/textinput/textinput.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void textInputText() {
        String textInputLocator = "id:myText1";
        find(textInputLocator).setText("ProvaTest");
        Assert.assertEquals("ProvaTest", find(textInputLocator).getText());

        find(textInputLocator).clear();
        Assert.assertEquals("", find(textInputLocator).getText());
    }

    @Test
    public void testLocatorStringBuilderContains() {
        find("input.contains.data-id.dataid123").setText("ProvaTest");
        find("input.contains.data-id.dataid123").shouldHave(Condition.exactText("ProvaTest"));
    }

    @Test
    public void testLocatorStringBuilderWith() {
        find("input.with.id.myText1").setText("ProvaTest");
        find("input.with.id.myText1").shouldHave(Condition.exactText("ProvaTest"));
    }

}
