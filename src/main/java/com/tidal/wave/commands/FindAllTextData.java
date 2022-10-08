package com.tidal.wave.commands;

import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.utils.CheckString;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public final class FindAllTextData extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private boolean visibility;

    @Override
    public void contextSetter(CommandContext context) {
        this.locatorSet = context.getLocatorSet();
        this.visibility = context.getVisibility();
    }

    @Override
    protected Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.TypeOf.stale();
    }

    public List<String> findAllTextDataAction() {
        List<WebElement> elements = webElement.getElements(locatorSet, visibility);

        List<String> textContent = elements.stream().map(WebElement::getText).collect(Collectors.toList());

        Predicate<List<String>> contentCheck = l -> l.stream().anyMatch(e -> !CheckString.isNullOrEmpty(e));

        if (contentCheck.test(textContent)) {
            return textContent;
        }
        textContent = elements.stream().map(e -> e.getAttribute("value")).collect(Collectors.toList());

        if (contentCheck.test(textContent)) {
            return textContent;
        }
        textContent = elements.stream().map(e -> e.getAttribute("innerHTML")).collect(Collectors.toList());

        return textContent;
    }

    public List<String> findAllTextData() {
        timeCounter.restart();
        return super.execute(Commands.GetCommands.FIND_ALL_TEXT_DATA.toString(), ignoredExceptions, timeCounter);
    }
}
