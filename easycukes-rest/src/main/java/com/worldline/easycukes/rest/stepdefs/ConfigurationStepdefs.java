package com.worldline.easycukes.rest.stepdefs;

import java.io.IOException;

import com.worldline.easycukes.commons.context.Configuration;
import com.worldline.easycukes.commons.context.DataInjector;
import com.worldline.easycukes.commons.context.ExecutionContext;
import com.worldline.easycukes.commons.utils.Constants;
import com.worldline.easycukes.rest.client.RestService;

import cucumber.api.java.en.Given;

/**
 * This class aims at containing all the common operations you may use in the
 * tests scenarios. Things like dealing with the configuration, or manipulating
 * external tools, etc.
 *
 * @author aneveux
 * @version 1.0
 */
public class ConfigurationStepdefs {

	/**
	 * Allows to load the default configuration files for the application
	 *
	 * @throws IOException
	 *             if something's going wrong while dealing with the
	 *             configuration files
	 */
	@Given("^I load the default configuration files$")
	public void load_the_default_configuration_files() throws IOException {
		Configuration.load();
	}

	/**
	 * Allows to specifically load some configuration files, by specifying
	 * explicitely the environment and target to be used
	 *
	 * @param environment
	 *            the environment on which the tests are executed
	 * @param target
	 *            the environment which is targeted by the tests
	 * @throws Exception
	 *             if something's going wrong while dealing with the
	 *             configuration files
	 */
	@Given("^I load the application configuration file \"(.*?)\"$")
	public void load_application_configuration_file(final String target)
			throws Exception {
		Configuration.loadTarget(target);
	}

	/**
	 * Allows to specifically load some configuration files, by specifying
	 * explicitely the environment and target to be used
	 *
	 * @param environment
	 *            the environment on which the tests are executed
	 * @param target
	 *            the environment which is targeted by the tests
	 * @throws Exception
	 *             if something's going wrong while dealing with the
	 *             configuration files
	 */
	@Given("^I load the environment configuration file \"(.*?)\"$")
	public void load_environment_configuration_file(final String environment)
			throws Exception {
		Configuration.loadEnvironment(environment);
	}

	/**
	 * Allows to set base url
	 *
	 * @param url
	 */
	@Given("^the base url is \"(.*?)\"$")
	public void the_base_url_is(String url) {
		ExecutionContext.put(Constants.BASE_URL_KEY,
				DataInjector.injectData(url));
		RestService.getInstance().initialize(
				ExecutionContext.get(Constants.BASE_URL_KEY));
	}

}
