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

package org.apache.tuscany.sca.binding.http.wireformat.jsonrpc.provider;

import org.apache.tuscany.sca.binding.http.HTTPBinding;
import org.apache.tuscany.sca.binding.http.HTTPBindingContext;
import org.apache.tuscany.sca.interfacedef.InterfaceContract;
import org.apache.tuscany.sca.interfacedef.java.JavaInterface;
import org.apache.tuscany.sca.invocation.Interceptor;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.invocation.Message;
import org.apache.tuscany.sca.invocation.MessageFactory;
import org.apache.tuscany.sca.runtime.RuntimeWire;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osoa.sca.ServiceRuntimeException;

import com.metaparadigm.jsonrpc.JSONRPCResult;

/**
 * JSON-RPC Wire Format Interceptor
 * 
 * @version $Rev$ $Date$
 */
public class JSONRPCWireFormatInterceptor implements Interceptor {
    private Invoker next;
    
    private RuntimeWire runtimeWire;
    private HTTPBinding binding;
    
    private InterfaceContract serviceContract;
    
    private MessageFactory messageFactory;
    
    public JSONRPCWireFormatInterceptor(HTTPBinding binding, RuntimeWire runtimeWire, InterfaceContract serviceContract, MessageFactory messageFactory) {
        this.binding = binding;
        this.runtimeWire = runtimeWire;
        
        this.serviceContract = serviceContract;
        
        this.messageFactory = messageFactory;
    }

    public Invoker getNext() {
        return next;
    }

    public void setNext(Invoker next) {
        this.next = next;
        
    }

    public Message invoke(Message msg) {
        HTTPBindingContext bindingContext = msg.getBindingContext();
        Message returnMessage = null;
        
        if ("smd".equals(bindingContext.getHttpRequest().getQueryString())) {
            returnMessage = handleSMDInvocation(msg);
        } else {
            returnMessage = handleJSONRPCInvocation(msg);
        }
        
        return returnMessage;
        
    }
    
    /**
     * Handle regular JSON-RPC Invocation
     * @param msg
     * @return
     */
    private Message handleJSONRPCInvocation (Message msg) {
        JSONObject jsonReq = (JSONObject) msg.getBody();
        String method = null;
        Object[] args = null;
        Object id = null;
        try {
            // Extract the method
            method = jsonReq.getString("method");
            if ((method != null) && (method.indexOf('.') < 0)) {
                jsonReq.putOpt("method", "Service" + "." + method);
            }
            
            // Extract the arguments
            JSONArray array = jsonReq.getJSONArray("params");
            args = new Object[array.length()];
            for (int i = 0; i < args.length; i++) {
                args[i] = array.get(i);
            }
            id = jsonReq.get("id");

        } catch (Exception e) {
            throw new RuntimeException("Unable to find json method name", e);
        }
        
        Message responseMessage = null;
        try {
            msg.setBody(args);
            responseMessage = runtimeWire.getInvocationChain(msg.getOperation()).getHeadInvoker().invoke(msg);
        } catch (RuntimeException re) {
            Throwable exception = new RuntimeException("Error invoking service :" + re.getMessage(), re);
            return createJSONFaultMessage(re);
        }

        Object result = null;
        if (!responseMessage.isFault()) {
            //successful execution of the invocation
            try {
                result = responseMessage.getBody();
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("result", result);
                jsonResponse.putOpt("id", id);
                //get response to send to client
                responseMessage.setBody(jsonResponse);
                return responseMessage;
            } catch (Exception e) {
                throw new ServiceRuntimeException("Unable to create JSON response", e);
            }
        } else {
            //exception thrown while executing the invocation
            Throwable exception = (Throwable)responseMessage.getBody();
            return createJSONFaultMessage(exception);
        }
    }
    
    /**
     * handles requests for the SMD descriptor for a service
     */
    private Message handleSMDInvocation(Message msg) {
        HTTPBindingContext bindingContext = msg.getBindingContext();
        
        String serviceUrl = bindingContext.getHttpRequest().getRequestURL().toString();
        JavaInterface interfaze = (JavaInterface) serviceContract.getInterface();
        String smd = JavaToSmd.interfaceToSmd(interfaze.getJavaClass(), serviceUrl);
        
        Message smdResponseMessage = messageFactory.createMessage();
        smdResponseMessage.setBody(smd);
        
        return smdResponseMessage;
    }

    /**
     * Create a Fault Message with a JSON representation of the current exception
     * @param throwable
     * @return
     */
    private Message createJSONFaultMessage(Throwable throwable) {
        Message jsonFaultMessage = messageFactory.createMessage();
        
        JSONRPCResult jsonFault = new JSONRPCResult(JSONRPCResult.CODE_REMOTE_EXCEPTION, null, throwable);
        jsonFaultMessage.setBody(jsonFault);
        
        return jsonFaultMessage;
    }
}
