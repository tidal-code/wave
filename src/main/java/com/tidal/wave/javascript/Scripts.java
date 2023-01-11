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

    public static String findElementInShadowRoot(){
        return "function findElementById(id, element) {\n" +
                "  // If the element has a shadowRoot property, search for the element inside the shadow DOM\n" +
                "  if (element.shadowRoot) {\n" +
                "    let el = element.shadowRoot.querySelector('#' + id);\n" +
                "    if (el) {\n" +
                "      return el;\n" +
                "    } else {\n" +
                "      // If the element was not found, search for it recursively in the shadow DOM\n" +
                "      return findElementById(id, element.shadowRoot);\n" +
                "    }\n" +
                "  }\n" +
                "  return null;\n" +
                "}\n" +
                "\n" +
                "// Example usage: find an element with the id \"my-element\" inside a shadow DOM\n" +
                "let element = findElementById('my-element', document);\n";
    }
}
