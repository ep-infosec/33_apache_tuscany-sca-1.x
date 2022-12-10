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

package org.apache.tuscany.sca.policy.security.http;

import java.io.IOException;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.tuscany.sca.policy.SecurityUtil;
import org.apache.tuscany.sca.policy.authentication.basic.BasicAuthenticationPrincipal;


/**
 * @version $Rev$ $Date$
 */
public class LDAPRealmAuthenticationCallbackHandler implements CallbackHandler {
    private final Subject subject;
    
    public LDAPRealmAuthenticationCallbackHandler(Subject subject) {
        this.subject = subject;
    }
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        BasicAuthenticationPrincipal principal =  SecurityUtil.getPrincipal(subject, BasicAuthenticationPrincipal.class);

        if (principal != null){
        	/*
            System.out.println(">>> LDAPRealmAuthenticationCallbackHandler" +
                               " Username: " + principal.getName() + 
                               " Password: " + principal.getPassword());
            */
            for (int i = 0; i < callbacks.length; i++) {
                if (callbacks[i] instanceof NameCallback) {
                    NameCallback nc = (NameCallback)callbacks[i];
                    nc.setName(principal.getName());
                } else if (callbacks[i] instanceof PasswordCallback) {
                    PasswordCallback pc = (PasswordCallback)callbacks[i];
                    pc.setPassword(principal.getPassword().toCharArray());
                } else {
                    throw new UnsupportedCallbackException
                    (callbacks[i], "Unsupported Callback!");
                }
            }
        }
        

    }

}
