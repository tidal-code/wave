package dev.tidalcode.wave.browser;

import dev.tidalcode.wave.config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Edge {

    private static final Logger logger = LoggerFactory.getLogger(Edge.class);

    public WebDriver getDriver(EdgeOptions options) {
        logger.info("Test Starting with Edge Browser");

        if(Config.DRIVER_MANAGER){
            logger.info("Browser (Edge) setup completed by WebDriver Manager");
            WebDriverManager.edgedriver().setup();
        }

        if (options == null) {
            return new EdgeDriver();
        }
        return new EdgeDriver(options);
    }
}
