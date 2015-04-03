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

import com.worldline.easycukes.commons.DataInjector;
import com.worldline.easycukes.commons.ExecutionContext;
import com.worldline.easycukes.selenium.pages.PageManager;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.openqa.selenium.browserlaunchers.Sleeper;

/**
 * This class aims at containing all the common operations you may use in the
 * tests scenarios. Things like dealing with the configuration, or manipulating
 * external tools, etc.
 *
 * @author mechikhi
 * @version 1.0
 */
@Slf4j
public class SeleniumCommonStepdefs extends SeleniumAbstractStepdefs {

    /**
     * Allows to navigate to a specified path
     *
     * @param path path on which you want to navigate
     */
    @Given("^navigate to path \"([^\"]*)\"$")
    public void navigateToPath(final String path) {
        log.info("Navigate to path : " + path);
        PageManager.getPage().navigateTo(DataInjector.injectData(path));
    }

    /**
     * Allows to refresh the current page
     */
    @Given("^refresh the page$")
    public void refreshPage() {
        log.info("refreshing the page");
        PageManager.getPage().refresh();
    }

    /**
     * Moves the cursor on an element identified by any selector and clicks on
     * it
     *
     * @param selector kind of selector to use in order to identify the element
     * @param value    value of the selector allowing to identify the element
     * @throws Exception
     */
    @Given("^move to and click element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
    public void moveAndClickOnAnElement(String selector, String value)
            throws Exception {
        log.info("move to and click element identified by [" + selector
                + " = " + value + "]");
        PageManager.getPage().moveToAndClick(getSelector(selector, value));
    }

    /**
     * Selects a particular text from an element identified by any selector
     *
     * @param selector kind of selector to use in order to identify the element
     * @param value    value of the selector allowing to identify the element
     * @param text     text to be selected from the element
     * @throws Exception
     */
    @Given("^select element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" by text \"([^\"]*)\"$")
    public void selectAnElement(String selector, String value, String text)
            throws Exception {
        log.info("select element identified by [" + selector + " = " + value
                + "]");
        PageManager.getPage().selectByText(getSelector(selector, value),
                DataInjector.injectData(text));
    }

    /**
     * Selects a particular value from an element identified by any selector
     *
     * @param selector kind of selector to use in order to identify the element
     * @param value    value of the selector allowing to identify the element
     * @param text     value to be used
     * @throws Exception
     */
    @Given("^select element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" by value \"([^\"]*)\"$")
    public void selectAnElementByValue(String selector, String value,
                                       String text) throws Exception {
        log.info("select element identified by [" + selector + " = " + value
                + "]");
        PageManager.getPage().selectByValue(getSelector(selector, value),
                DataInjector.injectData(text));
    }

    /**
     * Clicks on an element identified by any selector
     *
     * @param selector kind of selector to use in order to identify the element
     * @param value    value of the selector allowing to identify the element
     * @throws Exception
     */
    @Given("^click element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
    public void clickOnAnElement(String selector, String value)
            throws Exception {
        log.info("Click on element identified by [" + selector + " = "
                + value + "]");

        PageManager.getPage().click(getSelector(selector, value));
    }

    /**
     * Submits an element identified by any selector
     *
     * @param selector kind of selector to use in order to identify the element
     * @param value    value of the selector allowing to identify the element
     * @throws Exception
     */
    @Given("^submit element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
    public void submitAnElement(String selector, String value) throws Exception {
        log.info("Submit on element identified by [" + selector + " = "
                + value + "]");

        PageManager.getPage().submit(getSelector(selector, value));
    }

    /**
     * Sends a text to an element identified by any selector
     *
     * @param text     text to be sent to the specified element
     * @param selector kind of selector to use in order to identify the element
     * @param value    value of the selector allowing to identify the element
     * @throws Exception
     */
    @Given("^send a text \"([^\"]*)\" to element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
    public void addTextToAnElement(String text, String selector, String value)
            throws Exception {
        log.info("Send text to element identified by [" + selector + " = "
                + value + "]");

        PageManager.getPage().sendText(getSelector(selector, value),
                DataInjector.injectData(text));
    }

    /**
     * Sends a text to an alert and accepts it
     *
     * @param text text to be sent to the alert
     * @throws Exception
     */
    @Given("^send a text \"([^\"]*)\" to alert and accept$")
    public void addTextToAnAlertAndAccept(String text) throws Exception {
        log.info("send a text " + text + " to alert and accept");
        PageManager.getPage().sendTextToAlertAndAccept(
                DataInjector.injectData(text));
    }

    /**
     * Checks if an element identified by any selector is present on the page
     *
     * @param selector kind of selector to use in order to identify the element
     * @param value    value of the selector allowing to identify the element
     * @throws Throwable
     */
    @Then("^the element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" should be present$")
    public void checkPresenceOfAnElement(String selector, String value)
            throws Throwable {
        log.info("Check the presence of element identified by [" + selector
                + " = " + value + "]");
        Assert.assertTrue(PageManager.getPage().isElementPresent(
                getSelector(selector, value)));
    }

    /**
     * Checks if an element identified by any selector is not present on the
     * page
     *
     * @param selector kind of selector to use in order to identify the element
     * @param value    value of the selector allowing to identify the element
     * @throws Throwable
     */
    @Then("^the element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" should not be present$")
    public void checkAbsenceOfAnElement(String selector, String value)
            throws Throwable {
        log.info("Check the presence of element identified by [" + selector
                + " = " + value + "]");
        Assert.assertFalse(PageManager.getPage().isElementPresent(
                getSelector(selector, value)));
    }

    /**
     * Checks if an element identified by any selector contains some value
     *
     * @param selector kind of selector to use in order to identify the element
     * @param value    value of the selector allowing to identify the element
     * @param text     text that should be contained by the element
     * @throws Throwable
     */
    @Then("^the element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" should contain \"([^\"]*)\"$")
    public void checkIfAnElementContains(String selector, String value,
                                         String text) throws Throwable {
        log.info("Check if the element identified by [" + selector + " = "
                + value + "] contains " + text);

        Assert.assertTrue(PageManager.getPage()
                .getWebElement(getSelector(selector, value)).getText()
                .contains(DataInjector.injectData(text)));
    }

    /**
     * Checks if the notification message is as expected
     *
     * @param text the text that should be contained by the notification message
     * @throws Throwable
     */
    @Then("^the notification message should be \"([^\"]*)\"$")
    public void checkTheNotificationMessage(String text) throws Throwable {
        log.info("the notification message should be : " + text);

        Assert.assertTrue(PageManager.getPage().waitNotification()
                .contains(DataInjector.injectData(text)));
    }

    /**
     * Allows to wait till the atribute of a particular element has been loaded
     *
     * @param attribute the attribute that needs to be loaded
     * @param selector  kind of selector to use in order to identify the element
     * @param value     value of the selector allowing to identify the element
     * @throws Throwable
     */
    @When("^wait until loading the attribute \"([^\"]*)\" of element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
    public void waitUntilLoadingTheAttributeOfAnElement(String attribute,
                                                        String selector, String value) throws Throwable {

        log.info("wait until loading the attribute " + attribute
                + " of element having [" + selector + " = " + value + "]");

        PageManager.getPage().waitUntilElementContainsAttribute(
                getSelector(selector, value), attribute);
    }

    /**
     * Checks if a specified attribute of an element identified by any selector
     * contains a particular value
     *
     * @param attribute attribute of the element to be checked
     * @param selector  kind of selector to use in order to identify the element
     * @param value     value of the selector to use in order to identify the element
     * @param text      text that should be contained by the attribute
     * @throws Throwable
     */
    @Then("^the attribute \"([^\"]*)\" of element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" should contain \"([^\"]*)\"$")
    public void checkIfTheAttributeOfAnElementContains(String attribute,
                                                       String selector, String value, String text) throws Throwable {
        log.info("Check if the element identified by [" + selector + " = "
                + value + "] contains " + text);

        Assert.assertTrue(PageManager.getPage()
                .getWebElement(getSelector(selector, value))
                .getAttribute(attribute)
                .contains(DataInjector.injectData(text)));
    }

    /**
     * Allows to store in the execution context the attribute of an element
     * identified by any selector using a particular key
     *
     * @param key       key to be used for storing under the execution context
     * @param attribute attribute that needs to be saved in the context
     * @param selector  kind of selector to use in order to identify the element
     * @param value     value of the selector allowing to identify the element
     * @throws Throwable
     */
    @Then("^save with \"([^\"]*)\" key the attribute \"([^\"]*)\" of element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
    public void saveTheAttributeOfAnElement(String key, String attribute,
                                            String selector, String value) throws Throwable {
        log.info("save with " + key + " key the attribute " + attribute
                + " of element identified by [" + selector + " = " + value
                + "]");
        ExecutionContext.put(
                key,
                PageManager.getPage()
                        .getWebElement(getSelector(selector, value))
                        .getAttribute(attribute));
    }

    /**
     * Allows to check if the page source code contains a particular text
     *
     * @param text text that should be present in the page source code
     * @throws Throwable
     */
    @Then("^the text of the page source should contain \"([^\"]*)\"$")
    public void checkIfThePageSourceContains(String text) throws Throwable {
        log.info("Check if the text of the page source contains " + text);
        Assert.assertTrue(PageManager.getPage().isTextPresent(
                DataInjector.injectData(text)));
    }

    /**
     * Accepts the alert
     */
    @Then("^accept the alert$")
    public void acceptAnAlert() {
        log.info("accept the alert");
        PageManager.getPage().acceptAlert();
    }

    /**
     * Moves to an element identified by any selector
     *
     * @param selector kind of selector to use in order to identify an element
     * @param value    value of the selector allowing to identify an element
     * @throws Throwable
     */
    @When("^move to element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
    public void moveToAnElement(String selector, String value) throws Throwable {
        log.info("move to [" + selector + "=" + value + "]");
        PageManager.getPage().moveToElement(getSelector(selector, value));
    }

    /**
     * ALlows to wait for a certain amount of time for an element to to contain
     * a particular value
     *
     * @param timeout  seconds to wait
     * @param selector kind of selector to use in order to identify an element
     * @param value    value of the selector allowing to identify an element
     * @param text     text that should be present in the element
     * @throws Throwable
     */
    @When("^wait (\\d+) seconds for element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" contains \"([^\"]*)\"$")
    public void waitForAnElementToContain(int timeout, String selector,
                                          String value, String text) throws Throwable {
        log.info("wait " + timeout + " seconds for element having ["
                + selector + " = " + value + "]");

        PageManager.getPage().waitUntilElementContainsText(
                getSelector(selector, value), DataInjector.injectData(text),
                timeout);
    }

    /**
     * Waits for a certain time
     *
     * @param seconds amount of time to wait in seconds
     * @throws Throwable
     */
    @When("^wait for (\\d+) seconds$")
    public void waitForSomeSeconds(int seconds) throws Throwable {
        log.info("waiting " + seconds + " seconds");
        Sleeper.sleepTightInSeconds(seconds);
    }

    /**
     * Waits untill a particular element, identified by any selector, contains a
     * particular value
     *
     * @param selector kind of selector to use in order to identify an element
     * @param value    value of the selector allowing to identify an element
     * @param text     text that should be contained by the element
     * @throws Throwable
     */
    @When("^wait until element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" contains \"([^\"]*)\"$")
    public void waitUntilAnElementContains(String selector, String value,
                                           String text) throws Throwable {
        log.info("wait until element having [" + selector + " = " + value
                + "] contains " + text);
        PageManager.getPage().waitUntilElementContainsText(
                getSelector(selector, value), DataInjector.injectData(text));
    }

    /**
     * Waits untill a particular element is present
     *
     * @param selector kind of selector to use in order to identify an element
     * @param value    value of the selector allowing to identify an element
     * @throws Throwable
     */
    @When("^wait until element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" is present$")
    public void waitUntilAnElementIsPresent(String selector, String value)
            throws Throwable {
        log.info("wait until element having [" + selector + " = " + value
                + "] contains is present");
        PageManager.getPage().isElementPresent(getSelector(selector, value));
    }

    /**
     * Switches to a frame identified by any selector
     *
     * @param selector kind of selector to use in order to identify an element
     * @param value    value of the selector allowing to identify an element
     * @throws Throwable
     */
    @Given("^switch to frame having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
    public void switchToAFrame(String selector, String value) throws Throwable {
        log.info("Switching to frame identified by [" + selector + " = "
                + value + "]");
        PageManager.getPage().switchToFrame(getSelector(selector, value));
    }

    /**
     * Switches to the previous window
     *
     * @throws Throwable
     */
    @Given("^switch to previous window$")
    public void switchToPreviousWindow() throws Throwable {
        log.info("Switching to previous window");
        PageManager.getPage().switchToPreviousWindow();
    }

    /**
     * Switches to the main content in the page
     *
     * @throws Throwable
     */
    @Given("^switch to main content in page$")
    public void switchToMainContentInPage() throws Throwable {
        log.info("Switching to main page");
        PageManager.getPage().switchToDefaultContent();
    }

    /**
     * Switches to a new window
     *
     * @throws Throwable
     */
    @Given("^switch to new window$")
    public void switchToNewWindow() throws Throwable {
        log.info("Switching to new window");
        PageManager.getPage().switchToNewWindow();
    }

    /**
     * Closes the browser
     *
     * @throws Throwable
     */
    @Given("^close the browser$")
    public void closeTheBrowser() throws Throwable {
        log.info("Close the browser");
        PageManager.getPage().quit();
    }

}
