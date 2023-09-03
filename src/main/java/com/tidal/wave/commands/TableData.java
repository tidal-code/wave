package com.tidal.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.components.Table;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class TableData extends CommandAction implements Command<Table> {

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

    Function<CommandContext, Table> function = e -> {
        return new Table();
//        webElement.getElements(context).size();
    };

    @Override
    public Function<CommandContext, Table> getFunction() {
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
