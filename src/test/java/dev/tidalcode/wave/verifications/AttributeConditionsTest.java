package dev.tidalcode.wave.verifications;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.exceptions.TestAssertionError;
import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.webelement.ElementFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.verification.conditions.Condition.attribute;
import static dev.tidalcode.wave.verification.conditions.Condition.attributeAndValue;
import static dev.tidalcode.wave.webelement.ElementFinder.find;


public class AttributeConditionsTest {


    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/checkbox/checkbox.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void testAttributeName() {
        ElementFinder.find("id:h2_title").shouldHave(attributeAndValue("name", "title"));
    }

    @Test(expected = TestAssertionError.class)
    public void testAttributeNameFail() {
        ElementFinder.find("id:h2_title").shouldHave(attributeAndValue("name", "x_y_z"));
    }

    @Test
    public void testAttributeWithoutValue() {
        ElementFinder.find("id:checked_checkbox").shouldHave(attribute("checked"));
    }

    @Test
    public void testAttributeWithoutValueForDataModelTarget() {
        ElementFinder.find("id:checked_checkbox").shouldHave(attribute("data-modal-target"));
    }

    @Test(expected = TestAssertionError.class)
    public void testAttributeWithoutValueFail() {
        ElementFinder.find("id:test_checkbox_id").shouldHave(attribute("checked"));
    }

    @Test(expected = TestAssertionError.class)
    public void testAttributeWithoutValueForDataModelTargetFail() {
        ElementFinder.find("id:test_checkbox_id").shouldHave(attribute("data-modal-target"));
    }
}
