package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.webelement.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ScrollPage extends CommandAction implements Command<Void> {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();
    private int[] xyCords;

    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
        this.context.setVisibility(false);
        this.xyCords = context.getXYCords();
    }

    @Override
    public CommandContext getCommandContext() {
        return context;
    }

    Function<CommandContext, Void> function = e -> {
        WebElement element = webElement.getElement(context);
        ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript(String.format("window.scrollBy(%d ,%d)", xyCords[0], xyCords[1]), element);
        return Void.TYPE.cast(null);
    };

    @Override
    public Function<CommandContext, Void> getFunction() {
        return function;
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public void scrollPageAction() {
        function.apply(context);
    }

    public void scrollPage() {
        timeCounter.restart();
        super.execute(Commands.MoveCommands.SCROLL_PAGE.toString(), ignoredExceptions, timeCounter);
    }

}
