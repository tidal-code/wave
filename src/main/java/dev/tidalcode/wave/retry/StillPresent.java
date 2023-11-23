package dev.tidalcode.wave.retry;

import dev.tidalcode.wave.command.Executor;
import dev.tidalcode.wave.commands.GetSize;
import dev.tidalcode.wave.wait.ThreadSleep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StillPresent extends RetryCondition {

    public static final Logger logger = LoggerFactory.getLogger(StillPresent.class);


    @Override
    public boolean retry(Executor executor) {
        boolean result = (int) executor.invokeCommand(GetSize.class, "getSize") == 0;

        if (!result) {
            executeCommandsIgnoringExceptions(executor);
            ThreadSleep.forMilliS(500);
        } else {
            return true;
        }

        result = (int) executor.invokeCommand(GetSize.class, "getSize") == 0;

        return result;
    }

    public void executeCommandsIgnoringExceptions(Executor executor) {
        try {
            executor.invokeCommand();
        } catch (Exception e) {
            logger.info("Retry exceptions ignored");
        }
    }
}
