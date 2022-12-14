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

package org.apache.tuscany.sca.vtest.javaapi.apis.conversation;

import org.apache.tuscany.sca.vtest.utilities.ServiceFinder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This test class tests the Conversation interface described in 1.7.5 of the
 * SCA Java Annotations & APIs Specification 1.0.
 */
public class ConversationTestCase {

    protected static String compositeName = "conversation.composite";
    protected static AComponent a;
    protected static BComponent b;

    @BeforeClass
    public static void init() throws Exception {
        try {
            System.out.println("Setting up");
            ServiceFinder.init(compositeName);
            a = ServiceFinder.getService(AComponent.class, "AComponent");
            b = ServiceFinder.getService(BComponent.class, "BComponent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void destroy() throws Exception {
        System.out.println("Cleaning up");
        ServiceFinder.cleanup();
    }

    /**
     * Lines 941, 942 <br>
     * getConversationID() ? Returns the identifier for this conversation. If a
     * user-defined identity had been supplied for this reference then its value
     * will be returned; otherwise the identity generated by the system when the
     * conversation was initiated will be returned. <br>
     * end() ? Ends this conversation.
     * 
     * @throws Exception
     */
    @Test
    public void testConversation() throws Exception {
        a.testConversation();
    }

}
