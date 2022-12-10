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

package org.apache.tuscany.sca.monitor;

import java.util.List;
import org.apache.tuscany.sca.monitor.Problem.Severity;

/**
 * A monitor for the watching for validation problems
 *
 * @version $Rev$ $Date$
 */
public abstract class Monitor {
    /**
     * Reports a build problem.
     * 
     * @param problem
     */
	public abstract void problem(Problem problem);
    
    /** 
     * Returns a list of reported problems. 
     * 
     * @return the list of problems. The list may be empty
     */
	public abstract List<Problem> getProblems();
	
    /**
     * Create a new problem.
     * 
     * @param sourceClassName   the class name reporting the problem
     * @param bundleName        the name of the message bundle to use
     * @param severity          the severity of the problem
     * @param problemObject     the model object for which the problem is being reported
     * @param messageId         the id of the problem message
     * @param cause             the exception which caused the problem
     * @return
     */
    public abstract Problem createProblem(String sourceClassName,
                                          String bundleName,
                                          Severity severity,
                                          Object problemObject,
                                          String messageId,
                                          Exception cause);

    /**
     * Create a new problem.
     *  
     * @param sourceClassName   the class name reporting the problem
     * @param bundleName        the name of the message bundle to use
     * @param severity          the severity of the problem
     * @param problemObject     the model object for which the problem is being reported
     * @param messageId         the id of the problem message
     * @param messageParams     the parameters of the problem message
     * @return
     */
    public abstract Problem createProblem(String sourceClassName,
                                   String bundleName,
                                   Severity severity,
                                   Object problemObject,
                                   String messageId,
                                   Object... messageParams);	
    
    /**
     * A utility function for raising a warning. It creates the problem and 
     * adds it to the monitor
     * 
     * @param monitor
     * @param reportingObject
     * @param messageBundle
     * @param messageId
     * @param messageParameters
     */
    public static void warning (Monitor monitor, 
                                Object reportingObject,
                                String messageBundle,
                                String messageId, 
                                String... messageParameters){
        if (monitor != null) {
            Problem problem =
                monitor.createProblem(reportingObject.getClass().getName(),
                                      messageBundle,
                                      Severity.WARNING,
                                      null,
                                      messageId,
                                      (Object[])messageParameters);
            monitor.problem(problem);
        }
    }
   
    /**
     * A utility function for raising an error. It creates the problem and 
     * adds it to the monitor
     * 
     * @param monitor
     * @param reportingObject
     * @param messageBundle
     * @param messageId
     * @param messageParameters
     */
    public static void error (Monitor monitor, 
                              Object reportingObject,
                              String messageBundle,
                              String messageId, 
                              String... messageParameters){
        if (monitor != null) {
            Problem problem =
                monitor.createProblem(reportingObject.getClass().getName(),
                                      messageBundle,
                                      Severity.ERROR,
                                      null,
                                      messageId,
                                      (Object[])messageParameters);
            monitor.problem(problem);
        }
    }
    
    /**
     * A utility function for raising an error. It creates the problem and 
     * adds it to the monitor
     * 
     * @param monitor
     * @param reportingObject
     * @param messageBundle
     * @param messageId
     * @param exception
     */
    public static void error (Monitor monitor, 
                              Object reportingObject,
                              String messageBundle,
                              String messageId, 
                              Exception cause){
        if (monitor != null) {
            Problem problem =
                monitor.createProblem(reportingObject.getClass().getName(),
                                      messageBundle,
                                      Severity.ERROR,
                                      null,
                                      messageId,
                                      cause);
            monitor.problem(problem);
        }
    }  
    
}
