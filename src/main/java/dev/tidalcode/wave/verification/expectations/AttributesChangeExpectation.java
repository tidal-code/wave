package dev.tidalcode.wave.verification.expectations;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.GetAllAttributes;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.data.WaitTimeData;
import dev.tidalcode.wave.exceptions.TimeoutException;
import dev.tidalcode.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static dev.tidalcode.wave.data.WaitTimeData.getWaitTime;

public class AttributesChangeExpectation extends Expectation {

    @Override
    public void assertion(Executor executor) {

        String duration = WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        String attributes = executor.invokeCommand(GetAllAttributes.class, "getAllAttributes").toString();

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage("Expected a change of state of attribute values but did not happen")
                .until(e -> !e.invokeCommand(GetAllAttributes.class, "getAllAttributes").toString().equals(attributes));
    }

    @Override
    public void orElseFail() {
        super.orElseFail("Expected a change of state of attribute values but did not happen");
    }
}
