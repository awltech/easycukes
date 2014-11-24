package com.worldline.easycukes.selenium.utils;

/**
 * This {@link SeleniumConstants} class contains some specifics constants for
 * this project. It mainly consists in keys you can use in order to retrieve
 * some configuration values from the configuration file.
 * 
 * @author mechikhi
 * @version 1.0
 */
public interface SeleniumConstants {

	// jenkins_wait_time_seconds=180
	// testinstance_wait_time_seconds=90
	/**
	 * The default time to wait for loading elements in web page
	 */
	public static final String NOTIFICATON_BY_CSS_KEY = "notification_by_css";
	public static final String LOADING_PROGRESS_BY_CSS_KEY = "loading_progress_by_css";

	/**
	 * The default time to wait for loading elements in web page
	 */
	public static final String DEFAULT_WAIT_TIME_SECONDS_KEY = "default_timeout";

	/**
	 * Address of the remote web driver
	 */
	public static final String REMOTE_ADDRESS_KEY = "selenium_remote_address";

	/**
	 * Specifies the browser to use
	 */
	public static final String BROWSER_KEY = "target_browser";

	/**
	 * Flag to indicate if the remote web driver should be used
	 */
	public static final String USE_REMOTE_KEY = "use_remote";

	/**
	 * The path of chrome driver binary executable
	 */
	public static final String LOCAL_DRIVER_PATH = "local_driver_path";
}
