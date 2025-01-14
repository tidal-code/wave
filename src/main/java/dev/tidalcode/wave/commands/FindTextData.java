package dev.tidalcode.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import dev.tidalcode.wave.supplier.ObjectSupplier;
import dev.tidalcode.wave.command.Command;
import dev.tidalcode.wave.command.CommandAction;
import dev.tidalcode.wave.command.CommandContext;
import dev.tidalcode.wave.command.Commands;
import dev.tidalcode.wave.exceptions.CommandExceptions;
import dev.tidalcode.wave.webelement.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.google.common.base.Strings.isNullOrEmpty;

@SuppressWarnings("unused")
public final class FindTextData extends CommandAction implements Command<String> {

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
        String textContent;

        WebElement element = webElement.getElement(context);
        textContent = element.getText();

        if (!isNullOrEmpty(textContent)) {
            return textContent;
        }
        textContent = element.getDomAttribute("value");

        if (!isNullOrEmpty(textContent)) {
            return textContent;
        }
        textContent = element.getDomAttribute("innerHTML");

        if (!isNullOrEmpty(textContent)) {
            return textContent;
        }
        textContent = (String) ((JavascriptExecutor) ((RemoteWebElement) element).getWrappedDriver()).executeScript("return arguments[0].innerHTML;", element);

        if (!isNullOrEmpty(textContent)) {
            return textContent;
        }
        return "";
    };

    @Override
    public Function<CommandContext, String> getFunction() {
        return function;
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public String findTextDataAction() {
        return function.apply(context);
    }

    public String findTextData() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.FIND_TEXT_DATA.toString(), ignoredExceptions, timeCounter);
    }
}