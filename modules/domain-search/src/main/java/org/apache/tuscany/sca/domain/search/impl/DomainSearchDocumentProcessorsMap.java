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
package org.apache.tuscany.sca.domain.search.impl;

import org.apache.tuscany.sca.assembly.Binding;
import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.ComponentType;
import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.assembly.Property;
import org.apache.tuscany.sca.contribution.Artifact;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.domain.search.DocumentProcessorsMap;

/**
 * @version $Rev$ $Date$
 */
public class DomainSearchDocumentProcessorsMap extends DocumentProcessorsMap {

    private static final long serialVersionUID = -4651637686945322606L;

    public DomainSearchDocumentProcessorsMap() {
        put(Contribution.class, new ContributionDocumentProcessor());
        put(Artifact.class, new ArtifactDocumentProcessor());
        put(Property.class, new PropertyDocumentProcessor());
        put(ComponentType.class, new ComponentTypeDocumentProcessor());
        put(Binding.class, new BindingDocumentProcessor());
        put(Component.class, new ComponentDocumentProcessor());
        put(Composite.class, new CompositeDocumentProcessor());
        put(FileContent.class, new DomainSearchFileDocumentProcessor());
        put(Property.class, new PropertyDocumentProcessor());

    }

}
