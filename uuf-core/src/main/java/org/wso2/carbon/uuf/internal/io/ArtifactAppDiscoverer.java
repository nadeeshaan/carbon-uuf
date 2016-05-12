/*
 *  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.carbon.uuf.internal.io;

import org.wso2.carbon.kernel.utils.Utils;
import org.wso2.carbon.uuf.exception.UUFException;
import org.wso2.carbon.uuf.internal.core.create.AppDiscoverer;
import org.wso2.carbon.uuf.reference.AppReference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class ArtifactAppDiscoverer implements AppDiscoverer {

    private final Path appsHome;

    /**
     * This constructor will assume Aps home as '$PRODUCT_HOME/deployment/uufapps'
     */
    public ArtifactAppDiscoverer() {
        this(Utils.getCarbonHome().resolve("deployment").resolve("uufapps"));
    }

    public ArtifactAppDiscoverer(Path appsHome) {
        this.appsHome = appsHome;
    }

    @Override
    public Stream<AppReference> getAppReferences() {
        try {
            return Files
                    .list(appsHome)
                    .filter(Files::isDirectory)
                    .map(ArtifactAppReference::new);
        } catch (IOException e) {
            throw new UUFException("An error occurred while listing apps in '" + appsHome + "'.", e);
        }
    }
}
