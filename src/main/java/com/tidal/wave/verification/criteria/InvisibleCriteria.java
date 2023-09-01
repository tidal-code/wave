package com.tidal.wave.verification.criteria;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsVisible;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.wait.FluentWait;

import java.time.Duration;
import java.util.List;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;


public class InvisibleCriteria extends Criteria {
    private final Executor executor = new Executor();

    @Override
    public void verify(boolean isVisible, boolean isMultiple, List<String> locators) {
        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .withMessage(String.format("Element ' %s ' is visible or not disappeared as expected", locators.get(0)))
                .until(e -> !(boolean) (e
                        .withMultipleElements(false)
                        .usingLocator(locators)
                        .isVisible(isVisible)
                        .invokeCommand(IsVisible.class, "isVisible")));
    }
}
