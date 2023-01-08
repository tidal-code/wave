package com.tidal.wave.verification.expectations;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.FindTextData;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.TimeoutException;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class ExactTextExpectation extends Expectation {

    private static final String FIND_TEXT_DATA = "findTextData";
    private final String value;
    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);
    private boolean isMultiple;
    private boolean isVisible;
    private List<By> locators;

    public ExactTextExpectation(String value) {
        this.value = value;
    }

    @Override
    public void assertion(boolean isVisible, boolean isMultiple, List<By> locators) {
        this.isMultiple = isMultiple;
        this.isVisible = isVisible;
        this.locators = locators;

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Expected value '%s' is not equal to actual value %s", value, executor.isVisible(isVisible)
                        .withMultipleElements(isMultiple).usingLocator(locators).invokeCommand(FindTextData.class, FIND_TEXT_DATA)))
                .until(e -> e
                        .withMultipleElements(isMultiple)
                        .isVisible(isVisible)
                        .usingLocator(locators)
                        .invokeCommand(FindTextData.class, FIND_TEXT_DATA).equals(value));
    }

    @Override
    public void orElseFail() {
        super.orElseFail(String.format("Expected value '%s' is not equal to actual value %s", value,
                executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locators).invokeCommand(FindTextData.class,
                        FIND_TEXT_DATA)));
    }
}
