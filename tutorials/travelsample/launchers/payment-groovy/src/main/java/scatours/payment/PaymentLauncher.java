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

import static scatours.launcher.LauncherUtil.locate;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;

import com.tuscanyscatours.payment.Payment;

public class PaymentLauncher {

    public static void main(String[] args) throws Exception {
        SCANode node = SCANodeFactory.newInstance().createSCANode(null, 
        		                                                  locate("payment-groovy"),
        		                                                  locate("emailgateway"));
        node.start();
        
        SCAClient client = (SCAClient)node;
        Payment payment = client.getService(Payment.class, "Payment");

        System.out.println("Payment Groovy test");
        System.out.println("\nSuccessful Payment - Status = \n\n" + payment.makePaymentMember("c-0", 100.00f));
        
        node.stop();
    }
}
