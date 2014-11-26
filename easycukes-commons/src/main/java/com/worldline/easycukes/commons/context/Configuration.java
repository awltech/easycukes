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
package com.worldline.easycukes.commons.context;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.log4j.Logger;

import com.worldline.easycukes.commons.utils.Constants;

/**
 * Allows access to configuration parameters defined in the file properties
 * target
 * 
 * @author mechikhi
 * @version 2.0
 */
public class Configuration {

	/**
	 * Obviously a ... Logger!
	 */
	private static Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	/**
	 * The {@link Properties} matching with values defined in the loaded
	 * configuration file linked to the environment (not the target one, the one
	 * on which tests are executed. Mostly used to define the proxy
	 */
	private static Properties environment = new Properties();

	/**
	 * The {@link Properties} matching with values definde in the loaded
	 * configuration file linked to the target environment. It contains actually
	 * most of the configuration values we need.
	 */
	private static Properties target = new Properties();

	/**
	 * Allows to load the configuration files directly from the classpath (used
	 * when configuration files are compiled and added at root of the classpath
	 * (already filtered by Maven)).
	 * 
	 * It actually initializes the {@link Properties} contained in this class
	 * 
	 * @throws IOException
	 *             if something's going wrong while using the configuration
	 *             files
	 */
	public static void load() throws IOException {
		LOGGER.info("Loading configuration without anything specified in Cucumber...");
		try {
			environment.load(Configuration.class
					.getResourceAsStream("/env.properties"));
			target.load(Configuration.class
					.getResourceAsStream("/target.properties"));
		} catch (final IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Allows to load the configuration files using a specifically defined path
	 * in the filesystem. Could be useful for executing things while the whole
	 * source code isn't completely packaged (filtering not done by Maven), like
	 * it is when you use Cucumber Eclipse plugin.
	 * 
	 * This class actually intializes the {@link #environment} of that class
	 * using the configuration file found in the specified path.
	 * 
	 * @param path
	 *            the environment on which the tests are executed
	 * @throws IOException
	 *             if something's going wrong while using the configuration
	 *             files
	 */
	public static void loadEnvironment(String path) throws IOException {
		if (path != null && path.length() > 0) {
			LOGGER.info("Loading environment configuration file :" + path);
			try {
				environment.load(Configuration.class.getResourceAsStream(path
						+ ".properties"));
			} catch (final IOException e) {
				LOGGER.error(e.getMessage(), e);
				throw e;
			}
		}
	}

	/**
	 * Allows to load the configuration files using a specifically defined path
	 * in the filesystem. Could be useful for executing things while the whole
	 * source code isn't completely packaged (filtering not done by Maven), like
	 * it is when you use Cucumber Eclipse plugin.
	 * 
	 * This class actually intializes the {@link #target} of that class using
	 * the configuration file found in the specified path.
	 * 
	 * @param path
	 *            the environment targetted by the tests
	 * @throws IOException
	 *             if something's going wrong while using the configuration
	 *             files
	 */
	public static void loadTarget(String path) throws IOException {
		if (path != null && path.length() > 0) {
			LOGGER.info("Loading application configuration file :" + path);
			try {
				target.load(Configuration.class.getResourceAsStream(path
						+ ".properties"));
			} catch (final IOException e) {
				LOGGER.error(e.getMessage(), e);
				throw e;
			}
		}
	}

	/**
	 * Allows to get the value matching with the specified key from the
	 * environment configuration
	 * 
	 * @param key
	 *            a key allowing to get some values from the configuration
	 * @return the value corresponding to the provided key
	 */
	public static String getEnvProperty(final String key) {
		return environment.getProperty(key);
	}

	/**
	 * Allows to get the value matching with the specified key from the target
	 * application configuration
	 * 
	 * @param key
	 *            a key allowing to get some values from the configuration
	 * @return the value corresponding to the provided key
	 */
	public static String get(final String key) {
		return target.getProperty(key);
	}

	/**
	 * Allows to get {@link Properties} matching with the configuration values
	 * defined for the environment (the one on which tests are executed)
	 * 
	 * @return the {@link #environment} configuration values
	 */
	public static Properties getEnvironment() {
		return environment;
	}

	/**
	 * Allows to know if we should use the proxy or not. This information is
	 * extracted from the configuration file directly
	 * 
	 * @return true if we need to use the proxy and it is defined in the
	 *         configuration file, false otherwise
	 */
	public static boolean isProxyNeeded() {
		return environment.containsKey("use_proxy")
				&& Boolean.valueOf(environment.getProperty("use_proxy"))
				&& environment.containsKey("proxy_host")
				&& environment.containsKey("proxy_port");
	}

	/**
	 * Allows to deal with the proxy configuration. It creates a
	 * {@link HostConfiguration} object that can be used by the HTTPClient, and
	 * also sets the proxy in system variables.
	 * 
	 * @return a {@link HostConfiguration} containing information about the
	 *         proxy, along with the fact that system properties have been set.
	 *         Null if the proxy is not specified
	 */
	public static HostConfiguration configureProxy() {
		if (isProxyNeeded()) {
			LOGGER.debug("Proxy is needed => configuring the http client");
			final HostConfiguration hostConfiguration = new HostConfiguration();
			hostConfiguration.setProxy(
					environment.getProperty("proxy_host") != null ? environment
							.getProperty("proxy_host") : "",
					environment.getProperty("proxy_port") != null ? Integer
							.parseInt(environment.getProperty("proxy_port"))
							: 0);

			LOGGER.info("Setting the proxy... Using "
					+ hostConfiguration.getProxyHost() + ":"
					+ hostConfiguration.getProxyPort());

			// Set system properties too
			System.setProperty("https.proxyHost",
					environment.getProperty("proxy_host"));
			System.setProperty("https.proxyPort",
					environment.getProperty("proxy_port"));
			return hostConfiguration;
		}
		return null;
	}

	/**
	 * Allows to deal with the SSL configuration. It sets the ssl properties in
	 * system variables.
	 * 
	 */
	public static void configureSSL() {
		if (environment.containsKey("ssl_keystore")) {
			System.setProperty("javax.net.ssl.keyStore",
					environment.getProperty("ssl_keystore"));
			System.setProperty("javax.net.ssl.keyStorePassword",
					environment.getProperty("ssl_password"));
		} else if (environment.containsKey("ssl_truststore")) {
			System.setProperty("javax.net.ssl.trustStore",
					environment.getProperty("ssl_truststore"));
			System.setProperty("javax.net.ssl.trustStorePassword",
					environment.getProperty("ssl_password"));
		}
	}

}
