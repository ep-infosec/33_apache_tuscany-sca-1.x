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
import org.apache.tuscany.sca.binding.http.operationselector.jsonrpc.JSONRPCOperationSelector;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.provider.OperationSelectorProvider;
import org.apache.tuscany.sca.provider.OperationSelectorProviderFactory;
import org.apache.tuscany.sca.runtime.RuntimeComponent;
import org.apache.tuscany.sca.runtime.RuntimeComponentReference;
import org.apache.tuscany.sca.runtime.RuntimeComponentService;

/**
 * JSON-RPC Operation Selector Provider Factory
 * 
 * @version $Rev$ $Date$
 */
public class JSONRPCOperationSelectorProviderFactory implements OperationSelectorProviderFactory<JSONRPCOperationSelector> {
    private ExtensionPointRegistry extensionPoints;

    public JSONRPCOperationSelectorProviderFactory(ExtensionPointRegistry extensionPoints) {
        this.extensionPoints = extensionPoints;
    }

    public OperationSelectorProvider createReferenceOperationSelectorProvider(RuntimeComponent component,
                                                                              RuntimeComponentReference reference,
                                                                              Binding binding) {
        return null;
    }

    public OperationSelectorProvider createServiceOperationSelectorProvider(RuntimeComponent component,
                                                                            RuntimeComponentService service,
                                                                            Binding binding) {
        return new JSONRPCOperationSelectorServiceProvider(extensionPoints, component, service, binding);
    }

    public Class<JSONRPCOperationSelector> getModelType() {
        return JSONRPCOperationSelector.class;
    }
}
