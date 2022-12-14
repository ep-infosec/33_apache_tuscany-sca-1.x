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

package composite;

import junit.framework.TestCase;

import org.apache.tuscany.sca.host.embedded.SCADomain;

public class LocateServiceSCADomainTestCase extends TestCase {

    private SCADomain domain;
    

    @Override
    protected void setUp() throws Exception {
        domain = SCADomain.newInstance("OuterComposite.composite");
    }
    
    @Override
    protected void tearDown() throws Exception {
        domain.close();
    }

    public void testValidLocateService() throws Exception {
        Source source;
        try {
            source = domain.getService(Source.class, "SourceComponent");
            assertNotNull(source);
        } catch (Throwable t) {
            fail("Could not locate service");
        }
    }

    public void testInvalidLocateService() throws Exception {
        Source source;
        try {
            source = domain.getService(Source.class, "SourceComponentXXX");
            // The source proxy can be created for the remote target but it will throw exception when a method is invoked
            source.clientMethod("ABC");
            fail("Expected to generate org.osoa.sca.ServiceRuntimeException but did not when invoking service with async");
        } catch (org.osoa.sca.ServiceRuntimeException e) {
            // expected.
        }
    }
    
    public void testValidRecursiveLocateService() throws Exception {
        Source source;
        try {
            source = domain.getService(Source.class, "SourceComponent/InnerSourceService");
            assertNotNull(source);
        } catch (Throwable t) {
            fail("Could not locate service");
        }
    }

}
