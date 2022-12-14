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

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.awt.Image;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMText;
import javax.xml.transform.dom.DOMSource;
import org.apache.axiom.om.OMFactory;
import java.awt.image.BufferedImage;

import junit.framework.TestCase;
import org.apache.tuscany.sca.host.embedded.SCADomain;
import org.apache.tuscany.sca.binding.ws.axis2.itests.mtom.FileTransferServiceClient;
import org.apache.tuscany.sca.databinding.xml.String2Node;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FileTransferMTOMTestCase {

    private static SCADomain domain;
    private static FileTransferServiceClient filetransfer;
    
    /**
     * Runs once before the tests
     */
    @BeforeClass
    public static void setUp() throws Exception {
    	domain = SCADomain.newInstance("org/apache/tuscany/sca/binding/ws/axis2/itests/mtom/filetransferservice.composite");
    	filetransfer = domain.getService(FileTransferServiceClient.class, "FileTransferClientComponent");
    }
   
    /**
     * Runs once after the tests
     */
    @AfterClass
    public static void tearDown() throws Exception {
    	domain.close();
    }

    @Test
    public void testImageFileTransfer() throws Exception {
        Image image = new BufferedImage(80, 24, BufferedImage.TYPE_INT_RGB);
        assertEquals("File uploaded Sucessfully", filetransfer.uploadImageFileForward(image));            
    }
    
    @Test
    public void testSourceFileTransfer() throws Exception {
        String xml = "<a>A<b>B</b><c>C</c></a>";
        Source source = new DOMSource(new String2Node().transform(xml, null));
        assertEquals("File uploaded Sucessfully", filetransfer.uploadSourceFileForward(source));            
    }
    
    @Test
    public void testDataHandlerFileTransfer() throws Exception {
        // For testing purpose lets try uploading LICENSE file.
        DataHandler dataHandler = new DataHandler(new FileDataSource("./LICENSE"));
        assertEquals("File uploaded Sucessfully", filetransfer.uploadDataHandlerFileForward(dataHandler));            
    }
    
    @Test
    public void testOMElementFileTransfer() throws Exception {
        OMFactory factory = OMAbstractFactory.getOMFactory();                        
        OMElement imageElement = factory.createOMElement(new QName("image"));          

        DataHandler dataHandler = new DataHandler(new FileDataSource("./LICENSE"));

        OMText textData = factory.createOMText(dataHandler, true);
        imageElement.addChild(textData);
        assertEquals("File uploaded Sucessfully", filetransfer.uploadOMElementFileForward(imageElement));
    }
    
    @Test
    @Ignore("TUSCANY-3805")
    public void testSendMyException() throws Exception {
        MyException exp = new MyExceptionImpl();
        assertEquals("File uploaded Sucessfully", filetransfer.sendMyExceptionForward(exp));            
    }
}
