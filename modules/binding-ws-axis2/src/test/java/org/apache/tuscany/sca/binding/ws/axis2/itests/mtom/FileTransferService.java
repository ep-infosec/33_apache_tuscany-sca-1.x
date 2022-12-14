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
package org.apache.tuscany.sca.binding.ws.axis2.itests.mtom;

import org.osoa.sca.annotations.Remotable;
import javax.activation.DataHandler;
import java.awt.Image;
import javax.xml.transform.Source;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.axiom.om.OMElement;

/**
 * This is the business interface of the MTOM FileTransfer service.
 */
@Remotable
public interface FileTransferService {

    public String uploadImageFile(Image attachment) throws Exception;
    
    public String uploadSourceFile(Source attachment) throws Exception;
    
    public String uploadDataHandlerFile(DataHandler attachment) throws Exception;    
    
    public String uploadOMElementFile(OMElement attachment) throws Exception;

    // TUSCANY-3805: produces WSDL generation error with fix for TUSCANY-3298
    /*
    //This method uses an user defined interface MyException as parameter type.
    public String sendMyException(@XmlJavaTypeAdapter(MyExceptionAdapter.class) MyException attachment) 
    																				throws Exception;
    */
}

