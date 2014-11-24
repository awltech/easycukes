package com.worldline.easycukes.rest.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;

import com.sun.jersey.api.client.ClientResponse;

/**
 * This {@link ResponseWrapper} wrapper bean used to encapsulate the response
 * got from a REST call.
 * 
 * @author mechikhi
 * @version 1.0
 * 
 */
public class ResponseWrapper {

	/**
	 * A {@link String} representation of the {@link ClientResponse}
	 */
	private String responseString;

	/**
	 * A {@link int} representation of the {@link ClientResponse}
	 */
	private int status;

	private Map<String, String> headers;

	/**
	 * Construct a new ResponseWrapper.
	 * 
	 * @param responseString
	 *            The response body as String
	 * @param status
	 *            The response status
	 */
	public ResponseWrapper(String responseString, int status) {
		super();
		this.responseString = responseString;
		this.status = status;
	}

	/**
	 * 
	 * Construct a new ResponseWrapper.
	 * 
	 * @param responseString
	 *            The response body as String
	 * @param headers
	 * @param status
	 *            The response status
	 * 
	 */
	public ResponseWrapper(String responseString, Header[] headers, int status) {
		super();
		this.responseString = responseString;
		this.status = status;
		this.headers = getCondensedHeaders(headers);
	}

	/**
	 * Gets condensed headers. Each header got by the concatenation of all
	 * values ​​in the headers having the same name. The concatenation will be
	 * combined with a ","
	 * 
	 * @param headers
	 *            the headers to condense
	 * 
	 * @return Map of headers with condensed values
	 */
	public Map<String, String> getCondensedHeaders(Header[] headers) {

		Map<String, String> result = new HashMap<String, String>();
		Map<String, StringBuffer> mapHeaders = new HashMap<String, StringBuffer>();
		for (int i = 0; i < headers.length; i++) {
			if (!mapHeaders.containsKey(headers[i].getName())) {
				mapHeaders.put(headers[i].getName(), new StringBuffer(
						headers[i].getValue()));
			} else {
				mapHeaders.get(headers[i].getName()).append(", ")
						.append(headers[i].getValue());
			}
		}

		for (String name : mapHeaders.keySet()) {
			result.put(name, mapHeaders.get(name).toString());
		}
		return result;
	}

	/**
	 * Just a getter for {@link #responseString}...
	 * 
	 * @return {@link #responseString}, obviously
	 */
	public String getResponseString() {
		return responseString;
	}

	/**
	 * Just a getter for {@link #status}...
	 * 
	 * @return {@link #status}, obviously
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Gets header value with the given name
	 * 
	 * @param name
	 *            the name of the header to get
	 * @return the header value
	 */
	public String getHeaderValue(String name) {
		return headers.get(name);
	}
}
