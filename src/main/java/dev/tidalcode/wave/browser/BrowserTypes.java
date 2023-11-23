package dev.tidalcode.wave.browser;

public enum BrowserTypes {
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge"),
    OPERA("opera"),
    SAFARI("safari");
    //test comment
    final String browserName;

    BrowserTypes(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserName() {
        return browserName;
    }
}
