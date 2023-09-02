package com.tidal.wave.verification.expectations;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.FindTextData;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.TimeoutException;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.tidal.wave.data.WaitTimeData.getWaitTime;

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
