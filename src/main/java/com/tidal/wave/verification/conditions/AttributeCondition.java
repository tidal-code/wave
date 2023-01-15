package com.tidal.wave.verification.conditions;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.GetAttribute;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.TestAssertionError;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class AttributeCondition extends Condition{

    private final String attributeType;
    private final String value;
    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);

    public AttributeCondition(String attributeType, String value) {
        this.attributeType = attributeType;
        this.value = value;
    }

    @Override
    public void verify(boolean isVisible, boolean isMultiple, List<By> locators) {

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
                .until(e -> e
                        .withMultipleElements(isMultiple)
                        .withAttribute(attributeType)
                        .isVisible(isVisible)
                        .usingLocator(locators)
                        .invokeCommand(GetAttribute.class).equals(value));
    }
}
