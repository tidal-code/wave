package com.tidal.wave.config;

import com.tidal.utils.exceptions.PropertyHandlerException;
import com.tidal.utils.propertieshandler.PropertiesFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static com.google.common.base.Strings.isNullOrEmpty;

public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static final String[] defaultArrayValue = new String[]{};

    /**
     * To make sure that trying to read a non-existing property file <b>SHOULD NOT</b> cause a critical error
     */
    private static final Function<String, String> PROPERTY_FINDER = s -> {
        try {
            return PropertiesFinder.getProperty(s);
        } catch (PropertyHandlerException e) {
            logger.info(e.getMessage());
        }
        return null;
    };

    public static final String BROWSER_NAME = PROPERTY_FINDER.apply("browser.name") != null ? PROPERTY_FINDER.apply("browser.name") : "chrome";
    public static final boolean DRIVER_MANAGER = PROPERTY_FINDER.apply("driver.manager") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("driver.manager"));
    public static final boolean INCOGNITO_MODE = PROPERTY_FINDER.apply("incognito.mode") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("incognito.mode"));
    public static final String BASE_URL = PROPERTY_FINDER.apply("base.url") != null ? PROPERTY_FINDER.apply("base.url") : "";
    public static final String EXECUTION_TYPE = PROPERTY_FINDER.apply("execution.type") != null ? PROPERTY_FINDER.apply("execution.type") : "local";
    public static final String LOCAL_SCREEN_SIZE = PROPERTY_FINDER.apply("local.screensize") != null ? PROPERTY_FINDER.apply("local.screensize") : "window-size=1920,1080";
    public static final String REMOTE_SCREEN_SIZE = PROPERTY_FINDER.apply("remote.screensize") != null ? PROPERTY_FINDER.apply("remote.screensize") : "window-size=1920,1080";
    public static final String QTEST_PASSWORD_TOKEN = PROPERTY_FINDER.apply("qtest.password.token") != null ? PROPERTY_FINDER.apply("qtest.password.token") : " ";
    public static final String QTEST_PROJECT_ID = PROPERTY_FINDER.apply("qtest.project.id") != null ? PROPERTY_FINDER.apply("qtest.project.id") : " ";
    public static final String QTEST_CYCLE_ID = PROPERTY_FINDER.apply("qtest.cycle.id") != null ? PROPERTY_FINDER.apply("qtest.cycle.id") : "";
    public static final String[] QTEST_PROJECT_FOLDERS = PROPERTY_FINDER.apply("qtest.project.folder") != null ? PROPERTY_FINDER.apply("qtest.project.folder").split("[,. /\\\\]") : defaultArrayValue;
    public static final boolean UPDATE_QTEST = PROPERTY_FINDER.apply("updateQTest") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("updateQTest"));
    public static final boolean LOCAL_HEADLESS = PROPERTY_FINDER.apply("local.headless") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("local.headless"));
    public static final int LOCAL_TIMEOUT = PROPERTY_FINDER.apply("local.timeout") != null ? Integer.parseInt(PROPERTY_FINDER.apply("local.timeout")) : 5;
    public static final int REMOTE_TIMEOUT = PROPERTY_FINDER.apply("remote.timeout") != null ? Integer.parseInt(PROPERTY_FINDER.apply("remote.timeout")) : 5;
    public static final boolean ALL_ACTIVITIES_CHECK = PROPERTY_FINDER.apply("all.background.activities.check") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("all.background.activities.check"));
    public static final int ALL_ACTIVITIES_CHECK_TIME = PROPERTY_FINDER.apply("all.background.activities.timeout") != null ? Integer.parseInt(PROPERTY_FINDER.apply("all.background.activities.timeout")) : 1;
    public static final boolean DOC_READY_CHECK = PROPERTY_FINDER.apply("doc.ready.check") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("doc.ready.check"));
    public static final int DOC_READY_CHECK_TIME = PROPERTY_FINDER.apply("doc.ready.timeout") != null ? Integer.parseInt(PROPERTY_FINDER.apply("doc.ready.timeout")) : 2;
    public static final boolean ANGULAR_5_CHECK = PROPERTY_FINDER.apply("angular5.check") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("angular5.check"));
    public static final int ANGULAR_5_CHECK_TIME = PROPERTY_FINDER.apply("angular5.check.timeout") != null ? Integer.parseInt(PROPERTY_FINDER.apply("angular5.check.timeout")) : 2;
    public static final boolean ANGULAR_CHECK = PROPERTY_FINDER.apply("angular.check") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("angular.check"));
    public static final int ANGULAR_CHECK_TIME = PROPERTY_FINDER.apply("angular.check.timeout") != null ? Integer.parseInt(PROPERTY_FINDER.apply("angular.check.timeout")) : 2;
    public static final boolean JQUERY_LOAD_WAITER = PROPERTY_FINDER.apply("jquery.load.check") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("jquery.load.check"));
    public static final int JQUERY_LOAD_WAITER_TIME = PROPERTY_FINDER.apply("jquery.load.timeout") != null ? Integer.parseInt(PROPERTY_FINDER.apply("jquery.load.timeout")) : 2;
    public static final String DOWNLOADS_FOLDER = PROPERTY_FINDER.apply("downloads.folder") != null ? PROPERTY_FINDER.apply("downloads.folder") : "default";
    public static final String[] CONSOLE_LOG_PREFS = PROPERTY_FINDER.apply("console.logs.types") != null ? PROPERTY_FINDER.apply("console.logs.types").split(",") : new String[]{};
    public static final String DRIVER_EXECUTABLE_FOLDER = PROPERTY_FINDER.apply("driver.executable.folder") != null ? PROPERTY_FINDER.apply("driver.executable.folder") : "";
    public static final String RUNNER = PROPERTY_FINDER.apply("runner") != null ? PROPERTY_FINDER.apply("runner") : "TestNgRunner";
    public static final boolean RETRY_FAILED_TESTS = PROPERTY_FINDER.apply("retry.failed.tests") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("retry.failed.tests"));
    public static final boolean DEBUG = PROPERTY_FINDER.apply("debug") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("debug"));
    public static final boolean DEBUG_SLOW = PROPERTY_FINDER.apply("debug.slow") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("debug.slow"));
    public static final boolean SLOW_RUN = PROPERTY_FINDER.apply("slow.run") != null && Boolean.parseBoolean(PROPERTY_FINDER.apply("slow.run"));
    ;


    private Config() {
    }

    static {
        if (!isNullOrEmpty(Config.DRIVER_EXECUTABLE_FOLDER))
            System.setProperty("wdm.cachePath", Config.DRIVER_EXECUTABLE_FOLDER);
    }

}
