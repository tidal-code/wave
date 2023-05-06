package com.tidal.wave.options;

import com.tidal.wave.config.Config;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.HashMap;
import java.util.Map;

public final class EdgeBrowserOptions implements BrowserOptions {

    @Override
    public EdgeOptions getLocalOptions(){
        EdgeOptions options = new EdgeOptions();
        options.addArguments(Config.LOCAL_SCREEN_SIZE);
        setCommonOptions(options);

        return options;
    }

    @Override
    public EdgeOptions getRemoteOptions(){
        EdgeOptions options = new EdgeOptions();
        options.addArguments(Config.REMOTE_SCREEN_SIZE);
        setCommonOptions(options);

        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        return options;
    }

    public void setCommonOptions(EdgeOptions options){
        options.addArguments("--disable-notifications");

        //to remove the warning message
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);
    }
}
