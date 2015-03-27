/*
 * EasyCukes is just a framework aiming at making Cucumber even easier than what it already is.
 * Copyright (C) 2014 Worldline or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package com.worldline.easycukes.commons.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * {@link DomHelper} allows to perform certain operations in relation to the
 * manipulation of xml files.
 *
 * @author mechikhi
 * @version 1.0
 */
public class DomHelper {

    /**
     * {@link Logger} to be used in order to get information during execution
     */
    private final static Logger LOG = LoggerFactory.getLogger(DomHelper.class);

    private DocumentBuilderFactory dbFactory = DocumentBuilderFactory
            .newInstance();

    /**
     * File you want to inspect
     */
    private File file;

    /**
     * Default constructor
     *
     * @param f the file you need to inspect
     */
    public DomHelper(File f) {
        this.file = f;
        LOG.debug("Creating DomHelper for: " + f.getName());
    }

    /**
     * Allows to edit an element in xml file
     *
     * @param name  the name of the element whose the text will be changed
     * @param value the text to set in the element content
     * @throws Exception if anything's going wrong while editing the file
     */
    public void setElementContent(String name,
                                  String value) throws Exception {
        LOG.debug("Editing XML file " + file.getName());
        Document doc = parseDocument();
        Node node = getNode(doc, name);
        if (node != null) {
            node.setNodeValue(value);
            // write the DOM object to the file
            LOG.debug("Injecting " + name + " > " + value + " in XML file");
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(domSource, streamResult);
        } else {
            LOG.warn("No XML element found with the given name " + name);
        }
    }

    /**
     * Returns the text content of the node with the given name
     *
     * @param name the name of the element whose the text will be returned
     * @return the content of the element with the given name
     * @throws Exception
     */
    public String getElementContent(String name)
            throws Exception {
        Document doc = parseDocument();
        Node node = getNode(doc, name);
        if (node != null) {
            return node.getTextContent();
        }
        return null;
    }

    /**
     * Gets the element node with the given name
     *
     * @param doc  a DOM Document object representing the XML content
     * @param name the name of the node to return
     * @return the node element
     * @throws Exception
     */
    private static Node getNode(Document doc, String name) throws Exception {
        NodeList list = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            // get the node having name element
            if (node.getNodeType() == Node.ELEMENT_NODE
                    && name.equals(node.getNodeName())) {
                Node data = node.getChildNodes().item(0);
                if (data.getNodeType() == Node.TEXT_NODE) {
                    return data;
                }
            }
        }
        return null;
    }

    /**
     * This method allows to read a XML file
     *
     * @return a DOM Document object representing the XML content
     */
    private Document parseDocument() throws Exception {
        LOG.debug("Loading XML file " + file);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(file);
    }

}
