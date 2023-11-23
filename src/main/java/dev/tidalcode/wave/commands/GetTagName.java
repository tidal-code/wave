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

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class GetTagName extends CommandAction implements Command<String> {

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

    Function<CommandContext, String> function = e -> {
        WebElement element = webElement.getElement(context);
        return element.getTagName();
    };

    @Override
    public Function<CommandContext, String> getFunction() {
        return function;
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public String getTagNameAction() {
        return function.apply(context);
    }

    public String getTagName() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.GET_TAG_NAME.toString(), ignoredExceptions, timeCounter);
    }
}
