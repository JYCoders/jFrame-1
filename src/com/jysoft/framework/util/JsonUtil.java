package com.jysoft.framework.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
  
//import com.linghui.common.util.DateUtil;  
//import com.linghui.common.util.jsonutil.DateJsonValueProcessor;  
  
/** *//** 
 * 
 */  
public class JsonUtil {  
  
    /** *//** 
     * 从一个JSON 对象字符格式中得到一个java对象 
     * @param jsonString 
     * @param pojoCalss 
     * @return 
     */  
    public static Object getObject4JsonString(String jsonString,Class pojoCalss){  
        Object pojo;  
        JSONObject jsonObject = JSONObject.fromObject( jsonString );    
        pojo = JSONObject.toBean(jsonObject,pojoCalss);  
        return pojo;  
    }  
      
      
      
    /** *//** 
     * 从json HASH表达式中获取一个map，改map支持嵌套功能 
     * @param jsonString 
     * @return 
     */  
    public static Map getMap4Json(String jsonString) {  
        JSONObject jsonObject = JSONObject.fromObject( jsonString );    
        Iterator  keyIter = jsonObject.keys();  
        String key;  
        Object value;  
        Map valueMap = new HashMap();  
        while( keyIter.hasNext())  
         {  
            key = (String)keyIter.next();  
            value = jsonObject.get(key);  
            valueMap.put(key, value);  
        }  
          
        return valueMap;  
    }  
      /*
       *{"content":[
{\"name\":\"age2\",\"paramId\":\"1BA9D1E2509C479E9B333139A8314195\",\"value\":\"2\"},
{\"name\":\"age\",\"paramId\":\"89130481654943CAA55848D0A1FE3F0D\",\"value\":\"2\"},
{\"name\":\"name2\",\"paramId\":\"4877A9ABB0494C04A97A939DBDF1B9CA\",\"value\":\"2\"},
{\"name\":\"sex2\",\"paramId\":\"7C3FCC30BA844BBA9A79BE17E2D21A97\",\"value\":\"9%2520\"}
]}
       */
    public static Map<Object,Object> JsonArray2Map(String json){
    	  JSONObject jsonObject = JSONObject.fromObject(json);
    	  JSONArray jsonArray = jsonObject.getJSONArray("content");
    	  Map<Object,Object> map =new HashMap<Object,Object>();
    	 
    	  for(Object obj : jsonArray ){
    		  JSONObject jsonObj = JSONObject.fromObject(obj);
    		  Iterator  keyIter = jsonObj.keys();  
    		  Object key;  
    	      Object value;  
    	      key = jsonObj.get("name");  
    	      value = jsonObj.get("value");  
    	   //   System.out.println(key+"***"+value);
    	      map.put(key, value);  
    	  }
    	 return map;  
    }
/*    public static int countSum(String str) {  
        int count = 0;  
        for (int i = 0; i < str.length(); i++) {  
            char c = str.charAt(i);  
            if (c == '&') {  
                count++;  
            }
        }  
        System.out.println("参数符："+count);            
        return count;  
    }  
    public static List<String> getStr(String str){
    	List<String> list = new ArrayList();
    	Pattern p = Pattern.compile("\\&(.*?)\\&");//正则表达式，取=和|之间的字符串，不包括=和|
    	Matcher m = p.matcher(str);
    	 while(m.find()) {
    	     //  System.out.println(m.group(1));//m.group(1)不包括这两个字符
list.add(m.group(1));
    	        }
    	 return list;
    }
      public static void main(String[] args){
    	  String w="name=&ms& and age=&ds&";
    	  Map map=new HashMap();
    	  map.put("ms","mv");
    	  map.put("ds","dv");
    	  map.put("as","av");
    	  for(String str:getStr(w)){
    		  System.out.println(str);
    		  w=w.replaceAll("&"+str+"&", str);
    		  System.out.println("-->"+w);
    	  }}
    	  */
    	  
//		String json = "{\"content\":[{\"name\":\"age2\",\"paramId\":\"1BA9D1E2509C479E9B333139A8314195\",\"value\":\"2\"},"
//				+ "{\"name\":\"age\",\"paramId\":\"89130481654943CAA55848D0A1FE3F0D\",\"value\":\"2\"},"
//				+ "{\"name\":\"name2\",\"paramId\":\"4877A9ABB0494C04A97A939DBDF1B9CA\",\"value\":\"2\"},"
//				+ "{\"name\":\"sex2\",\"paramId\":\"7C3FCC30BA844BBA9A79BE17E2D21A97\",\"value\":\"9%2520\"}]}";
//		Map map = JsonArray2Map(json);
//		for(Object obj:map.keySet()){
//			System.out.println(obj+":"+map.get(obj));
//		}
      
    /** *//** 
     * 从json数组中得到相应java数组 
     * @param jsonString 
     * @return 
     */  
    public static Object[] getObjectArray4Json(String jsonString) {  
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        return jsonArray.toArray();  
          
    }  
      
      
    /** *//** 
     * 从json对象集合表达式中得到一个java对象列表 
     * @param jsonString 
     * @param pojoClass 
     * @return 
     */  
    public static List getList4Json(String jsonString, Class pojoClass) {  
          
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        JSONObject jsonObject;  
        Object pojoValue;  
          
        List list = new ArrayList();  
        for ( int i = 0 ; i<jsonArray.size(); i++) {  
              
            jsonObject = jsonArray.getJSONObject(i);  
            pojoValue = JSONObject.toBean(jsonObject,pojoClass);  
            list.add(pojoValue);  
              
        }  
        return list;  
  
    }  
      
    /** *//** 
     * 从json数组中解析出java字符串数组 
     * @param jsonString 
     * @return 
     */  
    public static String[] getStringArray4Json(String jsonString) {  
          
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        String[] stringArray = new String[jsonArray.size()];  
        for( int i = 0 ; i<jsonArray.size() ; i++ ) {  
            stringArray[i] = jsonArray.getString(i);  
              
        }  
          
        return stringArray;  
    }  
      
    /** *//** 
     * 从json数组中解析出javaLong型对象数组 
     * @param jsonString 
     * @return 
     */  
    public static Long[] getLongArray4Json(String jsonString) {  
          
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        Long[] longArray = new Long[jsonArray.size()];  
        for( int i = 0 ; i<jsonArray.size() ; i++ ) {  
            longArray[i] = jsonArray.getLong(i);  
              
        }  
        return longArray;  
    }  
      
    /** *//** 
     * 从json数组中解析出java Integer型对象数组 
     * @param jsonString 
     * @return 
     */  
    public static Integer[] getIntegerArray4Json(String jsonString) {  
          
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        Integer[] integerArray = new Integer[jsonArray.size()];  
        for( int i = 0 ; i<jsonArray.size() ; i++ ) {  
            integerArray[i] = jsonArray.getInt(i);  
              
        }  
        return integerArray;  
    }  
      
    /** *//** 
     * 从json数组中解析出java Date 型对象数组，使用本方法必须保证 
     * @param jsonString 
     * @return 
     */  
    public static Date[] getDateArray4Json(String jsonString,String DataFormat) {  
          
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        Date[] dateArray = new Date[jsonArray.size()];  
        String dateString;  
        Date date;  
          
        for( int i = 0 ; i<jsonArray.size() ; i++ ) {  
            dateString = jsonArray.getString(i);  
            //date = DateUtil.stringToDate(dateString, DataFormat);  
            //dateArray[i] = date;  
              
        }  
        return dateArray;  
    }  
      
    /** *//** 
     * 从json数组中解析出java Integer型对象数组 
     * @param jsonString 
     * @return 
     */  
    public static Double[] getDoubleArray4Json(String jsonString) {  
          
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        Double[] doubleArray = new Double[jsonArray.size()];  
        for( int i = 0 ; i<jsonArray.size() ; i++ ) {  
            doubleArray[i] = jsonArray.getDouble(i);  
              
        }  
        return doubleArray;  
    }  
      
      
    /** *//** 
     * 将java对象转换成json字符串 
     * @param javaObj 
     * @return 
     */  
    public static String getJsonString4JavaPOJO(Object javaObj) {  
          
        JSONObject json;  
        json = JSONObject.fromObject(javaObj);  
        return json.toString();  
          
    } 
    /**
     * 把一个list集合转成easyUI表格所需要的json数据格式字符串
     * @param total 总条数
     * @param rows  一页数据的list集合
     * @return
     */
    public static String parseListToJson(int total, List<?> rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", rows);
		JSONObject resultObj = JSONObject.fromObject(map); // 将map对象转换成为json对象
		return resultObj.toString();
	}
    
    /**
     * 把一个list集合转成easyUI表格所需要的json数据格式字符串
     * @param rows  list集合
     * @return
     */
    public static String parseListToJson(List<?> rows) {
		JSONArray jsonArray = JSONArray.fromObject(rows); // 将List对象转换成为json对象
		return jsonArray.toString();
	}
}  

