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

public final class RightClick extends CommandAction implements Command {

    final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean visibility;
    private boolean isMultiple;

    @Override
    public void contextSetter(CommandContext context) {
        this.isMultiple = context.isMultiple();
        this.visibility = context.getVisibility();
        this.locators = context.getLocators();
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public void rightClickAction() {
        WebElement element = webElement.getElement(locators, visibility, isMultiple);
        new Actions(((RemoteWebElement) element).getWrappedDriver()).contextClick(element).perform();
    }

    public void rightClick() {
        timeCounter.restart();
        super.execute(Commands.ClickCommands.RIGHT_CLICK.toString(), ignoredExceptions, timeCounter);
    }
}
