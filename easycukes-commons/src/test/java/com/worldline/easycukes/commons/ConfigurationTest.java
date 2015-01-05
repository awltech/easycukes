package com.worldline.easycukes.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>ConfigurationTest</code> contains tests for the class
 * <code>{@link Configuration}</code>.
 *
 * @author m.echikhi
 */
public class ConfigurationTest {

	/**
	 * Run the Configuration() constructor test.
	 *
	 */
	@Test
	public void testConfiguration() throws Exception {
		final Configuration result = new Configuration();
		assertNotNull(result);
	}

	/**
	 * Run the void load() method test.
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void testLoad() throws Exception {

		Configuration.load();
		assertEquals(true, Configuration.isLoaded);
	}

	/**
	 * Run the HostConfiguration configureProxy() method test.
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void testConfigureProxy() throws Exception {

		final HostConfiguration result = Configuration.configureProxy();

		assertNull(result);
		// assertTrue(result.toString().contains(
		// "HostConfiguration[proxyHost=http"));
	}

	/**
	 * Run the Map<String, Map<String, Object>> getEnvironment() method test.
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void testGetEnvironment() throws Exception {

		final Map<String, Map<String, Object>> result = Configuration
				.getEnvironment();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertTrue(result.containsKey("proxy"));
		assertTrue(result.containsKey("selenium"));
	}

	/**
	 * Run the Map<String, Object> getEnvironmentSelenium() method test.
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void testGetEnvironmentSelenium() throws Exception {

		final Map<String, Object> result = Configuration
				.getEnvironmentSelenium();

		assertNotNull(result);
		assertEquals(6, result.size());
		assertEquals("firefox", result.get("target_browser"));
		assertEquals(Boolean.FALSE, result.get("use_remote"));
		assertEquals("http://127.0.0.1:4444/wd/hub",
				result.get("selenium_remote_address"));
		assertEquals("/chrome_path", result.get("chrome_driver_path"));
		assertEquals("/ie_path", result.get("ie_driver_path"));
		assertEquals(new Integer(30), result.get("default_timeout"));
	}

	/**
	 * Run the Map<String, Map<String, Object>> getTarget() method test.
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void testGetTarget() throws Exception {

		final Map<String, Map<String, Object>> result = Configuration
				.getTarget();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertTrue(result.containsKey("credentials"));
		assertTrue(result.containsKey("variables"));
	}

	/**
	 * Run the Map<String, Object> getTargetCredentials() method test.
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void testGetTargetCredentials() throws Exception {

		final Map<String, Object> result = Configuration.getTargetCredentials();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("easycukesuser", result.get("username"));
		assertEquals("easycukespassword", result.get("password"));
	}

	/**
	 * Run the String getTargetVariable(String) method test.
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void testGetTargetVariable() throws Exception {
		final String key = "baseurl";

		final String result = Configuration.getTargetVariable(key);

		assertEquals("https://github.com", result);
	}

	/**
	 * Run the boolean isProxyNeeded() method test.
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void testIsProxyNeeded() throws Exception {

		final boolean result = Configuration.isProxyNeeded();

		assertEquals(false, result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *             if the initialization fails for some reason
	 *
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 *
	 */
	@After
	public void tearDown() throws Exception {
	}
}