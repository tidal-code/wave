package com.tidal.wave.verification.conditions;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.FindTextData;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class NonEmptyTextCondition extends Condition {

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
                .throwing(AssertionError.class)
                .withMessage("Some text value is expected but got null")
                .until(e -> !e
                        .withMultipleElements(isMultiple)
                        .usingLocator(locatorSet)
                        .isVisible(isVisible)
                        .usingLocator(locatorSet)
                        .invokeCommand(FindTextData.class, "findTextData").toString().equals(""));
    }
}
