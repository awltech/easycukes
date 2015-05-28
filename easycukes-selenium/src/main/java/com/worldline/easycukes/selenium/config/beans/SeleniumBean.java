package com.worldline.easycukes.selenium.config.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple POJO containing some selenium configuration
 *
 * @author aneveux
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class SeleniumBean {
    /**
     * Target browser to use for the selenium tests
     */
    public String target_browser;

    /**
     * Specifies if we need to use a remote Selenium cluster
     */
    public boolean use_remote;

    /**
     * Selenium remote address to be used if #use_remote is true
     */
    public String selenium_remote_address;

    /**
     * Path to find chrome driver
     */
    public String chrome_driver_path;

    /**
     * Path to find ie driver
     */
    public String ie_driver_path;

    /**
     * Default timeout
     */
    public int default_timeout;

}
