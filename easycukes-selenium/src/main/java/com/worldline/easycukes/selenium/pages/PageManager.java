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

import com.worldline.easycukes.commons.ExecutionContext;
import com.worldline.easycukes.commons.config.EasyCukesConfiguration;
import com.worldline.easycukes.selenium.WebDriverFactory;
import com.worldline.easycukes.selenium.config.beans.SeleniumConfigurationBean;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

/**
 * TODO
 *
 * @author mechikhi
 * @version 1.0
 */
@Slf4j
@UtilityClass
public class PageManager {

    /**
     * This simple line does all the mutlithread magic. For more details please
     * refer to the src link above :)
     */
    private static ThreadLocal<Page> page = new ThreadLocal<Page>();

    /**
     * Allows to initialize this instance of {@link PageManager} by creating the
     * {@link WebDriver}
     *
     * @throws Exception
     */
    public static void initialize() throws Exception {
        EasyCukesConfiguration<SeleniumConfigurationBean> configuration = new EasyCukesConfiguration<>(SeleniumConfigurationBean.class);
        if (configuration.isProxyNeeded())
            configuration.configureProxy();
        if (page.get() == null) {
            String browserName = ExecutionContext.get("browserName");
            if (browserName == null)
                browserName = configuration.getValues().selenium != null ? configuration.getValues().selenium.getTarget_browser()
                        : "firefox";
            WebDriver driver = null;
            if (configuration.getValues().selenium != null
                    && !configuration.getValues().selenium.isUse_remote())
                driver = WebDriverFactory.newLocalWebDriver(browserName);
            else
                try {
                    driver = WebDriverFactory.newRemoteWebDriver(browserName);
                } catch (final Exception e) {
                    log.warn("Setup : " + e.getMessage());
                    driver = WebDriverFactory.newLocalWebDriver(browserName);
                }
            driver.manage().window().maximize();
            page.set(new Page(driver));
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