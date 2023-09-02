package com.tidal.wave.verification.expectations;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsEnabled;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.TimeoutException;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class StaleExpectation extends Expectation {

    private String byLocator;

    @Override
    public void assertion(Executor executor) {

        byLocator = executor.getContext().getLocators().get(executor.getContext().getLocators().size()-1);

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        FluentWait<Executor> newFluentWait = new FluentWait<>(executor);

        result = newFluentWait
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Expected condition failed : Element %s expected to stale but was not", byLocator))
                .until(e -> !(boolean) (e.invokeCommand(IsEnabled.class, "isEnabled"))); //Expecting element not enabled
    }

    @Override
    public void orElseFail() {
        super.orElseFail(String.format("Expected condition failed : Element %s expected to be stale but was not", byLocator));
    }
}
