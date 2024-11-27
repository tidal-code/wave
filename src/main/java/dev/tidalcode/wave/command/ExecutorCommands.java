package dev.tidalcode.wave.command;

import dev.tidalcode.wave.data.IntervalTime;
import dev.tidalcode.wave.data.MaxTime;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface ExecutorCommands {
    <T> T invokeCommand(Class<? extends Command> commandClass, String method);

    <T> T invokeCommand(Class<? extends Command> commandClass);

    void invokeCommand();

    void clearCommands();

    Executor withMultipleElements(boolean isTrue);

    Executor withText(String text);

    Executor withTabIndex(int index);

    Executor withTimeToWait(int seconds);

    Executor withAttribute(String attributeName);

    Executor withCharSequence(CharSequence... sequence);

    Executor withSelectIndex(int index);

    Executor isVisible(boolean visible);

    Executor presenceOfShadowDom();

    Executor usingLocator(List<String> locators);

    Executor withXYCords(int xCords, int yCords);

    Executor withZoomLevel(double zoomLevel);

    Executor withElementIndex(int index);

    Executor pageRefreshData(MaxTime maxTime, IntervalTime intervalTime);
}
