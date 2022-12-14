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
package helloworld;

import org.osoa.sca.annotations.Remotable;


@Remotable
public interface HelloWorldService {

    String sayHello(String name);

    String sayHello2(String firstName, String lastName);
    
    BeanA sayHello3(BeanA bean);
    
    String[] sayHello4(String[] name);
    
    BeanA[] sayHello5(BeanA[] beans);
    
    String[] sayHello6(BeanA[] beans, String[] names, String anotherName);
    
    void sayHello7();
    
    void sayHello8(String name);
    
    int sayHello9(int name);
 
}
