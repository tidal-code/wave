package com.tidal.wave.command;

import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.MethodInvokerException;
import com.tidal.wave.exceptions.RuntimeTestException;
import com.tidal.wave.wait.ThreadSleep;
import org.openqa.selenium.By;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public abstract class CommandAction {
    protected List<By> locatorSet;

    protected abstract Map<Class<? extends Throwable>, Supplier<String>> ignoredEx();

    private By getLocator() {
        return locatorSet.get(locatorSet.size() - 1);
    }

    @SuppressWarnings("unchecked")
    protected final <T> T execute(String action, Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions, TimeCounter timeCounter) {
        final int duration = Integer.parseInt(getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME) : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME));

        Object value = null;
        Class<?> klass = this.getClass();

        try {
            Method method = klass.getDeclaredMethod(action);
            value = method.invoke(this);
        } catch (NoSuchMethodException e) {
            throw new MethodInvokerException(String.format("No such method with name '%s', in class '%s'", action, klass.getName()), e);
        } catch (IllegalAccessException e) {
            throw new MethodInvokerException(String.format("Method '%s', in class '%s' has got private/protected access", action, klass.getName()), e);
        } catch (InvocationTargetException e) {
            ThreadSleep.forMilliS(500);
            Set<Class<? extends Throwable>> exList = ignoredExceptions.get().keySet();
            String targetedException = e.getTargetException().getClass().toString();

            if (timeCounter.timeElapsed(Duration.ofSeconds(duration))) {
                if (exList.stream().anyMatch(s -> targetedException.contains(s.getSimpleName()))) {
                    throw new RuntimeTestException(String.format(ignoredExceptions.get().get(e.getTargetException().getClass()).get(), getLocator()));
                } else {
                    throw new RuntimeTestException(String.format("Exception caused by %s", e.getCause()));
                }
            }

            if (exList.stream().anyMatch(s -> targetedException.contains(s.getSimpleName()))) {
                execute(action, ignoredExceptions, timeCounter);
            } else {
                throw new RuntimeTestException(String.format("Exception caused by %s", e.getCause()));
            }
        }
        return (T) value;
    }

}
