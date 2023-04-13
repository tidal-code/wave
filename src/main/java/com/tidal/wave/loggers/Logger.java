package com.tidal.wave.loggers;

import io.qameta.allure.Step;
import org.slf4j.LoggerFactory;

public class Logger {

    private org.slf4j.Logger loggerKlass;

    private Logger(){}

    public Logger(Class<?> any){
        loggerKlass = LoggerFactory.getLogger(any.getSimpleName());
    }

    @Step("{1}")
    public static <T> void info(Class<T> klass, String logMessage) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(klass);
        logger.info(logMessage);
    }

    @Step("{1}")
    public static <T> void info(Class<T> klass, String logMessage, Throwable t) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(klass);
        logger.info(logMessage, t);
    }

    @Step("{1}")
    public static <T> void warn(Class<T> klass, String logMessage) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(klass);
        logger.warn(logMessage);
    }

    @Step("{1}")
    public static <T> void warn(Class<T> klass, String logMessage, Throwable t) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(klass);
        logger.warn(logMessage, t);
    }

    @Step("{1}")
    public static <T> void error(Class<T> klass, String logMessage) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(klass);
        logger.error(logMessage);
    }

    @Step("{1}")
    public static <T> void error(Class<T> klass, String logMessage, Throwable t) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(klass);
        logger.error(logMessage, t);
    }

    @Step("{1}")
    public static <T> void debug(Class<T> klass, String logMessage) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(klass);
        logger.debug(logMessage);
    }

    @Step("{1}")
    public static <T> void debug(Class<T> klass, String logMessage, Throwable t) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(klass);
        logger.debug(logMessage, t);
    }

    @Step("{0}")
    public void info(String logMessage) {
        org.slf4j.Logger logger = loggerKlass;
        logger.info(logMessage);
    }

    @Step("{0}")
    public void warn(String logMessage) {
        org.slf4j.Logger logger = loggerKlass;
        logger.warn(logMessage);
    }

    @Step("{0}")
    public void error(String logMessage) {
        org.slf4j.Logger logger = loggerKlass;
        logger.error(logMessage);
    }

    @Step("{0}")
    public void debug(String logMessage) {
        org.slf4j.Logger logger = loggerKlass;
        logger.debug(logMessage);
    }
}
