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
package org.apache.tuscany.sca.policy;

import javax.xml.namespace.QName;

/**
 * Interface that abstracts a PolicySet Reference
 *
 * @version $Rev$ $Date$
 */
public interface PolicySetReference {
    
    /**
     * Returns the name of the policyset being referred to
     * 
     * @return the QName of the referred policyset
     */
    QName getReferredPolicySetName();
    
    /**
     * @param refPolicySetName the name of the policyset being referred to
     * 
     */
    void setReferredPolicySetName(QName refPolicySetName);

}
