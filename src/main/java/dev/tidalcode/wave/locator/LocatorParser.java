package dev.tidalcode.wave.locator;

import org.openqa.selenium.By;

public abstract class LocatorParser {
    protected LocatorParser nextLocatorParser;

    public void setNextParser(LocatorParser nextLocatorParser) {
        this.nextLocatorParser = nextLocatorParser;
    }

    public abstract By parse(String locator);
}
