package com.tidal.wave.command;

import org.openqa.selenium.By;

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
    private int secondsToWait;
    private double zoomLevel;
    private CharSequence[] sequence;
    private List<By> locators = new LinkedList<>();

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getSecondsToWait() {
        return secondsToWait;
    }

    public void setSecondsToWait(int secondsToWait) {
        this.secondsToWait = secondsToWait;
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

    public List<By> getLocators() {
        return locators;
    }

    public void setLocatorSet(List<By> locators) {
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
}
