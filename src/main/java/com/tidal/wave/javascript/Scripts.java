package com.tidal.wave.javascript;

public class Scripts {

    //Not to be instantiated
    private Scripts() {
    }

    public static String getCssAttributes() {
        return "var s = '';" +
                "var o = getComputedStyle(arguments[0]);" +
                "for(var i = 0; i < o.length; i++){" +
                "s+=o[i] + ':' + o.getPropertyValue(o[i])+';';}" +
                "return s;";
    }

    public static String getAttributes() {
        return "var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) " +
                "{ items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value };" +
                " return items;";
    }

    public static String isInViewPort() {
        return "const rect = arguments[0].getBoundingClientRect();\n" +
                "    return (\n" +
                "        rect.top >= 0 &&\n" +
                "        rect.left >= 0 &&\n" +
                "        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&\n" +
                "        rect.right <= (window.innerWidth || document.documentElement.clientWidth)\n" +
                "    );";
    }
}
