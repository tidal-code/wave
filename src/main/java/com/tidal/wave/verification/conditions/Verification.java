package com.tidal.wave.verification.conditions;

import org.openqa.selenium.By;

import java.util.List;

public interface Verification {
    void verify(boolean isVisible, boolean isMultiple, List<By> locatorSet);
}
