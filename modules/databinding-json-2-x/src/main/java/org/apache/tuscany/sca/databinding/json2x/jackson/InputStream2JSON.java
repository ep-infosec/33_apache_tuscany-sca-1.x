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

package org.apache.tuscany.sca.databinding.json2x.jackson;

import java.io.InputStream;

import org.apache.tuscany.sca.databinding.PullTransformer;
import org.apache.tuscany.sca.databinding.TransformationContext;
import org.apache.tuscany.sca.databinding.json2x.JSONDataBinding;

/**
 * 
 */
public class InputStream2JSON implements PullTransformer<InputStream, Object> {

    public String getSourceDataBinding() {
        return "application/json" + "#" + InputStream.class.getName();
    }

    public String getTargetDataBinding() {
        return JSONDataBinding.NAME;
    }

    public int getWeight() {
        return 10;
    }

    public Object transform(InputStream source, TransformationContext context) {
        return JacksonHelper.createJsonParser(source);
    }

}
