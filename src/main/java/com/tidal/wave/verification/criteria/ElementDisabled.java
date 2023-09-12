package com.tidal.wave.verification.criteria;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsEnabled;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.wait.FluentWait;

import java.time.Duration;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class ElementDisabled extends Criteria {
    @Override
    public void verify(Executor executor) {
        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .withMessage(String.format("Element %s is enabled", executor.getContext().getLocators().get(executor.getContext().getLocators().size() - 1)))
                .until(e -> !(Boolean) e.invokeCommand(IsEnabled.class, "isEnabled"));
    }
}
