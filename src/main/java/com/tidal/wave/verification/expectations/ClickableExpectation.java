package com.tidal.wave.verification.expectations;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsEnabled;
import com.tidal.wave.commands.IsVisible;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.TimeoutException;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class ClickableExpectation extends Expectation {
    private boolean isVisibleResult;
    private boolean isEnabledResult;
    private String byLocator;

    @Override
    public void assertion(Executor executor) {
        byLocator = executor.getContext().getLocators().get(executor.getContext().getLocators().size() - 1);

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
                .withMessage(String.format("Expected condition failed : for clickable condition, element %s expected to visible but was not", byLocator))
                .until(e -> e.invokeCommand(IsVisible.class, "isVisible"));


        isEnabledResult = newFluentWait
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Expected condition failed : for clickable condition, element %s expected to be enabled but was not", byLocator))
                .until(e -> e.invokeCommand(IsEnabled.class, "isEnabled"));

    }

    @Override
    public void orElseFail() {
        result = isVisibleResult && isEnabledResult;
        super.orElseFail(String.format("Expected condition failed : Element %s expected to be clickable but was not", byLocator));
    }
}
