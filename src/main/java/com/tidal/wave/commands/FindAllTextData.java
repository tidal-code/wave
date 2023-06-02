package com.tidal.wave.commands;

import com.tidal.utils.counter.TimeCounter;
import com.tidal.utils.utils.CheckString;
import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public final class FindAllTextData extends CommandAction implements Command<List<String>> {

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

    Function<CommandContext, List<String>> function = e -> {
        List<WebElement> elements = webElement.getElements(context);

        List<String> textContent = elements.stream().map(WebElement::getText).collect(Collectors.toList());

        Predicate<List<String>> contentCheck = l -> l.stream().anyMatch(v -> !CheckString.isNullOrEmpty(v));

        if (contentCheck.test(textContent)) {
            return textContent;
        }
        textContent = elements.stream().map(v -> v.getAttribute("value")).collect(Collectors.toList());

        if (contentCheck.test(textContent)) {
            return textContent;
        }
        textContent = elements.stream().map(v -> v.getAttribute("innerHTML")).collect(Collectors.toList());

        return textContent;
    };

    @Override
    public Function<CommandContext, List<String>> getFunction() {
        return function;
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public List<String> findAllTextDataAction() {
       return function.apply(context);
    }

    public List<String> findAllTextData() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.FIND_ALL_TEXT_DATA.toString(), ignoredExceptions, timeCounter);
    }
}
