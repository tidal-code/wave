package com.tidal.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class SelectByText extends CommandAction implements Command<String> {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
    }

    @Override
    public CommandContext getCommandContext() {
        return context;
    }

    Function<CommandContext, String> function = e -> {
        WebElement element = webElement.getElement(context);
        Select select = new Select(element);
        select.selectByVisibleText(context.getTextInput());
        return select.getFirstSelectedOption().getText();
    };

    @Override
    public Function<CommandContext, String> getFunction() {
        return function;
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public String selectByTextAction() {
        return function.apply(context);
    }

    public String selectByText() {
        timeCounter.restart();
        return super.execute(Commands.SelectCommands.SELECT_BY_TEXT.toString(), ignoredExceptions, timeCounter);
    }
}
