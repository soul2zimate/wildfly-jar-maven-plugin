/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.plugins.bootablejar.maven.goals;

import java.nio.file.Path;

import org.junit.Test;

/**
 * @author jdenise
 */
public class OpenShiftDisabledConfigurationTestCase extends AbstractBootableJarMojoTestCase {

    public OpenShiftDisabledConfigurationTestCase() {
        super("test-cloud-disabled-pom.xml", true, null);
    }

    @Test
    public void testOpenshiftConfiguration() throws Exception {
        BuildBootableJarMojo mojo = lookupMojo("package");
        assertNotNull(mojo);
        assertFalse(mojo.layers.isEmpty());
        assertNotNull(mojo.cloud);
        assertFalse(mojo.cloud.isEnabled());
        assertEquals(2, mojo.layers.size());
        assertEquals("jaxrs", mojo.layers.get(0));
        assertEquals("management", mojo.layers.get(1));
        mojo.recordState = true;
        mojo.execute();
        String[] layers = {"jaxrs", "management"};
        final Path dir = getTestDir();
        checkJar(dir, true, true, layers, null, mojo.recordState);
        checkDeployment(dir, true);
    }
}
