package com.tidal.wave.xml;

import com.tidal.wave.exceptions.XMLHandlerException;
import org.w3c.dom.*;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.tidal.wave.xml.Processors.*;

public class XmlBuilder {
    private String nodeXPath = "$";
    private String xmlFileContent;

    public XmlBuilder(String nodeXPath, String xmlFileContent) {
        this.xmlFileContent = xmlFileContent;
        this.nodeXPath = pathParser(nodeXPath);
    }

    public XmlBuilder(String xmlFileContent) {
        this.xmlFileContent = xmlFileContent;
        this.nodeXPath = pathParser(nodeXPath);
    }


    /**
     * Extracts the transformed xml as a string
     *
     * @return the transformed xml string
     */
    public String extract() {
        return xmlFileContent;
    }

    /**
     * When methods are chained to modify the XML, and if there is a need to traverse/change the xml node,
     * use this method
     *
     * @param newXPath specifies the new xml path
     * @return - XmlBuilder class
     */
    public XmlBuilder newPath(String newXPath) {
        nodeXPath = pathParser(newXPath);
        return this;
    }

    /**
     * Changes the text content of an already existing node
     *
     * @param newNodeTextValue new value to replace
     * @return - XmlBuilder class
     */
    public XmlBuilder withNewNodeContent(String newNodeTextValue) {
        Document document = docBuilder(xmlFileContent);
        document.getDocumentElement().normalize();

        NodeList nodes = getNodeList(nodeXPath, document);

        for (int idx = 0; idx < nodes.getLength(); idx++) {
            nodes.item(idx).setTextContent(newNodeTextValue);
        }

        xmlFileContent = transformer(document);
        return this;
    }

    /**
     * Changes the attribute value of an existing node with existing attribute
     *
     * @param attribute         the attribute to find and change the value
     * @param newAttributeValue the attribute value to replace
     * @return - XmlBuilder class
     */
    public XmlBuilder withUpdatedAttribute(String attribute, String newAttributeValue) {
        Document document = docBuilder(xmlFileContent);
        document.getDocumentElement().normalize();

        NodeList nodes = getNodeList(nodeXPath, document);

        for (int index = 0; index < nodes.getLength(); index++) {
            NamedNodeMap attributes = nodes.item(index).getAttributes();
            Node attrib = attributes.getNamedItem(attribute);
            if (attributes.getLength() == 0) {
                throw new XMLHandlerException(String.format("No attribute found with name %s and xpath %s", attribute, nodeXPath));
            }
            attrib.setTextContent(newAttributeValue);
        }
        xmlFileContent = transformer(document);
        return this;
    }

    /**
     * Method to add a new node with a value
     *
     * @param newNodeToAppend  the node name of the new element
     * @param newNodeTextValue the text content of the new node
     * @return - XmlBuilder class
     */
    public XmlBuilder withAppendedNode(String newNodeToAppend, String newNodeTextValue) {
        return withAppendedNode(newNodeToAppend, newNodeTextValue, null, null);
    }

    /**
     * Method to add a new node without any text content and one set of attribute value pair
     *
     * @param newNodeToAppend   the node name of the new element
     * @param newAttribute      new attribute to be added
     * @param newAttributeValue new attribute value to be added
     * @return - XmlBuilder class
     */
    public XmlBuilder withAppendedNode(String newNodeToAppend, String newAttribute, String newAttributeValue) {
        return withAppendedNode(newNodeToAppend, "", newAttribute, newAttributeValue);
    }


    /**
     * Method to add a new node with a value and one set of attribute value pair
     *
     * @param newNodeToAppend   the node name of the new element
     * @param newNodeTextValue  the text content of the new node
     * @param newAttribute      new attribute to be added
     * @param newAttributeValue new attribute value to be added
     * @return - XmlBuilder class
     */
    public XmlBuilder withAppendedNode(String newNodeToAppend, String newNodeTextValue, String newAttribute, String newAttributeValue) {
        Map<String, String> map = new HashMap<>();

        if (!isNullOrEmpty(newAttribute) && !isNullOrEmpty(newAttributeValue)) {
            map.put(newAttribute, newAttributeValue);
        }
        return withAppendedNode(newNodeToAppend, newNodeTextValue, map);
    }

    /**
     * Method to add a new node with a value and multiple set of attributes
     * The attributes need to be captured into a map to pass as the parameter
     * Updates the first element found. If need to update a specific node, search with
     * more accurate xpath
     *
     * @param newNodeToAppend  the node name of the new element
     * @param newNodeTextValue the text content of the new node
     * @param attributes       the map containing the set of one or more attribute and value pairs
     * @return - XmlBuilder class
     */
    public XmlBuilder withAppendedNode(String newNodeToAppend, String newNodeTextValue, Map<String, String> attributes) {
        Document document = docBuilder(xmlFileContent);

        Element newNode = document.createElement(newNodeToAppend);
        newNode.appendChild(document.createTextNode(newNodeTextValue));

        if (!attributes.isEmpty()) {
            attributes.forEach(newNode::setAttribute);
        }

        Node node = getNodeList(nodeXPath, document).item(0);
        node.appendChild(newNode);

        xmlFileContent = transformer(document);
        return this;
    }

    public Document getDocument(){
        return docBuilder(xmlFileContent);
    }


}
