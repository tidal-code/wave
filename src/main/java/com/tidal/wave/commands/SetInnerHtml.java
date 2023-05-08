package com.tidal.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class SetInnerHtml extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private CommandContext context;
    private String inputText;

    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
        this.inputText = context.getTextInput();
    }

    @Override
    public CommandContext getCommandContext() {
        return context;
    }

    Function<CommandContext, Void> function = e -> {
        Function<WebElement, String> expectedValue = w -> w.getAttribute("innerHTML");

        WebElement element = webElement.getElement(context);
        ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript(String.format("arguments[0].innerHTML='%s';", inputText), element);

        if (!expectedValue.apply(element).equals(inputText)) {
            element.clear();
            throw new ElementNotInteractableException("Element Not Interactable");
        }
        return Void.TYPE.cast(null);
    };

    @Override
    public Function<CommandContext, Void> getFunction() {
        return function;
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.sendKeys();
    }

    public void setInnerHtmlAction() {
       function.apply(context);
    }

    public void setInnerHtml() {
        timeCounter.restart();
        super.execute(Commands.InputCommands.SET_INNER_HTML.toString(), ignoredExceptions, timeCounter);
    }
}
