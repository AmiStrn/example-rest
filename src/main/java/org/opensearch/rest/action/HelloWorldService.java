/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.rest.action;


import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestResponse;
import org.opensearch.rest.RestStatus;

public class HelloWorldService {

    public static RestResponse buildResponse(String name) {
        final String message = "Hi" + name + "! Your plugin is installed and working:)";
        return new BytesRestResponse(RestStatus.OK, message);
    }
}