package com.tidal.wave.xml;

import com.tidal.wave.exceptions.XMLHandlerException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import static com.tidal.wave.xml.Processors.*;


/**
 * <Code>XMLReader</Code>
 * The class can be utilised to read either a node text value or an attribute value.
 * The path syntax follows Groovy's GPath syntax, though not entirely. This is done to make it
 * more in align with the response verification of RestAssured which
 * follows <b><a style="color:orange" href="https://www.groovy-lang.org/processing-xml.html">Groovy Gpath</a></b> style.
 * The search traverse in one direction only currently.
 * By default the search starts from wherever the element is present.
 * To start the search from the root, add a '$' sign (no quotes) in front of the path.
 *
 * <p> ******************* XML Path examples ************************** </p>
 * <p> * Sample.Tests.Test.@Name </p>
 * <p> * Sample.Tests.Test[@Name = 'POTABLE_WATERS Arsenic (Acid-Soluble)'].@Cost - To get the attribute value</p>
 * <p> * Classification.Codes.Code[@listID] - To get the text content</p>
 * <p> * Sample.Tests.Test[@Name = 'POTABLE_WATERS Arsenic (Acid-Soluble)'].text() </p>
 * <p> * Tests.Test[last()].@ID </p>
 * <p> * Tests.Test[last()-1].@ID </p>
 * <p> * DocumentField.Name[text()='VATAmount'].\.\..Value</p> - To find a value relative to parent
 * <p> * ActionCriteria.ActionExpression.@actionCode - Gets the value of the attribute</p>
 * <p> * ActionCriteria.ActionExpression[@actionCode] - Gets the text content of the node</p>
 *
 */
public class XMLReader {

    private XMLReader(){}

    /**
     * Gets the value of a specific node. Be aware that the path syntax branches out from the node where the
     * multiple elements are present. For example for the below xml :
     * <pre>
     *  {@code
     * <Response>
     *     <Tests>
     *         <Test @id='10'>
     *             <Result>Success</Result>
     *         </Test>
     *         <Test @id='20'>
     *             <Result>Failure</Result>
     *         </Test>
     *     </Tests>
     * </Response>
     * }
     * </pre>
     * To retrieve the second result, the path should be - <p style="color:green"> Response.Tests.Test[2].Result </p>
     * If you use <p style="color:red"> Response.Tests.Test.Result[2] </p> it will throw an error as there is no such element
     * if '$' is given as the path value, then the root node name will be returned
     *
     * @param path           the path to the node
     * @param xmlFileContent the xml file content to parse
     * @return the result derived from the search
     */
    public static String getValue(String path, String xmlFileContent) {
        org.w3c.dom.Document document = docBuilder(xmlFileContent);
        if(path.equals("$")){
            return document.getDocumentElement().getNodeName();
        }
        NodeList nodes = getNodeList(pathParser(path), document);
        if(nodes.getLength() == 0){
            throw new XMLHandlerException(String.format("No node element found from the XML with the path '%s'", path));
        }
        return nodes.item(0).getTextContent();
    }

    /**
     * Retrieves a list of values from either xml nodes or attributes.
     *
     * @param path           path for elements
     * @param xmlFileContent xml string to be parsed
     * @return the list derived from the search
     */
    public static List<String> getValues(String path, String xmlFileContent) {
        org.w3c.dom.Document document = docBuilder(xmlFileContent);
        NodeList nodes = getNodeList(pathParser(path), document);

        List<String> values = new ArrayList<>();
        for (int idx = 0; idx < nodes.getLength(); idx++) {
            values.add(nodes.item(idx).getTextContent());
        }
        return values;
    }

    public static List<Node> getNodes(String path, String xmlFileContent) {
        org.w3c.dom.Document document = docBuilder(xmlFileContent);
        NodeList nodes = getNodeList(pathParser(path), document);

        List<Node> values = new ArrayList<>();
        for (int idx = 0; idx < nodes.getLength(); idx++) {
            values.add(nodes.item(idx));
        }
        return values;
    }

    public static NodeList getNodesList(String path, String xmlFileContent) {
        org.w3c.dom.Document document = docBuilder(xmlFileContent);
        return getNodeList(pathParser(path), document);
    }

}
