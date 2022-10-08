package com.tidal.wave.data;

public enum ElementData implements DataEnum {

    LOCATOR("byLocator"),
    INDEX("index");

    final String elementValue;

    ElementData(String elementValue) {
        this.elementValue = elementValue;
    }

    @Override
    public String getKey() {
        return elementValue;
    }

}
