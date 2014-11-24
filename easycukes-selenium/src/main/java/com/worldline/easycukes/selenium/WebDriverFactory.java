package com.worldline.easycukes.selenium;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.worldline.easycukes.commons.context.Configuration;
import com.worldline.easycukes.commons.utils.Constants;
import com.worldline.easycukes.selenium.utils.SeleniumConstants;

public class WebDriverFactory {

	/**
	 * Just a {@link Logger}...
	 */
	private static final Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	/**
	 * Creates a new local driver instance. supported browsers : chrome and
	 * firefox
	 *
	 * @return a local WebDriver instance
	 *
	 * @throws Exception
	 */
	public static WebDriver newLocalWebDriver(String browserName)
			throws Exception {
		LOGGER.info("Starting a local Driver");
		if ("firefox".equals(browserName))
			return newLocalFirefoxDriver();
		else if ("chrome".equals(browserName))
			return newLocalChromeDriver();
		else
			throw new Exception("Unknown or not supported browser : "
					+ browserName);
	}

	/**
	 * Creates a new remte driver instance. supported browsers : chrome and
	 * firefox
	 *
	 * @return a remote WebDriver instance
	 *
	 * @throws Exception
	 */
	public static WebDriver newRemoteWebDriver(String browserName)
			throws Exception {
		LOGGER.info("Starting a remote Driver");
		if ("firefox".equals(browserName))
			return new RemoteWebDriver(new URL(
					Configuration.get(SeleniumConstants.REMOTE_ADDRESS_KEY)),
					firefoxCapabilities());
		else if ("chrome".equals(browserName))
			return new RemoteWebDriver(new URL(
					Configuration.get(SeleniumConstants.REMOTE_ADDRESS_KEY)),
					chromeCapabilities());
		else
			throw new Exception("Unknown or not supported browser : "
					+ browserName);
	}

	/**
	 * Creates a new Firefox driver instance
	 *
	 * @return a FirefoxDriver instance
	 */
	private static WebDriver newLocalFirefoxDriver() {
		return new FirefoxDriver(firefoxCapabilities());
	}

	/**
	 * Allows to customize and configure the options of a Firefox session
	 *
	 * @return DesiredCapabilities
	 */
	private static DesiredCapabilities firefoxCapabilities() {
		final FirefoxProfile profile = new FirefoxProfile();
		// FirefoxProfile profile = new ProfilesIni().getProfile("default");
		final DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		return capabilities;
	}

	/**
	 * Creates a new chrome driver instance
	 *
	 * @return a ChromeDriver instance
	 */
	private static WebDriver newLocalChromeDriver() {
		final File file_chrome = new File(
				Configuration.get(SeleniumConstants.LOCAL_DRIVER_PATH));
		System.setProperty("webdriver.chrome.driver",
				file_chrome.getAbsolutePath());
		return new ChromeDriver(chromeCapabilities());

	}

	/**
	 * Allows to customize and configure the options of a chrome session
	 *
	 * @return DesiredCapabilities
	 */
	private static DesiredCapabilities chromeCapabilities() {
		final DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		final ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		return capabilities;
	}

}
