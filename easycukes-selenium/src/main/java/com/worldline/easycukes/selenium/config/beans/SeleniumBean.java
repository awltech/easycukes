package com.worldline.easycukes.selenium.config.beans;

/**
 * Simple POJO containing some selenium configuration
 *
 * @author aneveux
 * @version 1.0
 */
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

    public String getTarget_browser() {
        return target_browser;
    }

    public void setTarget_browser(String target_browser) {
        this.target_browser = target_browser;
    }

    public boolean isUse_remote() {
        return use_remote;
    }

    public void setUse_remote(boolean use_remote) {
        this.use_remote = use_remote;
    }

    public String getSelenium_remote_address() {
        return selenium_remote_address;
    }

    public void setSelenium_remote_address(String selenium_remote_address) {
        this.selenium_remote_address = selenium_remote_address;
    }

    public String getChrome_driver_path() {
        return chrome_driver_path;
    }

    public void setChrome_driver_path(String chrome_driver_path) {
        this.chrome_driver_path = chrome_driver_path;
    }

    public String getIe_driver_path() {
        return ie_driver_path;
    }

    public void setIe_driver_path(String ie_driver_path) {
        this.ie_driver_path = ie_driver_path;
    }

    public int getDefault_timeout() {
        return default_timeout;
    }

    public void setDefault_timeout(int default_timeout) {
        this.default_timeout = default_timeout;
    }
}
