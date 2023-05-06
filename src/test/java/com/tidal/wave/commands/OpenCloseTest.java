package com.tidal.wave.commands;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.browser.BrowserTypes;
import com.tidal.wave.browser.Driver;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.tidal.flow.assertions.Assert.verify;

public class OpenCloseTest {

    @Test
    public void chromeOpenTest(){
         ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        Assert.assertEquals(browserName, "chrome");
        Browser.close();
    }

    @Test
    public void firefoxOpenTest(){
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        Browser.type(BrowserTypes.FIREFOX).withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        verify("Firefox browser open test", browserName).isEqualTo("firefox");
        Browser.close();
    }

    @Test
    public void edgeOpenTest(){
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless");
        Browser.type("edge").withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        verify("Edge browser open test", browserName).isEqualTo("msedge");
        Browser.close();
    }
}
