package com.tidal.wave.verification.conditions;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.FindTextData;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.TestAssertionError;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class IgnoreCaseExactTextCondition extends Condition {
    private final String value;

    public IgnoreCaseExactTextCondition(String value) {
        this.value = value;
    }

    @Override
    public void verify(Executor executor) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(StaleElementReferenceException.class)
                .throwing(TestAssertionError.class)
                .withMessage(String.format("Expected value '%s' is not same as actual value '%s'", value, executor.invokeCommand(FindTextData.class, "findTextData")))
                .until(e -> e.invokeCommand(FindTextData.class, "findTextData").toString().equalsIgnoreCase(value));
    }

}
