package dev.tidalcode.wave.webelement;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.verification.conditions.Condition;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static dev.tidalcode.wave.webelement.ElementFinder.find;
import static dev.tidalcode.wave.webelement.ElementFinder.findAll;

public class IframesTest {

    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withWaitTime(Duration.ofSeconds(6))
                .withOptions(options)
                .open("file://" + Finder.findFilePath("frame/frame.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void findingElementOutsideFrame() {
        findAll("id:default_content_text_2").get(0).shouldHave(Condition.exactText("Text From Default Content 2"));
    }

    @Test
    public void findingOuterFrameElementTest() {
        findAll("#fist_innerframe_text").get(0).shouldHave(Condition.exactText("Text from First Inner Frame"));
        find("#fist_innerframe_text").shouldHave(Condition.exactText("Text from First Inner Frame"));
        find("id:outerframetext").shouldHave(Condition.exactText("Text from OuterIframe"));
    }


    @Test
    public void canAutoSwitchToFrame() {
        find("#fist_innerframe_text").waitFor(2).shouldHave(Condition.exactText("Text from First Inner Frame"));
    }

    @Test
    public void testInnerParallelElement() {
        find("id:inner_parallel").shouldHave(Condition.exactText("Inner Innerframe Parallel"));
    }

    @Test
    public void testNestedParallelInnerFrameElement() {
        find("id:inner_parallel").shouldHave(Condition.exactText("Inner Innerframe Parallel"));
    }

    @Test
    public void testNestedParallelInnerFrameHiddenElement() {
        Assert.assertFalse("Element is displayed", find("id:inner_parallel_hidden").isDisplayed());
        find("id:inner_parallel_hidden").invisibleElement().shouldHave(Condition.exactText("Inner Innerframe Parallel Hidden"));
    }

    @Test
    public void sixthParallelInnerFrameTest() {
        find("id:sixth_parallel_inner_frame_text_2").shouldHave(Condition.exactText("Sixth Parallel Inner Frame Text 2"));
    }


    @Test
    public void findSixthIframeElement() {
        find("id:sixth_inner_frame_text_2").shouldHave(Condition.exactText("Sixth Inner Frame Text 2"));
        find("id:sixth_inner_frame_text_2").shouldHave(Condition.exactText("Sixth Inner Frame Text 2"));
        find("#default_content_text").shouldHave(Condition.exactText("Text From Default Content"));
    }

    @Test
    public void findSeventhIframeElement() {
        find("id:seventh_inner_frame_text_2").shouldHave(Condition.exactText("Seventh Inner Frame Text 2"));
        find("id:seventh_inner_frame_text_2").shouldHave(Condition.exactText("Seventh Inner Frame Text 2"));

    }

    @Test
    public void canSwitchBackToDefault() {
        find("#fist_innerframe_text").shouldHave(Condition.exactText("Text from First Inner Frame"));
        find("#default_content_text").shouldHave(Condition.exactText("Text From Default Content"));
    }

    @Test
    public void canSwitchBackToInnerAfterDefault() {
        find("#fist_innerframe_text").shouldHave(Condition.exactText("Text from First Inner Frame"));
        find("#default_content_text").shouldHave(Condition.exactText("Text From Default Content"));
        find("#second_inner_frame_text").shouldHave(Condition.exactText("Second Inner Frame"));
    }

    @Test
    public void secondInnerFrameSecondElementTest() {
        find("#second_inner_frame_text").shouldHave(Condition.exactText("Second Inner Frame"));
        find("#second_inner_frame_text_2").shouldHave(Condition.exactText("Second Inner Frame Text 2"));
        find("#fist_innerframe_text").shouldHave(Condition.exactText("Text from First Inner Frame"));
        find("#default_content_text").shouldHave(Condition.exactText("Text From Default Content"));
    }

    @Test
    public void testIframeSwitching() {
        Assert.assertEquals("Iframe not loaded correctly", "Text from OuterIframe", find("#outerframetext").getText());
        Assert.assertEquals("First inner iframe not loaded correctly", "Text from First Inner Frame", find("#fist_innerframe_text").getText());
        Assert.assertEquals("Iframe not loaded correctly", "Text from OuterIframe", find("#outerframetext").getText());
        Assert.assertEquals("Iframe not loaded correctly", "Text From Default Content", find("#default_content_text").getText());
    }
}
