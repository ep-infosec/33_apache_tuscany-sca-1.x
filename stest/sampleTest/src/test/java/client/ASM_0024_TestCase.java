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
package client;


import test.ASM_0002_Client;
import testClient.TestInvocation;

/**
 * Client for ASM_0024_TestCase, which tests that where a <component/> 
 * <reference/> has @target set to some service, that the reference 
 * can have no child <binding/> elements   
 */
public class ASM_0024_TestCase extends BaseJAXWSTestCase {

 
	/**
	 * <p>
	 * OSOA 
	 * Line 1379~1381
	 * Note that a binding element may specify an endpoint which is the target of that binding. A 
	 * reference must not mix the use of endpoints specified via binding elements with target endpoints
	 * specified via the target attribute.
	 * <p>
	 * OASIS
	 * ASM50026
	 * If a reference has a value specified for one or more target services in its @target attribute, 
	 * there MUST NOT be any child <binding/> elements declared for that reference.
	 * <p>
	 * Jira issue:
	 * https://issues.apache.org/jira/browse/TUSCANY-2920
	 */
    protected TestConfiguration getTestConfiguration() {
    	TestConfiguration config = new TestConfiguration();
    	config.testName 		= "ASM_0024";
    	config.input 			= "request";
    	config.output 			= "exception";
    	config.composite 		= "Test_ASM_0024.composite";
    	config.testServiceName 	= "TestClient";
    	config.testClass 		= ASM_0002_Client.class;
    	config.serviceInterface = TestInvocation.class;
    	return config;
    }
    
} // end class Test_ASM_0003
