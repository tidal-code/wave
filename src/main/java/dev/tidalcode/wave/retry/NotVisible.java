package dev.tidalcode.wave.retry;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.IsVisible;
import dev.tidalcode.wave.wait.ThreadSleep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class NotVisible extends RetryCondition {

    public static final Logger logger = LoggerFactory.getLogger(NotVisible.class);

    private final List<String> newElementLocatorSet;

    public NotVisible(String locator) {
        newElementLocatorSet = new LinkedList<>();
        newElementLocatorSet.add(locator);
    }

    @Override
    public boolean retry(Executor executor) {

        boolean result = executor
                .usingLocator(newElementLocatorSet)
                .invokeCommand(IsVisible.class, "isVisible");


        if (!result) {
            executeCommandsIgnoringExceptions(executor);
            ThreadSleep.forMilliS(500);
        } else {
            return true;
        }

        result = executor
                .usingLocator(newElementLocatorSet)
                .invokeCommand(IsVisible.class, "isVisible");

        return result;
    }

    public void executeCommandsIgnoringExceptions(Executor executor) {
        try {
            executor.withTimeToWait(2).invokeCommand();
        } catch (Exception e) {
            logger.info("Retry exceptions ignored: " + e.getMessage());
        }

    }
}
