package com.tidal.wave.verification.conditions;

import com.tidal.wave.command.Executor;

import java.util.List;


public class TestVerification {

    private TestVerification() {
    }

    public static void verification(Executor executor, Verification... verifications) {
        for (Verification verification : verifications) {
            verification.verify(executor);
        }
    }
}
