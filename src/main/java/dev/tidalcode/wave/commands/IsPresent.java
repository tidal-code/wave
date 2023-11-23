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

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class IsPresent extends CommandAction implements Command<Boolean> {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
        context.setVisibility(false);
        context.setMultiple(true);
    }

    @Override
    public CommandContext getCommandContext() {
        return context;
    }

    Function<CommandContext, Boolean> function = e -> {
        List<WebElement> elements = webElement.getElements(context);
        return !elements.isEmpty();
    };

    @Override
    public Function<CommandContext, Boolean> getFunction() {
        return function;
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public boolean isPresentAction() {
        List<WebElement> elements = webElement.getElements(context);
        return !elements.isEmpty();
    }

    public boolean isPresent() {
        timeCounter.restart();
        return super.execute(Commands.StateCheckCommands.IS_PRESENT.toString(), ignoredExceptions, timeCounter);
    }
}
