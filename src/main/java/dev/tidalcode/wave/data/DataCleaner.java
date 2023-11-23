package dev.tidalcode.wave.data;

import com.tidal.utils.data.GlobalData;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.tabsandwindows.Tabs;
import dev.tidalcode.wave.tabsandwindows.Windows;
import dev.tidalcode.wave.wait.Wait;

public class DataCleaner {
    private DataCleaner() {
    }

    public static void cleanData() {
        GlobalData.clean();
        Store.clean();
        Wait.removeWait();
        Tabs.remove();
        Windows.remove();
        ObjectSupplier.flushInstances();
    }

    public static void cleanTestData() {
        GlobalData.cleanTestData();
    }
}
