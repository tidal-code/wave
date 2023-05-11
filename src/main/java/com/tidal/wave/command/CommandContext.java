package com.tidal.wave.command;

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

    public void setShadowDomPresence() {
        shadowDomPresence = true;
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

    public boolean getDebugMode(){
        return debugMode;
    }
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    @Override
    public String toString() {
        return "CommandContext{" +
                "\n xyCordsArray: " + Arrays.toString(xyCordsArray) +
                ", \n Finding Multiple Elements:" + isMultiple +
                ", \n Looking for visible element:" + isVisible +
                ", \n Text input:'" + textInput + '\'' +
                ", \n Attribute Name:'" + attributeName + '\'' +
                ", \n Select Index:" + selectIndex +
                ", \n Tab Index:" + tabIndex +
                ", \n Hover Wait Time:" + hoverWaitTime +
                ", \n Zoom Level:" + zoomLevel +
                ", \n Char Sequence Input:" + Arrays.toString(sequence) +
                ", \n Locators:" + String.join(",", locators) +
                ", \n Shadow DOM Check:" + shadowDomPresence +
                ", \n Element Index:" + elementIndex +
                ", \n Debug Mode:" + debugMode +
                '}';
    }
}
