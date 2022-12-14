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
package callbackservice;

import org.osoa.sca.annotations.Callback;

/**
 * This class implements MyService and uses a callback.
 */
public class MyServiceImpl implements MyService {

    private MyServiceCallback myServiceCallback;

    /**
     * The setter used by the runtime to set the callback reference 
     * @param myServiceCallback
     */
    @Callback
    protected void setMyServiceCallback(MyServiceCallback myServiceCallback) {
        this.myServiceCallback = myServiceCallback;
    }
    
    public void someMethod(String arg) {
        System.out.println("MyServiceImpl.someMethod");
        // invoke the callback
        try {
            myServiceCallback.receiveResult(arg + " -> receiveResult");
            System.out.println("MyServiceImpl.someMethod returned from receiveResult() call");
        } catch (RuntimeException e) {
            System.out.println("MyServiceImpl.someMethod exception invoking receiveResult: " + e.toString());
        }
    }

}
