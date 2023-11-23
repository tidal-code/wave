package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import dev.tidalcode.wave.exceptions.RuntimeTestException;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.webelement.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class DragAndDrop extends CommandAction implements Command<Void> {
    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private List<String> locators;

    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
        this.context.setVisibility(false);
        this.locators = context.getLocators();
    }

    @Override
    public CommandContext getCommandContext() {
        return context;
    }

    Function<CommandContext, Void> function = e -> {
        if (locators.size() <= 1) {
            throw new RuntimeTestException(
                    "Expecting two locators but found only one. \n" +
                            "For drag and drop two web elements needed to be found. \n" +
                            "Use the format find(StringLocator(x)).thenFind(StringLocator(y)).dragAndDrop(); "
            );
        }

        List<String> linkedListOne = new LinkedList<>();
        linkedListOne.add(locators.get(0));
        context.setLocatorSet(linkedListOne);
        WebElement sourceElement = webElement.getElement(context);

        List<String> linkedListTwo = new LinkedList<>();
        linkedListTwo.add(locators.get(1));
        context.setLocatorSet(linkedListTwo);
        WebElement targetElement = webElement.getElement(context);

        new Actions(((RemoteWebElement) sourceElement).getWrappedDriver()).dragAndDrop(sourceElement, targetElement).perform();

        return null;
    };

    @Override
    public Function<CommandContext, Void> getFunction() {
        return function;
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public void dragAndDropAction() {
        function.apply(context);
    }

    public void dragAndDrop() {
        timeCounter.restart();
        super.execute(Commands.MoveCommands.DRAG_AND_DROP.toString(), ignoredExceptions, timeCounter);
    }
}
