package dev.tidalcode.wave.verification.expectations;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.FindTextData;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.exceptions.TimeoutException;
import dev.tidalcode.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static dev.tidalcode.wave.data.WaitTimeData.getWaitTime;

public class NewTextValueExpectation extends Expectation {

    String existingValue;

    @Override
    public void assertion(Executor executor) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        existingValue = executor.invokeCommand(FindTextData.class, "findTextData");

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Existing value '%s' has not changed", existingValue))
                .until(e -> !e.invokeCommand(FindTextData.class, "findTextData").toString().equals(existingValue));
    }

    @Override
    public void orElseFail() {
        super.orElseFail(String.format("Existing value '%s' has not changed", existingValue));
    }
}
