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

package org.apache.tuscany.sca.itest;

import java.io.File;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCAContribution;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;

import helloworld.HelloWorldService;

/**
 * This client program shows how to create an SCA runtime, start it,
 * and locate and invoke a SCA component
 */
public class HelloWorldClient {
    public static void main(String[] args) throws Exception {        

        SCANodeFactory factory = SCANodeFactory.newInstance();
        SCANode node = factory.createSCANode(new File("src/main/resources/imports/wsdl/helloworldws.composite").toURL().toString(),
                new SCAContribution("TestContribution", new File("src/main/resources/imports/wsdl/").toURL().toString()));
        node.start();        
              
        HelloWorldService hwService = 
            ((SCAClient)node).getService(HelloWorldService.class, "HelloWorldServiceComponent");
        
        System.out.println("Hello " + hwService.getGreetings("World"));

        node.stop();
        System.out.println("Bye");
    }

}
