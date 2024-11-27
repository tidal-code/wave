package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.exceptions.RuntimeTestException;
import dev.tidalcode.wave.page.Page;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.wait.ThreadSleep;
import dev.tidalcode.wave.webelement.Element;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class PageRefresh extends CommandAction implements Command<Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(PageRefresh.class);
    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();
    private final TimeCounter pageRefreshTimeCounter = new TimeCounter();


    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
        this.context.setVisibility(false);
    }

    @Override
    public CommandContext getCommandContext() {
        return context;
    }

    Function<CommandContext, Boolean> function = e -> {
        List<WebElement> elements = webElement.getElements(context);

        int maxTime = context.getMaxRefreshTime().getMaxTime();
        int intervalTime = context.getRefreshIntervalTime().getIntervalTime();

        while (elements.isEmpty()) {
            if (pageRefreshTimeCounter.timeElapsed(Duration.ofSeconds(maxTime))) {
                String message = String.format("Element '%s' not found within the maximum time of %s seconds", context.getLocators().get(0), maxTime);
                message = message + String.format("\nTotal time elapsed: %s seconds", pageRefreshTimeCounter.timeElapsed().getSeconds());
                throw new RuntimeTestException(message);
            }
            Page.refresh();
            ThreadSleep.forSeconds(intervalTime);
            elements = webElement.getElements(context);
        }

        logger.info("Page Refresh Action found " + elements.size() + " elements with locator " + context.getLocators().get(0));

        pageRefreshTimeCounter.restart();
        return true;
    };

    @Override
    public Function<CommandContext, Boolean> getFunction() {
        return function;
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public boolean pageRefreshAction() {
        return function.apply(context);
    }

    public boolean pageRefresh() {
        timeCounter.restart();
        return super.execute(Commands.PageCommands.REFRESH_PAGE_ACTION.toString(), ignoredExceptions, timeCounter);
    }
}
