package com.tidal.wave.command;

import com.tidal.wave.data.CommandStore;
import com.tidal.wave.data.IntervalTime;
import com.tidal.wave.data.MaxTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Introspector;
import java.util.LinkedList;
import java.util.List;


@SuppressWarnings("rawtypes")
public class Executor implements ExecutorCommands {

    private CommandContext context;
    private final int[] xyCordsArray = new int[2];
    private boolean isMultiple = false;
    private boolean isVisible = true;
    private String textInput;
    private String attributeName;
    private int selectIndex;
    private int tabIndex;
    private int hoverWaitTime;
    private double zoomLevel;
    private CharSequence[] sequence;
    private List<String> locators = new LinkedList<>();
    private boolean shadowDomPresence;
    private int elementIndex;
    private boolean debugMode;
    private MaxTime maxTime;
    private IntervalTime intervalTime;

    private static final Logger logger = LoggerFactory.getLogger(Executor.class);
    List<Command> commands = new LinkedList<>();


    Command getInstance(Class<? extends Command> inputClass) {
        String className = inputClass.getSimpleName();
        Command classInstance = null;
        try {
            classInstance = inputClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            logger.error(String.format("Exception thrown with class initiation for %s", className));
            logger.error(e.getMessage());
        }
        return classInstance;
    }

    /**
     * This method is used to invoke a command with a specific method.
     * This method will not store the commands for later execution.
     * Retry methods use this method to invoke the command.
     *
     * @param commandClass The class of the command to be invoked
     * @param method       The method to be invoked
     * @param <U>          The return type of the method
     * @return The return type of the method
     */
    @Override
    @SuppressWarnings("unchecked")
    public <U> U invokeCommand(Class<? extends Command> commandClass, String method) {
        setCommandContext();
        CommandContext context = getContext();
        Command command = getInstance(commandClass);
        command.contextSetter(context);
        return (U) command.execute(method);
    }

    /**
     * This method is used to invoke a command with a specific method.
     * This method will store the commands for later execution.
     * The locator will be stored temporarily in the context and will be used later.
     * The reason is that the retry methods may overwrite the locator in the CommandContext class.
     *
     * @param commandClass The class of the command to be invoked
     * @param <U>          The return type of the method
     * @return The return type of the method
     */
    @Override
    @SuppressWarnings("unchecked")
    public <U> U invokeCommand(Class<? extends Command> commandClass) {
        setCommandContext();
        CommandContext context = getContext();
        Command command = getInstance(commandClass);
        command.contextSetter(context);
        CommandStore.storeCommand(command);
        locators = context.getLocators();
        return (U) command.execute(Introspector.decapitalize(command.getClass().getSimpleName()));
    }


    @Override
    public void invokeCommand() {
        CommandStore.getCommands().forEach(c -> c.execute(Introspector.decapitalize(c.getClass().getSimpleName())));
    }

    @Override
    public void clearCommands() {
        commands.clear();
    }

    @Override
    public Executor withMultipleElements(boolean isTrue) {
        isMultiple = isTrue;
        return this;
    }

    @Override
    public Executor withText(String text) {
        textInput = text;
        return this;
    }

    @Override
    public Executor withTabIndex(int index) {
        tabIndex = index;
        return this;
    }

    @Override
    public Executor withTimeToWait(int seconds) {
        hoverWaitTime = seconds;
        return this;
    }

    @Override
    public Executor withAttribute(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    @Override
    public Executor withCharSequence(CharSequence... sequence) {
        this.sequence = sequence;
        return this;
    }

    @Override
    public Executor withSelectIndex(int index) {
        selectIndex = index;
        return this;
    }

    @Override
    public Executor isVisible(boolean visible) {
        isVisible = visible;
        return this;
    }

    @Override
    public Executor presenceOfShadowDom() {
        shadowDomPresence = true;
        return this;
    }

    @Override
    public Executor usingLocator(List<String> locators) {
        this.locators = locators;
        return this;
    }

    @Override
    public Executor withXYCords(int xCords, int yCords) {
        xyCordsArray[0] = xCords;
        xyCordsArray[1] = yCords;
        return this;
    }

    @Override
    public Executor withZoomLevel(double zoomLevel) {
        this.zoomLevel = zoomLevel;
        return this;
    }

    @Override
    public Executor withElementIndex(int index) {
        this.elementIndex = index;
        return this;
    }

    @Override
    public Executor pageRefreshData(MaxTime maxTime, IntervalTime intervalTime) {
        this.maxTime = maxTime;
        this.intervalTime = intervalTime;
        return this;
    }

    public Executor debugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return this;
    }

    private void setCommandContext() {
        context = new CommandContext();
        context.setIntervalTime(intervalTime);
        context.setMaxRefreshTime(maxTime);
        context.setLocatorSet(locators);
        context.setDebugMode(debugMode);
        context.setAttributeName(attributeName);
        context.setMultiple(isMultiple);
        context.setVisibility(isVisible);
        context.setSequence(sequence);
        context.setElementIndex(elementIndex);
        context.setHoverWaitTime(hoverWaitTime);
        context.setShadowDomPresence(shadowDomPresence);
        context.setTabIndex(tabIndex);
        context.setZoomLevel(zoomLevel);
        context.setSelectIndex(selectIndex);
        context.setTextInput(textInput);
        context.setXYCords(xyCordsArray[0], xyCordsArray[1]);
    }

    public CommandContext getContext() {
        if (context == null) {
            setCommandContext();
        }
        return context;
    }
}
