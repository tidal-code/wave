package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import dev.tidalcode.wave.exceptions.SetTextException;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.webelement.Element;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class SetText extends CommandAction implements Command<Void> {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private TimeCounter timeCounter;
    private String inputText;

    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
        this.inputText = context.getTextInput();
    }

    @Override
    public CommandContext getCommandContext() {
        return context;
    }

    Function<CommandContext, Void> function = e -> {
        if (timeCounter == null) {
            timeCounter = new TimeCounter();
        }

        Function<WebElement, String> currentPropertyValue = w -> w.getDomProperty("value");

        WebElement element = webElement.getElement(context);
        if (!currentPropertyValue.apply(element).equals(inputText)) {
            element.sendKeys(inputText);
        }
        if (timeCounter.timeElapsed(Duration.ofSeconds(2))) {
            int existingCharsLength = currentPropertyValue.apply(element).length();
            for (int i = 0; i < existingCharsLength; i++) {
                element.sendKeys(Keys.BACK_SPACE);
            }
            element.sendKeys(inputText);
        }
        if (!currentPropertyValue.apply(element).equals(inputText)) {
            element.clear();
            throw new SetTextException("Error when tried to input text using setText method");
        }
        timeCounter = null;
        return null;
    };

    @Override
    public Function<CommandContext, Void> getFunction() {
        return function;
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.sendKeys();
    }

    public void setTextAction() {
        function.apply(context);
    }

    public void setText() {
        timeCounter = new TimeCounter();
        super.execute(Commands.InputCommands.SET_TEXT.toString(), ignoredExceptions, timeCounter);
    }
}
