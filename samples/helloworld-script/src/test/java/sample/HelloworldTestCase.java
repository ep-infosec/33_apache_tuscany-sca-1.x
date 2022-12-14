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
package sample;

import junit.framework.TestCase;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;

/**
 * This shows how to test the Helloworld component.
 */
public class HelloworldTestCase extends TestCase {

    private Helloworld service;
    private SCANode node;

    @Override
    protected void setUp() throws Exception {
        node = SCANodeFactory.newInstance().createSCANodeFromClassLoader("helloworld.composite", getClass().getClassLoader());
        node.start();
        
        service = ((SCAClient)node).getService(Helloworld.class, "HelloworldComponent");
    }

    @Override
    protected void tearDown() throws Exception {
        node.stop();
    }

    public void testHelloworld() throws Exception {
        assertEquals("Hello petra", service.sayHello("petra"));
    }
}
