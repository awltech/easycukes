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
package com.worldline.easycukes.rest.stepdefs;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;

import com.worldline.easycukes.commons.DataInjector;
import com.worldline.easycukes.commons.ExecutionContext;
import com.worldline.easycukes.commons.helpers.Constants;
import com.worldline.easycukes.commons.helpers.JSONHelper;
import com.worldline.easycukes.rest.client.RestService;
import com.worldline.easycukes.rest.utils.CukesHelper;
import com.worldline.easycukes.rest.utils.DateHelper;
import com.worldline.easycukes.rest.utils.RestConstants;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * This class aims at containing all the operations that could be needed in the
 * tests scenarios while dealing with REST operations over an API REST. It also
 * allows to deal with data which are manipulated over that API.
 * 
 * @author aneveux
 * @version 1.0
 */
public class RESTStepdefs {

	/**
	 * A logger...
	 */
	private static final Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	/**
	 * Allows to send a GET request
	 * 
	 * @param path
	 *            the path on which the request should be executed, from the
	 *            base URL
	 */
	@When("^I call the \"([^\"]*)\" resource$")
	public void i_call_the_resource(final String path) {
		RestService.getInstance().sendRequestWithParameters("GET",
				DataInjector.injectData(path), null);
	}

	/**
	 * Allows to validate that the result of a REST call is containing
	 * expectations
	 * 
	 * @param property
	 *            the element to search for in the JSON response
	 * 
	 * @param result
	 *            the result that should be contained in the response property
	 *            of a previous REST call
	 */
	@Then("^the response property \"(.*)\" is (\"?.*\"?)$")
	public void the_response_property_is(final String property,
			final String result) throws Throwable {
		LOGGER.info("Asserts that the response property is " + result);
		String expectedResult = result;
		if (CukesHelper.isExpression(result))
			expectedResult = CukesHelper.evalExpression(expectedResult);
		Assert.assertTrue(expectedResult.equals(RestService.getInstance()
				.getResponseProperty(DataInjector.injectData(property))));
	}

	/**
	 * Allows to validate that the result of a REST call is containing
	 * expectations
	 * 
	 * @param property
	 *            the element to search for in the JSON response
	 */
	@Then("^the response property \"(.*)\" has a value$")
	public void the_response_property_not_empty(final String property)
			throws Throwable {
		LOGGER.debug("Asserts that the response property " + property
				+ " has a value ");
		Assert.assertTrue(StringUtils.isNotEmpty(RestService.getInstance()
				.getResponseProperty(DataInjector.injectData(property))));
	}

	/**
	 * Allows to validate that the result of a REST call is containing
	 * expectations
	 * 
	 * @param property
	 *            the element to search for in the JSON response
	 * 
	 * @param result
	 *            the result that should be contained in the response property
	 *            of a previous REST call
	 */
	@Then("^the response property \"(.*)\" equals \"(.*)\"$")
	public void the_response_property_equals(final String property,
			final String result) throws Throwable {
		LOGGER.info("Asserts that the response property equals " + result);
		Assert.assertTrue(DataInjector.injectData(result).equals(
				RestService.getInstance().getResponseProperty(
						DataInjector.injectData(property))));
	}

	/**
	 * Allows to validate that the result of a REST call is containing
	 * expectations
	 * 
	 * @param property
	 *            the element to search for in the JSON response
	 * 
	 * @param result
	 *            the result that should be contained in the response property
	 *            of a previous REST call
	 */
	@Then("^the response property \"(.*)\" not contains \"([^\"]*)\"$")
	public void the_response_property_not_contains(final String property,
			final String result) throws Throwable {

		LOGGER.info("Asserts that the response property not contains " + result);
		Assert.assertFalse(RestService.getInstance()
				.getResponseProperty(DataInjector.injectData(property))
				.contains(DataInjector.injectData(result)));

	}

	/**
	 * Allows to validate that the result of a REST call is containing
	 * expectations
	 * 
	 * @param property
	 *            the element to search for in the JSON response
	 * 
	 * @param result
	 *            the result that should be contained in the response property
	 *            of a previous REST call
	 */
	@Then("^the response property \"(.*)\" contains \"([^\"]*)\"$")
	public void the_response_property_contains(final String property,
			final String result) throws Throwable {

		String expectedResult = DataInjector.injectData(result);
		if (CukesHelper.isExpression(result))
			expectedResult = CukesHelper.evalExpression(expectedResult);

		LOGGER.info("Asserts that the response property contains " + result);
		Assert.assertTrue(RestService.getInstance()
				.getResponseProperty(DataInjector.injectData(property))
				.contains(expectedResult));

	}

	/**
	 * Allows to validate that the response status of a REST call is matching
	 * with expectations.
	 * 
	 * Asserts that a response status equals the status passed as parameter
	 * 
	 * @param status
	 *            the status that should match the response one
	 */
	@Then("^the http response code is (\"?[0-9]*\"?)$")
	public void the_http_response_code_is(final String status) {
		LOGGER.info("Asserts that the response status is " + status);
		Assert.assertTrue(RestService.getInstance().getResponseStatus() == Integer
				.parseInt(status));
	}

	/**
	 * Allows to validate that the result of a REST call is containing
	 * expectations, using a specific format
	 * 
	 * @param format
	 *            the format in which the reponse should be expressed (PLAIN,
	 *            JSON, etc.)
	 * @param result
	 *            the result you expect to be contained in the response of a
	 *            previous REST call
	 */
	@Then("^the response contains \"(.*)\"$")
	public void the_response_contains(final String result) {

		LOGGER.debug("Asserts that the response contains " + result);

		Assert.assertTrue(RestService.getInstance().getResponseContent()
				.contains(DataInjector.injectData(result)));
	}

	/**
	 * Allows to validate that the result of a REST call is containing
	 * expectations, using a specific format
	 * 
	 * @param format
	 *            the format in which the reponse should be expressed (PLAIN,
	 *            JSON, etc.)
	 * @param result
	 *            the result you expect to be contained in the response of a
	 *            previous REST call
	 */
	@Then("^the response not contains \"(.*)\"$")
	public void the_response_not_contains(final String result) {

		LOGGER.debug("Asserts that the response not contains " + result);

		Assert.assertFalse(RestService.getInstance().getResponseContent()
				.contains(DataInjector.injectData(result)));
	}

	@Then("^the response content is:$")
	public void the_json_response_is(final String result) {
		LOGGER.debug("Asserts that response content is equivalent " + result);
		Assert.assertTrue(JSONHelper.equals(RestService.getInstance()
				.getResponseContent(), result));
	}

	/**
	 * Allows to validate that the authentication ticket isn't null
	 */
	@Then("^the response header \"(.*)\" has a value$")
	public void the_response_header_not_empty(String prop) {
		LOGGER.debug("Asserts that the response header " + prop
				+ " has a value ");
		final String headerValue = RestService.getInstance()
				.getPropertyFromResponseHeader(prop);
		Assert.assertTrue(StringUtils.isNotEmpty(headerValue));
	}

	/**
	 * 
	 * @param prop
	 * @param result
	 */
	@Then("^the response header \"(.*)\" is \"(.*)\"$")
	public void the_response_header_is(String prop, String result) {
		LOGGER.debug("Asserts that the response header is " + result);
		final String headerValue = RestService.getInstance()
				.getPropertyFromResponseHeader(prop);
		Assert.assertTrue(headerValue.equals(result));
	}

	/**
	 * 
	 * @param prop
	 * @param result
	 */
	@Then("^the response header \"(.*)\" matches \"(.*)\"$")
	public void the_response_header_matches(String prop, String result) {
		LOGGER.debug("Asserts that the response header matches " + result);
		final String headerValue = RestService.getInstance()
				.getPropertyFromResponseHeader(prop);
		Assert.assertTrue(headerValue.contains(result));
	}

	/**
	 * Allows to send a REST request with some JSON parameters
	 * 
	 * @param method
	 *            the HTTP method to be used (GET, POST, PUT, DELETE)
	 * @param path
	 *            the path on which the request should be executed, from the
	 *            base URL
	 * @param parameters
	 *            the parameters to be sent, as a String representation of JSON
	 *            data
	 */
	@When("^I (post|put|delete) to \"([^\"]*)\" the parameters:$")
	public void send_to_the_parameters(final String method, final String path,
			final String parameters) {
		RestService.getInstance().sendRequestWithParameters(method,
				DataInjector.injectData(path),
				DataInjector.injectData(parameters));
	}

	/**
	 * Allows to send a REST request with some JSON parameters
	 * 
	 * @param path
	 *            the path on which the request should be executed, from the
	 *            base URL
	 */
	@When("^I (post|put|delete) to \"(.*)\"$")
	public void send_to(final String method, final String path)
			throws Throwable {
		RestService.getInstance().sendRequestWithParameters(method,
				DataInjector.injectData(path), null);
	}

	/**
	 * 
	 * @param param
	 * @param property
	 * @throws Throwable
	 */
	@Then("^I set the parameter \"(.*?)\" to response property \"(.*?)\"$")
	public void set_the_parameter_to_response_property(final String param,
			final String property) throws Throwable {
		LOGGER.info("Setting the parameter " + param + " to " + property);
		CukesHelper.setParameter(param, RestService.getInstance()
				.getResponseProperty(DataInjector.injectData(property)));
	}

	/**
	 * 
	 * @param param
	 * @throws Throwable
	 */
	@Then("^I set the parameter \"(.*?)\" to response content$")
	public void set_the_parameter_to_response_content(final String param)
			throws Throwable {
		LOGGER.info("Setting the parameter " + param + " to response content");
		CukesHelper.setParameter(param, RestService.getInstance()
				.getResponseContent());
	}

	@Given("^I set the parameter \"(.*?)\" to (today|yesterday|tomorrow)$")
	public void set_the_parameter_to_keyword(final String param,
			final String value) throws Throwable {
		CukesHelper.setParameter(param, DateHelper.getDateValue(value));
	}

	@Given("^I set the date parameter \"(.*?)\" to (\"?.*\"?)$")
	public void set_the_parameter_to_date(final String param, final String value)
			throws Throwable {
		CukesHelper.setParameter(param, DateHelper.parseDateToJson(value));
	}

	@Given("^I set the parameter \"(.*?)\" to \"(.*?)\"$")
	public void set_the_parameter_to(final String param, final String value)
			throws Throwable {
		String result = DataInjector.injectData(value);
		if (CukesHelper.isExpression(result))
			result = CukesHelper.evalExpression(result);
		CukesHelper.setParameter(param, result);
	}

	@Given("^I set randomly the parameter \"(.*?)\" to property \"(.*?)\" in response array$")
	public void set_randomly_the_parameter_to_property_in_response_array(
			final String param, final String property) throws Throwable {
		CukesHelper.setParameter(param, RestService.getInstance()
				.getRandomlyPropertyFromResponseArray(property));
	}

	@Given("^I set randomly the parameter \"(.*?)\" to an item in response array$")
	public void set_randomly_the_parameter_to_an_item_in_response_array(
			String param) throws Throwable {
		CukesHelper.setParameter(param, RestService.getInstance()
				.getRandomlyAnItemFromResponseArray());
	}

	@Given("^I set max attempts to (\\d+)$")
	public void set_max_attempts_to(int attempts) throws Throwable {
		CukesHelper.setParameter(RestConstants.MAX_ATTEMPTS_KEY,
				String.valueOf(attempts));
	}

	@Given("^I set retry interval to (\\d+) seconds$")
	public void set_retry_interval_to_seconds(int timeToWait) throws Throwable {
		CukesHelper.setParameter(RestConstants.TIME_TO_WAIT_KEY,
				String.valueOf(timeToWait));
	}

	@When("^I retry get to \"(.*?)\" until response property \"(.*?)\" is (\"?.*\"?)$")
	public void send_get_to_and_retry_until_response_property_is(String path,
			String property, String result) throws Throwable {

		LOGGER.info("Sending GET request to " + path
				+ " and retry until response property " + property + " is "
				+ result);

		String expectedResult = result;
		if (CukesHelper.isExpression(result))
			expectedResult = CukesHelper.evalExpression(result);

		RestService.getInstance().retryGetRequestUntilObtainExpectedResponse(
				DataInjector.injectData(path), property + "=" + expectedResult);
	}

	@When("^I retry get to \"(.*?)\" until response contains \"(.*?)\"$")
	public void send_get_to_and_retry_until_response_contains(String path,
			String result) throws Throwable {

		LOGGER.info("Sending GET request to " + path
				+ " and retry until response contains " + result);

		String expectedResult = DataInjector.injectData(result);
		if (CukesHelper.isExpression(result))
			expectedResult = CukesHelper.evalExpression(expectedResult);

		RestService.getInstance().retryGetRequestUntilObtainExpectedResponse(
				DataInjector.injectData(path), expectedResult);
	}

	@When("^I retry get to \"(.*?)\" until succeed$")
	public void send_get_to_and_retry_until_succeed(String path)
			throws Throwable {
		LOGGER.info("Sending GET request to " + path
				+ " and retry until succeed");

		RestService.getInstance().retryGetRequestUntilSucceed(
				DataInjector.injectData(path));
	}

	@When("^I send get to \"(.*?)\"$")
	public void send_get_to(String path) throws Throwable {
		LOGGER.info("Sending GET request to " + path);

		RestService.getInstance().sendGetRequest(DataInjector.injectData(path));
	}

	@Then("^I set the parameter \"(.*?)\" to html input value \"(.*?)\"$")
	public void set_the_parameter_to_html_input_value(String param, String input)
			throws Throwable {
		final String value = RestService.getInstance()
				.extractInputValueFromHtmlResponse(input);
		CukesHelper.setParameter(param, value);
	}

	@Then("^I send post to \"(.*?)\" the parameters:$")
	public void send_post_to_the_parameters(String url, String params)
			throws Throwable {
		RestService.getInstance().sendPost(DataInjector.injectData(url),
				DataInjector.injectData(params));
	}

	@Then("^I set the parameter \"(.*?)\" to response header \"(.*?)\"$")
	public void set_the_parameter_to_response_header(String param,
			String property) throws Throwable {
		CukesHelper.setParameter(param, RestService.getInstance()
				.getPropertyFromResponseHeader(property));
	}

	@Then("^I set the request header \"(.*?)\" to \"(.*?)\"$")
	public void set_the_request_header_to(String header, String value)
			throws Throwable {
		RestService.getInstance().addRequestHeader(header,
				DataInjector.injectData(value));
	}

	@Then("^the parameter \"(.*?)\" contains \"(.*?)\"$")
	public void the_parameter_contains(String param, String value)
			throws Throwable {
		Assert.assertTrue(ExecutionContext.get(param).contains(value));
	}

	@Then("^the parameter \"(.*?)\" equals \"(.*?)\"$")
	public void the_parameter_equals(String parameter, String value)
			throws Throwable {
		Assert.assertTrue(value.equalsIgnoreCase(ExecutionContext
				.get(parameter)));
	}

	@Given("^I set date format \"(.*?)\"$")
	public void set_date_format(String format) throws Throwable {
		CukesHelper.setParameter(RestConstants.DATE_FORMAT, format);
	}
}
