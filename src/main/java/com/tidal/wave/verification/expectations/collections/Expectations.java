package com.tidal.wave.verification.expectations.collections;

import org.openqa.selenium.By;

import java.util.List;

public interface Expectations {

    static Expectations size(int size) {
        return new SizeEqualsExpectation(size);
    }

    static Expectations sizeGreaterThan(int size) {
        return new SizeGreaterThan(size);
    }

    static Expectations sizeLessThan(int size) {
        return new SizeLessThan(size);
    }

    void assertion(boolean isMultiple, List<By> locatorSet);

    void orElseFail();
}
