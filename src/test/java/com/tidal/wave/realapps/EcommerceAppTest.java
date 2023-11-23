package com.tidal.wave.realapps;

import com.tidal.wave.verification.expectations.Expectation;
import com.tidal.wave.webelement.ElementFinder;
import com.tidal.wave.browser.Browser;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static com.tidal.wave.webelement.ElementFinder.find;

public class EcommerceAppTest {

    @Test
    public void completePurchaseProcess() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");
        options.addArguments("window-size=1920,1080");

        Browser
                .withOptions(options)
                .withWaitTime(Duration.ofSeconds(10))
                .open("https://magento.softwaretestingboard.com/");

        ElementFinder.find("//span[contains(@class, 'ui-menu-icon')]").waitFor(4).expecting(Expectation.toBePresent);
        ElementFinder.find(String.format("//span[text()='%s']", "Men")).hover(1);
        ElementFinder.find(String.format("//span[text()='%s']", "Men")).getAllAttributes();
        ElementFinder.find(String.format("//span[text()='%s']", "Tops")).hover(1);
        ElementFinder.find(String.format("//span[text()='%s']", "Jackets")).click();
        ElementFinder.find("partialLinkText:Beaumont Summit Kit").hover(1);
        String addToCart = ElementFinder.find("Add to Cart").getText();
        System.out.println(addToCart);
        ElementFinder.find("Add to Cart").click();

        ElementFinder.find("//div[text()='You need to choose options for your item.']").expecting(Expectation.toBeVisible);
        ElementFinder.find("XS").click();
        ElementFinder.find("div with aria-label Orange").click();
        ElementFinder.find("Add to Cart").click();

        ElementFinder.find("//span[text()='1']").expecting(Expectation.toBePresent);
        ElementFinder.find("a contains class showcart").click();
        ElementFinder.find("id:top-cart-btn-checkout").click();

        ElementFinder.find("id:customer-email").sendKeys("testemail123@test.com");
        ElementFinder.find("name:firstname").sendKeys("Philip");
        ElementFinder.find("name:lastname").sendKeys("K");
        ElementFinder.find("name:street[0]").sendKeys("1 Nelson Street");
        ElementFinder.find("name:city").sendKeys("Auckland");
        ElementFinder.find("name:region_id").select("Alaska");
        ElementFinder.find("name:postcode").sendKeys("99513");
        ElementFinder.find("name:telephone").sendKeys("1234567677");
        ElementFinder.find("input with value tablerate_bestway").click();
        ElementFinder.find("span with text Next").click();
        ElementFinder.find("Place Order").click();

    }

    @After
    public void TestCleanup() {
        Browser.close();
    }
}
