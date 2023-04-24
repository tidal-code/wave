package com.tidal.wave.retry;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.GetSize;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.ThreadSleep;

import java.util.List;

public class StillPresent extends RetryCondition {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);

    @Override
    public boolean retry(boolean isVisible, boolean isMultiple, List<String> locators) {

        boolean result = (int) executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(locators)
                .invokeCommand(GetSize.class, "getSize") == 0;

        if (!result) {
            ThreadSleep.forMilliS(500);
            executor.invokeCommand();
        } else {
            return true;
        }

        result = (int) executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(locators)
                .invokeCommand(GetSize.class, "getSize") == 0;

        return result;
    }
}
