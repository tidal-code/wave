package com.tidal.wave.commands;

import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ScrollToView extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();
    private boolean visibility;
    private boolean isMultiple;

    @Override
    public void contextSetter(CommandContext context) {
        this.locatorSet = context.getLocatorSet();
        this.visibility = context.getVisibility();
        this.isMultiple = context.isMultiple();
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.notInteractableAndStale();
    }

    public void scrollToViewAction() {
        WebElement element = webElement.getElement(locatorSet, false,  isMultiple);
        ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript("arguments[0].scrollIntoView(true);", element);

        element = webElement.getElement(locatorSet, false,  isMultiple);

        if(visibility && !element.isDisplayed()){
            throw new ElementNotInteractableException("Element is not in view, applying scroll-to-view again");
        }
    }

    public void scrollToView() {
        timeCounter.restart();
        super.execute(Commands.MoveCommands.SCROLL_TO_VIEW.toString(), ignoredExceptions, timeCounter);
    }
}
