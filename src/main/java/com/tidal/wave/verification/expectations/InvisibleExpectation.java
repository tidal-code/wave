package com.tidal.wave.verification.expectations;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsVisible;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.ExpectationFailure;
import com.tidal.wave.exceptions.TimeoutException;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class InvisibleExpectation extends Expectation {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);
    private boolean result;
    private By byLocator;

    @Override
    public void assertion(boolean isVisible, boolean isMultiple, List<By> locatorSet) {
        byLocator = locatorSet.get(0);

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Expected condition failed : Element %s expected to be invisible but was not", locatorSet.get(0)))
                .until(e -> !(boolean) (e
                        .withMultipleElements(isMultiple)
                        .isVisible(isVisible)
                        .usingLocator(locatorSet)
                        .invokeCommand(IsVisible.class, "isVisible")));

    }

    @Override
    public void orElseFail() {
        if (!result) {
            throw new ExpectationFailure(String.format("Expected condition failed : Element %s expected to be invisible but was not", byLocator));
        }
    }
}
