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

package scatours;

import java.io.IOException;

import org.apache.tuscany.sca.node.SCANode;
import org.apache.tuscany.sca.node.SCANodeFactory;

/**
 * This launcher is only used when running from the binaries directory.
 * If the binaries directory was built using the mvn -Pselfcontained command,
 * it's important to ensure that no dependencies other than those explicitly
 * specified by the launcher jar manifest and its transitive dependencies
 * are used.  This launcher class must therefore avoid using the Tuscany
 * NodeLauncher class, because NodeLauncher builds a runtime classpath from
 * (among other things) the TUSCANY_HOME environment variable.
 */
public class IntroducingToursLauncher {

    public static void main(String[] args) throws Exception {
        SCANode node =
            SCANodeFactory.newInstance().createSCANodeFromURL("http://localhost:9990/node-config/ToursNode");
        node.start();

        System.out.println("Node started - Press enter to shutdown.");
        try {
            System.in.read();
        } catch (IOException e) {
        }

        node.stop();
    }
}
