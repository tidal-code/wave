package dev.tidalcode.wave.verification.expectations;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.FindTextData;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.exceptions.TimeoutException;
import dev.tidalcode.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static com.google.common.base.Strings.isNullOrEmpty;
import static dev.tidalcode.wave.data.WaitTimeData.getWaitTime;

public class TextNotEmptyExpectation extends Expectation {

    @Override
    public void assertion(Executor executor) {
        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage("Expected that element[s] have non empty text values")
                .until(e -> !isNullOrEmpty(e.invokeCommand(FindTextData.class, "findTextData")));
    }

    @Override
    public void orElseFail() {
        super.orElseFail("Expected condition 'text not empty' is not met");
    }
}
