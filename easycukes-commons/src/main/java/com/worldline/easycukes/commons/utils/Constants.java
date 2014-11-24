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

	public static final String CUKES_TESTS_LOGGER = "org.kazansource.tests.cucumber";

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
