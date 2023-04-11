package com.tidal.wave.options;

import com.tidal.wave.propertieshandler.Config;
import com.tidal.wave.propertieshandler.PropertiesFinder;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class ChromeBrowserOptions implements BrowserOptions {

    @Override
    public ChromeOptions getLocalOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Config.LOCAL_SCREEN_SIZE);
        setCommonOptions(options);

        return options;
    }

    @Override
    public ChromeOptions getRemoteOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Config.REMOTE_SCREEN_SIZE);
        setCommonOptions(options);

        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        return options;
    }

    public void setCommonOptions(ChromeOptions options){
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");

        //to remove the warning message
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("download.default_directory",
                System.getProperty("user.dir") + File.separator +
                        String.join(File.separator, PropertiesFinder.getProperty(
                                "path.downloads").split(",")));

        options.setExperimentalOption("prefs", prefs);
    }
}
