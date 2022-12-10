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

import org.apache.lucene.document.Field;
import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.ComponentProperty;
import org.apache.tuscany.sca.assembly.ComponentReference;
import org.apache.tuscany.sca.assembly.ComponentService;
import org.apache.tuscany.sca.assembly.Property;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;
import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.interfacedef.InterfaceContract;
import org.apache.tuscany.sca.interfacedef.Operation;

/**
 * 
 * @version $Rev$ $Date$
 */
public class ComponentDocumentProcessor implements DocumentProcessor {

    public Document process(DocumentProcessor parentProcessor,
                            DocumentMap documents,
                            Object object,
                            Document doc,
                            String parent) {

        if (object instanceof Component) {
            Component component = (Component)object;
            String uri = component.getURI();
            String name = component.getName();

            uri = (uri == null ? "" : uri) + (name == null ? "" : name);

            if (uri.length() == 0) {
                uri = null;
            }

            if (uri != null) {

                if (doc == null) {
                    doc = documents.get(uri);
                }

                parent +=
                    DomainPathAnalyzer.PATH_SEPARATOR + SearchFields.COMPONENT_FIELD
                        + DomainPathAnalyzer.TYPE_SEPARATOR
                        + uri
                        + DomainPathAnalyzer.URI_SEPARATOR
                        + component.getName();

                doc.add(new Field(SearchFields.COMPONENT_FIELD, uri, Field.Store.YES, Field.Index.ANALYZED));

                for (ComponentService service : component.getServices()) {
                    Document serviceDoc = documents.get(uri + ':' + service.getName());

                    serviceDoc.add(new Field(SearchFields.SERVICE_NAME_FIELD, service.getName(), Field.Store.YES,
                                             Field.Index.ANALYZED));

                    InterfaceContract interfaceContract = service.getInterfaceContract();

                    if (interfaceContract != null) {

                        for (Operation operation : interfaceContract.getInterface().getOperations()) {

                            serviceDoc.add(new Field(SearchFields.SERVICE_INTERFACE_FIELD, operation.getName(),
                                                     Field.Store.YES, Field.Index.ANALYZED));

                        }

                        if (interfaceContract.getCallbackInterface() != null) {
                            for (Operation operation : interfaceContract.getCallbackInterface().getOperations()) {

                                serviceDoc.add(new Field(SearchFields.SERVICE_INTERFACE_CALLBACK_FIELD,
                                                         operation.getName(), Field.Store.YES, Field.Index.ANALYZED));

                            }
                        }

                    }

                    serviceDoc.add(new Field(SearchFields.PARENT_FIELD, parent, Field.Store.YES, Field.Index.ANALYZED));

                }

                for (ComponentReference reference : component.getReferences()) {
                    Document referenceDoc = documents.get(uri + ':' + reference.getName());

                    referenceDoc.add(new Field(SearchFields.REFERENCE_NAME_FIELD, reference.getName(), Field.Store.YES,
                                               Field.Index.ANALYZED));

                    InterfaceContract interfaceContract = reference.getInterfaceContract();

                    if (interfaceContract != null) {

                        for (Operation operation : interfaceContract.getInterface().getOperations()) {

                            referenceDoc.add(new Field(SearchFields.REFERENCE_INTERFACE_FIELD, operation.getName(),
                                                       Field.Store.YES, Field.Index.ANALYZED));

                        }

                        if (interfaceContract.getCallbackInterface() != null) {
                            for (Operation operation : interfaceContract.getCallbackInterface().getOperations()) {

                                referenceDoc.add(new Field(SearchFields.REFERENCE_INTERFACE_CALLBACK_FIELD, operation
                                    .getName(), Field.Store.YES, Field.Index.ANALYZED));

                            }
                        } 

                    }

                    referenceDoc
                        .add(new Field(SearchFields.PARENT_FIELD, parent, Field.Store.YES, Field.Index.ANALYZED));

                }

            }

            Document implementationDoc =
                parentProcessor.process(parentProcessor, documents, component.getImplementation(), null, parent);

            if (uri != null && implementationDoc != null) {

                implementationDoc.add(new Field(SearchFields.PARENT_FIELD, uri, Field.Store.YES, Field.Index.ANALYZED));

            }

            for (ComponentProperty componentProperty : component.getProperties()) {

                Property property = componentProperty.getProperty();

                if (property != null) {
                    Document propertyDoc = parentProcessor.process(parentProcessor, documents, property, null, parent);

                    if (uri != null) {
                        propertyDoc.add(new Field(SearchFields.PARENT_FIELD, parent, Field.Store.YES,
                                                  Field.Index.ANALYZED));

                    }

                }

            }

            return doc == null ? FAKE_DOCUMENT : doc;

        }

        throw new IllegalArgumentException();

    }

    public Object getDocumentKey(Object obj) {

        if (obj instanceof Component) {
            Component component = (Component)obj;
            String uri = component.getURI();
            String name = component.getName();

            uri = (uri == null ? "" : uri) + (name == null ? "" : name);

            if (uri.length() == 0) {
                return null;
            }

            return uri;

        }

        throw new IllegalArgumentException();

    }

    public Result processDocument(org.apache.lucene.document.Document document, Result result) {
        String componentName = document.get(SearchFields.COMPONENT_FIELD);

        if (componentName != null) {

        }

        return null;

    }

}
