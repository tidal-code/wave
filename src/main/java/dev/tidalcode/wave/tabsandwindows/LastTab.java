package dev.tidalcode.wave.tabsandwindows;

public class LastTab implements BrowserWindows {
    @Override
    public int getIndex(int totalNumberOfTabs) {
        return totalNumberOfTabs;
    }
}
