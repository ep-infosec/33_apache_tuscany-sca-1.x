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

package scatours.payment;

import com.tuscanyscatours.payment.Payment;
import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCAContribution;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PaymentTestCase {
    private static SCANode node;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        node = SCANodeFactory.newInstance().createSCANode("payment.composite", 
                new SCAContribution("payment", "./target/classes"));
        node.start();
    }
    
    @Test
    public void testPayment() {
        SCAClient client = (SCAClient) node;
        Payment payment = client.getService(Payment.class, "Payment");
        System.out.println(payment.makePaymentMember("c-0", 100.00f));
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (node != null) {
            node.stop();
            node = null;
        }
    }
}
