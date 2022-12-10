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

package org.apache.tuscany.sca.binding.http.operationselector.jsonrpc.provider;

import org.apache.tuscany.sca.assembly.Binding;
import org.apache.tuscany.sca.assembly.BindingRRB;
import org.apache.tuscany.sca.assembly.OperationSelector;
import org.apache.tuscany.sca.binding.http.HTTPBinding;
import org.apache.tuscany.sca.binding.http.operationselector.jsonrpc.JSONRPCOperationSelector;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.invocation.Interceptor;
import org.apache.tuscany.sca.invocation.MessageFactory;
import org.apache.tuscany.sca.invocation.Phase;
import org.apache.tuscany.sca.provider.OperationSelectorProvider;
import org.apache.tuscany.sca.runtime.RuntimeComponent;
import org.apache.tuscany.sca.runtime.RuntimeComponentService;

/**
 * JSON-RPC Operation Selector Service Provider
 * 
 * @version $Rev$ $Date$
 */
public class JSONRPCOperationSelectorServiceProvider implements OperationSelectorProvider {
    private MessageFactory messageFactory;
    
    private RuntimeComponent component;
    private RuntimeComponentService service;
    private Binding binding;

    public JSONRPCOperationSelectorServiceProvider(ExtensionPointRegistry extensionPoints,
                                                   RuntimeComponent component, 
                                                   RuntimeComponentService service, 
                                                   Binding binding) {
        ModelFactoryExtensionPoint modelFactories = extensionPoints.getExtensionPoint(ModelFactoryExtensionPoint.class);
        messageFactory = modelFactories.getFactory(MessageFactory.class);
 
        this.component = component;
        this.service = service;
        this.binding = binding;
    }
    
    public Interceptor createInterceptor() {
        if(binding instanceof BindingRRB) {
            BindingRRB rrbBinding = (BindingRRB) binding;
            OperationSelector operationSelector = rrbBinding.getOperationSelector();
            if(operationSelector != null && operationSelector instanceof JSONRPCOperationSelector) {
                return new JSONRPCOperationSelectorInterceptor((HTTPBinding) binding, service.getRuntimeWire(binding), messageFactory);
            }
        }
        
        return null;
    }

    public String getPhase() {
        return Phase.SERVICE_BINDING_OPERATION_SELECTOR;
    }

}
