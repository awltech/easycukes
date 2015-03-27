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
package com.worldline.easycukes.selenium;

import java.io.File;
import java.net.URL;

import com.worldline.easycukes.commons.config.EasyCukesConfiguration;
import com.worldline.easycukes.selenium.config.beans.SeleniumConfigurationBean;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.worldline.easycukes.selenium.utils.SeleniumConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverFactory {

    /**
     * Just a {@link Logger}...
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(WebDriverFactory.class);

    static EasyCukesConfiguration<SeleniumConfigurationBean> configuration = new EasyCukesConfiguration<>(SeleniumConfigurationBean.class);

    /**
     * Creates a new local driver instance. supported browsers : chrome and
     * firefox
     *
     * @return a local WebDriver instance
     * @throws Exception
     */
    public static WebDriver newLocalWebDriver(String browserName)
            throws Exception {
        LOG.info("Starting a local Driver");
        if ("firefox".equals(browserName))
            return newLocalFirefoxDriver();
        else if ("chrome".equals(browserName))
            return newLocalChromeDriver();
        else if ("ie".equals(browserName))
            return newLocalIEDriver();
        else
            throw new Exception("Unknown or not supported browser : "
                    + browserName);
    }

    /**
     * Creates a new remte driver instance. supported browsers : chrome and
     * firefox
     *
     * @return a remote WebDriver instance
     * @throws Exception
     */
    public static WebDriver newRemoteWebDriver(String browserName)
            throws Exception {
        LOG.info("Starting a remote Driver");
        final String remoteAddress = configuration.getValues().selenium != null ? configuration.getValues().selenium.getSelenium_remote_address()
                : "";
        if ("firefox".equals(browserName))
            return new RemoteWebDriver(new URL(remoteAddress),
                    firefoxCapabilities());
        else if ("chrome".equals(browserName))
            return new RemoteWebDriver(new URL(remoteAddress),
                    chromeCapabilities());
        else if ("ie".equals(browserName))
            return new RemoteWebDriver(new URL(remoteAddress), ieCapabilities());
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
        if (configuration.getValues().selenium != null
                && configuration.getValues().selenium.getChrome_driver_path() != null) {
            final File file_chrome = new File(configuration.getValues().selenium.getChrome_driver_path());
            System.setProperty("webdriver.chrome.driver",
                    file_chrome.getAbsolutePath());
        }
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

    /**
     * Creates a new IE driver instance
     *
     * @return a InternetExplorerDriver instance
     */
    private static WebDriver newLocalIEDriver() {
        if (configuration.getValues().selenium != null
                && configuration.getValues().selenium.getIe_driver_path() != null) {
            final File file_ie = new File(configuration.getValues().selenium.getIe_driver_path());
            System.setProperty("webdriver.ie.driver", file_ie.getAbsolutePath());
        }
        return new InternetExplorerDriver(ieCapabilities());
    }

    /**
     * Allows to customize and configure the options of a IE session
     *
     * @return DesiredCapabilities
     */
    private static DesiredCapabilities ieCapabilities() {
        final DesiredCapabilities capabilities = DesiredCapabilities
                .internetExplorer();
        return capabilities;
    }

}
