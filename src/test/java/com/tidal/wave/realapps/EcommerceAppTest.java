package com.tidal.wave.realapps;

import com.tidal.wave.browser.Browser;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static com.tidal.wave.verification.expectations.Expectation.toBePresent;
import static com.tidal.wave.verification.expectations.Expectation.toBeVisible;
import static com.tidal.wave.webelement.ElementFinder.find;

public class EcommerceAppTest {

    @Test
    public void completePurchaseProcess(){
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");
        options.addArguments("window-size=1920,1080");

        Browser
                .withOptions(options)
                .withWaitTime(Duration.ofSeconds(10))
                .open("https://magento.softwaretestingboard.com/");

        find("//span[contains(@class, 'ui-menu-icon')]").waitFor(4).expecting(toBePresent);
        find(String.format("//span[text()='%s']", "Men")).hover(1);
        find(String.format("//span[text()='%s']", "Men")).getAllAttributes();
        find(String.format("//span[text()='%s']", "Tops")).hover(1);
        find(String.format("//span[text()='%s']", "Jackets")).click();
        find("partialLinkText:Beaumont Summit Kit").hover(1);
        String addToCart = find("Add to Cart").getText();
        System.out.println(addToCart);
        find("Add to Cart").click();

        find("//div[text()='You need to choose options for your item.']").expecting(toBeVisible);
        find("XS").click();
        find("div with aria-label Orange").click();
        find("Add to Cart").click();

        find("//span[text()='1']").expecting(toBePresent);
        find("a contains class showcart").click();
        find("id:top-cart-btn-checkout").click();

        find("id:customer-email").sendKeys("testemail123@test.com");
        find("name:firstname").sendKeys("Philip");
        find("name:lastname").sendKeys("K");
        find("name:street[0]").sendKeys("1 Nelson Street");
        find("name:city").sendKeys("Auckland");
        find("name:region_id").select("Alaska");
        find("name:postcode").sendKeys("99513");
        find("name:telephone").sendKeys("1234567677");
        find("input with value tablerate_bestway").click();
        find("span with text Next").click();
        find("Place Order").click();

    }

    @After
    public void TestCleanup(){
        Browser.close();
    }
}
