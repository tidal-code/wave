package dev.tidalcode.wave.command;


import dev.tidalcode.wave.config.Config;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.data.WaitTimeData;
import dev.tidalcode.wave.exceptions.MethodInvokerException;
import dev.tidalcode.wave.exceptions.RuntimeTestException;
import dev.tidalcode.wave.stackbuilder.ErrorStack;
import dev.tidalcode.wave.wait.ThreadSleep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public interface Command<T> {

    Logger logger = LoggerFactory.getLogger(Command.class);

    void contextSetter(CommandContext context);

    CommandContext getCommandContext();

    Function<CommandContext, T> getFunction();

    @SuppressWarnings("unchecked")
    default <X> X execute(String action) {

        CommandContext commandContext = getCommandContext();

        if (Config.SLOW_RUN) {
            ThreadSleep.forSeconds(1);
        }

        if (commandContext.getDebugMode() || Config.DEBUG) {
            logger.info("Executing action '" + action.replace("Action", "").toUpperCase() + "'");
            logger.info(commandContext.toString());
        }

        Object value;
        Class<?> klass = this.getClass();

        try {
            Method method = klass.getDeclaredMethod(action);
            value = method.invoke(this);
        } catch (NoSuchMethodException e) {
            throw new MethodInvokerException(String.format("No such method with name '%s', in class '%s'", action, klass.getName()), e);
        } catch (InvocationTargetException e) {
            if (commandContext.getDebugMode() || Config.DEBUG) {
                e.printStackTrace();
            }
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
        final int duration = Integer.parseInt(WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null ? WaitTimeData.getWaitTime(WaitTime.DEFAULT_WAIT_TIME) : WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME));
        try {
            return function.apply(getCommandContext());
        } catch (Exception e) {
            ThreadSleep.forSeconds((double) duration / 2);
            return function.apply(getCommandContext());
        }
    }
}
