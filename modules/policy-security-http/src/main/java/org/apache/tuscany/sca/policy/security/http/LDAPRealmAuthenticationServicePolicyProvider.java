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

package org.apache.tuscany.sca.policy.security.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.tuscany.sca.assembly.Binding;
import org.apache.tuscany.sca.assembly.ConfiguredOperation;
import org.apache.tuscany.sca.assembly.OperationsConfigurator;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.invocation.Interceptor;
import org.apache.tuscany.sca.invocation.Phase;
import org.apache.tuscany.sca.policy.PolicySet;
import org.apache.tuscany.sca.policy.authorization.AuthorizationPolicy;
import org.apache.tuscany.sca.policy.security.http.extensibility.LDAPSecurityHandler;
import org.apache.tuscany.sca.provider.PolicyProvider;
import org.apache.tuscany.sca.runtime.RuntimeComponent;
import org.apache.tuscany.sca.runtime.RuntimeComponentService;


/**
 * 
 * @version $Rev$ $Date$
 */
public class LDAPRealmAuthenticationServicePolicyProvider implements PolicyProvider {
    private RuntimeComponent component;
    private RuntimeComponentService service;
    private Binding binding;
    LDAPSecurityHandler securityHandler;

    private List<Operation> operations = new ArrayList<Operation>();
    
    public LDAPRealmAuthenticationServicePolicyProvider(RuntimeComponent component, RuntimeComponentService service, Binding binding, LDAPSecurityHandler securityHandler) {
        super();
        this.component = component;
        this.service = service;
        this.binding = binding;
        this.securityHandler = securityHandler;
        
        this.operations.addAll(service.getInterfaceContract().getInterface().getOperations());
    }

    public String getPhase() {
        return Phase.SERVICE_BINDING_POLICY;
    }

    public Interceptor createInterceptor(Operation operation) {
        List<LDAPRealmAuthenticationPolicy> authenticationPolicies = null;
        List<AuthorizationPolicy> authorizationPolicies = null;

        if (operation != null) {
            authenticationPolicies = findAuthenticationPolicies(operation);
            authorizationPolicies = findAuthorizationPolicies(operation);
        }

        if (authenticationPolicies == null || authenticationPolicies.isEmpty()) {
            return null;
        } else {
            return new LDAPRealmAuthenticationInterceptor(securityHandler, authenticationPolicies, authorizationPolicies);
        }
    }


    /**
     * Private utility methods
     */
    
    /**
     * Find applicable authorization policySets
     * It first check if any explicitly policySet was identified
     * Otherwise it look into the list of applicablePolicySets
     * @param op
     * @return
     */
    private List<LDAPRealmAuthenticationPolicy> findAuthenticationPolicies(Operation op) {
        List<LDAPRealmAuthenticationPolicy> polices = new ArrayList<LDAPRealmAuthenticationPolicy>();

        // check explicity added policies first
        ConfiguredOperation configuredOperation = findOperation(op);
        if (configuredOperation!= null && configuredOperation.getPolicySets().size() > 0) {
            for ( PolicySet ps : configuredOperation.getPolicySets()) {
                for (Object p : ps.getPolicies()) {
                    if (p instanceof LDAPRealmAuthenticationPolicy) {
                        polices.add((LDAPRealmAuthenticationPolicy)p);
                    }
                }
            }
        }

        
        // otherwise find applicable policySets
        if ( polices.size() == 0) {
            // FIXME: How do we get a list of effective policySets for a given operation?
            for(Operation operation : operations) {
                if (operation!= null && operation.getName() != null && operation.getName().equals(op.getName())) {
                    for (PolicySet ps : operation.getPolicySets()) {
                        for (Object p : ps.getPolicies()) {
                            if (p instanceof LDAPRealmAuthenticationPolicy) {
                                polices.add((LDAPRealmAuthenticationPolicy)p);
                            }
                        }
                    }
                }
            }

            if (service instanceof OperationsConfigurator) {
                OperationsConfigurator operationsConfigurator = (OperationsConfigurator)service;
                for (ConfiguredOperation cop : operationsConfigurator.getConfiguredOperations()) {
                    if (cop!= null && cop.getName() != null && cop.getName().equals(op.getName())) {
                        for (PolicySet ps : cop.getApplicablePolicySets()) {
                            for (Object p : ps.getPolicies()) {
                                if (p instanceof LDAPRealmAuthenticationPolicy) {
                                    polices.add((LDAPRealmAuthenticationPolicy)p);
                                }
                            }
                        }
                    }
                }
            }

            List<PolicySet> policySets = service.getPolicySets();
            for (PolicySet ps : policySets) {
                for (Object p : ps.getPolicies()) {
                    if (p instanceof LDAPRealmAuthenticationPolicy) {
                        polices.add((LDAPRealmAuthenticationPolicy)p);
                    }
                }
            }
        }
        
        return polices;
    }
    
    /**
     * Find applicable authorization policySets
     * It first check if any explicitly policySet was identified
     * Otherwise it look into the list of applicablePolicySets
     * @param op
     * @return
     */
    private List<AuthorizationPolicy> findAuthorizationPolicies(Operation op) {
        List<AuthorizationPolicy> polices = new ArrayList<AuthorizationPolicy>();
        
        // check explicity added policies first
        ConfiguredOperation configuredOperation = findOperation(op);
        if (configuredOperation!= null && configuredOperation.getPolicySets().size() > 0) {
            for ( PolicySet ps : configuredOperation.getPolicySets()) {
                for (Object p : ps.getPolicies()) {
                    if (p instanceof AuthorizationPolicy) {
                        polices.add((AuthorizationPolicy)p);
                    }
                }
            }
        }
        
        // otherwise find applicable policySets
        if ( polices.size() == 0) {
            // FIXME: How do we get a list of effective policySets for a given operation?
            for(Operation operation : operations) {
                if (operation!= null && operation.getName() != null && operation.getName().equals(op.getName())) {
                    for (PolicySet ps : operation.getPolicySets()) {
                        for (Object p : ps.getPolicies()) {
                            if (p instanceof AuthorizationPolicy) {
                                polices.add((AuthorizationPolicy)p);
                            }
                        }
                    }
                }
            }

            if (service instanceof OperationsConfigurator) {
                OperationsConfigurator operationsConfigurator = (OperationsConfigurator)service;
                for (ConfiguredOperation cop : operationsConfigurator.getConfiguredOperations()) {
                    if (cop != null && cop.getName() != null && cop.getName().equals(op.getName())) {
                        for (PolicySet ps : cop.getApplicablePolicySets()) {
                            for (Object p : ps.getPolicies()) {
                                if (p instanceof AuthorizationPolicy) {
                                    polices.add((AuthorizationPolicy)p);
                                }
                            }
                        }
                    }
                }
            }

            List<PolicySet> policySets = service.getPolicySets();
            for (PolicySet ps : policySets) {
                for (Object p : ps.getPolicies()) {
                    if (p instanceof AuthorizationPolicy) {
                        polices.add((AuthorizationPolicy)p);
                    }
                }
            }            
        }
        
        return polices;
    }
    
    /**
     * Find a given configured operation
     * @param operation
     * @return
     */
    private ConfiguredOperation findOperation(Operation operation) {
        ConfiguredOperation configuredOperation = null;
        
        for (ConfiguredOperation cOperation : ((OperationsConfigurator)component).getConfiguredOperations()) {
            if(cOperation.getName().equals(operation.getName())) {
                configuredOperation = cOperation;
                break;
            }
        }
        
        return configuredOperation;
    }
}
