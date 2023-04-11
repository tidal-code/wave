package com.tidal.wave.options;

import org.openqa.selenium.remote.AbstractDriverOptions;
public interface BrowserOptions {
    AbstractDriverOptions<?> getLocalOptions();
    AbstractDriverOptions<?> getRemoteOptions();
}
