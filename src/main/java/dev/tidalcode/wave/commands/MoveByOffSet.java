package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.webelement.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class MoveByOffSet extends CommandAction implements Command<Void> {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();
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

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.notInteractableAndStale();
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

    public void moveByOffSetAction() {
        function.apply(context);
    }

    public void moveByOffSet() {
        timeCounter.restart();
        super.execute(Commands.MoveCommands.MOVE_BY_OFFSET.toString(), ignoredExceptions, timeCounter);
    }
}
