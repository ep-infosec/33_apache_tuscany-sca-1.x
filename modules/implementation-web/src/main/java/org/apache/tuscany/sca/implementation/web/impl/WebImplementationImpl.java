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
package org.apache.tuscany.sca.implementation.web.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.ConstrainingType;
import org.apache.tuscany.sca.assembly.Property;
import org.apache.tuscany.sca.assembly.Reference;
import org.apache.tuscany.sca.assembly.Service;
import org.apache.tuscany.sca.assembly.builder.ComponentPreProcessor;
import org.apache.tuscany.sca.assembly.impl.ImplementationImpl;
import org.apache.tuscany.sca.contribution.jee.InjectionTarget;
import org.apache.tuscany.sca.implementation.java.impl.JavaElementImpl;
import org.apache.tuscany.sca.implementation.java.impl.JavaResourceImpl;
import org.apache.tuscany.sca.implementation.web.WebImplementation;
import org.apache.tuscany.sca.runtime.RuntimeComponent;


/**
 * The model representing an Web implementation in an SCA assembly model.
 */
class WebImplementationImpl extends ImplementationImpl implements WebImplementation, ComponentPreProcessor {

    private String webURI;

    private Map<String, JavaElementImpl> propertyInjectionPoints = new HashMap<String, JavaElementImpl>();

    private Map<String, JavaElementImpl> referenceInjectionPoints = new HashMap<String, JavaElementImpl>();

    private Map<String, JavaResourceImpl> resourceInjectionPoints = new HashMap<String, JavaResourceImpl>();
    
    private Map<InjectionTarget, Class<?>> optExtReferenceInjectionPoints = new HashMap<InjectionTarget, Class<?>>();
    
    private Map<String, String> optExtPropertyInjectionPoints = new HashMap<String, String>();
    /**
     * Constructs a new Web implementation.
     */
    WebImplementationImpl() {
        super();
    }

    @Override
    public ConstrainingType getConstrainingType() {
        // The Web implementation does not support constrainingTypes
        return null;
    }

    @Override
    public List<Service> getServices() {
        // The Web implementation does not offer services
        return Collections.emptyList();
    }
    
    @Override
    public void setConstrainingType(ConstrainingType constrainingType) {
        // The Web implementation does not support constrainingTypes
    }

    public String getWebURI() {
        return webURI;
    }

    public void setWebURI(String webURI) {
        this.webURI = webURI;
    }

    /**
     * Use preProcess to add any references and properties dynamically
     * TODO: also support introspection and handle WEB-INF/web.componentType (spec line 503) 
     */
    public void preProcess(Component component) {
        if (!(component instanceof RuntimeComponent)) {
            return;
        }
        RuntimeComponent rtc = (RuntimeComponent) component;
        
        for (Reference reference : rtc.getReferences()) {
            if (getReference(reference.getName()) == null) {
                getReferences().add(createReference(reference));
            }
        }

        for (Property property : rtc.getProperties()) {
            if (getProperty(property.getName()) == null) {
                getProperties().add(createProperty(property));
            }
        }
    }

    protected Reference getReference(String name) {
        for (Reference reference : getReferences()) {
            if (reference.getName().equals(name)) {
                return reference;
            }
        }
        return null;
    }

    protected Reference createReference(Reference reference) {
        Reference newReference;
        try {
            newReference = (Reference)reference.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e); // should not ever happen
        }
        return newReference;
    }

    protected Property getProperty(String name) {
        for (Property property : getProperties()) {
            if (property.getName().equals(name)) {
                return property;
            }
        }
        return null;
    }

    protected Property createProperty(Property property) {
        Property newProperty;
        try {
            newProperty = (Property)property.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e); // should not ever happen
        }
        return newProperty;
    }

    public Map<String, JavaElementImpl> getPropertyInjectionPoints() {
        return propertyInjectionPoints;
    }

    public Map<String, JavaElementImpl> getReferenceInjectionPoints() {
        return referenceInjectionPoints;
    }

    public Map<String, JavaResourceImpl> getResourceInjectionPoints() {
        return resourceInjectionPoints;
    }

    public Map<InjectionTarget, Class<?>> getOptExtensionReferenceInjectionPoints() {
        return optExtReferenceInjectionPoints;
    }

    public Map<String, String> getOptExtensionPropertyInjectionPoints() {
        return optExtPropertyInjectionPoints;
    }
}
