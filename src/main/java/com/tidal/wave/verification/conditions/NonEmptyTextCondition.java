package com.tidal.wave.verification.conditions;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.FindTextData;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.TestAssertionError;
import com.tidal.wave.wait.FluentWait;

import java.time.Duration;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class NonEmptyTextCondition extends Condition {

    @Override
    public void verify(Executor executor) {
        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .throwing(TestAssertionError.class)
                .withMessage("Some text value is expected but got null")
                .until(e -> !e.invokeCommand(FindTextData.class, "findTextData").toString().equals(""));
    }
}
