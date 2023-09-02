package com.tidal.wave.retry;

import com.tidal.wave.command.Executor;

import java.util.List;
import java.util.function.Supplier;

public abstract class RetryCondition {

    /**
     * The static variable to call the <b>StillPresent</b> retry condition. <br>
     * This would check if the element is still present in the DOM after performing an action or a set of actions.
     */
    public static final Supplier<RetryCondition> stillPresent = RetryCondition::stillPresent;

    /**
     * The static variable to call the <b>StillVisible</b> retry condition. <br>
     * This would check if the element is still visible in the DOM after performing an action or a set of actions.
     */
    public static final Supplier<RetryCondition> stillVisible = RetryCondition::stillVisible;

    /**
     * Method to call the <b>NotPresent</b> retry condition. <br>
     * This would check if the element is present or not and perform the actions to satisfy the condition
     *
     * @param locatorMatcher The locator of the new element expecting to be present
     * @return A self reference
     */
    public static RetryCondition notPresent(String locatorMatcher) {
        return new NotPresent(locatorMatcher);
    }

    /**
     * Method to call the <b>NotVisible</b> retry condition. <br>
     * This would check if the element is visible or not and perform the actions to satisfy the condition
     *
     * @param locatorMatcher The locator of the new element expecting to be visible
     * @return A self reference
     */
    public static RetryCondition notVisible(String locatorMatcher) {
        return new NotVisible(locatorMatcher);
    }

    private static RetryCondition stillPresent() {
        return new StillPresent();
    }

    private static RetryCondition stillVisible() {
        return new StillVisible();
    }

    public abstract boolean retry(Executor executor);
}
