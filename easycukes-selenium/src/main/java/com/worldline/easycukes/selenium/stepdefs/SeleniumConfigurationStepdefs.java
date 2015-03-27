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

import com.worldline.easycukes.commons.Constants;
import org.openqa.selenium.browserlaunchers.Sleeper;

import com.worldline.easycukes.commons.DataInjector;
import com.worldline.easycukes.commons.ExecutionContext;
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
public class SeleniumConfigurationStepdefs extends SeleniumAbstractStepdefs {

    /**
     * Defines the sleeping time to apply for a scenario
     *
     * @param scenario
     */
    @Before
    public void before(Scenario scenario) {
        Sleeper.sleepTightInSeconds(1);
    }

    /**
     * Defines the base URL to be used for running the Selenium tests
     *
     * @param url URL to be used as a basedir for all paths to test
     * @throws Exception
     */
    @Given("^the base url is \"(.*?)\"$")
    public void defineBaseUrl(String url) throws Exception {
        ExecutionContext.put(Constants.BASEURL,
                DataInjector.injectData(url));
        PageManager.initialize();
        PageManager.getPage().setBaseUrl(
                ExecutionContext.get(Constants.BASEURL));
    }

    /**
     * Allows to specify a graphical element from the GUI which is used to
     * indicate a loading mechanism (like an icon, a progressbar, or something),
     * this can be used later on for checking if some processes are properly
     * loaded
     *
     * @param selector the selector to use for identifying the element
     * @param value    the value of the selector allowing to identify the loading
     *                 graphical element
     * @throws Throwable
     */
    @Given("^the loading progress is identified by the element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
    public void defineTheElementIdentifyingTheLoadingProgress(String selector,
                                                              String value) throws Throwable {
        PageManager.getPage().setLoadingProgress(getSelector(selector, value));
    }

    /**
     * Allows to specify a graphical element from the GUI which is used to
     * display notifications (like a popup or something). This can be used later
     * on in order to check for particular notifications
     *
     * @param selector the selector to use for identifying the element
     * @param value    the value of the selector allowing to identify the
     *                 notification graphical element
     * @throws Throwable
     */
    @Given("^the notification is identified by the element having (id|name|class|css|link|tag|xpath) \"([^\"]*)\"$")
    public void defineTheElementIdentifyingTheNotification(String selector,
                                                           String value) throws Throwable {
        PageManager.getPage().setNotificationBy(getSelector(selector, value));
    }

    /**
     * Defines if you want to wait for notifications to be hidden before
     * continuing the tests or if you can proceed with the tests even if the
     * notification popup is still visible
     */
    @Given("^enable the waiting for notification to be hidden$")
    public void waitTillTheNotificationIsHidden() {
        PageManager.getPage().enableWaitNotificationToHide();
    }
}
