package dev.tidalcode.wave.browser;

import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.wait.Activity;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * The browser builder for tests. <br>
 *
 * Browser can be initiated as per the example below
 * <h3>Example: </h3>
 * &nbsp;Browser <br>
 * &nbsp; &nbsp; .type("Chrome") <br>
 * &nbsp; &nbsp; .withWaitTime(Duration.ofSeconds(15)) <br>
 * &nbsp; &nbsp; .withOptions(new ChromeOptions()) <br>
 * &nbsp; &nbsp; .open("https://google.co.nz"); <br>
 *
 *
 */
public final class Browser {

    private static final Supplier<BrowserConfig> browserConfig = Browser::getBrowserConfig;

    /**
     * Calls the static browserConfig supplier
     * @return instance of BrowserConfig
     */
    private static BrowserConfig getBrowserConfig() {
        return (BrowserConfig) ObjectSupplier.instanceOf(BrowserConfig.class);
    }

    /**
     * Adds the browser type.
     * @param browserType type of browser to be used.
     * @return instance of BrowserConfig.
     */
    public static BrowserConfig type(String browserType) {
        browserConfig.get().type(browserType);
        return browserConfig.get();
    }

    /**
     * Adds the browser type.
     * @param browserType - parameter passed as an enum value of {@link BrowserTypes}
     * @return instance of BrowserConfig
     */
    public static BrowserConfig type(BrowserTypes browserType) {
        browserConfig.get().type(browserType);
        return browserConfig.get();
    }

    /**
     * Adds the wait time in Duration
     * @param explicitWait explicit wait time to be added as global property
     * @return instance of BrowserConfig
     */
    public static BrowserConfig withWaitTime(Duration explicitWait) {
        browserConfig.get().withWaitTime(explicitWait);
        return browserConfig.get();
    }

    /**
     * Allows the page background activity to be checked before action is taken
     * @param activity background activity type
     * @param enable enable or disable (this can be used to switch off some activity checks enabled by default
     * @return instance of BrowserConfig
     */
    public static BrowserConfig pageBackGroundActivity(Activity activity, boolean enable) {
        browserConfig.get().pageBackGroundActivity(activity, enable);
        return browserConfig.get();
    }

    /**
     * Driver options to be set, for example, ChromeOptions, EdgeOptions, FirefoxOptions etc
     * @param options browser options
     * @return instance of BrowserConfig
     */
    public static BrowserConfig withOptions(AbstractDriverOptions<?> options) {
        browserConfig.get().withOptions(options);
        return browserConfig.get();
    }

    /**
     * Command to open a URL passed as a string
     * @param url the url to be opened
     */
    public static void open(String url) {
        browserConfig.get().open(url);
    }

    /**
     * Terminates the browser instance
     */
    public static void close() {
        browserConfig.get().close();
    }

}


