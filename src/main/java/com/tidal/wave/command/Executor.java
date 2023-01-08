package com.tidal.wave.command;

import com.tidal.wave.supplier.ObjectSupplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.beans.Introspector;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Executor implements ExecutorCommands {

    private static final Logger logger = LogManager.getLogger(Executor.class);

    Map<String, Command> commandCollection = new ConcurrentHashMap<>(200);
    CommandContext context = (CommandContext) ObjectSupplier.instanceOf(CommandContext.class);
    List<Class<? extends Command>> commands = new LinkedList<>();

    Command getInstance(Class<? extends Command> inputClass) {
        String className = inputClass.getSimpleName();
        try {
            if (commandCollection.get(className) == null) {
                logger.debug(String.format("No instance found for class %s, creating a new one", className));
                Command classInstance = inputClass.getDeclaredConstructor().newInstance();
                commandCollection.put(className, classInstance);
            }
        } catch (Exception e) {
            logger.error(String.format("Exception thrown with class initiation for %s", className));
            logger.error(e.getMessage());
        }
        return commandCollection.get(className);
    }

    @Override
    public <T> T invokeCommand(Class<? extends Command> commandClass, String method) {
        getInstance(commandClass).contextSetter(context);
        return getInstance(commandClass).execute(method);
    }

    @Override
    public <T> T invokeCommand(Class<? extends Command> commandClass) {
        getInstance(commandClass).contextSetter(context);
        commands.add(commandClass);
        return getInstance(commandClass).execute(Introspector.decapitalize(commandClass.getSimpleName()));
    }

    @Override
    public void invokeCommand() {
        commands.forEach(c -> getInstance(c).execute(Introspector.decapitalize(c.getSimpleName())));
    }

    @Override
    public void clearCommands() {
        commands.clear();
    }

    @Override
    public Executor withMultipleElements(boolean isTrue) {
        context.setMultiple(isTrue);
        return this;
    }

    @Override
    public Executor withText(String text) {
        context.setTextInput(text);
        return this;
    }

    @Override
    public Executor withTabIndex(int index) {
        context.setTabIndex(index);
        return this;
    }

    @Override
    public Executor withTimeToWait(int seconds) {
        context.setSecondsToWait(seconds);
        return this;
    }

    @Override
    public Executor withAttribute(String attributeName) {
        context.setAttributeName(attributeName);
        return this;
    }

    @Override
    public Executor withCharSequence(CharSequence... sequence) {
        context.setSequence(sequence);
        return this;
    }

    @Override
    public Executor withSelectIndex(int index) {
        context.setSelectIndex(index);
        return this;
    }

    @Override
    public Executor isVisible(boolean visible) {
        context.setVisibility(visible);
        return this;
    }

    @Override
    public Executor usingLocator(List<By> locators) {
        context.setLocatorSet(locators);
        return this;
    }

    @Override
    public Executor withXYCords(int xCords, int yCords) {
        context.setXYCords(xCords, yCords);
        return this;
    }

    @Override
    public Executor withZoomLevel(double zoomLevel) {
        context.setZoomLevel(zoomLevel);
        return this;
    }
}
