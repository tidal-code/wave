package com.tidal.wave.commands;

import com.tidal.wave.browser.Driver;
import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.wait.ActivityWaiter;
import com.tidal.wave.wait.Wait;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class PressEnter extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();
    private CommandContext context;
    private final ActivityWaiter activityWaiter = (ActivityWaiter) ObjectSupplier.instanceOf(ActivityWaiter.class);


    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public void pressEnterAction() {
        WebElement element = webElement.getElement(context);
        new Actions(((RemoteWebElement) element).getWrappedDriver()).sendKeys(element, Keys.ENTER).perform();
        activityWaiter.waitUntilDocReady(Driver.getDriver(), Wait.getBackgroundMaxWait());
    }

    public void pressEnter() {
        timeCounter.restart();
        super.execute(Commands.KeyCommands.PRESS_ENTER_ACTION.toString(), ignoredExceptions, timeCounter);
    }
}
