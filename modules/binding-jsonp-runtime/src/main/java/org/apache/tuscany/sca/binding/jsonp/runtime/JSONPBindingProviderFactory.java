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

package org.apache.tuscany.sca.binding.jsonp.runtime;

import org.apache.tuscany.sca.binding.jsonp.JSONPBinding;
import org.apache.tuscany.sca.contribution.ModelFactoryExtensionPoint;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.host.http.ServletHost;
import org.apache.tuscany.sca.host.http.ServletHostExtensionPoint;
import org.apache.tuscany.sca.invocation.MessageFactory;
import org.apache.tuscany.sca.provider.BindingProviderFactory;
import org.apache.tuscany.sca.provider.ReferenceBindingProvider;
import org.apache.tuscany.sca.provider.ServiceBindingProvider;
import org.apache.tuscany.sca.runtime.RuntimeComponent;
import org.apache.tuscany.sca.runtime.RuntimeComponentReference;
import org.apache.tuscany.sca.runtime.RuntimeComponentService;

public class JSONPBindingProviderFactory implements BindingProviderFactory<JSONPBinding> {

    private ServletHost servletHost;

    public JSONPBindingProviderFactory(ExtensionPointRegistry extensionPoints) {
        ServletHostExtensionPoint servletHosts = extensionPoints.getExtensionPoint(ServletHostExtensionPoint.class);
        this.servletHost = servletHosts.getServletHosts().get(0);
        
        ModelFactoryExtensionPoint modelFactories = extensionPoints.getExtensionPoint(ModelFactoryExtensionPoint.class);
    }

    public Class<JSONPBinding> getModelType() {
        return JSONPBinding.class;
    }

    public ReferenceBindingProvider createReferenceBindingProvider(RuntimeComponent component,
                                                                   RuntimeComponentReference reference,
                                                                   JSONPBinding binding) {
        return new JSONPReferenceBindingProvider(component, reference, binding);
    }

    public ServiceBindingProvider createServiceBindingProvider(RuntimeComponent component,
                                                               RuntimeComponentService service,
                                                               JSONPBinding binding) {
        return new JSONPServiceBindingProvider(component, service, binding, servletHost);
    }

}
