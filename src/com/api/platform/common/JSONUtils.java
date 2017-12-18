package com.api.platform.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import net.sf.json.JSONObject;


public class JSONUtils
{
	public static String getJson(Map map) {
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}
	
	 /**
     * 将json格式的字符串解析成Map对象 <li>
     * json格式：{"name":"admin","retries":"3fff","testname"
     * :"ddd","testretries":"fffffffff"}
     */
	public static HashMap<String, Object> toHashMap(Object object)
    {
        HashMap<String, Object> data = new HashMap<String, Object>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = JSONObject.fromObject(object);
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            Object value =  jsonObject.get(key);
            data.put(key, value);
        }
        return data;
    }
    
    public static void main(String[] args){
    	//String json = "{\"code\":0,\"message\":\"success\"}";
    	//HashMap<String, Object> data = toHashMap(json);
    	//System.out.println(data.get("code"));
    	//System.out.println(data.get("message"));
    	//TestJsonBean();
    }
    
   
}