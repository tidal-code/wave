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
    private List<By> locatorSet;
    private boolean isMultiple;
    private int[] xyCords;

    @Override
    public void contextSetter(CommandContext context) {
        this.locatorSet = context.getLocatorSet();
        this.isMultiple = context.isMultiple();
        this.xyCords = context.getXYCords();
    }

    public void moveByOffSet() {
        WebElement element = webElement.getElement(locatorSet, false, isMultiple);
        new Actions(((RemoteWebElement) element).getWrappedDriver()).moveToElement(element, xyCords[0], xyCords[1]);
    }
}
