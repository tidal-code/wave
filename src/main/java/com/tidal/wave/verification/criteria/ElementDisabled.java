package com.tidal.wave.verification.criteria;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsEnabled;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class ElementDisabled extends Criteria {
    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);

    @Override
    public void verify(boolean isVisible, boolean isMultiple, List<By> locators) {
        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .withMessage(String.format("Element %s is enabled", locators.get(0)))
                .until(e -> !(Boolean) e
                        .withMultipleElements(isMultiple)
                        .usingLocator(locators)
                        .isVisible(isVisible)
                        .invokeCommand(IsEnabled.class, "isEnabled"));
    }
}
