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

package sca.services;

import org.apache.tuscany.sca.itest.spring.AbstractHelloWorldTestCase;

/**
 * A basic test case of:
 * 1) A composite containing a component with a Spring implementation
 * 2) The composite has a component with a Java POJO implementation which uses the
 * Spring implementation to satisfy a reference
 * 3) The <implementation.spring.../> element references an application context that
 * uses an explicit sca: element to identify the service offered by the Spring application
 *
 * @version $Rev$ $Date$
 */
public class SpringExplicitServiceTestCase extends AbstractHelloWorldTestCase {
    // super class does it all getting composite based on this class name
	
	public SpringExplicitServiceTestCase() {
        super("SpringExplicitService.composite", "sca/services");        
    }
}
