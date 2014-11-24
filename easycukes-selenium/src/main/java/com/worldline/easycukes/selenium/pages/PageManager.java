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
				browserName = Configuration.get(SeleniumConstants.BROWSER_KEY);
			WebDriver driver = null;
			if (!"true".equals(Configuration
					.get(SeleniumConstants.USE_REMOTE_KEY)))
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