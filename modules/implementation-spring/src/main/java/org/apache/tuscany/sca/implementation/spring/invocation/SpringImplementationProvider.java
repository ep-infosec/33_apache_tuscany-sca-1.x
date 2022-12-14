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
package org.apache.tuscany.sca.implementation.spring.invocation;

import org.apache.tuscany.sca.core.invocation.ProxyFactory;
import org.apache.tuscany.sca.implementation.java.injection.JavaPropertyValueObjectFactory;
import org.apache.tuscany.sca.implementation.spring.SpringImplementation;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.provider.ImplementationProvider;
import org.apache.tuscany.sca.runtime.RuntimeComponent;
import org.apache.tuscany.sca.runtime.RuntimeComponentService;

/**
 * A provider class for runtime Spring implementation instances
 * @version $Rev: 511195 $ $Date: 2007-02-24 02:29:46 +0000 (Sat, 24 Feb 2007) $ 
 */
public class SpringImplementationProvider implements ImplementationProvider {
    private RuntimeComponent component;
    
    // A Spring application context object
    private SpringContextStub springContext;
    
    private SpringImplementation implementation;
    
    private JavaPropertyValueObjectFactory propertyValueObjectFactory;

    /**
     * Constructor for the provider - takes a component definition and a Spring implementation
     * description
     * @param component - the component in the assembly
     * @param implementation - the implementation
     */
    public SpringImplementationProvider(RuntimeComponent component,
                                        SpringImplementation implementation,
                                        ProxyFactory proxyService,
                                        JavaPropertyValueObjectFactory propertyValueObjectFactory,
                                        boolean annotationSupport,
                                        String versionSupported) {
        super();
        this.implementation = implementation;
        this.component = component;
        this.propertyValueObjectFactory = propertyValueObjectFactory;

        springContext = new SpringContextStub(component, implementation, proxyService, propertyValueObjectFactory, annotationSupport, versionSupported);        
        
    } // end constructor

    public Invoker createInvoker(RuntimeComponentService service, Operation operation) {
        return new SpringInvoker(component, springContext, service, operation);
    }
    
    public boolean supportsOneWayInvocation() {
        return false;
    }

    /**
     * Start this Spring implementation instance
     */
    public void start() {
        springContext.start();
    }

    /**
     * Stop this implementation instance
     */
    public void stop() {
    	springContext.close();
    }

} // end class SpringImplementationProvider
