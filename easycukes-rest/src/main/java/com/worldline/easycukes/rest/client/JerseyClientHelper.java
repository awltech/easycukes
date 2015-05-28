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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.httpclient.HttpStatus;

import javax.ws.rs.core.MediaType;

/**
 * {@link JerseyClientHelper} allows to deal with REST requests. It proposes
 * some functions allowing to invoke HTTP methods (GET, POST, PUT, etc.) on the
 * Web resource
 *
 * @author aneveux
 * @version 1.0
 */
@UtilityClass
public class JerseyClientHelper {

    /**
     * Generic GET request to a service
     *
     * @param jerseyClient instance of Jersey client to be used for REST requests
     * @param path         path to be used for the GET request
     * @return a {@link ResponseWrapper} that you can use. Common usage is for
     * example: <code></code>
     */
    public static ResponseWrapper get(@NonNull final Client jerseyClient,
                                      @NonNull final String path) {
        final WebResource webResource = jerseyClient.resource(path);
        final ClientResponse response = webResource.accept(
                MediaType.APPLICATION_JSON).get(ClientResponse.class);

        return toResponseWrapper(response);
    }

    /**
     * Generic POST request to a service
     *
     * @param jerseyClient instance of Jersey client to be used for REST requests
     * @param path         path to be used for the POST request
     * @return a {@link ClientResponse} containing the results of the REST call
     */
    public static ResponseWrapper post(@NonNull final Client jerseyClient,
                                       @NonNull final String path) {
        final WebResource webResource = jerseyClient.resource(path);
        final ClientResponse response = webResource
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).post(ClientResponse.class);
        return toResponseWrapper(response);
    }

    /**
     * Generic POST request to a service
     *
     * @param jerseyClient instance of Jersey client to be used for REST requests
     * @param path         path to be used for the POST request
     * @param data         paremeters to be used (JSON format) as a string
     * @return a {@link ClientResponse} containing the results of the REST call
     */
    public static ResponseWrapper post(@NonNull final Client jerseyClient,
                                       @NonNull final String path, @NonNull final String data) {
        final WebResource webResource = jerseyClient.resource(path);
        final ClientResponse response = webResource
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, data);
        return toResponseWrapper(response);
    }

    /**
     * Generic DELETE request to a service
     *
     * @param jerseyClient instance of Jersey client to be used for REST requests
     * @param path         path to be used for the DELETE request
     * @return a {@link ClientResponse} containing the results of the REST call
     */
    public static ResponseWrapper delete(@NonNull final Client jerseyClient,
                                         @NonNull final String path) {
        final WebResource webResource = jerseyClient.resource(path);
        final ClientResponse response = webResource
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);
        return toResponseWrapper(response);
    }

    /**
     * Generic DELETE request to a service
     *
     * @param jerseyClient instance of Jersey client to be used for REST requests
     * @param path         path to be used for the DELETE request
     * @param data         paremeters to be used (JSON format) as a string
     * @return a {@link ClientResponse} containing the results of the REST call
     */
    public static ResponseWrapper delete(@NonNull final Client jerseyClient,
                                         @NonNull final String path, @NonNull final String data) {
        final WebResource webResource = jerseyClient.resource(path);
        final ClientResponse response = webResource
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class, data);
        return toResponseWrapper(response);
    }

    /**
     * Generic PUT request to a service
     *
     * @param jerseyClient instance of Jersey client to be used for REST requests
     * @param path         path to be used for the PUT request
     * @return a {@link ClientResponse} containing the results of the REST call
     */
    public static ResponseWrapper put(@NonNull final Client jerseyClient,
                                      @NonNull final String path) {
        final WebResource webResource = jerseyClient.resource(path);
        final ClientResponse response = webResource
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        return toResponseWrapper(response);
    }

    /**
     * Generic PUT request to a service
     *
     * @param jerseyClient instance of Jersey client to be used for REST requests
     * @param path         path to be used for the PUT request
     * @param data         paremeters to be used (JSON format) as a string
     * @return a {@link ClientResponse} containing the results of the REST call
     */
    public static ResponseWrapper put(@NonNull final Client jerseyClient,
                                      @NonNull final String path, @NonNull final String data) {
        final WebResource webResource = jerseyClient.resource(path);
        final ClientResponse response = webResource
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .put(ClientResponse.class, data);
        return toResponseWrapper(response);
    }

    /**
     * Convert the reponse got from a REST call to bean wrapper
     *
     * @param response the response got from a REST call
     * @return a {@link ClientResponse} containing the results of the REST call
     */
    private static ResponseWrapper toResponseWrapper(
            @NonNull final ClientResponse response) {
        String responseString = null;
        if (response != null
                && response.getStatus() != HttpStatus.SC_NO_CONTENT) {
            responseString = response.getEntity(String.class);
        }
        return new ResponseWrapper(responseString, response.getStatus());
    }

}
