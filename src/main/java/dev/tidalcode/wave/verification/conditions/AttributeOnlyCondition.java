package dev.tidalcode.wave.verification.conditions;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.GetAttribute;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.exceptions.TestAssertionError;
import dev.tidalcode.wave.wait.FluentWait;
import dev.tidalcode.wave.data.WaitTimeData;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

public class AttributeOnlyCondition extends Condition {
    private final String attribute;

    public AttributeOnlyCondition(String attribute) {
        this.attribute = attribute;
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
                .ignoring(StaleElementReferenceException.class)
                .throwing(TestAssertionError.class)
                .withMessage(String.format("Failed to find attribute '%s'", attribute))
                .until(e -> e.invokeCommand(GetAttribute.class));
    }
}
