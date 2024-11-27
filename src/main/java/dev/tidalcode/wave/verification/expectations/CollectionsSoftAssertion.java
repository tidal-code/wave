package dev.tidalcode.wave.verification.expectations;

import dev.tidalcode.wave.verification.expectations.collections.Expectations;

import java.util.List;

public class CollectionsSoftAssertion {

    private CollectionsSoftAssertion() {
    }

    public static Expectations softAssert(boolean isMultiple, List<String> locators, Expectations expectations) {
        expectations.assertion(isMultiple, locators);
        return expectations;
    }
}
