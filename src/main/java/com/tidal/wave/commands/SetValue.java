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
public final class SetValue extends CommandAction implements Command {

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
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.sendKeys();
    }

    public void setValueAction() {
        Function<WebElement, String> expectedValue = e -> e.getAttribute("value");

        WebElement element = webElement.getElement(context);
        ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript(String.format("arguments[0].value='%s';", inputText), element);

        if (!expectedValue.apply(element).equals(inputText)) {
            element.clear();
            throw new ElementNotInteractableException("Element Not Interactable");
        }
    }

    public void setValue() {
        timeCounter.restart();
        super.execute(Commands.InputCommands.SET_VALUE.toString(), ignoredExceptions, timeCounter);
    }

}
