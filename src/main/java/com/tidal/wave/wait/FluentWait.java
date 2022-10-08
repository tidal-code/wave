package com.tidal.wave.wait;


import com.tidal.wave.exceptions.RuntimeTestException;
import com.tidal.wave.exceptions.TimeoutException;
import com.tidal.wave.loggers.Logger;
import org.openqa.selenium.support.ui.Sleeper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Ignoring 'TimeoutException.class', the wait can be used for 'soft' assertion.
 * For that the Timeout Exception should be the first in the list of exceptions provided
 *
 * @param <T> Condition type
 */
public class FluentWait<T> {

    private final Logger logger = new Logger().forClass(FluentWait.class);
    private final T input;
    private final Sleeper sleeper;
    private final List<Class<? extends Throwable>> ignoredExceptions = new ArrayList<>();
    private Duration duration;
    private Duration pollingInterval;
    private Supplier<String> messageSupplier = () -> null;
    private Class<? extends Throwable> throwingException = TimeoutException.class;

    public FluentWait(T input) {
        this.input = input;
        sleeper = Sleeper.SYSTEM_SLEEPER;
    }

    public FluentWait<T> withMessage(String message) {
        this.messageSupplier = () -> message;
        return this;
    }

    public FluentWait<T> withMessage(Supplier<String> messageSupplier) {
        this.messageSupplier = messageSupplier;
        return this;
    }

    public FluentWait<T> forDuration(Duration timeDuration) {
        duration = timeDuration;
        return this;
    }

    public FluentWait<T> pollingEvery(Duration pollingInterval) {
        this.pollingInterval = pollingInterval;
        return this;
    }

    public <K extends Throwable> FluentWait<T> ignoreAll(Collection<Class<? extends K>> types) {
        ignoredExceptions.addAll(types);
        return this;
    }

    public FluentWait<T> ignoring(Class<? extends Throwable> exceptionType) {
        ignoredExceptions.add(exceptionType);
        return this;
    }

    public FluentWait<T> throwing(Class<? extends Throwable> throwingType) {
        throwingException = throwingType;
        return this;
    }

    public <V> V until(Function<? super T, V> isTrue) {
        Duration timeout = duration;
        Duration interval = pollingInterval;

        Clock clock = Clock.systemDefaultZone();

        Instant end = clock.instant().plus(timeout);
        Throwable lastException;

        while (true) {

            V value;

            try {
                value = isTrue.apply(input);

                if (value != null && Boolean.class != value.getClass() || Boolean.TRUE.equals(value)) {
                    return value;
                }

                lastException = null;
            } catch (Exception e) {
                lastException = propagateIfNotIgnored(e);
            }

            if (end.isBefore(clock.instant())) {
                String message = messageSupplier != null ? messageSupplier.get() : null;

                String timeOutMessage = String.format(
                        "Expected condition failed: %s (tried for %d second(s) with %d milliseconds interval)",
                        message == null ? "waiting for " + isTrue : message,
                        timeout.getSeconds(), interval.toMillis());

                RuntimeException ex = null;

                try {
                    Constructor<? extends Throwable> constructor = throwingException.getConstructor(String.class, Throwable.class);
                    ex = (RuntimeException) constructor.newInstance(timeOutMessage, lastException);
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    logger.error(e.getMessage());
                }

                if (ex != null) {
                    if (!ignoredExceptions.isEmpty() && ignoredExceptions.get(0).getName().equals(ex.getClass().getName())) {
                        logger.warn(timeOutMessage);
                        return isTrue.apply(input);
                    }
                    throw ex;
                } else {
                    throw new TimeoutException(timeOutMessage);
                }
            }

            try {
                sleeper.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeTestException(e);
            }
        }
    }

    private Throwable propagateIfNotIgnored(Throwable e) {
        for (Class<? extends Throwable> exception : ignoredExceptions) {
            if (exception.isInstance(e)) {
                return e;
            }
        }
        throw new RuntimeTestException(e);
    }
}
