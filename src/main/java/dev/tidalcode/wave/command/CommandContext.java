package dev.tidalcode.wave.command;

import dev.tidalcode.wave.config.Config;
import dev.tidalcode.wave.data.IntervalTime;
import dev.tidalcode.wave.data.MaxTime;
import dev.tidalcode.wave.data.WaitTime;
import dev.tidalcode.wave.data.WaitTimeData;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommandContext {

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

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getHoverWaitTime() {
        return hoverWaitTime;
    }

    public void setHoverWaitTime(int hoverWaitTime) {
        this.hoverWaitTime = hoverWaitTime;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public CharSequence[] getSequence() {
        return sequence;
    }

    public void setSequence(CharSequence... sequence) {
        this.sequence = sequence;
    }

    public String getTextInput() {
        return textInput;
    }

    public void setTextInput(String textInput) {
        this.textInput = textInput;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public boolean getVisibility() {
        return isVisible;
    }

    public void setVisibility(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public List<String> getLocators() {
        return locators;
    }

    public void setLocatorSet(List<String> locators) {
        this.locators = locators;
    }

    public void setXYCords(int xCords, int yCords) {
        xyCordsArray[0] = xCords;
        xyCordsArray[1] = yCords;
    }

    public int[] getXYCords() {
        return xyCordsArray;
    }

    public double getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(double zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public void setShadowDomPresence(boolean shadowDomPresence) {
        this.shadowDomPresence = shadowDomPresence;
    }

    public boolean getShadowDomPresence() {
        return shadowDomPresence;
    }

    public int getElementIndex() {
        return elementIndex;
    }

    public void setElementIndex(int elementIndex) {
        this.elementIndex = elementIndex;
    }

    public boolean getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public MaxTime getMaxRefreshTime() {
        return maxTime;
    }

    public IntervalTime getRefreshIntervalTime() {
        return intervalTime;
    }

    public void setMaxRefreshTime(MaxTime maxTime) {
        this.maxTime = maxTime;
    }

    public void setIntervalTime(IntervalTime intervalTime) {
        this.intervalTime = intervalTime;
    }

    private final StringBuilder sb = new StringBuilder("CommandContext{");

    @Override
    public String toString() {

        int duration = Integer.parseInt(WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME) == null ? WaitTimeData.getWaitTime(WaitTime.DEFAULT_WAIT_TIME) : WaitTimeData.getWaitTime(WaitTime.EXPLICIT_WAIT_TIME));

        sb.append("\n xyCordsArray: ").append(Arrays.toString(xyCordsArray))
                .append(", \n Finding Multiple Elements:").append(isMultiple)
                .append(", \n Looking for visible element:").append(isVisible)
                .append("  \n Wait duration: ").append(duration).append(" seconds")
                .append(", \n Text input:'").append(textInput)
                .append(", \n Attribute Name:'").append(attributeName)
                .append(", \n Select Index:").append(selectIndex)
                .append(", \n Tab Index:").append(tabIndex)
                .append(", \n Hover Wait Time:").append(hoverWaitTime)
                .append(", \n Zoom Level:").append(zoomLevel)
                .append(", \n Char Sequence Input:").append(Arrays.toString(sequence))
                .append(", \n Locators:").append(String.join(",", locators))
                .append(", \n Shadow DOM Check:").append(shadowDomPresence)
                .append(", \n Element Index:").append(elementIndex)
                .append(", \n Debug Mode:").append((debugMode || Config.DEBUG) ? "true" : "false")
                .append('}');

        return sb.toString();
    }


}
