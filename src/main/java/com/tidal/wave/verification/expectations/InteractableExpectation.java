package com.tidal.wave.verification.expectations;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsEnabled;
import com.tidal.wave.commands.IsVisible;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.TimeoutException;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class InteractableExpectation extends Expectation {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);
    private boolean isVisibleResult;
    private boolean isEnabledResult;
    private By byLocator;

    @Override
    public void assertion(boolean isVisible, boolean isMultiple, List<By> locatorSet) {
        byLocator = locatorSet.get(0);

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        FluentWait<Executor> newFluentWait = new FluentWait<>(executor);

        isVisibleResult = newFluentWait
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Expected condition failed : Element %s expected to interactable but was not", locatorSet.get(0)))
                .until(e -> e
                        .withMultipleElements(isMultiple)
                        .isVisible(isVisible)
                        .usingLocator(locatorSet)
                        .invokeCommand(IsVisible.class, "isVisible"));

        isEnabledResult = newFluentWait
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Expected condition failed : Element %s expected to be interactable but was not", locatorSet.get(0)))
                .until(e -> e.withMultipleElements(false)
                        .invokeCommand(IsEnabled.class, "isEnabled"));
    }

    @Override
    public void orElseFail() {
        result = isVisibleResult && isEnabledResult;
        super.orElseFail(String.format("Expected condition failed : Element %s expected to be interactable but was not", byLocator));
    }
}
