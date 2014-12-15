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

import org.openqa.selenium.browserlaunchers.Sleeper;

import com.worldline.easycukes.commons.DataInjector;
import com.worldline.easycukes.commons.ExecutionContext;
import com.worldline.easycukes.commons.helpers.Constants;
import com.worldline.easycukes.selenium.pages.PageManager;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

/**
 * This class allow to configure the environment for the execution of test
 * cases.
 *
 * @author mechikhi
 * @version 1.0
 */
public class ConfigurationStepdefs extends AbstractStepdefs {

	@Before
	/**
	 * Allows to waiting 2 seconds before the start of each scenario
	 */
	public void before(Scenario scenario) {
		Sleeper.sleepTightInSeconds(1);
	}

	/**
	 * Allows to set base url
	 *
	 * @param url
	 */
	@Given("^the base url is \"(.*?)\"$")
	public void the_base_url_is(String url) throws Exception {
		ExecutionContext.put(Constants.BASE_URL_KEY,
				DataInjector.injectData(url));
		PageManager.initialize();
		PageManager.getPage().setBaseUrl(
				ExecutionContext.get(Constants.BASE_URL_KEY));
	}

	@Given("^the loading progress is identified by the element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
	public void the_loading_progress_is_identified_by_the_element_having(
			String selector, String value) throws Throwable {

		PageManager.getPage().setLoadingProgress(getSelector(selector, value));
	}

	@Given("^the notification is identified by the element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
	public void the_notification_is_identified_by_the_element_having(
			String selector, String value) throws Throwable {

		PageManager.getPage().setNotificationBy(getSelector(selector, value));
	}

	@Given("^enable the waiting for notification to be hidden$")
	public void enable_the_waiting_for_notification_to_be_hidden() {

		PageManager.getPage().enableWaitNotificationToHide();
	}
}
