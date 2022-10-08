package com.tidal.wave.locator;

import com.tidal.wave.exceptions.PendingException;
import org.openqa.selenium.By;

/**
 * Class to parse the string locator to Selenium 'By' locator type.
 * String locator de-couples test suites from direct Selenium dependency.
 * Follow framework guidelines to pass the locator string in the correct format
 *
 * To ensure that the locators are parsed properly,
 * the order should be kept in the below order in the static block:  <br>
 * - {@link Parser.IdParser} <br>
 * - {@link Parser.XPathParser} <br>
 * - {@link Parser.NameTagNameParser} <br>
 * - {@link Parser.LinkTextParser} <br>
 * - {@link Parser.CSSParser} <br>
 * - {@link Parser.ClassParser} <br>
 * - {@link Parser.TextParser} <br>
 *  <br>
 * The order ensures that the text parser will be evaluated in the end as plain string.
 *
 *  <br>
 *
 */
public class LocatorMatcher {

    private static final LocatorParser idParser = new Parser.IdParser();
    private static final LocatorParser xpathParser = new Parser.XPathParser();
    private static final LocatorParser nameTagNameParser = new Parser.NameTagNameParser();
    private static final LocatorParser linkTextParser = new Parser.LinkTextParser();
    private static final LocatorParser cssParser = new Parser.CSSParser();
    private static final LocatorParser classParser = new Parser.ClassParser();
    private static final LocatorParser textParser = new Parser.TextParser();

    private static final LocatorParser locatorParser;

    //So as not to instantiate.
    private LocatorMatcher() {
    }


    //The order should be kep exactly as per the class documentation.
    static{
        idParser.setNextParser(xpathParser);
        xpathParser.setNextParser(nameTagNameParser);
        nameTagNameParser.setNextParser(linkTextParser);
        linkTextParser.setNextParser(cssParser);
        cssParser.setNextParser(classParser);
        classParser.setNextParser(textParser);

        locatorParser = idParser;
    }


    public static By getMatchedLocator(String locatorMatcher) {
        //Static locator type to throw pending exception when a failing test is written.
        if (locatorMatcher.equalsIgnoreCase("//pending")) {
            throw new PendingException("This locator is pending to be added");
        }

        //The chain of command to determine the locator and return the parsed 'By' locator.
        return locatorParser.parse(locatorMatcher);
    }
}




