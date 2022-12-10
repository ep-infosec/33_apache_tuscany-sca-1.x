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

package services.json.rpc;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONRpc {

    protected JSONRpc() {

    }

    public static JSONObject invoke(String serviceURI, String rpcRequest) throws JSONException{
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(serviceURI);

        JSONObject result = null;
        try {
            httpPost.setHeader("Content-Type", "text/xml");
            httpPost.setEntity(new StringEntity(rpcRequest));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String jsonResult = EntityUtils.toString(httpResponse.getEntity());
                result = new JSONObject(jsonResult);
            } else {
                String errorMessage = httpResponse.getStatusLine()
                .getReasonPhrase();
                System.out.println(errorMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
