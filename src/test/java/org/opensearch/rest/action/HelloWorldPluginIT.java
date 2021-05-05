/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */


package org.opensearch.rest.action;

import org.opensearch.client.Request;
import org.opensearch.plugins.Plugin;
import org.opensearch.test.OpenSearchIntegTestCase;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class HelloWorldPluginIT extends OpenSearchIntegTestCase {

    @Override
    protected Collection<Class<? extends Plugin>> nodePlugins() {
        return Collections.singletonList(HelloWorldPlugin.class);
    }

    public void testPluginExists() throws IOException {
        createRestClient().performRequest(new Request("GET", "/_cat/plugins"));
    }
}