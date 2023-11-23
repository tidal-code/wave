package dev.tidalcode.wave.verification.expectations;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.IsEnabled;
import dev.tidalcode.wave.commands.IsVisible;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.exceptions.TimeoutException;
import dev.tidalcode.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static dev.tidalcode.wave.data.WaitTimeData.getWaitTime;

public class InteractableExpectation extends Expectation {

    private boolean isVisibleResult;
    private boolean isEnabledResult;

    private Executor executor;

    @Override
    public void assertion(Executor executor) {

        this.executor = executor;

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
                .withMessage(String.format("Expected condition failed : Element %s expected to interactable but was not", executor.getContext().getLocators().get(executor.getContext().getLocators().size() - 1)))
                .until(e -> e.invokeCommand(IsVisible.class, "isVisible"));

        isEnabledResult = newFluentWait
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Expected condition failed : Element %s expected to be interactable but was not", executor.getContext().getLocators().get(executor.getContext().getLocators().size() - 1)))
                .until(e -> e.withMultipleElements(false)
                        .invokeCommand(IsEnabled.class, "isEnabled"));
    }

    @Override
    public void orElseFail() {
        result = isVisibleResult && isEnabledResult;
        super.orElseFail(String.format("Expected condition failed : Element %s expected to be interactable but was not", executor.getContext().getLocators().get(executor.getContext().getLocators().size() - 1)));
    }
}
