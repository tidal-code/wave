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
import org.openqa.selenium.support.ui.Select;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class SelectByValue extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean isMultiple;
    private boolean visibility;
    private String selectionValue;

    @Override
    public void contextSetter(CommandContext context) {
        this.isMultiple = context.isMultiple();
        this.locators = context.getLocators();
        this.visibility = context.getVisibility();
        this.selectionValue = context.getTextInput();
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public String selectByValueAction() {
        WebElement element = webElement.getElement(locators, visibility, isMultiple);
        Select select = new Select(element);
        select.selectByValue(selectionValue);
        return select.getFirstSelectedOption().getText();
    }

    public String selectByValue() {
        timeCounter.restart();
        return super.execute(Commands.SelectCommands.SELECT_BY_VALUE.toString(), ignoredExceptions, timeCounter);
    }
}
