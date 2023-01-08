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

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class GetAttribute extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean visibility;
    private boolean isMultiple;
    private String attributeName;

    @Override
    public void contextSetter(CommandContext context) {
        this.isMultiple = context.isMultiple();
        this.visibility = context.getVisibility();
        this.locators = context.getLocators();
        this.attributeName = context.getAttributeName();
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public String getAttributeAction() {
        WebElement element = webElement.getElement(locators, visibility, isMultiple);
        return element.getAttribute(attributeName);
    }

    public String getAttribute() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.GET_ATTRIBUTE.toString(), ignoredExceptions, timeCounter);
    }
}
