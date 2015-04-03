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

import com.sun.jersey.api.client.ClientResponse;
import lombok.Getter;
import org.apache.commons.httpclient.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * This {@link ResponseWrapper} wrapper bean used to encapsulate the response
 * got from a REST call.
 *
 * @author mechikhi
 * @version 1.0
 */
public class ResponseWrapper {

    /**
     * A {@link String} representation of the {@link ClientResponse}
     */
    @Getter
    private String responseString;

    /**
     * A {@link int} representation of the {@link ClientResponse}
     */
    @Getter
    private int status;

    /**
     * Headers
     */
    private Map<String, String> headers;

    /**
     * Construct a new ResponseWrapper.
     *
     * @param responseString The response body as String
     * @param status         The response status
     */
    public ResponseWrapper(String responseString, int status) {
        super();
        this.responseString = responseString;
        this.status = status;
    }

    /**
     * Construct a new ResponseWrapper.
     *
     * @param responseString The response body as String
     * @param headers
     * @param status         The response status
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
     * @param headers the headers to condense
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
     * Gets header value with the given name
     *
     * @param name the name of the header to get
     * @return the header value
     */
    public String getHeaderValue(String name) {
        return headers.get(name);
    }
}
