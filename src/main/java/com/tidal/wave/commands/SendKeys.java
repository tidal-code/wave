package com.tidal.wave.commands;

import com.tidal.wave.command.Command;
import com.tidal.wave.command.CommandAction;
import com.tidal.wave.command.CommandContext;
import com.tidal.wave.command.Commands;
import com.tidal.wave.counter.TimeCounter;
import com.tidal.wave.exceptions.CommandExceptions;
import com.tidal.wave.supplier.ObjectSupplier;
import com.tidal.wave.webelement.Element;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class SendKeys extends CommandAction implements Command {

    private final Supplier<Map<Class<? extends Throwable>, Supplier<String>>> ignoredExceptions = this::ignoredEx;
    private final Element webElement = (Element) ObjectSupplier.instanceOf(Element.class);
    private final TimeCounter timeCounter = new TimeCounter();

    private CommandContext context;
    private CharSequence[] charSequences;

    @Override
    public void contextSetter(CommandContext context) {
        this.context = context;
        this.charSequences = context.getSequence();
    }

    @Override
    public Map<Class<? extends Throwable>, Supplier<String>> ignoredEx() {
        return CommandExceptions.Of.sendKeys();
    }

    public void sendKeysAction() {
        WebElement element = webElement.getElement(context);
        element.sendKeys(charSequences);
    }

    public void sendKeys() {
        timeCounter.restart();
        super.execute(Commands.InputCommands.SEND_KEYS.toString(), ignoredExceptions, timeCounter);
    }
}
