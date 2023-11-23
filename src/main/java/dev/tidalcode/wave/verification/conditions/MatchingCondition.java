package dev.tidalcode.wave.verification.conditions;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.FindTextData;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.exceptions.TestAssertionError;
import dev.tidalcode.wave.wait.FluentWait;
import dev.tidalcode.wave.data.WaitTimeData;

import java.time.Duration;

public class MatchingCondition extends Condition {
    private final String value;

    public MatchingCondition(String value) {
        this.value = value;
    }

    @Override
    public void verify(Executor executor) {

        String duration = WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? WaitTimeData.getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .throwing(TestAssertionError.class)
                .withMessage(String.format("Expected value %s is not matching with actual value %s", value, executor.invokeCommand(FindTextData.class, "findTextData")))
                .until(e -> e.invokeCommand(FindTextData.class, "findTextData").toString().contains(value));
    }
}
