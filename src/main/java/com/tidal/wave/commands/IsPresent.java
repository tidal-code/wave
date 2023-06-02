package com.tidal.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class IsPresent extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

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
        return !elements.isEmpty();
    };

    @Override
    public Function<CommandContext, Boolean> getFunction() {
        return function;
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public boolean isPresentAction() {
        List<WebElement> elements = webElement.getElements(context);
        return !elements.isEmpty();
    }

    public boolean isPresent() {
        timeCounter.restart();
        return super.execute(Commands.StateCheckCommands.IS_PRESENT.toString(), ignoredExceptions, timeCounter);
    }
}
