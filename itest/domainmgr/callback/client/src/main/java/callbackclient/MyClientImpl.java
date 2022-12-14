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
package callbackclient;

import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

/**
 * Demonstrates a component-to-component callback invocation
 */
@Service(TestService.class)
public class MyClientImpl implements TestService {

    private MyService myService;
    private static String result;

    @Reference
    protected void setMyService(MyService myService) {
        this.myService = myService;
    }

    public void runTest() {
        System.out.println("MyClientImpl.runTest");
        myService.someMethod("-> someMethod");
    }

    public String getResult() {
        System.out.println("MyClientImpl.getResult");
        return MyClientImpl.result;
    }

    public void receiveResult(String result) {
        System.out.println("MyClientImpl.receiveResult: result=" + result);
        MyClientImpl.result = result;
    }
}
