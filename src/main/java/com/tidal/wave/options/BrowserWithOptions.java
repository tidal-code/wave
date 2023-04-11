package com.tidal.wave.options;

import org.openqa.selenium.remote.AbstractDriverOptions;

public class BrowserWithOptions {
    public AbstractDriverOptions<?> getLocalOptions(String browserType) {
        if (browserType.equalsIgnoreCase("chrome")) {
            return new ChromeBrowserOptions().getLocalOptions();
        }

        if (browserType.equalsIgnoreCase("firefox")) {
            return new FirefoxBrowserOptions().getLocalOptions();
        }

        if (browserType.equalsIgnoreCase("edge")) {
            return new EdgeBrowserOptions().getLocalOptions();
        }

        throw new RuntimeException("Other browsers are not configured in the project yet");
    }

    public AbstractDriverOptions<?> getRemoteOptions(String browserType) {
        if (browserType.equalsIgnoreCase("chrome")) {
            return new ChromeBrowserOptions().getRemoteOptions();
        }

        if (browserType.equalsIgnoreCase("firefox")) {
            return new FirefoxBrowserOptions().getRemoteOptions();
        }

        if (browserType.equalsIgnoreCase("edge")) {
            return new EdgeBrowserOptions().getLocalOptions();
        }

        throw new RuntimeException("Other browsers are not configured in the project yet");
    }
}
