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

package scatours;

import static scatours.launcher.LauncherUtil.locate;

import org.apache.tuscany.sca.node.SCAClient;
import org.apache.tuscany.sca.node.SCAContribution;
import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;
import org.osoa.sca.ServiceRuntimeException;

import scatours.notification.Notification;

public class NotificationWSLauncher {

    public static void main(String[] args) throws Exception {
        SCAContribution notificationContribution = locate("notification");
        SCAContribution notificationWSContribution = locate("notification-ws");

        SCANode node =
            SCANodeFactory.newInstance().createSCANode("notification-ws.composite",
                                                       notificationContribution,
                                                       notificationWSContribution);
        node.start();

        System.out.println("Quick notification test");
        Notification notification = ((SCAClient)node).getService(Notification.class, "Notification");
        String accountID = "1234";
        String subject = "Holiday payment taken";
        String message = "Payment of £102.37 accepted...";
        try {
            notification.notify(accountID, subject, message);
        } catch (ServiceRuntimeException ex) {
            System.out.println("========================= Error =========================");
            System.out.println("Failed to call notification service.");
            System.out.println("Did you remember to start it using the launcher in the services/smsgateway-jaxws project?");
            System.out.println("========================= Error =========================");
            System.exit(-1);
        }

        System.out.println("Node started - Press enter to shutdown.");
        System.in.read();
        node.stop();
    }
}
