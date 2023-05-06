package com.tidal.wave.verification.expectations;

import java.util.List;

public class SoftAssertion {

    private SoftAssertion() {
    }

    public static Expectation softAssert(boolean isVisible, boolean isMultiple, List<String> locators, Expectation expectation) {
        expectation.assertion(isVisible, isMultiple, locators);
        return expectation;
    }
}
