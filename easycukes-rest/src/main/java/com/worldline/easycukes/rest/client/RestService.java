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
package com.worldline.easycukes.rest.client;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.worldline.easycukes.commons.context.Configuration;
import com.worldline.easycukes.commons.context.ExecutionContext;
import com.worldline.easycukes.commons.utils.Constants;
import com.worldline.easycukes.commons.utils.HtmlHelper;
import com.worldline.easycukes.commons.utils.JSONHelper;
import com.worldline.easycukes.rest.utils.RestConstants;

/**
 * {@link RestService} is actually the main entry point to be used in order to
 * interact with REST services. It provides a way to send requests to the target
 * platform, but also to manipulate the responses.
 * 
 * @author mechikhi
 * @version 1.0
 */
public class RestService {

	/**
	 * Just a {@link Logger}...
	 */
	private static final Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	private String baseUrl = null;

	/**
	 * Jersey client to be used for REST requests
	 */
	private final Client jerseyClient;

	/**
	 * HTTP Client (cap'tain obvious)
	 */
	private final HttpClient httpClient;

	private final Map<String, String> requestHeaders = new LinkedHashMap<String, String>();

	/**
	 * {@link ClientResponse} containing the latest response got from a REST
	 * call
	 */
	private ResponseWrapper response;

	/**
	 * Singleton instance
	 */
	private static RestService _instance;

	/**
	 * Allows to get the singleton instance of {@link RestService}
	 * 
	 * @return the singleton instance ({@link #_instance}) of
	 *         {@link RestService}
	 */
	public static final RestService getInstance() {
		if (_instance == null)
			_instance = new RestService();
		return _instance;
	}

	/**
	 * Private default constructor to ensure we're using the singleton pattern
	 */
	private RestService() {
		jerseyClient = newJerseyClient();
		httpClient = new HttpClient();
	}

	/**
	 * Creates and returns a new instance of jersey client
	 * 
	 * @return the instance created of jersey client
	 */
	private static Client newJerseyClient() {
		final Client c = Client.create();
		c.addFilter(new LoggingFilter(System.out));
		return c;
	}

	/**
	 * Destroy the client.
	 */
	public void destroy() {
		jerseyClient.destroy();
		_instance = null;
	}

	/**
	 * Allows to initialize the instance of {@link #httpClient} by setting the
	 * configuration of proxy
	 * 
	 */
	public void initialize(String baseUrl) {
		if (Configuration.isProxyNeeded())
			httpClient.setHostConfiguration(Configuration.configureProxy());
		Configuration.configureSSL();
		this.baseUrl = baseUrl;
	}

	/**
	 * Allows to send a REST request with parameters in JSON format
	 * 
	 * @param method
	 *            the HTTP method you want to use (GET, PUT, DELETE, POST)
	 * @param path
	 *            the path on which the request should be sent (starting from
	 *            base URL, see in the configuration files)
	 * @param parameters
	 *            the parameters to be used for the request (in JSON formatted
	 *            as a String)
	 */
	public void sendRequestWithParameters(final String method,
			final String path, final String parameters) {
		LOGGER.info("Sending " + method + " to " + path + " with: "
				+ parameters);

		response = null;
		String fullpath = path;
		if (path.startsWith("/"))
			fullpath = baseUrl + path;

		if (method.equalsIgnoreCase("GET")) {
			if (parameters == null || parameters.length() == 0)
				response = JerseyClientHelper.get(jerseyClient, fullpath);
			else
				LOGGER.error("Sorry, parameters cannot be given this way for GET requests...");
		} else if (method.equalsIgnoreCase("POST")) {
			if (parameters == null || parameters.length() == 0)
				response = JerseyClientHelper.post(jerseyClient, fullpath);
			else
				try {
					response = JerseyClientHelper.post(jerseyClient, fullpath,
							JSONHelper.toProperJSON(parameters));
				} catch (final ParseException e) {
					LOGGER.error("Sorry, parameters are not proper JSON...", e);
				}
		} else if (method.equalsIgnoreCase("PUT")) {
			if (parameters == null || parameters.length() == 0)
				response = JerseyClientHelper.put(jerseyClient, fullpath);
			else
				try {
					response = JerseyClientHelper.put(jerseyClient, fullpath,
							JSONHelper.toProperJSON(parameters));
				} catch (final ParseException e) {
					LOGGER.error("Sorry, parameters are not proper JSON...", e);
				}
		} else if (method.equalsIgnoreCase("DELETE")) {
			if (parameters == null || parameters.length() == 0)
				response = JerseyClientHelper.delete(jerseyClient, fullpath);
			else
				try {
					response = JerseyClientHelper.delete(jerseyClient,
							fullpath, JSONHelper.toProperJSON(parameters));
				} catch (final ParseException e) {
					LOGGER.error("Sorry, parameters are not proper JSON...", e);
				}
		} else
			LOGGER.error("Unknown m: " + method);

	}

	/**
	 * Gets the response status of a previous REST call
	 * 
	 */
	public int getResponseStatus() {
		return response.getStatus();
	}

	/**
	 * Gets the value of the specified property in the response header of a
	 * previous REST call
	 * 
	 * @param property
	 * @return the value if it's found (else it'll be null)
	 */
	public String getPropertyFromResponseHeader(String property) {

		final int index = property.indexOf(".");
		String headerName = property;
		if (index > 0)
			headerName = property.substring(0, index);
		final String headerValue = response.getHeaderValue(headerName);
		if (index < 0)
			return headerValue;
		if (headerValue != null)
			return extractHeaderParam(headerValue,
					property.substring(index + 1));
		return null;
	}

	/**
	 * Extract the input value from the last html response.
	 * 
	 * @param input
	 *            the name of the input in the html form
	 * @return The value, if it could be extracted, null otherwise.
	 */
	public String extractInputValueFromHtmlResponse(final String input) {
		return HtmlHelper.extractInputValueFromHtmlContent(input,
				response.getResponseString());
	}

	/**
	 * Extract the attribute from the header value passed
	 * 
	 * @param headerValue
	 *            header value
	 * @param param
	 *            the attribute whose value will be extracted
	 * @return the value if it's found (else it'll be null)
	 */
	protected String extractHeaderParam(final String headerValue,
			final String param) {
		final String PARAM_BEGIN = param + "=";
		int start = headerValue.indexOf(PARAM_BEGIN);
		if (start >= 0) {
			start += PARAM_BEGIN.length();
			int end = headerValue.indexOf(";", start);
			if (end > start)
				return headerValue.substring(start, end);
			end = headerValue.indexOf(",", start);
			if (end > start)
				return headerValue.substring(start, end);
			return headerValue.substring(start);
		} else
			LOGGER.warn("Could not extract " + param
					+ " from response header. Raw data is:\n'" + headerValue
					+ "'");
		return null;
	}

	/**
	 * Allows to get the specified property from the response of a previous REST
	 * call
	 * 
	 * @throws ParseException
	 *             if there's something wrong while parsing the JSON content
	 */
	public String getResponseProperty(String property) throws ParseException {
		return JSONHelper.getPropertyValue(response.getResponseString(),
				property);
	}

	/**
	 * Allows to get the specified property randomly from the response array of
	 * a previous REST call
	 * 
	 * @param property
	 * @return the value if it's found (else it'll be null)
	 * @throws ParseException
	 */
	public String getRandomlyPropertyFromResponseArray(String property)
			throws ParseException {
		final JSONArray jsonArray = JSONHelper.toJSONArray(response
				.getResponseString());
		if (jsonArray != null && !jsonArray.isEmpty())
			return JSONHelper.getValue((JSONObject) jsonArray.get(new Random()
					.nextInt(jsonArray.size())), property);
		return null;
	}

	/**
	 * Allows to get an item randomly from the response array of a previous REST
	 * call
	 * 
	 * @return the value if it's found (else it'll be null)
	 * @throws ParseException
	 */
	public String getRandomlyAnItemFromResponseArray() throws ParseException {
		final JSONArray jsonArray = JSONHelper.toJSONArray(response
				.getResponseString());
		if (jsonArray != null && !jsonArray.isEmpty())
			return (String) jsonArray
					.get(new Random().nextInt(jsonArray.size()));
		return null;
	}

	/**
	 * Gets response content from the response
	 * 
	 */
	public String getResponseContent() {
		return response.getResponseString();
	}

	/**
	 * Gets the result of the execution of a get request. the attempt will be
	 * repeated until obtain the exepected result
	 * 
	 * @param path
	 *            the path on which the request should be executed
	 * @param expression
	 *            the result that should be returned by the GET request, which
	 *            allows to know if that request is completely processed or not
	 * @throws Exception
	 *             if something's going wrong...
	 */
	public void retryGetRequestUntilObtainExpectedResponse(String path,
			String expression) throws Exception {

		String fullpath = path;
		if (path.startsWith("/"))
			fullpath = baseUrl + path;
		LOGGER.debug("Sending GET request to " + fullpath
				+ " with several attemps");

		final int maxAttempts = Integer.parseInt(ExecutionContext
				.get(RestConstants.MAX_ATTEMPTS_KEY));
		final int timeToWait = Integer.parseInt(ExecutionContext
				.get(RestConstants.TIME_TO_WAIT_KEY));

		final HttpMethod method = new GetMethod(fullpath);
		try {
			for (final Map.Entry<String, String> header : requestHeaders
					.entrySet())
				method.setRequestHeader(header.getKey(), header.getValue());

			String responseAsString = null;
			String toCheck = null;
			String expected = expression;
			String prop = null;
			final int idx = expression.indexOf("=");
			if (idx > 0) {
				prop = expression.substring(0, idx);
				expected = expression.substring(idx + 1);
			}
			int statusCode;
			int attempts = 0;
			boolean success = false;
			do {
				// waiting timeToWait seconds
				Thread.sleep(timeToWait * 1000);
				statusCode = httpClient.executeMethod(method);
				attempts++;
				if (statusCode == HttpStatus.SC_OK) {
					responseAsString = method.getResponseBodyAsString();
					toCheck = responseAsString;
					if (prop != null)
						toCheck = JSONHelper.getPropertyValue(responseAsString,
								prop);
					if (toCheck.contains(expected)) {
						success = true;
						LOGGER.debug("The result is available! ");
					} else
						LOGGER.warn("The result is not yet available! | Waiting "
								+ timeToWait + " seconds ...");
				} else
					LOGGER.warn("unsuccessful GET request : "
							+ method.getStatusLine() + " | Waiting "
							+ timeToWait + " seconds ...");
			} while (!success && maxAttempts > attempts);
			response = new ResponseWrapper(responseAsString, statusCode);
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		} finally {
			method.releaseConnection();
		}
	}

	/**
	 * Gets the result of the execution of a get request. the attempt will be
	 * repeated several times in case of failures
	 * 
	 * @param path
	 *            the path on which the request should be sent
	 * @throws Exception
	 *             if something's going wrong...
	 */
	public void retryGetRequestUntilSucceed(String path) throws Exception {

		String fullpath = path;
		if (path.startsWith("/"))
			fullpath = baseUrl + path;

		LOGGER.debug("Sending GET request to " + fullpath
				+ " with several attemps");

		final int maxAttempts = Integer.parseInt(ExecutionContext
				.get(RestConstants.MAX_ATTEMPTS_KEY));
		final int timeToWait = Integer.parseInt(ExecutionContext
				.get(RestConstants.TIME_TO_WAIT_KEY));

		final HttpMethod method = new GetMethod(fullpath);
		try {
			for (final Map.Entry<String, String> header : requestHeaders
					.entrySet())
				method.setRequestHeader(header.getKey(), header.getValue());

			String responseAsString = null;
			int statusCode;
			int attempts = 0;
			boolean success = false;
			do {
				// waiting timeToWait seconds
				Thread.sleep(timeToWait * 1000);
				statusCode = httpClient.executeMethod(method);
				attempts++;
				// check for status code 200
				if (statusCode == HttpStatus.SC_OK) {
					responseAsString = method.getResponseBodyAsString();
					success = true;
					LOGGER.info("The result is available! ");
				} else
					LOGGER.warn("unsuccessful GET request : "
							+ method.getStatusLine() + " | Waiting "
							+ timeToWait + " seconds ...");
			} while (!success && maxAttempts > attempts);
			response = new ResponseWrapper(responseAsString, statusCode);
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		} finally {
			method.releaseConnection();
		}
	}

	/**
	 * Allows to send a POST request, with parameters using JSON format
	 * 
	 * @param path
	 *            path to be used for the POST request
	 * @param data
	 *            paremeters to be used (JSON format) as a string
	 */
	@SuppressWarnings("unchecked")
	public void sendPost(final String path, final String data) throws Exception {
		String fullpath = path;
		if (path.startsWith("/"))
			fullpath = baseUrl + path;

		LOGGER.info("Sending POST request to " + fullpath);
		final PostMethod method = new PostMethod(fullpath);
		for (final Map.Entry<String, String> header : requestHeaders.entrySet())
			method.setRequestHeader(header.getKey(), header.getValue());
		if (data != null) {
			JSONObject jsonObject = null;
			try {
				jsonObject = JSONHelper.toJSONObject(data);
				for (final Iterator<String> iterator = jsonObject.keySet()
						.iterator(); iterator.hasNext();) {
					final String param = iterator.next();
					method.setParameter(
							param,
							(jsonObject.get(param) != null) ? jsonObject.get(
									param).toString() : null);
				}
			} catch (final ParseException e) {
				LOGGER.error("Sorry, parameters are not proper JSON...", e);
				throw new IOException(e.getMessage(), e);
			}

		}
		try {
			final int statusCode = httpClient.executeMethod(method);
			response = new ResponseWrapper(method.getResponseBodyAsString(),
					method.getResponseHeaders(), statusCode);
		} catch (final IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw new IOException(e.getMessage(), e);
		} finally {
			method.releaseConnection();
		}
	}

	/**
	 * Allows to send a GET request to the path passed using the http client
	 * 
	 * @param path
	 *            the path on which the request should be sent
	 */
	public void sendGetRequest(final String path) throws Exception {

		String fullpath = path;
		if (path.startsWith("/"))
			fullpath = baseUrl + path;
		LOGGER.info("Sending GET request to " + fullpath);

		final HttpMethod method = new GetMethod(fullpath);
		try {
			for (final Map.Entry<String, String> header : requestHeaders
					.entrySet())
				method.setRequestHeader(header.getKey(), header.getValue());
			final int statusCode = httpClient.executeMethod(method);
			response = new ResponseWrapper(method.getResponseBodyAsString(),
					statusCode);
		} catch (final IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		} finally {
			method.releaseConnection();
		}

	}

	/**
	 * Allows to setup request specific parameters. it will be used to define a
	 * custom headers for all http requests.(example cookie header).
	 * 
	 * @param header
	 *            the header name to add
	 * @param value
	 *            the header value
	 */
	public void addRequestHeader(String header, String value) {
		if (requestHeaders.isEmpty())
			jerseyClient.addFilter(new ClientFilter() {
				@Override
				public ClientResponse handle(final ClientRequest request)
						throws ClientHandlerException {
					for (final Map.Entry<String, String> header : requestHeaders
							.entrySet())
						request.getHeaders().putSingle(header.getKey(),
								header.getValue());
					return getNext().handle(request);
				}
			});
		requestHeaders.put(header, value);
	}
}