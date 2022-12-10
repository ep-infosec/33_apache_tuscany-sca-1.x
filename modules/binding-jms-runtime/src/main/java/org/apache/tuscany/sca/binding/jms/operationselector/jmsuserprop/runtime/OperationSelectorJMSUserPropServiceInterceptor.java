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
package org.apache.tuscany.sca.binding.jms.operationselector.jmsuserprop.runtime;

import java.util.List;

import javax.jms.JMSException;

import org.apache.tuscany.sca.binding.jms.context.JMSBindingContext;
import org.apache.tuscany.sca.binding.jms.impl.JMSBinding;
import org.apache.tuscany.sca.binding.jms.impl.JMSBindingException;
import org.apache.tuscany.sca.binding.jms.operationselector.jmsuserprop.OperationSelectorJMSUserProp;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.invocation.Interceptor;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.invocation.Message;
import org.apache.tuscany.sca.runtime.RuntimeComponentService;
import org.apache.tuscany.sca.runtime.RuntimeWire;

/**
  * Interceptor for user property based operation selection
 * 
 * <operationSelector.jmsUser propertName="MyHeaderProperty"/>
 * 
 */
public class OperationSelectorJMSUserPropServiceInterceptor implements Interceptor {

    private Invoker next;
    private RuntimeWire runtimeWire;
    private JMSBinding jmsBinding;
    private OperationSelectorJMSUserProp operationSelector;
    private RuntimeComponentService service;
    private List<Operation> serviceOperations;

    public OperationSelectorJMSUserPropServiceInterceptor(JMSBinding jmsBinding, RuntimeWire runtimeWire) {
        super();
        this.jmsBinding = jmsBinding;
        this.operationSelector = (OperationSelectorJMSUserProp)jmsBinding.getOperationSelector();
        this.runtimeWire = runtimeWire;
        this.service = (RuntimeComponentService) runtimeWire.getTarget().getContract();
        this.serviceOperations = service.getInterfaceContract().getInterface().getOperations();
    }

    public Message invoke(Message msg) {
        return next.invoke(invokeRequest(msg));
    }

    public Message invokeRequest(Message msg) {
            // get the jms context
            JMSBindingContext context = msg.getBindingContext();
            javax.jms.Message jmsMsg = context.getJmsMsg();
           
            Operation operation = getTargetOperation(jmsMsg);
            msg.setOperation(operation);

            return msg;
    }

    protected Operation getTargetOperation(javax.jms.Message jmsMsg) {
        String operationName = null;
        String opSelectorPropertyName = operationSelector.getPropertyName();
        
        try {
            operationName = jmsMsg.getStringProperty(opSelectorPropertyName);
        } catch(JMSException e) {
            throw new JMSBindingException(e);
        }
        
        if (operationName == null){
            throw new JMSBindingException("Property " + opSelectorPropertyName + " not found in message header");
        }
        
        for (Operation op : serviceOperations) {
            if (op.getName().equals(operationName)) {
                return op;
            }
        }
        
        throw new JMSBindingException("Can't find operation " + operationName);
    }


    public Invoker getNext() {
        return next;
    }

    public void setNext(Invoker next) {
        this.next = next;
    }

}
