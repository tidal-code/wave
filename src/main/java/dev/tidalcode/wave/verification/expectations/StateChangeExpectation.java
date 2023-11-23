package dev.tidalcode.wave.verification.expectations;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.FindTextData;
import dev.tidalcode.wave.commands.GetAllAttributes;
import dev.tidalcode.wave.commands.GetAllCssAttributes;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.exceptions.TimeoutException;
import dev.tidalcode.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static dev.tidalcode.wave.data.WaitTimeData.getWaitTime;

public class StateChangeExpectation extends Expectation {


    @Override
    public void assertion(Executor executor) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        String text = executor.invokeCommand(FindTextData.class, "findTextData").toString();
        String cssProperties = executor.invokeCommand(GetAllCssAttributes.class, "getAllCssAttributes").toString();
        String attributes = executor.invokeCommand(GetAllAttributes.class, "getAllAttributes").toString();

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage("Expected a change of state but did not happen")
                .until(e ->
                        (!e.invokeCommand(FindTextData.class, "findTextData").toString().equals(text))
                                || !e.invokeCommand(GetAllCssAttributes.class, "getAllCssAttributes").toString().equals(cssProperties)
                                || !e.invokeCommand(GetAllAttributes.class, "getAllAttributes").toString().equals(attributes));
    }

    @Override
    public void orElseFail() {
        super.orElseFail("Expected a change of state but did not happen");
    }
}
