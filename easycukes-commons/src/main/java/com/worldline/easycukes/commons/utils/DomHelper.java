package com.worldline.easycukes.commons.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	private final static Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	private static DocumentBuilderFactory dbFactory = DocumentBuilderFactory
			.newInstance();

	/**
	 * Allows to edit an element in xml file
	 * 
	 * @param filename
	 *            the path to xml file
	 * @param name
	 *            the name of the element whose the text will be changed
	 * @param value
	 *            the text to set in the element content
	 * @throws Exception
	 *             if anything's going wrong while editing the file
	 */
	public static void setElementContent(String filename, String name,
			String value) throws Exception {
		LOGGER.info("Editing the xml file " + filename);

		Document doc = parseDocument(filename);
		Node node = getNode(doc, name);
		if (node != null) {
			node.setNodeValue(value);

			// write the DOM object to the file
			LOGGER.info("Injecting data in the xml file");
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File(filename));
			transformer.transform(domSource, streamResult);
		} else {
			LOGGER.warn("No XML element found with the given name " + name);
		}
	}

	/**
	 * Returns the text content of the node with the given name
	 * 
	 * @param filename
	 *            the path to xml file
	 * @param name
	 *            the name of the element whose the text will be returned
	 * @return the content of the element with the given name
	 * @throws Exception
	 */
	public static String getElementContent(String filename, String name)
			throws Exception {
		Document doc = parseDocument(filename);
		Node node = getNode(doc, name);
		if (node != null) {
			return node.getTextContent();
		}
		return null;

	}

	/**
	 * Gets the element node with the given name
	 * 
	 * @param doc
	 *            a DOM Document object representing the XML content
	 * @param name
	 *            the name of the node to return
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
	 * @param filename
	 *            the path to XML file
	 * @return a DOM Document object representing the XML content
	 */
	private static Document parseDocument(String filename) throws Exception {
		LOGGER.info("Loading the xml file " + filename);
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		LOGGER.info("parse the content of the file");
		return dBuilder.parse(new File(filename));
	}

}
