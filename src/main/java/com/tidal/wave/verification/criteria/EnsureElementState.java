package com.tidal.wave.verification.criteria;

import com.tidal.wave.command.Executor;
import com.tidal.wave.verification.conditions.Verification;

import java.util.List;
import java.util.function.Supplier;


public class EnsureElementState {

    //Class not to be instantiated.
    private EnsureElementState() {
    }

    public static void affirmation(Executor executor, Supplier<Criteria>[] verifications) {
        for (Supplier<Criteria> verification : verifications) {
            verification.get().verify(executor);
        }
    }

    public static void affirmation(Executor executor, Criteria... verifications) {
        for (Verification verification : verifications) {
            verification.verify(executor);
        }
    }
}
