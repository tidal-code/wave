package com.tidal.wave.retry;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsVisible;
import com.tidal.wave.wait.ThreadSleep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StillVisible extends RetryCondition {

    public static final Logger logger = LoggerFactory.getLogger(StillPresent.class);
    private final Executor executor = new Executor();

    @Override
    public boolean retry(boolean isVisible, boolean isMultiple, List<String> locators) {


        boolean result = (executor
                .withMultipleElements(isMultiple)
                .usingLocator(locators)
                .isVisible(isVisible)
                .invokeCommand(IsVisible.class, "isVisible"));

        if (result) {
            executeCommandsIgnoringExceptions();
            ThreadSleep.forMilliS(500);
        } else {
            return true;
        }

        result = !(boolean) (executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(locators)
                .invokeCommand(IsVisible.class, "isVisible"));

        return result;
    }

    public void executeCommandsIgnoringExceptions() {
        try {
            executor.withTimeToWait(2).invokeCommand();
        } catch (Exception e) {
            logger.info("Retry exceptions ignored: " + e.getMessage());
        }
    }
}
