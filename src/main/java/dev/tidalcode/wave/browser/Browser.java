package dev.tidalcode.wave.browser;

import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.wait.Activity;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.time.Duration;
import java.util.function.Supplier;

public final class Browser {

    private static final Supplier<BrowserConfig> browserConfig = Browser::getBrowserConfig;

    private static BrowserConfig getBrowserConfig() {
        return (BrowserConfig) ObjectSupplier.instanceOf(BrowserConfig.class);
    }

    public static BrowserConfig type(String browserType) {
        browserConfig.get().type(browserType);
        return browserConfig.get();
    }

    public static BrowserConfig type(BrowserTypes browserType) {
        browserConfig.get().type(browserType);
        return browserConfig.get();
    }

    public static BrowserConfig withWaitTime(Duration explicitWait) {
        browserConfig.get().withWaitTime(explicitWait);
        return browserConfig.get();
    }

    public static BrowserConfig pageBackGroundActivity(Activity activity, boolean enable) {
        browserConfig.get().pageBackGroundActivity(activity, enable);
        return browserConfig.get();
    }

    public static BrowserConfig withOptions(AbstractDriverOptions<?> options) {
        browserConfig.get().withOptions(options);
        return browserConfig.get();
    }

    public static void open(String url) {
        browserConfig.get().open(url);
    }

    public static void close() {
        browserConfig.get().close();
    }

}


