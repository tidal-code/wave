package com.tidal.wave.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import static io.github.bonigarcia.wdm.WebDriverManager.getInstance;

class Edge{

    public WebDriver getDriver(EdgeOptions options) {
        getInstance(EdgeDriver.class).arch64().setup();
        if(options == null){
           return new EdgeDriver();
        }
        return new EdgeDriver(options);
    }
}
