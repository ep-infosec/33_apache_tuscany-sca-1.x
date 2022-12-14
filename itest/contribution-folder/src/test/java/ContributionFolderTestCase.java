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


import junit.framework.TestCase;

import org.apache.tuscany.sca.host.embedded.SCADomain;

import calculator.CalculatorService;



/**
 * Test SCADomain.newInstance and invocation of a service.
 * 
 * @version $Rev: 608205 $ $Date: 2008-01-02 20:29:05 +0000 (Wed, 02 Jan 2008) $
 */
public class ContributionFolderTestCase extends TestCase {

    private SCADomain domain;
    
    @Override
    protected void setUp() throws Exception {
        domain = SCADomain.newInstance("myDomain", "src/test/resources/repository2/folderWithJars", null );
    }

    public void testInvoke() throws Exception {
        CalculatorService service = domain.getService(CalculatorService.class, "CalculatorServiceComponent");
        assertEquals(3.0, service.add(1, 2));
    }

    @Override
    protected void tearDown() throws Exception {
        domain.close();
    }

}
