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
package callbacks;

import junit.framework.TestCase;

import org.apache.tuscany.sca.host.embedded.SCADomain;

/**
 * This shows how to test the Calculator service component.
 */
public class CallbacksTestCase extends TestCase {

    private OrderServiceClient orderServiceClient;
    private SCADomain scaDomain;

    @Override
    protected void setUp() throws Exception {
        scaDomain = SCADomain.newInstance("callbacks.composite");
        orderServiceClient = scaDomain.getService(OrderServiceClient.class, "ClientComponent");
    }

    @Override
    protected void tearDown() throws Exception {
        scaDomain.close();
    }

    public void testOderClient() throws Exception {

        orderServiceClient.doSomeOrdering();

        // wait to give the service time to respond
        Thread.sleep(500);
    }
    
    public static void main(String[] args) {
        try {

            CallbacksTestCase runner = new CallbacksTestCase();
            runner.setUp();
            runner.testOderClient();
            runner.tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
