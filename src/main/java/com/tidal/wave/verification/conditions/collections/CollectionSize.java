package com.tidal.wave.verification.conditions.collections;

import com.tidal.wave.command.Executor;
import com.tidal.wave.commands.GetSize;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.FluentWait;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;


/*
 * Wait duration is hard coded as not to double up the wait time because find.elements will wait for the explicit wait time
 */
public class CollectionSize extends CollectionsCondition {
    private final int value;
    private final Executor executor = (Executor) ObjectSupplier.instanceOf(Executor.class);

    public CollectionSize(int value) {
        this.value = value;
    }

    @Override
    public void verify(boolean isVisible, boolean isMultiple, List<By> locators) {

        Duration waitDuration = Duration.ofSeconds(1);

        new FluentWait<>(executor)
                .pollingEvery(Duration.ofMillis(500))
                .forDuration(waitDuration)
                .withMessage(String.format("Expected number of elements is %d but could find %d element[s]", value, (int) executor.isVisible(isVisible).withMultipleElements(isMultiple).usingLocator(locators).invokeCommand(GetSize.class, "getSize")))
                .until(e -> (int) e
                        .isVisible(isVisible)
                        .withMultipleElements(isMultiple)
                        .usingLocator(locators)
                        .invokeCommand(GetSize.class) == value);
    }
}
