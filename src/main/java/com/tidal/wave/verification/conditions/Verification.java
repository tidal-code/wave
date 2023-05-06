package com.tidal.wave.verification.conditions;

import java.util.List;

public interface Verification {
    void verify(boolean isVisible, boolean isMultiple, List<String> locators);
}
