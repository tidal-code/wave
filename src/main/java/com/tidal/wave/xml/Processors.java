package com.tidal.wave.xml;

import com.tidal.wave.exceptions.XMLHandlerException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * <Code>Processors</Code> contains package protected methods to be used
 * exclusively for the XMLBuilder and XMLReader
 */
public class Processors {

    private Processors() {
    }

    /**
     * DocBuilder for the xml string
     *
     * @param xmlContentString the xml content read from the file or input source
     * @return - XmlBuilder class
     */
    protected static Document docBuilder(String xmlContentString) {
        Document document;
        try {
            DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();
            document = docBuilder.parse(new InputSource(new StringReader(xmlContentString)));
            document.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLHandlerException("Error parsing the document", e.getCause());
        }
        return document;
    }

    /**
     * Get a list of all nodes corresponding to the xml path
     *
     * @param nodeXPath specifies the new xml path
     * @param document  the document built from the xml content
     * @return - XmlBuilder class
     */
    protected static NodeList getNodeList(String nodeXPath, Document document) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            return (NodeList) xpath.evaluate(nodeXPath, document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new XMLHandlerException(String.format("Error parsing xpath %s", nodeXPath));
        }
    }


    /**
     * Transforms the document to a stream and in turn to a string
     *
     * @param document the document built from the xml content
     * @return - XmlBuilder class
     */
    public static String transformer(Document document) {
        StringWriter writer;
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
        } catch (TransformerException e) {
            throw new XMLHandlerException("Error transforming the xml document", e.getCause());
        }
        return writer.toString();
    }

    /**
     * Parses the dot (.) separated path into proper xpath syntax.
     * The method adds a double slash to mark the start of the search.
     * The '$' sign will be replaced wth single slash to force the search from the root node. That is the same as root node
     * search in RestAssured.
     *
     * @param path the path to be parsed
     * @return the parsed string in xpath format
     */
    protected static String pathParser(String path) {
        return (path.startsWith("$") ? "/" : "//") + path
                .replace("////", "//")
                .replace("$", "")
                .replace(".@", "O-py0mgJ3UqOJl3_BnyRaQ")  // The GUID is a position marker
                .replace("\\.", "2A8A1278-5866-4BC8-83F0-5B520BB928F4")
                .replaceAll("\\.", "/")
                .replace("O-py0mgJ3UqOJl3_BnyRaQ", "/@")
                .replace("2A8A1278-5866-4BC8-83F0-5B520BB928F4", ".");
    }

}
