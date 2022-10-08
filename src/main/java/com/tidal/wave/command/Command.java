package com.tidal.wave.command;



import com.tidal.wave.exceptions.MethodInvokerException;
import com.tidal.wave.exceptions.RuntimeTestException;
import com.tidal.wave.stackbuilder.ErrorStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface Command {
    void contextSetter(CommandContext context);

    @SuppressWarnings("unchecked")
    default <T> T execute(String action) {
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

        return (T) value;
    }
}
