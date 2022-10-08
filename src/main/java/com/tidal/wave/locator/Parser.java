package com.tidal.wave.locator;

import org.openqa.selenium.By;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private Parser() {
    }

    /**
     * IdParser class parses string locators prefixed with '#' and 'id:'. <br>
     * If parser cannot determine the type, the control will be given to the next parser.
     */
    static class IdParser extends LocatorParser {

        public IdParser() {
            super();
        }

        @Override
        public By parse(String locator) {
            if (locator.startsWith("#"))
                return By.id(locator.substring(1));

            if (locator.startsWith("id:"))
                return By.id(locator.replace("id:", ""));

            return this.nextLocatorParser.parse(locator);
        }
    }

    /**
     * XPathParser class parses string locators prefixed with '//', "(//" and './/'. <br>
     * String locators prefixed with 'title:' also will be parsed by this class. <br>
     * If parser cannot determine the type, the control will be given to the next parser.
     */
    static class XPathParser extends LocatorParser {

        public XPathParser() {
            super();
        }

        @Override
        public By parse(String locator) {
            if (locator.startsWith("//") || locator.startsWith("(//") || locator.startsWith(".//"))
                return By.xpath(locator);

            if (locator.startsWith("title:")) {
                locator = locator.replace("title:", "");
                return By.xpath(String.format("//*[@title='%s']", locator));
            }

            return this.nextLocatorParser.parse(locator);
        }
    }

    /**
     * NameTagNameParser parses string locators prefixed with 'name:' and 'tagName:' <br>
     * If parser cannot determine the type, the control will be given to the next parser.
     */
    static class NameTagNameParser extends LocatorParser {

        public NameTagNameParser() {
            super();
        }

        @Override
        public By parse(String locator) {
            if (locator.startsWith("name:"))
                return By.name(locator.replace("name:", ""));

            if (locator.startsWith("tagName:"))
                return By.tagName(locator.replace("tagName:", ""));

            return this.nextLocatorParser.parse(locator);
        }
    }

    /**
     * LinkTextParser parses string locators prefixed with 'linkText:' and 'partialLinkText:' <br>
     * If parser cannot determine the type, the control will be given to the next parser.
     */
    static class LinkTextParser extends LocatorParser {

        public LinkTextParser() {
            super();
        }

        @Override
        public By parse(String locator) {
            if (locator.startsWith("linkText:"))
                return By.linkText(locator.replace("linkText:", ""));

            if (locator.startsWith("partialLinkText:"))
                return By.partialLinkText(locator.replace("partialLinkText:", ""));

            return this.nextLocatorParser.parse(locator);
        }
    }

    /**
     * CSSParser parses string locators prefixed with 'css:' <br>
     * If parser cannot determine the type, the control will be given to the next parser.
     */
    static class CSSParser extends LocatorParser {

        public CSSParser() {
            super();
        }

        @Override
        public By parse(String locator) {
            if (locator.startsWith("css:"))
                return By.cssSelector(locator.replace("css:", ""));

            return this.nextLocatorParser.parse(locator);
        }
    }

    /**
     * ClassParser parses string locators prefixed with '.' (dot) notation.
     * It will parse 'class:' and 'className:' as well. <br>
     * If parser cannot determine the type, the control will be given to the next parser.
     */
    static class ClassParser extends LocatorParser {

        public ClassParser() {
            super();
        }

        @Override
        public By parse(String locator) {
            if (locator.startsWith("."))
                return By.className(locator.substring(1));

            if (locator.startsWith("class:") || locator.startsWith("className:")) {
                locator = locator.replace("class:", "").replace("className:", "");
                return By.className(locator);
            }

            return this.nextLocatorParser.parse(locator);
        }
    }

    /**
     * <p>Text parser is the last parser in order. Any locator failed to get parsed by other parsers come to TextParser.
     * Text parser interprets all its input as a text component in the application.
     * In rare occasions when other parsers fail to parse a given input due to incorrect
     * prefixes in locators, it would come to the text parser. The text parser would then wrap it as a text locator.
     * This may throw a TimeoutException as the wrong locator has been used. </p>
     * <br>
     * <p>This class will not pass the responsibility to the next parser. So the in the chain of responsibility, this class should come last.</p>
     *
     * <p>Text parser also handles the plain text description locators.
     */
    static class TextParser extends LocatorParser {

        public TextParser() {
            super();
        }

        @Override
        public By parse(String locator) {
            if (locator.startsWith("text:")) {
                locator = locator.replace("text:", "");
                return By.xpath(String.format("//*[text()='%s']", locator));
            }

            Pattern pattern = Pattern.compile("[. ]with|contains[ .]");
            Matcher matcher = pattern.matcher(locator);
            if (matcher.find()) {
                return By.xpath(X.path(locator));
            }

            return By.xpath(String.format("//*[text()='%s']", locator));
        }
    }
}
