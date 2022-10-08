package com.tidal.wave.commands;

import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Supplier;

public final class ClickByJS extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean visibility;
    private boolean isMultiple;

    @Override
    public void contextSetter(CommandContext context) {
        this.visibility = context.getVisibility();
        locatorSet = context.getLocatorSet();
        isMultiple = context.isMultiple();
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.click();
    }

    public void clickByJSAction() {
        WebElement element = webElement.getElement(locatorSet, visibility, isMultiple);
        ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript("arguments[0].click();", element);
    }

    public void clickByJS() {
        timeCounter.restart();
        super.execute(Commands.ClickCommands.CLICK_BY_JS.toString(), ignoredExceptions, timeCounter);
    }

}
