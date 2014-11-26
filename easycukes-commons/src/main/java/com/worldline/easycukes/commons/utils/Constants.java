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
package com.worldline.easycukes.commons.utils;

/**
 * This {@link Constants} class contains all constants that could be manipulated
 * in this project. It mainly consists in keys you can use in order to retrieve
 * some configuration values from the configuration file.
 *
 * @author mechikhi
 * @version 1.0
 */
public interface Constants {

	public static final String CUKES_TESTS_LOGGER = "com.worldline.easycukes";

	/*
	 * Keys to be used in order to get information from the configuration file
	 */

	/**
	 * Key matching with the base URL
	 */
	public static final String BASE_URL_KEY = "baseurl";

	/**
	 * Key matching with the username to use for executing the tests
	 */
	public static final String USERNAME_KEY = "username";

	/**
	 * Key matching with the password to use for the authentication of
	 * previously specified user
	 */
	public static final String PASSWORD_KEY = "password";

}
