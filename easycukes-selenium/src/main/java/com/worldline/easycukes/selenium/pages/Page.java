package com.worldline.easycukes.selenium.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.browserlaunchers.Sleeper;
import org.openqa.selenium.interactions.Actions;

import com.worldline.easycukes.commons.context.Configuration;
import com.worldline.easycukes.selenium.utils.SeleniumConstants;
import com.worldline.easycukes.selenium.utils.SeleniumHelper;

/**
 * Page Object base class. It provides the base structure and properties for a
 * page object to extend.
 *
 * @author mechikhi
 * @version 1.0
 */
public class Page {

	private String baseUrl = null;

	/** This page's WebDriver */
	private final WebDriver driver;

	private final By notifBy = By.cssSelector(Configuration
			.get(SeleniumConstants.NOTIFICATON_BY_CSS_KEY));

	private final By loadProgress = By.cssSelector(Configuration
			.get(SeleniumConstants.LOADING_PROGRESS_BY_CSS_KEY));

	private final List<String> handledWindowList = new ArrayList<String>();

	/**
	 * @param baseUrl
	 *            the base URL
	 * @param driver
	 */
	public Page(WebDriver driver, String baseUrl) {
		this.driver = driver;
		this.baseUrl = baseUrl;
	}

	/**
	 * Send text keys to the element that finds by selector.
	 *
	 * @param by
	 *            selector to find the element
	 * @param text
	 */
	public void sendText(By by, String text) {
		final WebElement element = getWebElement(by);
		if ("text".equalsIgnoreCase(element.getAttribute("type")))
			element.clear();
		element.sendKeys(text);
	}

	/**
	 *
	 */
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	/**
	 * switch focus of WebDriver to the next found window handle (that's your
	 * newly opened window)
	 *
	 */
	public void switchToNewWindow() {
		handledWindowList.add(driver.getWindowHandle());
		Sleeper.sleepTightInSeconds(3);
		for (final String winHandle : driver.getWindowHandles())
			if (!handledWindowList.contains(winHandle)) {
				driver.switchTo().window(winHandle);
				break;
			}
	}

	/**
	 * switch focus of WebDriver to the previous window
	 *
	 */
	public void switchToPreviousWindow() {

		// close newly opened window
		driver.close();
		// switch back to the previous window
		final int index = handledWindowList.size() - 1;
		driver.switchTo().window(handledWindowList.get(index));
		handledWindowList.remove(index);
	}

	/**
	 *
	 * @param by
	 *            selector to find the element
	 */
	public void switchToFrame(By by) {
		driver.switchTo().frame(getWebElement(by));
	}

	/**
	 *
	 * @param by
	 *            selector to find the element
	 * @return
	 */
	public String getText(By by) {
		return getWebElement(by).getText();
	}

	/**
	 *
	 * @param by
	 *            selector to find the element
	 */
	public void submit(By by) {
		getWebElement(by).submit();
	}

	/**
	 *
	 */
	public void click(By by) {
		getWebElement(by).click();
	}

	/**
	 * Is the text present in page.
	 *
	 * @param text
	 * @return
	 */
	public boolean isTextPresent(String text) {
		return driver.getPageSource().contains(text);
	}

	/**
	 * Is the Element in page. if it does not find the element throw
	 * NoSuchElementException, thus returns false.
	 *
	 * @param by
	 *            selector to find the element
	 * @return
	 */
	public boolean isElementPresent(By by) {
		try {
			getWebElement(by);
			return true;
		} catch (final TimeoutException e) {
			return false;
		} catch (final NoSuchElementException e) {
			return false;
		}
	}

	/**
	 *
	 * @param by
	 * @param text
	 * @return
	 */
	public void waitUntilElementContainsText(final By by, final String text,
			int timeout) {
		SeleniumHelper.waitUntilElementContainsText(driver, by, text, timeout);
	}

	/**
	 *
	 * @param by
	 * @param text
	 * @return
	 */
	public void waitUntilElementContainsText(final By by, final String text) {
		SeleniumHelper.waitUntilElementContainsText(driver, by, text);
	}

	/**
	 *
	 * @param by
	 * @param attribute
	 * @return
	 */
	public void waitUntilElementContainsAttribute(final By by,
			final String attribute) {
		SeleniumHelper.waitUntilElementContainsAttribute(driver, by, attribute);
	}

	/**
	 * Checks if the elment is in the DOM and displayed.
	 *
	 * @param by
	 *            selector to find the element
	 * @return true or false
	 */
	public boolean isDisplayed(By by) {
		try {
			return driver.findElement(by).isDisplayed();
		} catch (final StaleElementReferenceException e) {
			return false;
		} catch (final NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Close the browser
	 */
	public void quit() {
		driver.quit();
	}

	/**
	 *
	 * @param by
	 *            selector to find the element
	 * @return the first WebElement
	 */
	public WebElement getWebElement(final By by) {

		if (!by.equals(notifBy))
			waitLoadingPage();
		return SeleniumHelper.waitForElementToBePresent(driver, by);
	}

	/**
	 *
	 * @return
	 */
	public void waitLoadingPage() {
		waitUntilElementIsHidden(loadProgress);
		waitUntilElementIsHidden(notifBy);
		Sleeper.sleepTightInSeconds(1);
	}

	/**
	 *
	 * @return
	 */
	public String waitNotification() {
		final String notification = getWebElement(notifBy).getText();
		waitUntilElementIsHidden(notifBy);
		return notification;
	}

	/**
	 *
	 * @return
	 */
	public void waitUntilElementIsHidden(final By by) {
		SeleniumHelper.waitUntilElementIsHidden(driver, by);
	}

	/**
	 * Allows to navigate to a given URL through the WebDriver .
	 *
	 * @param path
	 */
	public void navigateTo(String path) {
		final String url = path.contains(baseUrl) ? path : baseUrl + path;
		driver.navigate().to(url);
		waitLoadingPage();
	}

	/**
	 * Allows TODO
	 *
	 */
	public void refresh() {
		driver.navigate().refresh();
		Sleeper.sleepTightInSeconds(2);
		waitLoadingPage();
	}

	/**
	 *
	 * @param selector
	 */
	public void moveToElement(By selector) {
		final Actions builder = new Actions(driver);
		builder.moveToElement(getWebElement(selector)).perform();
		builder.release();
	}

	public void moveToAndClick(By selector) {
		final Actions builder = new Actions(driver);
		builder.moveToElement(getWebElement(selector)).click().perform();
		builder.release();
	}

	/**
	 *
	 */
	public void acceptAlert() {
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
	}

	/**
	 *
	 * @param text
	 */
	public void sendTextToAlertAndAccept(String text) {
		final Alert alert = driver.switchTo().alert();
		alert.sendKeys(text);
		alert.accept();
		driver.switchTo().defaultContent();
	}

	/**
	 *
	 * @param by
	 * @param text
	 */
	public void selectByText(final By by, final String text) {
		SeleniumHelper.waitUntilTextIsSelected(driver, by, text);
	}

	public void selectByValue(final By by, final String value) {
		SeleniumHelper.waitUntilValueIsSelected(driver, by, value);
	}

}
