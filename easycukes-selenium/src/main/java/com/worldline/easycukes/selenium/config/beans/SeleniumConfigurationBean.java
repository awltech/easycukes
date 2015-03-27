package com.worldline.easycukes.selenium.config.beans;

import com.worldline.easycukes.commons.config.beans.CommonConfigurationBean;

/**
 * Extension of CommonConfigurationBean allowing to deal with some Selenium configuration
 *
 * @author aneveux
 * @version 1.0
 */
public class SeleniumConfigurationBean extends CommonConfigurationBean {

    /**
     * Selenium specific configuration
     */
    public SeleniumBean selenium;

    public SeleniumBean getSelenium() {
        return selenium;
    }

    public void setSelenium(SeleniumBean selenium) {
        this.selenium = selenium;
    }
}
