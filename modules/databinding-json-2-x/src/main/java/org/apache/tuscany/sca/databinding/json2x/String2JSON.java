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

package org.apache.tuscany.sca.databinding.json2x;

import org.apache.tuscany.sca.databinding.PullTransformer;
import org.apache.tuscany.sca.databinding.TransformationContext;
import org.apache.tuscany.sca.databinding.TransformationException;
import org.apache.tuscany.sca.databinding.impl.BaseTransformer;

/**
 * @version $Rev: 916888 $ $Date: 2010-02-27 00:44:05 +0000 (Sat, 27 Feb 2010) $
 */
public class String2JSON extends BaseTransformer<String, Object> implements PullTransformer<String, Object> {

    @Override
    protected Class<String> getSourceType() {
        return String.class;
    }

    @Override
    protected Class<Object> getTargetType() {
        return Object.class;
    }

    public Object transform(String source, TransformationContext context) {
        try {
            Class type = null;
            if (context != null && context.getTargetDataType() != null) {
                type = context.getTargetDataType().getPhysical();
            }
            return JSONHelper.toJSON(source, type);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }

    @Override
    public int getWeight() {
        return 500;
    }

    @Override
    public String getTargetDataBinding() {
        return JSONDataBinding.NAME;
    }

}
