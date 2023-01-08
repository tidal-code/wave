package com.tidal.wave.loggers;

import com.tidal.wave.propertieshandler.PropertiesFinder;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allure Log Helper Class
 */
public class LoggerUtil {

    private static final boolean LOG_ENABLED = getLogEnabled();
    private static final boolean ELEMENTS_LOG_ENABLED = getElementsLogEnabled();
    private static final boolean IFRAME_LOG_ENABLED = getIframeLogEnabled();
    private static Logger loggerKlass;

    public static LoggerUtil getLogger(Class<?> any) {
        loggerKlass = LoggerFactory.getLogger(any.getSimpleName());
        return new LoggerUtil();
    }


    private static boolean getLogEnabled() {
        try {
            return PropertiesFinder.getProperty("log.status").equals("true");
        } catch (Exception | ExceptionInInitializerError e) {
            return false;
        }
    }

    private static boolean getElementsLogEnabled() {
        try {
            return PropertiesFinder.getProperty("log.elements.status").equals("true");
        } catch (Exception | ExceptionInInitializerError e) {
            return false;
        }
    }

    private static boolean getIframeLogEnabled() {
        try {
            return PropertiesFinder.getProperty("log.iframe.status").equals("true");
        } catch (Exception | ExceptionInInitializerError e) {
            return false;
        }
    }

    /**
     * method log add the log message to allure report
     *
     * @param message pass the message string to be printed
     */
    @Step("{0}")
    private void logger(final String message) {
        Logger logger = loggerKlass;
        logger.info(message);
    }

    protected void log(String message) {
        if (LOG_ENABLED) {
            logger(message);
        }
    }

    public void iframeLog(String message) {
        if (IFRAME_LOG_ENABLED) {
            log(message);
        }
    }


    public void elementsLog(String message) {
        if (ELEMENTS_LOG_ENABLED) {
            log(message);
        }
    }
}
