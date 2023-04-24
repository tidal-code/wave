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

@SuppressWarnings("unused")
public final class Zoom extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private CommandContext context;
    private double zoomLevel;


    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
        this.zoomLevel = context.getZoomLevel();
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public void zoomAction() {
        WebElement element = webElement.getElement(context);
        ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript("document.body.style.zoom='" + zoomLevel + "%';");
    }

    public void zoom() {
        timeCounter.restart();
        super.execute(Commands.ZoomCommands.ZOOM.toString(), ignoredExceptions, timeCounter);
    }

}
