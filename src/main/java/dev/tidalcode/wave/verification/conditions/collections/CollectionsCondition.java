package dev.tidalcode.wave.verification.conditions.collections;


import dev.tidalcode.wave.verification.conditions.Verification;

public abstract class CollectionsCondition implements Verification {

    /**
     * Condition for a collection of element that the size should be equal to the given value.
     *
     * @param numberOfElements input number of elements to be verified
     *                         Throws TestAssertionError if the condition is not satisfied
     * @return A self reference
     */
    public static CollectionsCondition size(int numberOfElements) {
        return new CollectionSize(numberOfElements);
    }

    /**
     * Condition for a collection of element that the size should be greater than the given value.
     *
     * @param numberOfElements input number of elements to be verified
     *                         Throws TestAssertionError if the condition is not satisfied
     * @return A self reference
     */
    public static CollectionsCondition sizeGreaterThan(int numberOfElements) {
        return new CollectionSizeGreaterThan(numberOfElements);
    }

    /**
     * Condition for a collection of element that the size should be less than the given value.
     *
     * @param numberOfElements input number of elements to be verified.
     *                         Throws TestAssertionError if the condition is not satisfied.
     * @return A self reference
     */
    public static CollectionsCondition sizeLessThan(int numberOfElements) {
        return new CollectionSizeLessThan(numberOfElements);
    }
}
