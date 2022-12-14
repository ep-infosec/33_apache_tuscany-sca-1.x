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

import org.osoa.sca.annotations.Reference;


public class HelloWorldClient implements HelloWorldService {

    @Reference
    public HelloWorldService  ref;
    
    public String sayHello(String name) {
        return ref.sayHello(name);
    }

    public String sayHello2(String firstName, String lastName) {
        return ref.sayHello2(firstName, lastName);
    }

    public BeanA sayHello3(BeanA bean) {
        return ref.sayHello3(bean);
    }
    
    public String[] sayHello4(String[] name) {
        return ref.sayHello4(name);
    }
    
    public BeanA[] sayHello5(BeanA[] beans){
    	return ref.sayHello5(beans);
    }
    
    public String[] sayHello6(BeanA[] beans, String[] names, String anotherName){
    	return ref.sayHello6(beans, names, anotherName);
    }    
    
    public void sayHello7() {
    	ref.sayHello7();
    }   
    
    public void sayHello8(String name) {
    	ref.sayHello8(name);
    }
    
    public int sayHello9(int name) {
    	return ref.sayHello9(name);
    }    
}
