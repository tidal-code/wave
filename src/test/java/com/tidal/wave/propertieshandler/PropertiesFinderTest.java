package com.tidal.wave.propertieshandler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PropertiesFinderTest {

    @Test
    public void getPassword() {
        String password = PropertiesFinder.getProperty("login.password");
        assertEquals("Infor2022!", password);
    }

    @Test
    public void getPasswortempd() {
        String password = PropertiesFinder.getProperty("eam_secret");
        System.out.println(password);
    }

    @Test
    public void getIPSPassword() {
        String password = PropertiesFinder.getProperty("login.password.test");
        assertEquals("Billing2026!", password);
    }

    @Test
    public void getEnvProperty() {
        assertEquals(PropertiesFinder.getProperty("eam_secret"), System.getenv("eam_secret"));
        System.out.println(PropertiesFinder.getProperty("eam_secret"));
    }

    @Test
    public void getBrowserProperty() {
        assertEquals("chrome", PropertiesFinder.getProperty("browser.name"));
    }
}
