package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.webelement.Element;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class GetSize extends CommandAction implements Command<Integer> {

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

    Function<CommandContext, Integer> function = e -> webElement.getElements(context).size();

    @Override
    public Function<CommandContext, Integer> getFunction() {
        return function;
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public int getSizeAction() {
        return webElement.getElements(context).size();
    }

    public int getSize() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.GET_SIZE.toString(), ignoredExceptions, timeCounter);
    }
}
