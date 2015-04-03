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

import com.worldline.easycukes.selenium.utils.SeleniumHelper;
import lombok.NonNull;
import lombok.Setter;
import org.openqa.selenium.*;
import org.openqa.selenium.browserlaunchers.Sleeper;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Page Object base class. It provides the base structure and properties for a
 * page object to extend.
 *
 * @author mechikhi
 * @version 1.0
 */
public class Page {

    @Setter
    private String baseUrl = null;

    /**
     * This page's WebDriver
     */
    private final WebDriver driver;

    private By notifyBy = null;

    private boolean waitNotificationToHide = false;

    private By loadProgressBy = null;

    private final List<String> handledWindowList = new ArrayList<String>();

    /**
     * Sets the
     *
     * @param driver
     */
    public Page(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Send text keys to the element that finds by selector.
     *
     * @param by   selector to find the element
     * @param text
     */
    public void sendText(By by, @NonNull String text) {
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
     * @param by selector to find the element
     */
    public void switchToFrame(By by) {
        driver.switchTo().frame(getWebElement(by));
    }

    /**
     * @param by selector to find the element
     * @return
     */
    public String getText(By by) {
        return getWebElement(by).getText();
    }

    /**
     * @param by selector to find the element
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
     * @param by selector to find the element
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
     * @param by
     * @param text
     * @return
     */
    public void waitUntilElementContainsText(final By by, final String text,
                                             int timeout) {
        SeleniumHelper.waitUntilElementContainsText(driver, by, text, timeout);
    }

    /**
     * @param by
     * @param text
     * @return
     */
    public void waitUntilElementContainsText(final By by, final String text) {
        SeleniumHelper.waitUntilElementContainsText(driver, by, text);
    }

    /**
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
     * @param by selector to find the element
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
     * @param by selector to find the element
     * @return the first WebElement
     */
    public WebElement getWebElement(final By by) {
        if (!by.equals(notifyBy))
            waitLoadingPage();
        return SeleniumHelper.waitForElementToBePresent(driver, by);
    }

    /**
     * @return
     */
    public void waitLoadingPage() {
        if (loadProgressBy != null)
            waitUntilElementIsHidden(loadProgressBy);
        if (waitNotificationToHide && notifyBy != null)
            waitUntilElementIsHidden(notifyBy);
        Sleeper.sleepTightInSeconds(1);
    }

    /**
     * @return
     */
    public String waitNotification() {
        final String notification = getWebElement(notifyBy).getText();
        if (waitNotificationToHide)
            waitUntilElementIsHidden(notifyBy);
        return notification;
    }

    /**
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
     */
    public void refresh() {
        driver.navigate().refresh();
        Sleeper.sleepTightInSeconds(2);
        waitLoadingPage();
    }

    /**
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
     * @param text
     */
    public void sendTextToAlertAndAccept(String text) {
        final Alert alert = driver.switchTo().alert();
        alert.sendKeys(text);
        alert.accept();
        driver.switchTo().defaultContent();
    }

    /**
     * @param by
     * @param text
     */
    public void selectByText(final By by, final String text) {
        SeleniumHelper.waitUntilTextIsSelected(driver, by, text);
    }

    public void selectByValue(final By by, final String value) {
        SeleniumHelper.waitUntilValueIsSelected(driver, by, value);
    }

    public void setLoadingProgress(By by) {
        this.loadProgressBy = by;
    }

    public void setNotificationBy(By by) {
        this.notifyBy = by;
    }

    public void enableWaitNotificationToHide() {
        this.waitNotificationToHide = true;
    }
}
