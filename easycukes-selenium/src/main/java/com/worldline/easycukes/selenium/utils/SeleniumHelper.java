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
package com.worldline.easycukes.selenium.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import com.worldline.easycukes.commons.Configuration;
import com.worldline.easycukes.commons.helpers.Constants;

public class SeleniumHelper {

	/**
	 * Just a {@link Logger}...
	 */
	private static final Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	/** TODO many waiting time sould be defined */
	static final int WAITING_TIME_OUT_IN_SECONDS = Integer
			.parseInt(Configuration.getEnvironmentSelenium() != null
			&& Configuration.getEnvironmentSelenium().get(
					SeleniumConstants.DEFAULT_WAIT_TIME_SECONDS_KEY) != null ? Configuration
					.getEnvironmentSelenium()
					.get(SeleniumConstants.DEFAULT_WAIT_TIME_SECONDS_KEY)
					.toString()
							: "10");
	static final int POLLING_INTERVAL_IN_MILLIS = 500;

	public static WebElement waitForElementToBePresent(final WebDriver driver,
			final By by) {

		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(WAITING_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
				.pollingEvery(POLLING_INTERVAL_IN_MILLIS, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);

		return wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver webDriver) {
				try {
					if (webDriver.findElement(by).isDisplayed())
						return webDriver.findElement(by);
					return null;
				} catch (final ElementNotVisibleException e) {
					return null;
				}
			}
		});
	}

	/**
	 *
	 * @return
	 */
	public static void waitUntilElementIsHidden(final WebDriver driver,
			final By by) {

		// Waiting for an element to be present on the page, checking for its
		// presence once every second.
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(WAITING_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
				.pollingEvery(POLLING_INTERVAL_IN_MILLIS, TimeUnit.MILLISECONDS)
				.ignoring(TimeoutException.class);

		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				try {
					LOGGER.debug("waiting for hidden " + by);
					final List<WebElement> list = webDriver.findElements(by);
					for (final WebElement webElement : list)
						if (webElement.isDisplayed()) {
							LOGGER.debug("Still displayed !");// TODO
							return false;
						}
					return true;
				} catch (final StaleElementReferenceException e) {
					return true;
				} catch (final NoSuchElementException e) {
					return true;
				}
			}
		});
	}

	public static void waitUntilElementContainsText(final WebDriver driver,
			final By by, final String text) {
		waitUntilElementContainsText(driver, by, text,
				WAITING_TIME_OUT_IN_SECONDS);
	}

	/**
	 *
	 * @param by
	 * @param text
	 * @return
	 */
	public static void waitUntilElementContainsText(final WebDriver driver,
			final By by, final String text, int timeout) {
		// Waiting for an element to be present on the page, checking for its
		// presence once every second.

		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(POLLING_INTERVAL_IN_MILLIS, TimeUnit.MILLISECONDS)
				.ignoring(TimeoutException.class);

		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				try {
					return webDriver.findElement(by).getText().contains(text);
				} catch (final StaleElementReferenceException e) {
					return false;
				} catch (final NoSuchElementException e) {
					return false;
				}
			}
		});
	}

	/**
	 *
	 * @param by
	 * @param text
	 * @return
	 */
	public static void waitUntilElementContainsAttribute(
			final WebDriver driver, final By by, final String attribute) {
		// Waiting for an element to be present on the page, checking for its
		// presence once every second.

		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(WAITING_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
				.pollingEvery(POLLING_INTERVAL_IN_MILLIS, TimeUnit.MILLISECONDS)
				.ignoring(TimeoutException.class);

		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				try {
					return StringUtils.isNotEmpty(webDriver.findElement(by)
							.getAttribute(attribute));
				} catch (final StaleElementReferenceException e) {
					return false;
				} catch (final NoSuchElementException e) {
					return false;
				}
			}
		});
	}

	/**
	 *
	 * @param selector
	 * @param text
	 */
	public static void waitUntilTextIsSelected(final WebDriver driver,
			final By by, final String text) {
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(WAITING_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
				.pollingEvery(POLLING_INTERVAL_IN_MILLIS, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);

		wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver webDriver) {
				final WebElement webElement = webDriver.findElement(by);
				new Select(webElement).selectByVisibleText(text);
				return webElement;
			}
		});
	}

	/**
	 *
	 * @param by
	 * @param text
	 * @return
	 */
	public static void waitUntilValueIsSelected(final WebDriver driver,
			final By by, final String value) {
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(WAITING_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
				.pollingEvery(POLLING_INTERVAL_IN_MILLIS, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);

		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				try {
					final WebElement webElement = webDriver.findElement(by);
					new Select(webElement).selectByValue(value);
					return webElement.getAttribute("value").equals(value);
				} catch (final StaleElementReferenceException e) {
					return false;
				}
			}
		});
	}

}
