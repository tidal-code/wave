package com.tidal.wave.verification.expectations;

import com.tidal.wave.command.Executor;
import com.tidal.wave.exceptions.ExpectationFailure;

import java.util.List;
import java.util.function.Supplier;

public abstract class Expectation {

    protected boolean result;

    /**
     * Expecting an element TO BE STALE or no longer attached to the current DOM.<br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    public static final Supplier<Expectation> toBeStale = Expectation::toBeStale;

    /**
     * Expecting an element to be <b>VISIBLE.</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    public static final Supplier<Expectation> toBeVisible = Expectation::toBeVisible;

    /**
     * Expecting an element to be <b>NOT VISIBLE.</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    public static final Supplier<Expectation> toBeInvisible = Expectation::toBeInvisible;

    /**
     * Expecting an element to be <b>INTERACTABLE.</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    public static final Supplier<Expectation> toBeInteractable = Expectation::toBeInteractable;

    /**
     * Expecting an element to be <b>CLICKABLE.</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    public static final Supplier<Expectation> toBeClickable = Expectation::toBeClickable;

    /**
     * Expecting an element to be <b>PRESENT (A size of 1 or more elements).</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    public static final Supplier<Expectation> toBePresent = Expectation::toBePresent;

    /**
     * Expecting an element to be <b>NOT PRESENT (A size of zero elements).</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    public static final Supplier<Expectation> toBeNotPresent = Expectation::toBeNotPresent;

    /**
     * Expecting an ELEMENT TEXT <b>NOT EMPTY.</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    public static final Supplier<Expectation> textNotEmpty = Expectation::textNotEmpty;

    /**
     * Expecting <b>A NEW TEXT VALUE.</b><br>
     * This expected condition would read the existing value and would wait till it is replaced by a new value or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    public static final Supplier<Expectation> newTextValue = Expectation::newTextValue;

    /**
     * Expecting <b>A NEW ELEMENT STATE.</b><br>
     * This expected condition would wait until either the text value, css attribute or inline attribute changes or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    public static final Supplier<Expectation> aChangeOfState = Expectation::aChangeOfState;

    /**
     * Expecting <b>NEW ELEMENT ATTRIBUTES.</b><br>
     * This expected condition would wait until the inline attribute of an element changes or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    public static final Supplier<Expectation> newAttributes = Expectation::newAttributes;

    /**
     * Expecting an element <b>TO BE STALE</b> or no longer attached to the current DOM.<br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     *
     * @return Instance of Stale Expectation class
     */
    private static Expectation toBeStale() {
        return new StaleExpectation();
    }

    /**
     * @return Instance of Visible Expectation class.
     * Expecting an element to be <b>VISIBLE.</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method.
     */
    private static Expectation toBeVisible() {
        return new VisibleExpectation();
    }

    /**
     * @return Instance of Interactable Expectation class.
     * Expecting an element to be <b>INTERACTABLE.</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method.
     */
    private static Expectation toBeInteractable() {
        return new InteractableExpectation();
    }

    /**
     * @return Instance of Clickable Expectation class.
     * Expecting an element to be <b>CLICKABLE.</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    private static Expectation toBeClickable() {
        return new ClickableExpectation();
    }

    /**
     * @return Instance of Invisible Expectation class.
     * Expecting an element to be <b>NOT VISIBLE.</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method.
     */
    private static Expectation toBeInvisible() {
        return new InvisibleExpectation();
    }

    /**
     * @return Instance of ElementPresent Expectation class.
     * Expecting an element to be <b>PRESENT (A size of 1 or more elements).</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method.
     */
    private static Expectation toBePresent() {
        return new ElementPresentExpectation();
    }

    /**
     * @return Instance of ElementNotPresent Expectation class.
     * Expecting an element to be <b>NOT PRESENT (A size of zero elements).</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method.
     */
    private static Expectation toBeNotPresent() {
        return new ElementNotPresentExpectation();
    }

    /**
     * @return Instance of TextNotEmpty Expectation class.
     * Expecting an ELEMENT TEXT <b>NOT EMPTY.</b><br>
     * This expected condition would wait till the expectation is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method.
     */
    private static Expectation textNotEmpty() {
        return new TextNotEmptyExpectation();
    }

    /**
     * @return Instance of NewTextValue Expectation class.
     * Expecting <b>A NEW TEXT VALUE.</b><br>
     * This expected condition would read the existing value and would wait till it is replaced by a new value or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method.
     */
    private static Expectation newTextValue() {
        return new NewTextValueExpectation();
    }

    /**
     * @return Instance of StateChange Expectation class.
     * Expecting <b>A NEW ELEMENT STATE.</b><br>
     * This expected condition would wait until either the text value, css attribute or inline attribute changes or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method.
     */
    private static Expectation aChangeOfState() {
        return new StateChangeExpectation();
    }

    /**
     * @return Instance of AttributesChange Expectation class.
     * Expecting <b>NEW ELEMENT ATTRIBUTES.</b><br>
     * This expected condition would wait until the inline attribute of an element changes or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method.
     */
    private static Expectation newAttributes() {
        return new AttributesChangeExpectation();
    }

    /**
     * @return Instance of CSSChange Expectation class.
     * Expecting <b>NEW CSS ATTRIBUTES.</b><br>
     * This expected condition would wait till any of the css attributes of the element changes is met or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     */
    private static Expectation newCSSAttributes() {
        return new CSSChangeExpectation();
    }

    /**
     * Expecting <b>EXACT TEXT.</b><br>
     * This expected condition would wait till the text value of the element matches exactly as the given value or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     *
     * @param text that needs to be exactly matched
     * @return Instance of ExactChange Expectation class.
     */
    public static Expectation exactText(String text) {
        return new ExactTextExpectation(text);
    }

    /**
     * Expecting <b>MATCHING TEXT</b><br>
     * This expected condition would wait till the element text matches the given value or the default or explicit time out is reached.
     * Expectations are soft assertions, and they do not fail unless called by 'orElseFail()' method
     *
     * @param text that needs to be exactly matched
     * @return Instance of MatchingText Expectation class.
     */
    public static Expectation matchingText(String text) {
        return new MatchingTextExpectation(text);
    }

    public abstract void assertion(Executor executor);

    public abstract void orElseFail();

    public void orElseFail(String message) {
        if (!result) {
            throw new ExpectationFailure(message);
        }
    }
}
