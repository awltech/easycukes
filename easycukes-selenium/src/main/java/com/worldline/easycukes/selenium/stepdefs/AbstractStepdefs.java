package com.worldline.easycukes.selenium.stepdefs;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.worldline.easycukes.commons.utils.Constants;

/**
 * TODO
 *
 * @author mechikhi
 * @version TODO
 */
public abstract class AbstractStepdefs {

	/**
	 * Just a {@link Logger}...
	 */
	protected static final Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	/**
	 * TODO
	 *
	 * @param selector
	 * @param value
	 * @return
	 * @throws Exception
	 */
	protected By getSelector(String selector, String value) throws Exception {
		By by = null;
		if (selector.equalsIgnoreCase("id"))
			by = By.id(value);
		else if (selector.equalsIgnoreCase("name"))
			by = By.name(value);
		else if (selector.equalsIgnoreCase("class"))
			by = By.className(value);
		else if (selector.equalsIgnoreCase("css"))
			by = By.cssSelector(value);
		else if (selector.equalsIgnoreCase("link"))
			by = By.linkText(value);
		else if (selector.equalsIgnoreCase("tag"))
			by = By.tagName(value);
		else if (selector.equalsIgnoreCase("xpath"))
			by = By.xpath(value);
		if (by == null) {
			LOGGER.error("Unknown type of selector : " + selector);
			throw new Exception("Unknown type of selector : " + selector);
		}
		return by;
	}

}