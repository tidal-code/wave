package com.tidal.wave.retry;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsVisible;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.ThreadSleep;

import java.util.LinkedList;
import java.util.List;

public class NotVisible extends RetryCondition {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);
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
            ThreadSleep.forMilliS(500);
            executor.invokeCommand();
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
}
