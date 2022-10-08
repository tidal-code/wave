package com.tidal.wave.propertieshandler;

import org.junit.Assert;
import org.junit.Test;

public class ConfigPropertiesTest {

    @Test
    public void readConfig() {
        String personName = PropertiesFinder.getProperty("person.name");
        Assert.assertEquals("John Doe", personName);
    }
}
