package com.tidal.wave.retry;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.GetSize;
import com.tidal.wave.locator.LocatorMatcher;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.ThreadSleep;
import org.openqa.selenium.By;

import java.util.LinkedList;
import java.util.List;

public class NotPresent extends RetryCondition {

    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);
    private final List<By> newElementLocatorSet;

    public NotPresent(String locatorMatcher) {
        newElementLocatorSet = new LinkedList<>();
        newElementLocatorSet.add(LocatorMatcher.getMatchedLocator(locatorMatcher));
    }

    @Override
    public boolean retry(boolean isVisible, boolean isMultiple, List<By> locators) {

        boolean result = (int) executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(newElementLocatorSet)
                .invokeCommand(GetSize.class, "getSize") > 0;

        if (!result) {
            ThreadSleep.forMilliS(500);
            executor.invokeCommand();
        } else {
            return true;
        }

        result = (int) executor
                .withMultipleElements(isMultiple)
                .isVisible(isVisible)
                .usingLocator(newElementLocatorSet)
                .invokeCommand(GetSize.class) > 0;

        return result;
    }
}
