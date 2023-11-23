package dev.tidalcode.wave.verification.conditions;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.FindTextData;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.exceptions.TestAssertionError;
import dev.tidalcode.wave.wait.FluentWait;
import dev.tidalcode.wave.data.WaitTimeData;

import java.time.Duration;

public class NonEmptyTextCondition extends Condition {

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
                .withMessage("Some text value is expected but got null")
                .until(e -> !e.invokeCommand(FindTextData.class, "findTextData").toString().equals(""));
    }
}
