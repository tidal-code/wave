package com.tidal.wave.retry;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsVisible;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.ThreadSleep;

import java.util.List;

public class StillVisible extends RetryCondition {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);

    @Override
    public boolean retry(boolean isVisible, boolean isMultiple, List<String> locators) {


        boolean result = (executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(locators)
                .invokeCommand(IsVisible.class, "isVisible"));

        if (result) {
            ThreadSleep.forMilliS(500);
            executor.invokeCommand();
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
}
