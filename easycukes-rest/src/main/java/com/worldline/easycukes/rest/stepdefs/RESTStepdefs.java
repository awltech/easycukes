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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import com.worldline.easycukes.commons.DataInjector;
import com.worldline.easycukes.commons.ExecutionContext;
import com.worldline.easycukes.commons.helpers.JSONHelper;
import com.worldline.easycukes.rest.client.RestService;
import com.worldline.easycukes.rest.utils.CukesHelper;
import com.worldline.easycukes.rest.utils.DateHelper;
import com.worldline.easycukes.rest.utils.RestConstants;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RESTStepdefs are all the step definitions you can use in your Cucumber
 * features in order to test REST services. It allows you to execute REST
 * requests, but also to parse and validate the results, etc.
 *
 * @author aneveux
 * @version 1.0
 */
@Slf4j
public class RESTStepdefs {

    /**
     * Allows to send a GET request
     *
     * @param path the path on which the request should be executed, from the
     *             base URL
     */
    @When("^I call the \"([^\"]*)\" resource$")
    public void callAResource(final String path) {
        RestService.getInstance().sendRequestWithParameters("GET",
                DataInjector.injectData(path), null);
    }

    /**
     * Allows to validate that the result of a REST call is containing
     * expectations
     *
     * @param property the element to search for in the JSON response
     * @param result   the result that should be contained in the response property
     *                 of a previous REST call
     */
    @Then("^the response property \"(.*)\" is (\"?.*\"?)$")
    public void checkAResponseProperty(final String property,
                                       final String result) throws Throwable {
        log.info("Asserts that the response property is " + result);
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
     * @param property the element to search for in the JSON response
     */
    @Then("^the response property \"(.*)\" has a value$")
    public void checkIfResponsePropertyIsEmpty(final String property)
            throws Throwable {
        log.debug("Asserts that the response property " + property
                + " has a value ");
        Assert.assertTrue(StringUtils.isNotEmpty(RestService.getInstance()
                .getResponseProperty(DataInjector.injectData(property))));
    }

    /**
     * Allows to validate that the result of a REST call is containing
     * expectations
     *
     * @param property the element to search for in the JSON response
     * @param result   the result that should be contained in the response property
     *                 of a previous REST call
     */
    @Then("^the response property \"(.*)\" equals \"(.*)\"$")
    public void validateAResponsePropertyEqualsTo(final String property,
                                                  final String result) throws Throwable {
        log.info("Asserts that the response property equals " + result);
        Assert.assertTrue(DataInjector.injectData(result).equals(
                RestService.getInstance().getResponseProperty(
                        DataInjector.injectData(property))));
    }

    /**
     * Allows to validate that the result of a REST call is containing
     * expectations
     *
     * @param property the element to search for in the JSON response
     * @param result   the result that should be contained in the response property
     *                 of a previous REST call
     */
    @Then("^the response property \"(.*)\" not contains \"([^\"]*)\"$")
    public void validateAResponsePropertyDoesntContain(final String property,
                                                       final String result) throws Throwable {
        log.info("Asserts that the response property not contains " + result);
        Assert.assertFalse(RestService.getInstance()
                .getResponseProperty(DataInjector.injectData(property))
                .contains(DataInjector.injectData(result)));
    }

    /**
     * Allows to validate that the result of a REST call is containing
     * expectations
     *
     * @param property the element to search for in the JSON response
     * @param result   the result that should be contained in the response property
     *                 of a previous REST call
     */
    @Then("^the response property \"(.*)\" contains \"([^\"]*)\"$")
    public void validateAResponsePropertyContains(final String property,
                                                  final String result) throws Throwable {
        String expectedResult = DataInjector.injectData(result);
        if (CukesHelper.isExpression(result))
            expectedResult = CukesHelper.evalExpression(expectedResult);

        log.info("Asserts that the response property contains " + result);
        Assert.assertTrue(RestService.getInstance()
                .getResponseProperty(DataInjector.injectData(property))
                .contains(expectedResult));
    }

    /**
     * Allows to validate that the response status of a REST call is matching
     * with expectations.
     * <p/>
     * Asserts that a response status equals the status passed as parameter
     *
     * @param status the status that should match the response one
     */
    @Then("^the http response code is (\"?[0-9]*\"?)$")
    public void validateHTTPResponseCodeIs(final String status) {
        log.info("Asserts that the response status is " + status);
        Assert.assertTrue(RestService.getInstance().getResponseStatus() == Integer
                .parseInt(status));
    }

    /**
     * Allows to validate that the result of a REST call is containing
     * expectations, using a specific format
     *
     * @param result the result you expect to be contained in the response of a
     *               previous REST call
     */
    @Then("^the response contains \"(.*)\"$")
    public void validateResponseIs(final String result) {
        log.debug("Asserts that the response contains " + result);

        Assert.assertTrue(RestService.getInstance().getResponseContent()
                .contains(DataInjector.injectData(result)));
    }

    /**
     * Allows to validate that the result of a REST call is containing
     * expectations, using a specific format
     *
     * @param result the result you expect to be contained in the response of a
     *               previous REST call
     */
    @Then("^the response not contains \"(.*)\"$")
    public void validateResponseDoesntContain(final String result) {
        log.debug("Asserts that the response not contains " + result);

        Assert.assertFalse(RestService.getInstance().getResponseContent()
                .contains(DataInjector.injectData(result)));
    }

    @Then("^the response content is:$")
    public void validateJSONResponseIs(final String result) {
        log.debug("Asserts that response content is equivalent " + result);
        Assert.assertTrue(JSONHelper.equals(RestService.getInstance()
                .getResponseContent(), result));
    }

    /**
     * Allows to validate that the authentication ticket isn't null
     */
    @Then("^the response header \"(.*)\" has a value$")
    public void checkIfResponseHeaderIsNotEmpty(String prop) {
        log.debug("Asserts that the response header " + prop
                + " has a value ");
        final String headerValue = RestService.getInstance()
                .getPropertyFromResponseHeader(prop);
        Assert.assertTrue(StringUtils.isNotEmpty(headerValue));
    }

    /**
     * @param prop
     * @param result
     */
    @Then("^the response header \"(.*)\" is \"(.*)\"$")
    public void checkIfResponseHeaderIs(String prop, String result) {
        log.debug("Asserts that the response header is " + result);
        final String headerValue = RestService.getInstance()
                .getPropertyFromResponseHeader(prop);
        Assert.assertTrue(headerValue.equals(result));
    }

    /**
     * @param prop
     * @param result
     */
    @Then("^the response header \"(.*)\" matches \"(.*)\"$")
    public void checkIfResponseHeaderMatches(String prop, String result) {
        log.debug("Asserts that the response header matches " + result);
        final String headerValue = RestService.getInstance()
                .getPropertyFromResponseHeader(prop);
        Assert.assertTrue(headerValue.contains(result));
    }

    /**
     * Allows to send a REST request with some JSON parameters
     *
     * @param method     the HTTP method to be used (GET, POST, PUT, DELETE)
     * @param path       the path on which the request should be executed, from the
     *                   base URL
     * @param parameters the parameters to be sent, as a String representation of JSON
     *                   data
     */
    @When("^I (post|put|delete) to \"([^\"]*)\" the parameters:$")
    public void sendRequestWithParameters(final String method, final String path,
                                          final String parameters) {
        RestService.getInstance().sendRequestWithParameters(method,
                DataInjector.injectData(path),
                DataInjector.injectData(parameters));
    }

    /**
     * Allows to send a REST request with some JSON parameters
     *
     * @param path the path on which the request should be executed, from the
     *             base URL
     */
    @When("^I (post|put|delete) to \"(.*)\"$")
    public void sendRequestWithoutParameters(final String method, final String path)
            throws Throwable {
        RestService.getInstance().sendRequestWithParameters(method,
                DataInjector.injectData(path), null);
    }

    /**
     * @param param
     * @param property
     * @throws Throwable
     */
    @Then("^I set the parameter \"(.*?)\" to response property \"(.*?)\"$")
    public void setAParameterToAResponseProperty(final String param,
                                                 final String property) throws Throwable {
        log.info("Setting the parameter " + param + " to " + property);
        CukesHelper.setParameter(param, RestService.getInstance()
                .getResponseProperty(DataInjector.injectData(property)));
    }

    /**
     * @param param
     * @throws Throwable
     */
    @Then("^I set the parameter \"(.*?)\" to response content$")
    public void setAParameterToAResponseContent(final String param)
            throws Throwable {
        log.info("Setting the parameter " + param + " to response content");
        CukesHelper.setParameter(param, RestService.getInstance()
                .getResponseContent());
    }

    @Given("^I set the parameter \"(.*?)\" to (today|yesterday|tomorrow)$")
    public void setAParameterToAKeyword(final String param,
                                        final String value) throws Throwable {
        CukesHelper.setParameter(param, DateHelper.getDateValue(value));
    }

    @Given("^I set the date parameter \"(.*?)\" to (\"?.*\"?)$")
    public void setAParameterToADate(final String param, final String value)
            throws Throwable {
        CukesHelper.setParameter(param, DateHelper.parseDateToJson(value));
    }

    @Given("^I set the parameter \"(.*?)\" to \"(.*?)\"$")
    public void setAParameterToADynamicValue(final String param, final String value)
            throws Throwable {
        String result = DataInjector.injectData(value);
        if (CukesHelper.isExpression(result))
            result = CukesHelper.evalExpression(result);
        CukesHelper.setParameter(param, result);
    }

    @Given("^I set randomly the parameter \"(.*?)\" to property \"(.*?)\" in response array$")
    public void setAParameterToARandomPropertyFromResponseArray(
            final String param, final String property) throws Throwable {
        CukesHelper.setParameter(param, RestService.getInstance()
                .getRandomlyPropertyFromResponseArray(property));
    }

    @Given("^I set randomly the parameter \"(.*?)\" to an item in response array$")
    public void setAParameterToARandomItemFromResponseArray(
            String param) throws Throwable {
        CukesHelper.setParameter(param, RestService.getInstance()
                .getRandomlyAnItemFromResponseArray());
    }

    @Given("^I set max attempts to (\\d+)$")
    public void setMaxAttempts(int attempts) throws Throwable {
        CukesHelper.setParameter(RestConstants.MAX_ATTEMPTS_KEY,
                String.valueOf(attempts));
    }

    @Given("^I set retry interval to (\\d+) seconds$")
    public void setRetryInterval(int timeToWait) throws Throwable {
        CukesHelper.setParameter(RestConstants.TIME_TO_WAIT_KEY,
                String.valueOf(timeToWait));
    }

    @When("^I retry get to \"(.*?)\" until response property \"(.*?)\" is (\"?.*\"?)$")
    public void sendGetRequestAndRetryTillResponsePropertyIs(String path,
                                                             String property, String result) throws Throwable {
        log.info("Sending GET request to " + path
                + " and retry until response property " + property + " is "
                + result);

        String expectedResult = result;
        if (CukesHelper.isExpression(result))
            expectedResult = CukesHelper.evalExpression(result);

        RestService.getInstance().retryGetRequestUntilObtainExpectedResponse(
                DataInjector.injectData(path), property + "=" + expectedResult);
    }

    @When("^I retry get to \"(.*?)\" until response contains \"(.*?)\"$")
    public void sendGetRequestAndRetryTillResponseContains(String path,
                                                           String result) throws Throwable {
        log.info("Sending GET request to " + path
                + " and retry until response contains " + result);

        String expectedResult = DataInjector.injectData(result);
        if (CukesHelper.isExpression(result))
            expectedResult = CukesHelper.evalExpression(expectedResult);

        RestService.getInstance().retryGetRequestUntilObtainExpectedResponse(
                DataInjector.injectData(path), expectedResult);
    }

    @When("^I retry get to \"(.*?)\" until succeed$")
    public void sendGetRequestAndRetryTillSuccess(String path)
            throws Throwable {
        log.info("Sending GET request to " + path
                + " and retry until succeed");

        RestService.getInstance().retryGetRequestUntilSucceed(
                DataInjector.injectData(path));
    }

    @When("^I send get to \"(.*?)\"$")
    public void sendGetRequest(String path) throws Throwable {
        log.info("Sending GET request to " + path);

        RestService.getInstance().sendGetRequest(DataInjector.injectData(path));
    }

    @Then("^I set the parameter \"(.*?)\" to html input value \"(.*?)\"$")
    public void setAParameterToAHTMLInputValue(String param, String input)
            throws Throwable {
        final String value = RestService.getInstance()
                .extractInputValueFromHtmlResponse(input);
        CukesHelper.setParameter(param, value);
    }

    @Then("^I send post to \"(.*?)\" the parameters:$")
    public void sendPostRequestWithParameters(String url, String params)
            throws Throwable {
        RestService.getInstance().sendPost(DataInjector.injectData(url),
                DataInjector.injectData(params));
    }

    @Then("^I set the parameter \"(.*?)\" to response header \"(.*?)\"$")
    public void setAParameterToResponseHeader(String param,
                                              String property) throws Throwable {
        CukesHelper.setParameter(param, RestService.getInstance()
                .getPropertyFromResponseHeader(property));
    }

    @Then("^I set the request header \"(.*?)\" to \"(.*?)\"$")
    public void setTheRequestHeader(String header, String value)
            throws Throwable {
        RestService.getInstance().addRequestHeader(header,
                DataInjector.injectData(value));
    }

    @Then("^the parameter \"(.*?)\" contains \"(.*?)\"$")
    public void checkIfParameterContains(String param, String value)
            throws Throwable {
        Assert.assertTrue(ExecutionContext.get(param).contains(value));
    }

    @Then("^the parameter \"(.*?)\" equals \"(.*?)\"$")
    public void checkIfParameterEquals(String parameter, String value)
            throws Throwable {
        Assert.assertTrue(value.equalsIgnoreCase(ExecutionContext
                .get(parameter)));
    }

    @Given("^I set date format \"(.*?)\"$")
    public void setDateFormat(String format) throws Throwable {
        CukesHelper.setParameter(RestConstants.DATE_FORMAT, format);
    }
}
