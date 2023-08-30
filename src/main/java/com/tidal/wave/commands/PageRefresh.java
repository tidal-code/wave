package com.tidal.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.exceptions.RuntimeTestException;
import com.tidal.wave.page.Page;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.ThreadSleep;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class PageRefresh extends CommandAction implements Command<Boolean> {
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

        while(elements.isEmpty()){
            if(pageRefreshTimeCounter.timeElapsed(Duration.ofSeconds(maxTime))){
                String message = String.format("Element '%s' not found within the maximum time of %s seconds", context.getLocators().get(0), maxTime);
                message = message + String.format("\nTotal time elapsed: %s seconds", pageRefreshTimeCounter.timeElapsed().getSeconds());
                throw new RuntimeTestException(message);
            }
            Page.refresh();
            ThreadSleep.forSeconds(intervalTime);
            elements = webElement.getElements(context);
        }
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
