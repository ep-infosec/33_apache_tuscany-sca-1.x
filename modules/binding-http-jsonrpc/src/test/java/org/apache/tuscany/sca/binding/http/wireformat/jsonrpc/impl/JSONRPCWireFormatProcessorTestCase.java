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

package org.apache.tuscany.sca.binding.http.wireformat.jsonrpc.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.assembly.WireFormat;
import org.apache.tuscany.sca.binding.http.HTTPBinding;
import org.apache.tuscany.sca.binding.http.wireformat.jsonrpc.JSONRPCWireFormat;
import org.apache.tuscany.sca.contribution.processor.DefaultStAXArtifactProcessorExtensionPoint;
import org.apache.tuscany.sca.contribution.processor.ExtensibleStAXArtifactProcessor;
import org.apache.tuscany.sca.contribution.processor.StAXArtifactProcessorExtensionPoint;
import org.apache.tuscany.sca.core.DefaultExtensionPointRegistry;
import org.apache.tuscany.sca.core.UtilityExtensionPoint;
import org.apache.tuscany.sca.monitor.Monitor;
import org.apache.tuscany.sca.monitor.MonitorFactory;
import org.apache.tuscany.sca.monitor.impl.DefaultMonitorFactoryImpl;
import org.junit.BeforeClass;
import org.junit.Test;


/**
* JSON RPC wire format processor tests
* 
* @version $Rev$ $Date$
*/
public class JSONRPCWireFormatProcessorTestCase {
    
    public static final String COMPOSITE_WITH_WIRE_FORMAT =
        "<?xml version='1.0' encoding='UTF-8'?>" 
        + "<composite xmlns=\"http://www.osoa.org/xmlns/sca/1.0\" xmlns:ns1=\"http://www.osoa.org/xmlns/sca/1.0\" targetNamespace=\"http://binding-http\" name=\"binding-http\">"
        + "<component name=\"HelloWorldComponent\">"
        +     "<implementation.java class=\"services.HelloWorld\" />"
        +        "<service name=\"HelloWorldService\">"
        +            "<wstxns1:binding.http xmlns:wstxns1=\"http://tuscany.apache.org/xmlns/sca/1.0\" xmlns:ns2=\"http://tuscany.apache.org/xmlns/sca/1.0\" uri=\"http://localhost:8080/uri\">"
        +                "<ns2:wireFormat.jsonrpc />"
        +            "</wstxns1:binding.http>"
        +        "</service>"
        +  "</component>"
        + "</composite>";
        
    private static XMLInputFactory inputFactory;
    private static XMLOutputFactory outputFactory;
    private static ExtensibleStAXArtifactProcessor staxProcessor;
    private static Monitor monitor;
    
    @BeforeClass
    public static void setUp() throws Exception {
        DefaultExtensionPointRegistry extensionPoints = new DefaultExtensionPointRegistry();
        inputFactory = XMLInputFactory.newInstance();
        outputFactory = XMLOutputFactory.newInstance();
        // Create a monitor
        UtilityExtensionPoint utilities = extensionPoints.getExtensionPoint(UtilityExtensionPoint.class);
        MonitorFactory monitorFactory = new DefaultMonitorFactoryImpl();  
        if (monitorFactory != null) {
                monitor = monitorFactory.createMonitor();
                utilities.addUtility(monitorFactory);
        }
        StAXArtifactProcessorExtensionPoint staxProcessors = new DefaultStAXArtifactProcessorExtensionPoint(extensionPoints);
        staxProcessor = new ExtensibleStAXArtifactProcessor(staxProcessors, inputFactory, outputFactory, monitor);

    }
    
    /**
     * Tests the APIs:
     *     public WireFormat getRequstWireFormat();
     *     public WireFormat getResponseWireFormat();
     * 
     * @throws Exception
     */
    @Test
    public void testWireFormat() throws Exception {
        XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(COMPOSITE_WITH_WIRE_FORMAT));
        
        Composite composite = (Composite)staxProcessor.read(reader);
        HTTPBinding binding = (HTTPBinding)   composite.getComponents().get(0).getServices().get(0).getBindings().get(0);        
        assertNotNull(binding);
        
        WireFormat requestWireFormat = binding.getRequestWireFormat();
        assertEquals(JSONRPCWireFormat.class, requestWireFormat.getClass().getInterfaces()[0]);
        
        WireFormat responseWireFormat = binding.getResponseWireFormat();
        assertEquals(JSONRPCWireFormat.class, responseWireFormat.getClass().getInterfaces()[0]);
    }
    
    @Test
    public void testWriteWireFormat() throws Exception {
        XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(COMPOSITE_WITH_WIRE_FORMAT));
        
        Composite composite = (Composite)staxProcessor.read(reader);
        assertNotNull(composite);
        reader.close();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        staxProcessor.write(composite, bos);

        // used for debug comparison
        // System.out.println(COMPOSITE_WITH_WIRE_FORMAT);
        // System.out.println(bos.toString());

        assertEquals(COMPOSITE_WITH_WIRE_FORMAT, bos.toString());      
        
    }    
}
