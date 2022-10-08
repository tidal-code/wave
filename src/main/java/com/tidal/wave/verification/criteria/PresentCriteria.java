package com.tidal.wave.verification.criteria;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.GetSize;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class PresentCriteria extends Criteria {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);

    @Override
    public void verify(boolean isVisible, boolean isMultiple, List<By> locatorSet) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .withMessage(String.format("No element is found with %s", locatorSet.get(0)))
                .until(e -> (int) e
                        .withMultipleElements(isMultiple)
                        .isVisible(isVisible)
                        .usingLocator(locatorSet)
                        .invokeCommand(GetSize.class, "getSize") > 0);
    }
}
