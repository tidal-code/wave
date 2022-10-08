package com.tidal.wave.data;

import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.tabsandwindows.Tabs;
import com.tidal.wave.tabsandwindows.Windows;
import com.tidal.wave.wait.Wait;

public class DataCleaner {
    private DataCleaner() {
    }

    public static void cleanData() {
        GlobalData.clean();
        Wait.removeWait();
        Tabs.remove();
        Windows.remove();
        ObjectSupplier.flushInstances();
    }

    public static void cleanTestData() {
        GlobalData.cleanTestData();
    }
}
