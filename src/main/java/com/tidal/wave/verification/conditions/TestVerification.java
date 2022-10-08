package com.tidal.wave.verification.conditions;

import org.openqa.selenium.By;

import java.util.List;


public class TestVerification {

    private TestVerification() {
    }

    public static void verification(boolean isVisible, boolean isMultiple, List<By> locatorSet, Verification... verifications) {
        for (Verification verification : verifications) {
            verification.verify(isVisible, isMultiple, locatorSet);
        }
    }
}
