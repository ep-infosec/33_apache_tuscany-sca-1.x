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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.tuscany.sca.assembly.Binding;
import org.apache.tuscany.sca.binding.jsonp.JSONPBinding;
import org.apache.tuscany.sca.interfacedef.DataType;
import org.apache.tuscany.sca.interfacedef.InterfaceContract;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.runtime.RuntimeComponent;
import org.apache.tuscany.sca.runtime.RuntimeComponentService;
import org.apache.tuscany.sca.runtime.RuntimeWire;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONPServlet extends GenericServlet {
    private static final long serialVersionUID = 1L;

    private RuntimeComponentService service;
    private JSONPBinding binding;
    protected transient Operation operation;
    private InterfaceContract contract;
    protected transient ObjectMapper mapper;
    
    public JSONPServlet(RuntimeComponentService componentService,
    		            JSONPBinding binding,
                        InterfaceContract contract,
                        Operation operation) {
        this.service = componentService;
        this.binding = binding;
        this.operation = operation;
        this.contract = contract;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        Object[] args = getJSONRequestStringArray(servletRequest);
        Object response = invokeService(args);   
        
        if (response != null){
	        String jsonResponse = getJSONResponseAsString(servletRequest, response);
	        servletResponse.getOutputStream().println(jsonResponse);
        }
    }
   
    /**
     * Turn the request into a string array of JSON structures. The Databinding
     * layer will then convert each of the individual parameters into the appropriate
     * types for the implementation interface
     * 
     */
    protected Object[] getJSONRequestStringArray(ServletRequest servletRequest) throws IOException, JsonParseException, JsonMappingException {
        
        List<DataType> types = operation.getInputType().getLogical();
        int typesIndex = 0;
        
        List<String> jsonRequestArray = new ArrayList<String>();
        
        for (String name : getOrderedParameterNames(servletRequest)) {
            String jsonRequest = "";
            if (!name.startsWith("_") && !"callback".equals(name)) {

                // automatically quote string parameters so clients work in the usual javascript way               
                if (typesIndex < types.size() && String.class.equals(types.get(typesIndex).getGenericType())) {
                    String x = servletRequest.getParameter(name);
                    // TODO: do this more properly
                    if (!x.startsWith("\"")) {
                        jsonRequest += "\"" + x + "\"";
                    } else {
                        jsonRequest += x;
                    }
                } else {               
                    jsonRequest += servletRequest.getParameter(name);
                }  
                jsonRequestArray.add(jsonRequest);
            }
        }

        return jsonRequestArray.toArray();
    }    

    /**
     * Get the request parameter names in the correct order.
     * The request args need to be in the correct order to invoke the service so work out the
     * order from the order in the query string. Eg, the url:
     *   http://localhost:8085/HelloWorldService/sayHello2?first=petra&last=arnold&callback=foo"
     * should invoke:
     *   sayHello2("petra", "arnold")
     * so the parameter names should be ordered: "first", "last"
     */
    protected SortedSet<String> getOrderedParameterNames(ServletRequest servletRequest) {
        final String queryString = ((HttpServletRequest)servletRequest).getQueryString();

        SortedSet<String> sortedNames = new TreeSet<String>(new Comparator<String>(){
            public int compare(String o1, String o2) {
                int i = queryString.indexOf(o1);
                int j = queryString.indexOf(o2);
                return i - j;
            }});

        Set<String> parameterNames = servletRequest.getParameterMap().keySet();
        for (String name : parameterNames) {
            sortedNames.add(name);    
        }

        return sortedNames;
    }
    
    /**
     * The databinding layer will have converterted the return type into a JSON string so simply 
     * add wrap it for return.
     */
    protected String getJSONResponseAsString(ServletRequest servletRequest, Object response) throws IOException, JsonParseException {
        String jsonResponse = response.toString();

        String callback = servletRequest.getParameter("callback");
        if (callback != null && callback.length() > 1) {
            jsonResponse = callback + "(" + jsonResponse + ");";
        }

        return jsonResponse;
    }    
   

    /**
     * Send the request down the wire to invoke the service 
     */
    protected Object invokeService(Object[] args) {
        try {
        	RuntimeWire wire = service.getRuntimeWire(binding, contract);
            return wire.invoke(operation, args);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}