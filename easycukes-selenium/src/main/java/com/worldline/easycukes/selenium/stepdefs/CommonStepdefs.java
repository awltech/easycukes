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

import org.junit.Assert;
import org.openqa.selenium.browserlaunchers.Sleeper;

import com.worldline.easycukes.commons.DataInjector;
import com.worldline.easycukes.commons.ExecutionContext;
import com.worldline.easycukes.selenium.pages.PageManager;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * This class aims at containing all the common operations you may use in the
 * tests scenarios. Things like dealing with the configuration, or manipulating
 * external tools, etc.
 * 
 * @author mechikhi
 * @version 1.0
 */
public class CommonStepdefs extends AbstractStepdefs {

	/**
	 *
	 */
	@Given("^navigate to path \"([^\"]*)\"$")
	public void navigate_to_path(final String path) {
		LOGGER.info("Navigate to path : " + path);
		PageManager.getPage().navigateTo(DataInjector.injectData(path));
	}

	/**
	 *
	 */
	@Given("^refresh the page$")
	public void refresh_the_page() {
		LOGGER.info("refreshing the page");
		PageManager.getPage().refresh();
	}

	@Given("^move to and click element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
	public void move_to_and_click_element_having(String selector, String value)
			throws Exception {

		LOGGER.info("move to and click element identified by [" + selector
				+ " = " + value + "]");
		PageManager.getPage().moveToAndClick(getSelector(selector, value));
	}

	@Given("^select element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" by text \"([^\"]*)\"$")
	public void select_element_having_by_text(String selector, String value,
			String text) throws Exception {

		LOGGER.info("select element identified by [" + selector + " = " + value
				+ "]");
		PageManager.getPage().selectByText(getSelector(selector, value),
				DataInjector.injectData(text));
	}

	@Given("^select element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" by value \"([^\"]*)\"$")
	public void select_element_having_by_value(String selector, String value,
			String text) throws Exception {

		LOGGER.info("select element identified by [" + selector + " = " + value
				+ "]");
		PageManager.getPage().selectByValue(getSelector(selector, value),
				DataInjector.injectData(text));
	}

	@Given("^click element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
	public void click_element_having(String selector, String value)
			throws Exception {

		LOGGER.info("Click on element identified by [" + selector + " = "
				+ value + "]");

		PageManager.getPage().click(getSelector(selector, value));
	}

	@Given("^submit element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
	public void submit_element_having(String selector, String value)
			throws Exception {

		LOGGER.info("Submit on element identified by [" + selector + " = "
				+ value + "]");

		PageManager.getPage().submit(getSelector(selector, value));
	}

	@Given("^send a text \"([^\"]*)\" to element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
	public void send_a_text_to_element_having(String text, String selector,
			String value) throws Exception {

		LOGGER.info("Send text to element identified by [" + selector + " = "
				+ value + "]");

		PageManager.getPage().sendText(getSelector(selector, value),
				DataInjector.injectData(text));
	}

	@Given("^send a text \"([^\"]*)\" to alert and accept$")
	public void send_a_text_to_alert_and_accept(String text) throws Exception {
		LOGGER.info("send a text " + text + " to alert and accept");
		PageManager.getPage().sendTextToAlertAndAccept(
				DataInjector.injectData(text));
	}

	@Then("^the element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" should be present$")
	public void the_element_having_should_be_present(String selector,
			String value) throws Throwable {
		LOGGER.info("Check the presence of element identified by [" + selector
				+ " = " + value + "]");
		Assert.assertTrue(PageManager.getPage().isElementPresent(
				getSelector(selector, value)));
	}

	@Then("^the element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" should not be present$")
	public void the_element_having_should_not_be_present(String selector,
			String value) throws Throwable {
		LOGGER.info("Check the presence of element identified by [" + selector
				+ " = " + value + "]");
		Assert.assertFalse(PageManager.getPage().isElementPresent(
				getSelector(selector, value)));
	}

	@Then("^the element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" should contain \"([^\"]*)\"$")
	public void the_element_having_should_contain(String selector,
			String value, String text) throws Throwable {
		LOGGER.info("Check if the element identified by [" + selector + " = "
				+ value + "] contains " + text);

		Assert.assertTrue(PageManager.getPage()
				.getWebElement(getSelector(selector, value)).getText()
				.contains(DataInjector.injectData(text)));
	}

	@Then("^the notification message should be \"([^\"]*)\"$")
	public void the_notification_message_should_be(String text)
			throws Throwable {
		LOGGER.info("the notification message should be : " + text);

		Assert.assertTrue(PageManager.getPage().waitNotification()
				.contains(DataInjector.injectData(text)));
	}

	@When("^wait until loading the attribute \"([^\"]*)\" of element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
	public void wait_until_loading_the_attribute_of_element_having(
			String attribute, String selector, String value) throws Throwable {

		LOGGER.info("wait until loading the attribute " + attribute
				+ " of element having [" + selector + " = " + value + "]");

		PageManager.getPage().waitUntilElementContainsAttribute(
				getSelector(selector, value), attribute);
	}

	@Then("^the attribute \"([^\"]*)\" of element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" should contain \"([^\"]*)\"$")
	public void the_attribute_of_element_having_should_contain(
			String attribute, String selector, String value, String text)
			throws Throwable {
		LOGGER.info("Check if the element identified by [" + selector + " = "
				+ value + "] contains " + text);

		Assert.assertTrue(PageManager.getPage()
				.getWebElement(getSelector(selector, value))
				.getAttribute(attribute)
				.contains(DataInjector.injectData(text)));
	}

	@Then("^save with \"([^\"]*)\" key the attribute \"([^\"]*)\" of element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
	public void save_with_key_the_attribute_of_element_having(String key,
			String attribute, String selector, String value) throws Throwable {
		LOGGER.info("save with " + key + " key the attribute " + attribute
				+ " of element identified by [" + selector + " = " + value
				+ "]");
		ExecutionContext.put(
				key,
				PageManager.getPage()
						.getWebElement(getSelector(selector, value))
						.getAttribute(attribute));
	}

	@Then("^the text of the page source should contain \"([^\"]*)\"$")
	public void the_text_of_the_page_source_should_contain(String text)
			throws Throwable {
		LOGGER.info("Check if the text of the page source contains " + text);
		Assert.assertTrue(PageManager.getPage().isTextPresent(
				DataInjector.injectData(text)));
	}

	@Then("^accept the alert$")
	public void accept_the_alert() {
		LOGGER.info("accept the alert");
		PageManager.getPage().acceptAlert();
	}

	@When("^move to element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
	public void move_to_element_having(String selector, String value)
			throws Throwable {
		LOGGER.info("move to [" + selector + "=" + value + "]");
		PageManager.getPage().moveToElement(getSelector(selector, value));
	}

	@When("^wait (\\d+) seconds for element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" contains \"([^\"]*)\"$")
	public void wait_seconds_for_element_having_contains(int timeout,
			String selector, String value, String text) throws Throwable {

		LOGGER.info("wait " + timeout + " seconds for element having ["
				+ selector + " = " + value + "]");

		PageManager.getPage().waitUntilElementContainsText(
				getSelector(selector, value), DataInjector.injectData(text),
				timeout);
	}

	@When("^wait for (\\d+) seconds$")
	public void wait_for_seconds(int seconds) throws Throwable {
		LOGGER.info("waiting " + seconds + " seconds");
		Sleeper.sleepTightInSeconds(seconds);
	}

	@When("^wait until element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" contains \"([^\"]*)\"$")
	public void wait_until_element_having_contains(String selector,
			String value, String text) throws Throwable {
		LOGGER.info("wait until element having [" + selector + " = " + value
				+ "] contains " + text);
		PageManager.getPage().waitUntilElementContainsText(
				getSelector(selector, value), DataInjector.injectData(text));
	}

	@When("^wait until element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\" is present$")
	public void wait_until_element_having_equal_is_present(String selector,
			String value) throws Throwable {
		LOGGER.info("wait until element having [" + selector + " = " + value
				+ "] contains is present");
		PageManager.getPage().isElementPresent(getSelector(selector, value));
	}

	@Given("^switch to frame having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
	public void switch_to_frame_having(String selector, String value)
			throws Throwable {
		LOGGER.info("Switching to frame identified by [" + selector + " = "
				+ value + "]");
		PageManager.getPage().switchToFrame(getSelector(selector, value));
	}

	@Given("^switch to previous window$")
	public void switch_to_previous_window() throws Throwable {
		LOGGER.info("Switching to previous window");
		PageManager.getPage().switchToPreviousWindow();
	}

	@Given("^switch to main content in page$")
	public void switch_to_main_content_in_page() throws Throwable {
		LOGGER.info("Switching to main page");
		PageManager.getPage().switchToDefaultContent();
	}

	@Given("^switch to new window$")
	public void switch_to_new_window() throws Throwable {
		LOGGER.info("Switching to new window");
		PageManager.getPage().switchToNewWindow();
	}

	@Given("^close the browser$")
	public void close_the_browser() throws Throwable {
		LOGGER.info("Close the browser");
		PageManager.getPage().quit();
	}

}
