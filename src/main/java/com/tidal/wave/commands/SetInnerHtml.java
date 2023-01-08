package com.tidal.wave.commands;

import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.counter.TimeCounter;
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

    private boolean isMultiple;
    private boolean visibility;
    private String inputText;

    @Override
    public void contextSetter(CommandContext context) {
        this.inputText = context.getTextInput();
        this.locators = context.getLocators();
        this.visibility = context.getVisibility();
        this.isMultiple = context.isMultiple();
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.sendKeys();
    }

    public void setInnerHtmlAction() {
        Function<WebElement, String> expectedValue = e -> e.getAttribute("innerHTML");

        WebElement element = webElement.getElement(locators, visibility, isMultiple);
        ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript(String.format("arguments[0].innerHTML='%s';", inputText), element);

        if (!expectedValue.apply(element).equals(inputText)) {
            element.clear();
            throw new ElementNotInteractableException("Element Not Interactable");
        }
    }

    public void setInnerHtml() {
        timeCounter.restart();
        super.execute(Commands.InputCommands.SET_INNER_HTML.toString(), ignoredExceptions, timeCounter);
    }
}
