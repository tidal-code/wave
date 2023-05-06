package com.tidal.wave.data;

import com.tidal.utils.propertieshandler.PropertiesFinder;

public class TestData {

    public static String readProperty(String key){
        return PropertiesFinder.getProperty(key);
    }

    public static String getTestEnvironment(){
        return PropertiesFinder.getEnvironment();
    }
}
