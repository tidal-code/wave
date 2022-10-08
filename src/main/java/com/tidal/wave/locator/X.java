package com.tidal.wave.locator;

import java.util.Locale;

public class X {

    private X() {
    }

    public static String path(String toParse) {
        return parser(toParse);
    }

    private static String parser(String locator) {
        char charToSplit = locator.charAt(locator.matches(".*with.*") ? locator.indexOf("with") - 1 : locator.indexOf("contains") - 1);
        String[] xpathArray = locator.split(String.format("\\%s", charToSplit));

        if (locator.contains("'")) {
            xpathArray[3] = locator.substring(locator.indexOf("'") + 1, locator.lastIndexOf("'"));
        }

        StringBuilder xpath = new StringBuilder("//");
        xpath.append(xpathArray[0].toLowerCase(Locale.ROOT));
        xpath.append("[");

        if (xpathArray[1].startsWith("with")) {
            if (xpathArray[2].equals("text")) {
                xpath.append(xpathArray[2].toLowerCase(Locale.ROOT));
                xpath.append("()");
            } else {
                xpath.append("@");
                xpath.append(xpathArray[2]);
            }
            xpath.append("='");
            xpath.append(xpathArray[3]);
            xpath.append("']");
        }

        if (xpathArray[1].startsWith("contains")) {
            xpath.append("contains(");
            if (xpathArray[2].equals("text")) {
                xpath.append(xpathArray[2].toLowerCase(Locale.ROOT));
                xpath.append("()");
            } else {
                xpath.append("@");
                xpath.append(xpathArray[2]);
            }
            xpath.append(", '");
            xpath.append(xpathArray[3]);
            xpath.append("')]");
        }
        return xpath.toString();
    }
}
