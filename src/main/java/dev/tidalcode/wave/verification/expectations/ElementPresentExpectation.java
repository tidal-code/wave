package dev.tidalcode.wave.verification.expectations;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.GetSize;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.exceptions.TimeoutException;
import dev.tidalcode.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static dev.tidalcode.wave.data.WaitTimeData.getWaitTime;

public class ElementPresentExpectation extends Expectation {
    private String byLocator;

    @Override
    public void assertion(Executor executor) {
        byLocator = executor.getContext().getLocators().get(executor.getContext().getLocators().size() - 1);

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Expected condition failed : Element %s expected to be present but was not", byLocator))
                .until(e -> (int) e.invokeCommand(GetSize.class) > 0);
    }

    @Override
    public void orElseFail() {
        super.orElseFail(String.format("Expected condition failed : Element %s expected to present but was not", byLocator));
    }
}
