package com.tidal.wave.junit;

public class Template {

    private Template() {
    }

    protected static String resultXMLTemplate(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<testsuite errors=\"\" failures=\"\" name=\"io.cucumber.core.plugin.JUnitFormatter\" skipped=\"\" tests=\"\" time=\"\">\n" +
                "</testsuite>";
    }
}
