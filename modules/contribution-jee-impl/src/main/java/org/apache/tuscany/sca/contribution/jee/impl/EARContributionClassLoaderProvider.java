/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

package org.apache.tuscany.sca.contribution.jee.impl;

import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.PackageType;
import org.apache.tuscany.sca.contribution.java.ContributionClassLoaderProvider;
import org.apache.tuscany.sca.contribution.java.impl.ContributionClassLoader;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;

/**
 * The default implementation of the ContributionClassLoaderProvider
 */
public class EARContributionClassLoaderProvider implements ContributionClassLoaderProvider {

    public EARContributionClassLoaderProvider() {
        super();
    }
    
    public String getContributionType() {
        return PackageType.EAR;
    }

    public ClassLoader getClassLoader(Contribution contribution, ClassLoader parent) {
        // TODO - This is not quite right at the CCL will load up the nested jars
        //        also. However we do need to pick up the import processing so 
        //        need a bit of a refactor
        ContributionClassLoader ccl = new ContributionClassLoader(contribution, parent);
        return new EARContributionClassLoader(contribution, ccl);
    }

}
