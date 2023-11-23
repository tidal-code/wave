package dev.tidalcode.wave.verification.criteria;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.verification.conditions.Verification;

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
