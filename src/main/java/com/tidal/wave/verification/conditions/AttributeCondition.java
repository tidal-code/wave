package com.tidal.wave.verification.conditions;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.GetAttribute;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.TestAssertionError;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class AttributeCondition extends Condition {

    private final String attributeType;
    private final String value;

    public AttributeCondition(String attributeType, String value) {
        this.attributeType = attributeType;
        this.value = value;
    }

    @Override
    public void verify(Executor executor) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(StaleElementReferenceException.class)
                .throwing(TestAssertionError.class)
                .withMessage(String.format("Failed to find attribute '%s' with value '%s'",
                        attributeType, value))
                .until(e -> e.withAttribute(attributeType).invokeCommand(GetAttribute.class).equals(value));
    }
}
