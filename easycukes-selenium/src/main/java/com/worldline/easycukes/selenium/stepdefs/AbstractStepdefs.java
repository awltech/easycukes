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