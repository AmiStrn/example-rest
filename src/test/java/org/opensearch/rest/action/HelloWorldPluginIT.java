/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */


package org.opensearch.rest.action;

import com.carrotsearch.randomizedtesting.annotations.ThreadLeakScope;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.opensearch.client.Request;
import org.opensearch.client.Response;
import org.opensearch.plugins.Plugin;
import org.opensearch.test.OpenSearchIntegTestCase;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@ThreadLeakScope(ThreadLeakScope.Scope.NONE)
@OpenSearchIntegTestCase.ClusterScope(scope = OpenSearchIntegTestCase.Scope.SUITE)
public class HelloWorldPluginIT extends OpenSearchIntegTestCase {

    @Override
    protected Collection<Class<? extends Plugin>> nodePlugins() {
        return Collections.singletonList(HelloWorldPlugin.class);
    }

    public void testPluginInstalled() throws IOException {
        Response response = createRestClient().performRequest(new Request("GET", "/_cat/plugins"));
        logger.info("response body: {}", EntityUtils.toString(response.getEntity()));
        assertThat(EntityUtils.toString(response.getEntity()), containsString("my-rest-plugin"));
    }

    public void testPluginIsWorkingNoValue() throws IOException {
        Response response = createRestClient().performRequest(new Request("GET", "/hello-world"));
        logger.info("response body: {}", EntityUtils.toString(response.getEntity()));
        assertThat(EntityUtils.toString(response.getEntity()), equalTo("Hi! Your plugin is installed and working:)"));
    }

    public void testPluginIsWorkingWithValue() throws IOException {
        Request request = new Request("POST", "/hello-world");
        request.setEntity(
                new NStringEntity(
                        "{\"name\":\"Amitai\"}",
                        ContentType.APPLICATION_JSON
                )
        );
        Response response = createRestClient().performRequest(request);
        logger.info("response body: {}", EntityUtils.toString(response.getEntity()));
        assertThat(EntityUtils.toString(response.getEntity()), equalTo("Hi Amitai! Your plugin is installed and working:)"));
    }
}