package com.tidal.wave.commands;

import com.tidal.wave.browser.Browser;
import com.tidal.wave.browser.BrowserTypes;
import com.tidal.wave.browser.Driver;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OpenCloseTest {

    @Test
    public void chromeOpenTest(){
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        Browser.withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        assertThat(browserName, is(equalTo("chrome")));
        Browser.close();
    }

    @Test
    public void firefoxOpenTest(){
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        Browser.type(BrowserTypes.FIREFOX).withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        assertThat(browserName, is(equalTo("firefox")));
        Browser.close();
    }

    @Test
    public void edgeOpenTest(){
        EdgeOptions options = new EdgeOptions();
        options.setHeadless(true);
        Browser.type("edge").withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        assertThat(browserName, is(equalTo("msedge")));
        Browser.close();
    }

    @Test
    public void operaOpenTest(){
        OperaOptions options = new OperaOptions();
        Browser.type("opera").withOptions(options).open("https://google.co.nz");
        String browserName = ((RemoteWebDriver) Driver.getDriver()).getCapabilities().getBrowserName();
        assertThat(browserName, is(equalTo("opera")));
        Browser.close();
    }
}
