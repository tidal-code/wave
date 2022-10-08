package com.tidal.wave.verification.conditions;



public abstract class Condition implements Verification {

    /**
     * Checking the condition that the element should have a particular attribute with value specified.
     * @param type type of attribute
     * @param value value of the attribute
     * @return A self reference.
     */
    public static Condition attributeAndValue(String type, String value) {
        return new AttributeCondition(type, value);
    }

    /**
     * Checking the condition that the element should have a particular attribute.
     * @param attribute type of attribute
     * @return A self reference.
     */
    public static Condition attribute(String attribute) {
        return new AttributeOnlyCondition(attribute);
    }

    /**
     * Condition to be satisfied that the element should have the exact text.<br>
     * Throws AssertionError if the condition is not satisfied.
     *
     * @param value The string to be matched
     * @return A self reference.
     */
    public static Condition exactText(String value) {
        return new ExactCondition(value);
    }

    /**
     * Condition to be satisfied that the element should have the text, ignoring case.<br>
     * Throws AssertionError if the condition is not satisfied.
     * @param value The string to be matched
     * @return A self reference.
     */
    public static Condition ignoreCaseExactText(String value) {
        return new IgnoreCaseExactTextCondition(value);
    }

    /**
     * Condition to be satisfied that the element should have a matching text.
     * Throws AssertionError if the condition is not satisfied
     *
     * @param value The string to be matched
     * @return A self reference.
     */
    public static Condition matchingText(String value) {
        return new MatchingCondition(value);
    }

    /**
     * Condition to be satisfied that the element should have a non-empty text.
     * Throws AssertionError if the condition is not satisfied
     *
     * @return A self reference.
     */
    public static Condition textNotNull() {
        return new NonEmptyTextCondition();
    }

}
