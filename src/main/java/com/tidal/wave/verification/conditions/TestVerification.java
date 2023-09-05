package com.tidal.wave.verification.conditions;

import com.tidal.wave.command.Executor;


public class TestVerification {
    private TestVerification() {
    }

    public static void verification(Executor executor, Verification... verifications) {
        for (Verification verification : verifications) {
            verification.verify(executor);
        }
    }
}
