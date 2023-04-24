package com.tidal.wave.verification.conditions;

import java.util.List;


public class TestVerification {

    private TestVerification() {
    }

    public static void verification(boolean isVisible, boolean isMultiple, List<String> locators, Verification... verifications) {
        for (Verification verification : verifications) {
            verification.verify(isVisible, isMultiple, locators);
        }
    }
}
