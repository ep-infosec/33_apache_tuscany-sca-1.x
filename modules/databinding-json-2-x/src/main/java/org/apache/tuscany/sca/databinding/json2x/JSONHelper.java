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

import java.util.Collection;

import org.apache.tuscany.sca.databinding.json2x.jackson.JacksonHelper;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;

/**
 * @version $Rev: 945980 $ $Date: 2010-05-19 01:53:52 +0100 (Wed, 19 May 2010) $
 */
public class JSONHelper {
    private JSONHelper() {

    }

    /**
     * Convert to Jettison JSONObject
     * @param source
     * @return
     */
    public static JSONObject toJettison(Object source) {
        JSONObject json = null;
        if (source instanceof JSONObject) {
            json = (JSONObject)source;
        } else if (source instanceof org.json.JSONObject || source instanceof String) {
            json = stringToJettision(source.toString());
        } else if (source instanceof JsonNode) {
            json = stringToJettision(JacksonHelper.toString((JsonNode)source));
        } else if (source instanceof JsonParser) {
            json = stringToJettision(JacksonHelper.toString((JsonParser)source));
        }
        return json;
    }

    private static JSONObject stringToJettision(String content) {
        try {
            return new JSONObject(content);
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Convert to org.json.JSONObject
     * @param source
     * @return
     */
    public static org.json.JSONObject toJSONOrg(Object source) {
        org.json.JSONObject json = null;
        if (source instanceof JSONObject) {
            try {
                json = new org.json.JSONObject(((JSONObject)source).toString());
            } catch (org.json.JSONException e) {
                throw new IllegalArgumentException(e);
            }
        } else if (source instanceof org.json.JSONObject) {
            json = (org.json.JSONObject)source;
        }
        return json;
    }

    public static Object toJSON(String json, Class<?> type) {
        if (type == JSONObject.class) {
            try {
                return new JSONObject(json);
            } catch (JSONException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            if (type == null) {
                type = org.json.JSONObject.class;
            }
            try {
                if (type == JSONArray.class || type.isArray() || Collection.class.isAssignableFrom(type)) {
                    return new JSONArray(json);
                }
                return new org.json.JSONObject(json);
            } catch (org.json.JSONException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
