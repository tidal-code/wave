package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.webelement.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class Check extends CommandAction implements Command<Void> {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();


    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public void checkAction() {
        WebElement element = webElement.getElement(context);
        if (!element.isSelected()) {
            ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript("arguments[0].click();", element);
        }
    }

    @Override
    public CommandContext getCommandContext() {
        return context;
    }

    final Function<CommandContext, Void> function = e -> {
        WebElement element = webElement.getElement(context);
        if (!element.isSelected()) {
            ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript("arguments[0].click();", element);
        }
        return null;
    };

    @Override
    public Function<CommandContext, Void> getFunction() {
        return function;
    }

    public void check() {
        timeCounter.restart();
        super.execute(Commands.ClickCommands.CHECK.toString(), ignoredExceptions, timeCounter);
    }
}
