package com.tidal.wave.shadowdom;

import com.tidal.utils.filehandlers.Finder;
import com.tidal.wave.verification.criteria.Criteria;
import com.tidal.wave.wait.ThreadSleep;
import com.tidal.wave.browser.Browser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.tidal.wave.verification.criteria.Criteria.present;
import static com.tidal.wave.webelement.ElementFinder.find;

public class ShadowDomTest {
    @Before
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("file://" + Finder.findFilePath("components/nestedshadowdom/index.html"));
    }

    @After
    public void terminate() {
        Browser.close();
    }

    @Test
    public void testShadowDom() {
        ThreadSleep.forSeconds(2);
        find("id:non_host").shouldBe(present);
        find("id:nested_shadow_content")
                .inShadowDom().shouldBe(present);
    }
}
