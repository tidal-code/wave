package dev.tidalcode.wave.options;

import dev.tidalcode.wave.config.Config;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxBrowserOptions {
    public FirefoxOptions getLocalOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(Config.LOCAL_SCREEN_SIZE);
        return options;
    }

    public FirefoxOptions getRemoteOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(Config.REMOTE_SCREEN_SIZE);
        options.addArguments("--headless");

        return options;
    }

    public void commonOption(FirefoxOptions options) {
        //todo - complete the common options for firefox
    }
}
