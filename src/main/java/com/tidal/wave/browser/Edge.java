package com.tidal.wave.browser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;


class Edge{

    private static final Logger logger = LogManager.getLogger(Edge.class);

    public WebDriver getDriver(EdgeOptions options) {
        logger.info("Test Starting with Edge Browser");
        if(options == null){
           return new EdgeDriver();
        }
        return new EdgeDriver(options);
    }
}
