package com.tidal.wave.propertieshandler;

import java.util.function.UnaryOperator;

import static com.google.common.base.Strings.isNullOrEmpty;

public class Config {

    private static final String[] defaultArrayValue = new String[]{};
    private static final UnaryOperator<String> CONFIGURATION = PropertiesFinder::getProperty;

    public static final String BROWSER_NAME = CONFIGURATION.apply("browser.name") != null ? CONFIGURATION.apply("browser.name") : "chrome";
    public static final boolean INCOGNITO_MODE = CONFIGURATION.apply("incognito.mode") != null && Boolean.parseBoolean(CONFIGURATION.apply("incognito.mode"));
    public static final String BASE_URL = CONFIGURATION.apply("base.url") != null ? CONFIGURATION.apply("base.url") : "";
    public static final String EXECUTION_TYPE = CONFIGURATION.apply("execution.type") != null ? CONFIGURATION.apply("execution.type") : "local";
    public static final String LOCAL_SCREEN_SIZE = CONFIGURATION.apply("local.screensize") != null ? CONFIGURATION.apply("local.screensize") : "window-size=1920,1080";
    public static final String REMOTE_SCREEN_SIZE = CONFIGURATION.apply("remote.screensize") != null ? CONFIGURATION.apply("remote.screensize") : "window-size=1920,1080";
    public static final String QTEST_PASSWORD_TOKEN = CONFIGURATION.apply("qtest.password.token") != null ? CONFIGURATION.apply("qtest.password.token") : " ";
    public static final String QTEST_PROJECT_ID = CONFIGURATION.apply("qtest.project.id") != null ? CONFIGURATION.apply("qtest.project.id") : " ";
    public static final String QTEST_CYCLE_ID = CONFIGURATION.apply("qtest.cycle.id") != null ? CONFIGURATION.apply("qtest.cycle.id") : "";
    public static final String[] QTEST_PROJECT_FOLDERS = CONFIGURATION.apply("qtest.project.folder") != null ? CONFIGURATION.apply("qtest.project.folder").split("[,. /\\\\]") : defaultArrayValue;
    public static final boolean UPDATE_QTEST = CONFIGURATION.apply("updateQTest") != null && Boolean.parseBoolean(CONFIGURATION.apply("updateQTest"));
    public static final boolean LOCAL_HEADLESS = CONFIGURATION.apply("local.headless") != null && Boolean.parseBoolean(CONFIGURATION.apply("local.headless"));
    public static final int LOCAL_TIMEOUT = CONFIGURATION.apply("local.timeout") != null ? Integer.parseInt(CONFIGURATION.apply("local.timeout")) : 5;
    public static final int REMOTE_TIMEOUT = CONFIGURATION.apply("remote.timeout") != null ? Integer.parseInt(CONFIGURATION.apply("remote.timeout")) : 5;
    public static final boolean ALL_ACTIVITIES_CHECK = CONFIGURATION.apply("all.background.activities.check") != null && Boolean.parseBoolean(CONFIGURATION.apply("all.background.activities.check"));
    public static final int ALL_ACTIVITIES_CHECK_TIME = CONFIGURATION.apply("all.background.activities.timeout") != null ? Integer.parseInt(CONFIGURATION.apply("all.background.activities.timeout")) : 1;
    public static final boolean DOC_READY_CHECK = CONFIGURATION.apply("doc.ready.check") != null && Boolean.parseBoolean(CONFIGURATION.apply("doc.ready.check"));
    public static final int DOC_READY_CHECK_TIME = CONFIGURATION.apply("doc.ready.timeout") != null ? Integer.parseInt(CONFIGURATION.apply("doc.ready.timeout")) : 2;
    public static final boolean ANGULAR_5_CHECK = CONFIGURATION.apply("angular5.check") != null && Boolean.parseBoolean(CONFIGURATION.apply("angular5.check"));
    public static final int ANGULAR_5_CHECK_TIME = CONFIGURATION.apply("angular5.check.timeout") != null ? Integer.parseInt(CONFIGURATION.apply("angular5.check.timeout")) : 2;
    public static final boolean ANGULAR_CHECK = CONFIGURATION.apply("angular.check") != null && Boolean.parseBoolean(CONFIGURATION.apply("angular.check"));
    public static final int ANGULAR_CHECK_TIME = CONFIGURATION.apply("angular.check.timeout") != null ? Integer.parseInt(CONFIGURATION.apply("angular.check.timeout")) : 2;
    public static final boolean JQUERY_LOAD_WAITER = CONFIGURATION.apply("jquery.load.check") != null && Boolean.parseBoolean(CONFIGURATION.apply("jquery.load.check"));
    public static final int JQUERY_LOAD_WAITER_TIME = CONFIGURATION.apply("jquery.load.timeout") != null ? Integer.parseInt(CONFIGURATION.apply("jquery.load.timeout")) : 2;
    public static final String DOWNLOADS_FOLDER = CONFIGURATION.apply("downloads.folder") != null ? CONFIGURATION.apply("downloads.folder") : "default";
    public static final String[] CONSOLE_LOG_PREFS = CONFIGURATION.apply("console.logs.types") != null ? CONFIGURATION.apply("console.logs.types").split(",") : new String[]{};
    public static final String DRIVER_EXECUTABLE_FOLDER = CONFIGURATION.apply("driver.executable.folder") != null ? CONFIGURATION.apply("driver.executable.folder") : "";
    public static final String RUNNER = CONFIGURATION.apply("runner") != null ? CONFIGURATION.apply("runner") : "TestNgRunner";
    public static final boolean RETRY_FAILED_TESTS = CONFIGURATION.apply("retry.failed.tests") != null && Boolean.parseBoolean(CONFIGURATION.apply("retry.failed.tests"));

    private Config() {
    }

    static {
        if(!isNullOrEmpty(Config.DRIVER_EXECUTABLE_FOLDER)) System.setProperty("wdm.cachePath", Config.DRIVER_EXECUTABLE_FOLDER);
    }

}
