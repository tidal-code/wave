package com.tidal.wave.verification.criteria;

import com.tidal.wave.verification.conditions.Verification;

import java.util.function.Supplier;

public abstract class Criteria implements Verification {

    /**
     * Condition to be satisfied that the element should be <b>VISIBLE.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     */
    public static final Supplier<Criteria> visible = Criteria::visible;

    /**
     * Condition to be satisfied that the element should be <b>NOT VISIBLE.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     */
    public static final Supplier<Criteria> notVisible = Criteria::notVisible;

    /**
     * Condition to be satisfied that the element should be <b>PRESENT.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     */
    public static final Supplier<Criteria> present = Criteria::present;

    /**
     * Condition to be satisfied that the element should be <b>NOT PRESENT.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     */
    public static final Supplier<Criteria> notPresent = Criteria::notPresent;

    /**
     * Condition to be satisfied that the element should be <b>ENABLED.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     */
    public static final Supplier<Criteria> enabled = Criteria::enabled;

    /**
     * Condition to be satisfied that the element should be <b>NOT ENABLED.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     */
    public static final Supplier<Criteria> notEnabled = Criteria::notEnabled;

    /**
     * <p>Please use static variable 'visible'</p><br/><br/>
     * Condition to be satisfied that the element should be <b>VISIBLE.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     *
     * @return Instance of Visible Criteria class
     */
    private static Criteria visible() {
        return new VisibleCriteria();
    }

    /**
     * <p>Please use static variable 'notVisible'</p><br/><br/>
     * Condition to be satisfied that the element should be <b>NOT VISIBLE.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     *
     * @return Instance of Invisible Criteria class.
     */
    private static Criteria notVisible() {
        return new InvisibleCriteria();
    }

    /**
     * <p>Please use static variable 'present'</p><br/><br/>
     * Condition to be satisfied that the element should be <b>PRESENT.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     *
     * @return Instance of Present Criteria class.
     */
    private static Criteria present() {
        return new PresentCriteria();
    }

    /**
     * <p>Please use static variable 'notPresent'</p><br/><br/>
     * Condition to be satisfied that the element should be <b>NOT PRESENT.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     *
     * @return Instance of NotPresent Criteria class
     */
    private static Criteria notPresent() {
        return new NotPresentCriteria();
    }

    /**
     * <p>Please use static variable 'enabled'</p><br/><br/>
     * Condition to be satisfied that the element should be <b>ENABLED.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     *
     * @return Instance of Element Enabled Criteria class.
     */
    private static Criteria enabled() {
        return new ElementEnabled();
    }

    /**
     * <p>Please use static variable 'notEnabled'</p><br/><br/>
     * Condition to be satisfied that the element should be <b>NOT ENABLED.</b><br>
     * Throws TestAssertionError if the condition is not satisfied.
     *
     * @return Instance of Element Disabled Criteria class.
     */
    private static Criteria notEnabled() {
        return new ElementDisabled();
    }

}
