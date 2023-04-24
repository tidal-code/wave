package com.tidal.wave.commands;

import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;

public final class MoveByOffSet implements Command {

    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private CommandContext context;
    private List<By> locators;
    private boolean isMultiple;
    private int[] xyCords;

    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
        this.context.setVisibility(false);
    }

    public void moveByOffSet() {
        WebElement element = webElement.getElement(context);
        new Actions(((RemoteWebElement) element).getWrappedDriver()).moveToElement(element, xyCords[0], xyCords[1]);
    }
}
