package com.tidal.wave.commands;

import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.function.Function;

public final class MoveByOffSet implements Command<Void> {

    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private CommandContext context;
    private int[] xyCords;

    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
        this.xyCords = context.getXYCords();
        this.context.setVisibility(false);
    }

    @Override
    public CommandContext getCommandContext() {
        return context;
    }

    Function<CommandContext, Void> function = e -> {
        WebElement element = webElement.getElement(context);
        new Actions(((RemoteWebElement) element).getWrappedDriver()).moveToElement(element, xyCords[0], xyCords[1]);
        return null;
    };

    @Override
    public Function<CommandContext, Void> getFunction() {
        return function;
    }

    public void moveByOffSet() {
       function.apply(context);
    }
}
