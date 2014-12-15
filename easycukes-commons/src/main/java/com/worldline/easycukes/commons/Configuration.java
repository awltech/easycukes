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
package com.worldline.easycukes.commons;

import java.io.InputStream;
import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import com.worldline.easycukes.commons.helpers.Constants;

/**
 * Allows access to configuration parameters defined in the yml files
 *
 * @version 2.0
 */
public class Configuration {

	protected static boolean isLoaded = false;

	protected static Map<String, Map<String, Object>> environment;
	protected static Map<String, Map<String, Object>> target;

	public static final String ENVIRONMENT_CONFIGURATION_FILE = "/environment.yml";
	public static final String TARGET_CONFIGURATION_FILE = "/target.yml";

	private final static Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	/**
	 * Allows to load the configuration files directly from the classpath (used
	 * when configuration files are compiled and added at root of the classpath
	 * (already filtered by Maven)).
	 *
	 */
	@SuppressWarnings("unchecked")
	public static void load() {
		LOGGER.info("Loading configuration files...");
		final InputStream environmentIS = Configuration.class
				.getResourceAsStream(ENVIRONMENT_CONFIGURATION_FILE);
		if (environmentIS != null) {
			LOGGER.info("Found environment configuration file at "
					+ ENVIRONMENT_CONFIGURATION_FILE);
			final Yaml yaml = new Yaml();
			environment = (Map<String, Map<String, Object>>) yaml
					.load(environmentIS);
			LOGGER.info("Loaded " + environment.keySet().size()
					+ " elements from environment configuration file...");
		} else
			LOGGER.warn("No environment configuration file found at "
					+ ENVIRONMENT_CONFIGURATION_FILE);
		final InputStream targetIS = Configuration.class
				.getResourceAsStream(TARGET_CONFIGURATION_FILE);
		if (targetIS != null) {
			LOGGER.info("Found target configuration file at "
					+ TARGET_CONFIGURATION_FILE);
			final Yaml yaml = new Yaml();
			target = (Map<String, Map<String, Object>>) yaml.load(targetIS);
			LOGGER.info("Loaded " + target.keySet().size()
					+ " elements from target configuration file...");
		} else
			LOGGER.warn("No target configuration file found at "
					+ TARGET_CONFIGURATION_FILE);
		LOGGER.info("Configuration files loaded...");
		isLoaded = true;
	}

	public static Map<String, Map<String, Object>> getEnvironment() {
		if (!isLoaded)
			load();
		return environment;
	}

	public static Map<String, Map<String, Object>> getTarget() {
		if (!isLoaded)
			load();
		return target;
	}

	/**
	 * Allows to know if we should use the proxy or not. This information is
	 * extracted from the configuration file directly
	 *
	 * @return true if we need to use the proxy and it is defined in the
	 *         configuration file, false otherwise
	 */
	public static boolean isProxyNeeded() {
		if (!isLoaded)
			load();
		if (environment.containsKey("proxy")) {
			LOGGER.info("Checking proxy: "
					+ environment.get("proxy").containsKey("enabled")
					+ " : "
					+ Boolean.valueOf(environment.get("proxy").get("enabled")
							.toString()));
			return environment.get("proxy").containsKey("enabled") ? Boolean
					.valueOf(environment.get("proxy").get("enabled").toString())
					: false;
		}
		LOGGER.info("No proxy required...");
		return false;
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
		if (!isLoaded)
			load();
		if (isProxyNeeded()) {
			LOGGER.info("Proxy is needed => configuring the http client");
			final HostConfiguration hostConfiguration = new HostConfiguration();
			hostConfiguration.setProxy(
					environment.get("proxy").get("host") != null ? environment
							.get("proxy").get("host").toString() : "",
							environment.get("proxy").get("port") != null ? Integer
									.parseInt(environment.get("proxy").get("port")
											.toString()) : 0);

			LOGGER.info("Setting the proxy... Using "
					+ hostConfiguration.getProxyHost() + ":"
					+ hostConfiguration.getProxyPort());

			// Set system properties too
			System.setProperty("https.proxyHost",
					environment.get("proxy").get("host").toString());
			System.setProperty("https.proxyPort",
					environment.get("proxy").get("port").toString());
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
		if (!isLoaded)
			load();
		LOGGER.info("Configuring SSL...");
		if (environment.containsKey("ssl")) {
			LOGGER.info("Found some SSL configuration...");
			if (environment.get("ssl").containsKey("keystore")) {
				System.setProperty(
						"javax.net.ssl.keyStore",
						environment.get("ssl").get("keystore") != null ? environment
								.get("ssl").get("keystore").toString()
								: "");
				System.setProperty(
						"javax.net.ssl.keyStorePassword",
						environment.get("ssl").get("password") != null ? environment
								.get("ssl").get("password").toString()
								: "");
				LOGGER.info("Configured SSL keystore...");
			}
			if (environment.get("ssl").containsKey("truststore")) {
				System.setProperty(
						"javax.net.ssl.trustStore",
						environment.get("ssl").get("truststore") != null ? environment
								.get("ssl").get("truststore").toString()
								: "");
				System.setProperty(
						"javax.net.ssl.trustStorePassword",
						environment.get("ssl").get("password") != null ? environment
								.get("ssl").get("password").toString()
								: "");
				LOGGER.info("Configured SSL truststore...");
			}
		}
	}

	public static String getEnvironmentVariable(String key) {
		if (!isLoaded)
			load();
		if (environment.containsKey("variables"))
			return environment.get("variables").containsKey(key) ? environment
					.get("variables").get(key).toString() : null;
					return null;
	}

	public static String getTargetVariable(String key) {
		if (!isLoaded)
			load();
		if (target.containsKey("variables"))
			return target.get("variables").containsKey(key) ? target
					.get("variables").get(key).toString() : null;
					return null;
	}

	public static Map<String, Object> getTargetCredentials() {
		if (!isLoaded)
			load();
		if (target.containsKey("credentials"))
			return target.get("credentials");
		else
			return null;
	}

	public static Map<String, Object> getEnvironmentSelenium() {
		if (!isLoaded)
			load();
		if (environment.containsKey("selenium"))
			return environment.get("selenium");
		else
			return null;
	}

}
