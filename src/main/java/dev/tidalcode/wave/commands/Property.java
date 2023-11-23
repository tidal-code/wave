package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.javascript.Scripts;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.webelement.Element;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class Property extends CommandAction implements Command<Map<String, Object>> {

    private static final Logger logger = LoggerFactory.getLogger(Property.class);

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
    }

    @Override
    public CommandContext getCommandContext() {
        return context;
    }

    Function<CommandContext, Map<String, Object>> function = e -> {
        Map<String, Object> properties = new LinkedHashMap<>();
        WebElement element = webElement.getElement(context);
        properties.put("Is Enabled", element.isEnabled());
        properties.put("Is Visible", element.isDisplayed());
        Point location = element.getLocation();
        properties.put("Location", "X Coordinate: " + location.getX() + ", " + "Y Coordinate " + location.getY());
        properties.put("Tag Name", element.getTagName());
        properties.put("Text Value", element.getText());
        properties.put("Is Selected", element.isSelected());
        Dimension dimension = element.getSize();
        properties.put("Dimension", "Height: " + dimension.getHeight() + " , " + "Width: " + dimension.getWidth());
        properties.put("Accessible Name", element.getAccessibleName());
        properties.put("Aria Role", element.getAriaRole());
        properties.put("Attributes", ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript(Scripts.getAttributes(), element));
        return properties;
    };

    @Override
    public Function<CommandContext, Map<String, Object>> getFunction() {
        return function;
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public void propertyAction() {
        function.apply(context).forEach((k, v) -> logger.info(k.toUpperCase() + " = " + v));
    }

    public void property() {
        timeCounter.restart();
        super.execute("propertyAction", ignoredExceptions, timeCounter);
    }
}
