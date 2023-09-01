package com.tidal.wave.retry;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsVisible;
import com.tidal.wave.wait.ThreadSleep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class NotVisible extends RetryCondition {

    public static final Logger logger = LoggerFactory.getLogger(StillPresent.class);

    private final Executor executor = new Executor();
    private final List<String> newElementLocatorSet;

    public NotVisible(String locator) {
        newElementLocatorSet = new LinkedList<>();
        newElementLocatorSet.add(locator);
    }

    @Override
    public boolean retry(boolean isVisible, boolean isMultiple, List<String> locators) {

        boolean result = executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(newElementLocatorSet)
                .invokeCommand(IsVisible.class, "isVisible");


        if (!result) {
            executeCommandsIgnoringExceptions();
            ThreadSleep.forMilliS(500);
        } else {
            return true;
        }

        result = executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(newElementLocatorSet)
                .invokeCommand(IsVisible.class, "isVisible");

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
