package com.worldline.easycukes.selenium.config.beans;

import com.worldline.easycukes.commons.config.beans.CommonConfigurationBean;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Extension of CommonConfigurationBean allowing to deal with some Selenium configuration
 *
 * @author aneveux
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class SeleniumConfigurationBean extends CommonConfigurationBean {

    /**
     * Selenium specific configuration
     */
    public SeleniumBean selenium;

}
