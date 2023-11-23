package dev.tidalcode.wave.shadowdom;

import com.tidal.utils.filehandlers.Finder;
import dev.tidalcode.wave.browser.Browser;
import dev.tidalcode.wave.wait.ThreadSleep;
import dev.tidalcode.wave.verification.criteria.Criteria;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static dev.tidalcode.wave.webelement.ElementFinder.find;

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
        find("id:non_host").shouldBe(Criteria.present);
        find("id:nested_shadow_content")
                .inShadowDom().shouldBe(Criteria.present);
    }
}
