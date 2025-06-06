package dev.tidalcode.wave.verification.expectations.collections;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.GetSize;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.exceptions.ExpectationFailure;
import dev.tidalcode.wave.exceptions.TimeoutException;
import dev.tidalcode.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;

import static dev.tidalcode.wave.data.WaitTimeData.getWaitTime;

@SuppressWarnings("all")
public class SizeLessThan implements Expectations {
    private final Executor executor = new Executor();
    private final int size;
    private boolean result;

    public SizeLessThan(int size) {
        this.size = size;
    }


    @Override
    public void assertion(boolean isMultiple, List<String> locators) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage(String.format("Expected number of elements is %d but found %d element[s]", size, (int) executor.isVisible(false).withMultipleElements(isMultiple).usingLocator(locators).invokeCommand(GetSize.class, "getSize")))
                .until(e -> (int) e
                        .usingLocator(locators)
                        .withMultipleElements(isMultiple)
                        .invokeCommand(GetSize.class, "getSize") < size);
    }

    @Override
    public void orElseFail() {
        if (!result) {
            throw new ExpectationFailure(String.format("Expected number of elements is %d but found %d element[s]", size, (int) executor.invokeCommand(GetSize.class, "getSize")));
        }
    }
}
