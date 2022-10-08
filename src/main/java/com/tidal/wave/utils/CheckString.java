package com.tidal.wave.utils;

public class CheckString {

    //Not to be instantiated
    private CheckString() {
    }

    public static boolean isNullOrEmpty(String value){
        return value == null || value.isEmpty();
    }

    public static boolean isNotNullOrEmpty(String value){
        return value != null && !value.isEmpty();
    }
}
