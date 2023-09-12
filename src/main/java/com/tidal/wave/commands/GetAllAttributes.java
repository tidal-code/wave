package com.tidal.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.javascript.Scripts;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused, unchecked")
public final class GetAllAttributes extends CommandAction implements Command<Map<String, String>> {

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

    Function<CommandContext, Map<String, String>> function = e -> {
        WebElement element = webElement.getElement(context);
        return (Map<String, String>) ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript(Scripts.getAttributes(), element);
    };

    @Override
    public Function<CommandContext, Map<String, String>> getFunction() {
        return function;
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public Map<String, String> getAllAttributesAction() {
        WebElement element = webElement.getElement(context);
        return (Map<String, String>) ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript(Scripts.getAttributes(), element);
    }

    public Map<String, String> getAllAttributes() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.GET_ALL_ATTRIBUTES.toString(), ignoredExceptions, timeCounter);
    }
}
