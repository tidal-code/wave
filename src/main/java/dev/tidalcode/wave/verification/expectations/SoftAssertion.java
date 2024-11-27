package dev.tidalcode.wave.verification.expectations;

import dev.tidalcode.wave.command.Executor;

public class SoftAssertion {

    private SoftAssertion() {
    }

    public static Expectation softAssert(Executor executor, Expectation expectation) {
        expectation.assertion(executor);
        return expectation;
    }
}
