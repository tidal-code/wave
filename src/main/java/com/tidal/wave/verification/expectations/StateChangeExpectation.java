package com.tidal.wave.verification.expectations;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.FindTextData;
import com.tidal.wave.commands.GetAllAttributes;
import com.tidal.wave.commands.GetAllCssAttributes;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.TimeoutException;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public class StateChangeExpectation extends Expectation {

    private final Executor executor = new Executor();

    @Override
    public void assertion(boolean isVisible, boolean isMultiple, List<String> locators) {

        String duration = getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null
                ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME)
                : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME);

        Duration waitDuration = Duration.ofSeconds(Integer.parseInt(duration));

        String text = executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locators).invokeCommand(FindTextData.class, "findTextData").toString();
        String cssProperties = executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locators).invokeCommand(GetAllCssAttributes.class, "getAllCssAttributes").toString();
        String attributes = executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locators).invokeCommand(GetAllAttributes.class, "getAllAttributes").toString();

        result = new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage("Expected a change of state but did not happen")
                .until(e ->
                        (!e
                                .withMultipleElements(isMultiple)
                                .isVisible(isVisible)
                                .usingLocator(locators)
                                .invokeCommand(FindTextData.class, "findTextData").toString().equals(text))
                                || !e
                                .withMultipleElements(isMultiple)
                                .isVisible(isVisible)
                                .usingLocator(locators)
                                .invokeCommand(GetAllCssAttributes.class, "getAllCssAttributes").toString().equals(cssProperties)
                                || !e
                                .withMultipleElements(isMultiple)
                                .isVisible(isVisible)
                                .usingLocator(locators)
                                .invokeCommand(GetAllAttributes.class, "getAllAttributes").toString().equals(attributes));
    }

    @Override
    public void orElseFail() {
        super.orElseFail("Expected a change of state but did not happen");
    }
}
