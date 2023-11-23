package dev.tidalcode.wave.options;

import dev.tidalcode.wave.config.Config;
import dev.tidalcode.wave.data.TestData;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class ChromeBrowserOptions implements BrowserOptions {

    @Override
    public ChromeOptions getLocalOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Config.LOCAL_SCREEN_SIZE);
        setCommonOptions(options);

        return options;
    }

    @Override
    public ChromeOptions getRemoteOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Config.REMOTE_SCREEN_SIZE);
        setCommonOptions(options);

        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        return options;
    }

    public void setCommonOptions(ChromeOptions options) {
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");

        //to remove the warning message
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        String downloadPath = System.getProperty("user.dir") + File.separator +
                String.join(File.separator, TestData.readProperty(
                        "path.downloads").split(","));

        System.out.println("Download path is " + downloadPath);
        prefs.put("download.default_directory",
                downloadPath);

        options.setExperimentalOption("prefs", prefs);
    }
}
