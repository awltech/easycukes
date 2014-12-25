/**
 * 
 */
package com.worldline.easycukes.scm.utils;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.worldline.easycukes.commons.Configuration;
import com.worldline.easycukes.commons.helpers.Constants;

/**
 * The class <code>GitHelperTest</code> contains tests for the class
 * <code>{@link GitHelper}</code>.
 * 
 * @author a513260
 * 
 */
public class GitHelperTest {

	private String username;
	private String password;
	private String baseurl;

	/**
	 * Run the void clone(String,String,String,String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void cloneTest() throws Exception {
		final String fullpath = baseurl + "/awltech/eclipse-appprofiles";
		final String directory = "/tmp/githelper/test/";

		GitHelper.clone(fullpath, username, password, directory);
		File file = new File(directory);
		Assert.assertTrue(file.isDirectory());
		Assert.assertTrue(file.listFiles().length > 0);
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

		if (Configuration.isProxyNeeded())
			Configuration.configureProxy();

		if (Configuration.getTargetCredentials() != null) {
			username = Configuration.getTargetCredentials().get(
					Constants.USERNAME_KEY) != null ? Configuration
					.getTargetCredentials().get(Constants.USERNAME_KEY)
					.toString() : "";
			password = Configuration.getTargetCredentials().get(
					Constants.PASSWORD_KEY) != null ? Configuration
					.getTargetCredentials().get(Constants.PASSWORD_KEY)
					.toString() : "";
		}
		baseurl = Configuration.getTargetVariable("baseurl");

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
