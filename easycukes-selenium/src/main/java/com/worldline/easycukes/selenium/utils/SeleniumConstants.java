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
	public static final String CHROME_DRIVER_PATH = "chrome_driver_path";

	/**
	 * The path of IE driver binary executable
	 */
	public static final String IE_DRIVER_PATH = "ie_driver_path";
}
