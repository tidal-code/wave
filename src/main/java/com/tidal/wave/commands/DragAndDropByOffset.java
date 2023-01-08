package com.tidal.wave.commands;

import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class DragAndDropByOffset extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean isMultiple;
    private int[] xyCords;

    @Override
    public void contextSetter(CommandContext context) {
        this.locators = context.getLocators();
        this.isMultiple = context.isMultiple();
        this.xyCords = context.getXYCords();
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public void dragAndDropByOffsetAction() {
        List<By> linkedListOne = new LinkedList<>();
        linkedListOne.add(locators.get(0));
        WebElement sourceElement = webElement.getElement(linkedListOne, false, isMultiple);

        new Actions(((RemoteWebElement) sourceElement).getWrappedDriver()).dragAndDropBy(sourceElement, xyCords[0], xyCords[1]).perform();
    }

    public void dragAndDropByOffset() {
        timeCounter.restart();
        super.execute(Commands.MoveCommands.DRAG_AND_DROP_BY_OFFSET.toString(), ignoredExceptions, timeCounter);
    }
}
