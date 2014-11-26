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
package com.worldline.easycukes.selenium.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.worldline.easycukes.commons.context.Configuration;
import com.worldline.easycukes.commons.context.ExecutionContext;
import com.worldline.easycukes.commons.utils.Constants;
import com.worldline.easycukes.selenium.WebDriverFactory;
import com.worldline.easycukes.selenium.utils.SeleniumConstants;

/**
 * TODO
 * 
 * @author mechikhi
 * @version 1.0
 */
public class PageManager {

	/**
	 * Just a {@link Logger}...
	 */
	private static final Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	/*
	 * This simple line does all the mutlithread magic. For more details please
	 * refer to the src link above :)
	 */
	private static ThreadLocal<Page> page = new ThreadLocal<Page>();

	/**
	 * Allows to initialize this instance of {@link PageManager} by creating the
	 * {@link WebDriver}
	 * 
	 * @param baseUrl
	 *            the base url
	 * @throws Exception
	 */
	public static void initialize(String baseUrl) throws Exception {

		if (Configuration.isProxyNeeded())
			Configuration.configureProxy();
		if (page.get() == null) {
			String browserName = ExecutionContext.get("browserName");
			if (browserName == null)
				browserName = Configuration
						.getEnvProperty(SeleniumConstants.BROWSER_KEY);
			WebDriver driver = null;
			if (!"true".equals(Configuration
					.getEnvProperty(SeleniumConstants.USE_REMOTE_KEY)))
				driver = WebDriverFactory.newLocalWebDriver(browserName);
			else
				try {
					driver = WebDriverFactory.newRemoteWebDriver(browserName);
				} catch (final Exception e) {
					LOGGER.warn("Setup : " + e.getMessage());
					driver = WebDriverFactory.newLocalWebDriver(browserName);
				}
			driver.manage().window().maximize();
			page.set(new Page(driver, baseUrl));
		}
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public static Page getPage() {
		return page.get();
	}

}