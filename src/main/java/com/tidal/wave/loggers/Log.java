package com.tidal.wave.loggers;

import com.tidal.wave.propertieshandler.PropertiesFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private static final boolean LOG_ENABLED = getLogEnabled();
    private static Logger loggerKlass;

    private static boolean getLogEnabled() {
        try {
            return PropertiesFinder.getProperty("log.status").equals("true");
        } catch (Exception | ExceptionInInitializerError e) {
            return false;
        }
    }

    /**
     * @param any Class as parameter
     * @return Instance of the class
     * @deprecated Use Logger class instead of this one
     */
    @Deprecated
    public static Log getLogger(Class<?> any) {
        loggerKlass = LoggerFactory.getLogger(any.getSimpleName());
        return new Log();
    }

    public static <T> void info(Class<T> klass, String logMessage) {
        if (LOG_ENABLED) {
            Logger logger = LoggerFactory.getLogger(klass);
            logger.info(logMessage);
        }
    }

    public static <T> void info(Class<T> klass, String logMessage, Throwable t) {
        if (LOG_ENABLED) {
            Logger logger = LoggerFactory.getLogger(klass);
            logger.info(logMessage, t);
        }
    }

    public static <T> void warn(Class<T> klass, String logMessage) {
        Logger logger = LoggerFactory.getLogger(klass);
        logger.warn(logMessage);
    }

    public static <T> void warn(Class<T> klass, String logMessage, Throwable t) {
        Logger logger = LoggerFactory.getLogger(klass);
        logger.warn(logMessage, t);
    }

    public static <T> void error(Class<T> klass, String logMessage) {
        Logger logger = LoggerFactory.getLogger(klass);
        logger.error(logMessage);
    }

    public static <T> void error(Class<T> klass, String logMessage, Throwable t) {
        Logger logger = LoggerFactory.getLogger(klass);
        logger.error(logMessage, t);
    }

    public static <T> void debug(Class<T> klass, String logMessage) {
        Logger logger = LoggerFactory.getLogger(klass);
        logger.debug(logMessage);
    }

    public static <T> void debug(Class<T> klass, String logMessage, Throwable t) {
        Logger logger = LoggerFactory.getLogger(klass);
        logger.debug(logMessage, t);
    }

    public void info(String logMessage) {
        if (LOG_ENABLED) {
            Logger logger = loggerKlass;
            logger.info(logMessage);
        }
    }

    public void warn(String logMessage) {
        Logger logger = loggerKlass;
        logger.warn(logMessage);
    }

    public void error(String logMessage) {
        Logger logger = loggerKlass;
        logger.error(logMessage);
    }

    public void debug(String logMessage) {
        Logger logger = loggerKlass;
        logger.debug(logMessage);
    }
}
