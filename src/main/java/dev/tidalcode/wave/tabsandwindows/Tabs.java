package dev.tidalcode.wave.tabsandwindows;


import dev.tidalcode.wave.browser.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Tabs {

    /***
     * Opens a new tab with the given url.
     * It will switch to the newly opened window by assuming that it is the last window opened.
     * If that is not the case, it is required to switch manually to the correct tab using switchToTab() methods.
     * @param url To navigate to, passed a string.
     */
    public static void openNewTab(String url) {
        WebDriver driver = Driver.getDriver();
        driver.switchTo().newWindow(WindowType.TAB);
        switchToTab(last);
        driver.navigate().to(url);
    }

    /**
     * Switches to the first tab with index starting from left
     */
    public static final Supplier<BrowserWindows> first = Tabs::firstTab;

    /**
     * Switches to the last tab with index starting from left
     */
    public static final Supplier<BrowserWindows> last = Tabs::lastTab;

    private static final ThreadLocal<ArrayList<String>> windowHandles = new ThreadLocal<>();
    private static String currentHandle;

    //Not to be instantiated.
    private Tabs() {
    }

    /**
     * Switches to the tab using an index. Unlike array sequencing, the tab order starts with '1'.
     * To switch to first tab use <code>switchTo(1)</code> instead of <code>switchTo(0)</code>
     *
     * @param index the order of the tab starting from left.
     */
    public static void switchToTab(int index) {
        if (index == 0) {
            index = index + 1;
        }
        captureWindowHandles();
        currentHandle = windowHandles.get().get(index - 1);
        Driver.getDriver().switchTo().window(currentHandle);
    }

    /**
     * Switches to the first and last tabs using keywords 'first' and 'last'.
     * The left most tab will be the first and right most tab will be the last
     *
     * @param tabOrder Either first or last
     */
    public static void switchToTab(Supplier<BrowserWindows> tabOrder) {
        captureWindowHandles();
        currentHandle = windowHandles.get().get(tabOrder.get().getIndex(windowHandles.get().size()) - 1);
        Driver.getDriver().switchTo().window(currentHandle);
    }

    /**
     * Closes the current active tab and switches to the last tab present.
     */
    public static void closeTab() {
        Driver.getDriver().close();
        switchToTab(last);
    }

    /**
     * Can be used to close either the first tab or the last tab
     * After that it switches to the closest tab.
     * For example, if it closed the first tab, then it will switch to the remaining first tab.
     * If it closed the last tab, it will switch to the last of the remaining tab.
     *
     * @param tabOrder Either first or last tab
     */
    public static void closeTab(Supplier<BrowserWindows> tabOrder) {
        switchToTab(tabOrder);
        Driver.getDriver().close();
        switchToTab(tabOrder);
    }

    /**
     * Can be used to close any tab in any order. This will not switch to any other active tabs.
     * Use switchToTab() to change your active tab.
     *
     * @param index index of the tab to be closed. Starts from 1.
     */
    public static void closeTab(int index) {
        switchToTab(index);
        Driver.getDriver().close();
    }

    public static void remove() {
        windowHandles.remove();
    }

    private static void captureWindowHandles() {
        windowHandles.set(new ArrayList<>(Driver.getDriver().getWindowHandles()));
    }

    private static BrowserWindows firstTab() {
        return new FirstTab();
    }

    private static BrowserWindows lastTab() {
        return new LastTab();
    }
}
