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

package services;


public class Item {
    private String name;
    private String price;
    private String key;
    
    /**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}









	public Item() {
    }
    
    
    
    
    




	/**
     * Parses a string entry to an Item object
     * @param s
     * @return Item
     */
    public static Item parseItem(String s)
    {
		Item i=new Item();
		i.setName(s.split("-")[0].trim());
		i.setPrice(s.split("-")[1].trim());
		return i;
    	
    }
    
    public Item(String name, String price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Item(String name, String price, String key) {
		super();
		this.name = name;
		this.price = price;
		this.key = key;
	}

	public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    public String toString()
    {
    	return name + " - " + price;
    }

}
