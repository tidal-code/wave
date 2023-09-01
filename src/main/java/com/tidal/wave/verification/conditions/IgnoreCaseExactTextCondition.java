package com.tidal.wave.verification.conditions;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.FindTextData;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.TestAssertionError;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class IgnoreCaseExactTextCondition extends Condition {
    private final String value;
    private final Executor executor = new Executor();

    public IgnoreCaseExactTextCondition(String value) {
        this.value = value;
    }

    @Override
    public void verify(boolean isVisible, boolean isMultiple, List<String> locators) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(StaleElementReferenceException.class)
                .throwing(TestAssertionError.class)
                .withMessage(String.format("Expected value '%s' is not same as actual value '%s'", value, executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locators).invokeCommand(FindTextData.class, "findTextData")))
                .until(e -> e
                        .withMultipleElements(isMultiple)
                        .isVisible(isVisible)
                        .usingLocator(locators)
                        .invokeCommand(FindTextData.class, "findTextData")
                        .toString().equalsIgnoreCase(value));
    }

}
