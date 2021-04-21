/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.rest.action;


import org.opensearch.client.node.NodeClient;
import org.opensearch.rest.BaseRestHandler;
import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestRequest;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.opensearch.rest.RestRequest.Method.GET;
import static org.opensearch.rest.RestRequest.Method.POST;

public class RestHelloWorldAction extends BaseRestHandler {
    @Override
    public String getName() {
        return "rest_handler_hello_world";
    }

    @Override
    public List<Route> routes() {
        return unmodifiableList(asList(
                new Route(GET, "/hello-world"),
                new Route(POST, "/hello-world")));
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
        String name = request.hasContent()? request.contentParser().mapStrings().get("name"): "";

        return channel -> {
            try {
                channel.sendResponse(HelloWorldService.buildResponse(name));
            } catch (final Exception e) {
                channel.sendResponse(new BytesRestResponse(channel, e));
            }
        };
    }
}