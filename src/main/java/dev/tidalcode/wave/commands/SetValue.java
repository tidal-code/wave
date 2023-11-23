package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.webelement.Element;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class SetValue extends CommandAction implements Command<Void> {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();
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
        Function<WebElement, String> expectedValue = v -> v.getAttribute("value");

        WebElement element = webElement.getElement(context);
        ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript(String.format("arguments[0].value='%s';", inputText), element);

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

    public void setValueAction() {
        function.apply(context);
    }

    public void setValue() {
        timeCounter.restart();
        super.execute(Commands.InputCommands.SET_VALUE.toString(), ignoredExceptions, timeCounter);
    }

}
