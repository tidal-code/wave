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

    public static String findElementInShadowRoot() {
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


    public static String dragAndDropFileUpload() {
        return "var target = arguments[0]," +
                "    offsetX = 0," +
                "    offsetY = 0," +
                "    document = target.ownerDocument || document," +
                "    window = document.defaultView || window;" +
                "" +
                "var input = document.createElement('INPUT');" +
                "input.type = 'file';" +
                "input.style.display = 'none';" +
                "input.onchange = function () {" +
                "  var rect = target.getBoundingClientRect()," +
                "      x = rect.left + (offsetX || (rect.width >> 1))," +
                "      y = rect.top + (offsetY || (rect.height >> 1))," +
                "      dataTransfer = { files: this.files };" +
                "" +
                "  ['dragenter', 'dragover', 'drop'].forEach(function (name) {" +
                "    var evt = document.createEvent('MouseEvent');" +
                "    evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);" +
                "    evt.dataTransfer = dataTransfer;" +
                "    target.dispatchEvent(evt);" +
                "  });" +
                "" +
                "  setTimeout(function () { document.body.removeChild(input); }, 25);" +
                "};" +
                "document.body.appendChild(input);" +
                "return input;";
    }
}
