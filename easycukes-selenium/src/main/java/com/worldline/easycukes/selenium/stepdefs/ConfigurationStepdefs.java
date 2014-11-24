package com.worldline.easycukes.selenium.stepdefs;

import java.io.IOException;

import org.openqa.selenium.browserlaunchers.Sleeper;

import com.worldline.easycukes.commons.context.Configuration;
import com.worldline.easycukes.commons.context.DataInjector;
import com.worldline.easycukes.commons.context.ExecutionContext;
import com.worldline.easycukes.commons.utils.Constants;
import com.worldline.easycukes.selenium.pages.PageManager;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

/**
 * This class allow to configure the environment for the execution of test
 * cases.
 *
 * @author mechikhi
 * @version 1.0
 */
public class ConfigurationStepdefs extends AbstractStepdefs {

	@Before
	/**
	 * Allows to waiting 2 seconds before the start of each scenario
	 */
	public void before(Scenario scenario) {
		Sleeper.sleepTightInSeconds(1);
	}

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
	public void the_base_url_is(String url) throws Exception {
		ExecutionContext.put(Constants.BASE_URL_KEY,
				DataInjector.injectData(url));
		PageManager.initialize(ExecutionContext.get(Constants.BASE_URL_KEY));
	}
}
