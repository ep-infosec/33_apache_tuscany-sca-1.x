<%--
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
--%>

<%@ page import="org.apache.tuscany.sca.node.SCAClient"%>
<%@ page import="services.Catalog" %>
<%@page import="services.Item"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  
  SCAClient client = (SCAClient) application.getAttribute("org.apache.tuscany.sca.node.SCAClient");
   
  Catalog catalog = (Catalog)client.getService(Catalog.class, "LocalFruitsCatalog");
  Item[] items = catalog.get();
  
%>

<html>
<head><title>Catalog</title></head>

<body>
<h1>Catalog</h1>

<table border="0">

<% for (int i = 0, n = items.length; i < n; i++) { %>

    <tr><td><%=items[i].getName() %></td><td><%=items[i].getPrice() %></td></tr>

<% } %>

</table>

</body>
</html>
