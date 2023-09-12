package com.tidal.wave.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Edge {

    private static final Logger logger = LoggerFactory.getLogger(Edge.class);

    public WebDriver getDriver(EdgeOptions options) {
        logger.info("Test Starting with Edge Browser");
        if (options == null) {
            return new EdgeDriver();
        }
        return new EdgeDriver(options);
    }
}
