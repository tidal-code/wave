package com.tidal.wave.commands;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.browser.BrowserTypes;
import com.tidal.wave.browser.Driver;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OpenCloseTest {

    @Test
    public void chromeOpenTest(){
         ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        Browser.withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        assertThat(browserName, is(equalTo("chrome")));
        Browser.close();
    }

    @Test
    public void firefoxOpenTest(){
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        Browser.type(BrowserTypes.FIREFOX).withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        assertThat(browserName, is(equalTo("firefox")));
        Browser.close();
    }

    @Test
    public void edgeOpenTest(){
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless");
        Browser.type("edge").withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        assertThat(browserName, is(equalTo("msedge")));
        Browser.close();
    }
}
