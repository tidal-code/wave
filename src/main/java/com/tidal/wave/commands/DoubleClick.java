package com.tidal.wave.commands;

import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class DoubleClick extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();
    private CommandContext context;


    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.click();
    }

    public void doubleClickAction() {
        WebElement element = webElement.getElement(context);
        new Actions(((RemoteWebElement) element).getWrappedDriver()).doubleClick(element).perform();
    }

    public void doubleClick() {
        timeCounter.restart();
        super.execute(Commands.ClickCommands.DOUBLE_CLICK.toString(), ignoredExceptions, timeCounter);
    }
}
