package com.tidal.wave.waiter;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.wave.exceptions.TimeoutException;
import com.tidal.wave.wait.FluentWait;
import org.junit.Test;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class FluentWaitTest {

    @Test
    public void fluentWaitIgnoreExceptions() {
        TimeCounter timeCounter = new TimeCounter();

        new FluentWait<>(false)
                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage("Example of soft assertion that did not fail")
                .forDuration(Duration.ofSeconds(4)).pollingEvery(Duration.ofMillis(500)).until(c -> false);

        System.out.println(timeCounter.timeElapsed().getSeconds());

        assertEquals("4", String.valueOf(timeCounter.timeElapsed().getSeconds()));
    }

    //Will throw TimeoutException as it is the second in the list of exceptions
    @Test(expected = TimeoutException.class)
    public void fluentWaitIgnoreExceptionsThrowsError() {
        new FluentWait<>(false)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(TimeoutException.class)
                .forDuration(Duration.ofSeconds(4)).pollingEvery(Duration.ofMillis(500)).until(c -> false);
    }
}
