package com.tidal.wave.command;


import com.tidal.utils.counter.TimeCounter;
import com.tidal.wave.config.Config;
import com.tidal.wave.data.WaitTime;
import com.tidal.wave.exceptions.MethodInvokerException;
import com.tidal.wave.exceptions.RuntimeTestException;
import com.tidal.wave.stackbuilder.ErrorStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import static com.tidal.wave.data.WaitTimeData.getWaitTime;

public interface Command<T> {
    void contextSetter(CommandContext context);

    CommandContext getCommandContext();

    Function<CommandContext, T> getFunction();

    @SuppressWarnings("unchecked")
    default <X> X execute(String action) {

        if (Config.DEBUG) {
            return (X) executeAction(getFunction());
        }

        Object value;
        Class<?> klass = this.getClass();

        try {
            Method method = klass.getDeclaredMethod(action);
            value = method.invoke(this);
        } catch (NoSuchMethodException e) {
            throw new MethodInvokerException(String.format("No such method with name '%s', in class '%s'", action, klass.getName()), e);
        } catch (InvocationTargetException e) {
            String errorDetail = e.getCause().getMessage();
            errorDetail = new ErrorStack().constructedError(errorDetail, Thread.currentThread().getStackTrace());
            //ReportBuilder class would be using the exception message to sort result types.
            //Changes in the exception messages here needs to be reflected in the ReportBuilder class.
            throw new RuntimeTestException(String.format("Exception caused from action '%s' %s", action, errorDetail));
        } catch (IllegalAccessException e) {
            throw new MethodInvokerException(String.format("Method '%s', in class '%s' has got private/protected access", action, klass.getName()), e);
        }

        return (X) value;
    }

    default <Y> Y executeAction(Function<CommandContext, Y> function) {
        final int duration = Integer.parseInt(getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null ? getWaitTime(WaitTime.DEFAULT_WAIT_TIME) : getWaitTime(WaitTime.EXPLICIT_WAIT_TIME));
        TimeCounter timeCounter = new TimeCounter();

        return function.apply(getCommandContext());
    }

}
