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

package payment;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;

import org.osoa.sca.annotations.Authentication;
import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import payment.creditcard.AuthorizeFault_Exception;
import payment.creditcard.CreditCardDetailsType;
import payment.creditcard.CreditCardPayment;
import scatours.customer.Customer;
import scatours.customer.CustomerNotFoundException;
import scatours.customer.CustomerRegistry;
import scatours.emailgateway.EmailGateway;

/**
 * The payment implementation
 */
@Service(Payment.class)
@RolesAllowed({"Admin", "Billing"})
@RunAs("Billing")
public class PaymentImpl implements Payment {

    @Reference
    protected CustomerRegistry customerRegistry;

    @Reference
    @Authentication
    protected CreditCardPayment creditCardPayment;

    @Reference
    protected EmailGateway emailGateway;

    @Property
    protected float transactionFeeRate = 0.01f;

    public String makePaymentMember(String customerId, float amount) {
        Customer customer = null;

        try {
            customer = customerRegistry.getCustomer(customerId);
        } catch (CustomerNotFoundException ex) {
            return "Payment failed due to " + ex.getMessage();
        } catch (Throwable t) {
            return "Payment failed due to system error " + t.getMessage();
        }

        CreditCardDetailsType ccDetails = customer.getCreditCard();

        String status;
        try {
            status = creditCardPayment.authorize(ccDetails, amount);
        } catch (AuthorizeFault_Exception e) {
            status = e.getFaultInfo().getErrorCode();
        }

        StringBuffer body = new StringBuffer();
        body.append(customer);
        body.append("\n").append("Status: ").append(status).append("\n");
        emailGateway.sendEmail("order@tuscanyscatours.com", customer.getEmail(), "Status for your payment", body
            .toString());

        return status;
    }

}
