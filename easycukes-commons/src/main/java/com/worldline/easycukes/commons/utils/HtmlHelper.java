package com.worldline.easycukes.commons.utils;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * {@link HtmlHelper} allows to perform some operations in relation to the
 * manipulation of Html content. It use the API Jsoup for extracting data.
 * 
 * @author mechikhi
 * @version 1.0
 */
public class HtmlHelper {

	/**
	 * {@link Logger} to be used in order to get information during execution
	 */
	private final static Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	/**
	 * Extract the input value from an html content.
	 * 
	 * @param input
	 *            the name of the input in the html form
	 * @param htmlContent
	 *            the html content
	 * @return The value, if it could be extracted, null otherwise.
	 */
	public static String extractInputValueFromHtmlContent(final String input,
			final String htmlContent) {

		Document document = Jsoup.parse(htmlContent);

		Elements elements = document.select("input[name=" + input + "]");

		if (elements == null || elements.isEmpty()) {
			LOGGER.warn(input + " input not found in response.");
			return null;
		}
		Element element = elements.iterator().next();
		return element.val();
	}
}
