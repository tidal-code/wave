package com.tidal.wave.retry;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.IsVisible;
import com.tidal.wave.locator.LocatorMatcher;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.ThreadSleep;
import org.openqa.selenium.By;

import java.util.LinkedList;
import java.util.List;

public class NotVisible extends RetryCondition {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);
    private final List<By> newElementLocatorSet;

    public NotVisible(String locatorMatcher) {
        newElementLocatorSet = new LinkedList<>();
        newElementLocatorSet.add(LocatorMatcher.getMatchedLocator(locatorMatcher));
    }

    @Override
    public boolean retry(boolean isVisible, boolean isMultiple, List<By> locators) {

        boolean result = executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(newElementLocatorSet)
                .invokeCommand(IsVisible.class);


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
