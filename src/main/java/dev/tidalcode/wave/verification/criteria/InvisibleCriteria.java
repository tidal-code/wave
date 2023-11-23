package dev.tidalcode.wave.verification.criteria;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.IsVisible;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.wait.FluentWait;
import dev.tidalcode.wave.data.WaitTimeData;

import java.time.Duration;


public class InvisibleCriteria extends Criteria {

    @Override
    public void verify(Executor executor) {
        String duration = WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? WaitTimeData.getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .withMessage(String.format("Element ' %s ' is visible or not disappeared as expected", executor.getContext().getLocators().get(executor.getContext().getLocators().size() - 1)))
                .until(e -> !(boolean) (e.invokeCommand(IsVisible.class, "isVisible")));
    }
}
