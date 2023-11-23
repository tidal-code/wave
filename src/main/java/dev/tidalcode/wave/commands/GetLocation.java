package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.webelement.Element;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class GetLocation extends CommandAction implements Command<Point> {

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

    Function<CommandContext, Point> function = e -> {
        WebElement element = webElement.getElement(context);
        return element.getLocation();
    };

    @Override
    public Function<CommandContext, Point> getFunction() {
        return function;
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public Point getLocationAction() {
        return function.apply(context);
    }

    public Point getLocation() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.GET_LOCATION.toString(), ignoredExceptions, timeCounter);
    }
}
