package com.tidal.wave.browser;

import com.tidal.utils.utils.CheckString;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.Locale;

public class BrowserFactory {

    public WebDriver getBrowser(String browser, AbstractDriverOptions<?> options) {
        if (CheckString.isNullOrEmpty(browser)) {
            return new Chrome().getDriver((ChromeOptions) options);
        }
        switch (browser.toUpperCase(Locale.ROOT)) {
            case "FIREFOX":
                return new Firefox().getDriver((FirefoxOptions) options);
            case "EDGE":
                return new Edge().getDriver((EdgeOptions) options);
            case "SAFARI":
                return new SafariDriver((SafariOptions) options);
            default:
                return new Chrome().getDriver((ChromeOptions) options);
        }
    }

    public WebDriver getBrowser() {
        return getBrowser(null, null);
    }

}
