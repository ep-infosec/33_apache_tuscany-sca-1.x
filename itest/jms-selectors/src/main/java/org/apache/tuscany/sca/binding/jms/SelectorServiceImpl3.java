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

package org.apache.tuscany.sca.binding.jms;

public class SelectorServiceImpl3 implements SelectorService {

    public static Object lock = new Object();
    public static String name;
    
    public void sayHello(String name) {
        if (SelectorServiceImpl3.name != null) {
            throw new IllegalStateException("name already set");
        }
        System.out.println("SelectorServiceImpl3 " + name);
        SelectorServiceImpl3.name = name;
        synchronized (SelectorServiceImpl3.lock) {
            SelectorServiceImpl3.lock.notify();
        }
    }

}
