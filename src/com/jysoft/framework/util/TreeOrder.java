package com.jysoft.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
/**
 * 
 * 类功能描述:根据参数list的顺序对树进行排序
 * 创建者： 黄智强
 * 创建日期： 2015-12-14
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 * 
 */
public class TreeOrder {
	private static int temRootCount = 0;
    /**
     * 函数方法描述:将树形list转化成easyui tree使用格式的json数据
     * 
     * @param <T>
     * @param _list
     * @param _id 获取ID的 get方法名
     * @param _pId 获取父节点ID的get方法名
     * @param _text 获取节点显示文字的 get方法名
     * @return
     */
    public static<T> String formatListToTreeJsonStringReform(List<T> _list,String _id, String _pId, String _text){
    	if (_list == null || _list.size() == 0) return null;
    	temRootCount = 0;
    	Map<String,TreeJson> temMap = parseListToMap(_list,_id,_pId,_text);
    	StringBuilder res = new StringBuilder();
    	if(temRootCount > 0){
    		Map<String,TreeJson> rootMap = setChildrenToParent(temMap,temRootCount);
    		Iterator<String> iter = rootMap.keySet().iterator();
    		res.append("[");
    		while(iter.hasNext()){
    			String key = iter.next();
    			res.append(JsonUtil.getJsonString4JavaPOJO(rootMap.get(key)));
    			res.append(",");
    		}
    		res.deleteCharAt(res.length()-1);
    		res.append("]");
    		return res.toString();
    	}else {
    		//res.append("没有找到任何root节点！");
    		return null;
    	}
	}
    @SuppressWarnings("unchecked")
	private static <T> Map<String, TreeJson> parseListToMap(List<T> list,String _id, String _pId, String _text) {
    	//Map<String,TreeJson> map = new HashMap<String,TreeJson>();
    	Map<String,TreeJson> map = new LinkedHashMap<String,TreeJson>();
		String mn1 = _id;
		String mn2 = _pId;
		String mn3 = _text;
		for (T item : list) {
			TreeJson tj = new TreeJson();
			Class c = item.getClass();
			try {
				Method m1 = c.getDeclaredMethod(mn1);
				Method m2 = c.getDeclaredMethod(mn2);
				Method m3 = c.getDeclaredMethod(mn3);
				tj.setId((String) m1.invoke(item));
				tj.setPid((String) m2.invoke(item));
				tj.setText((String) m3.invoke(item));
				tj.setAttributes(JSONObject.fromObject(item));
				if(mn1.equals(mn2)){
					tj.setPid("");
				}
				map.put(tj.getId(), tj);
				if(tj.getPid() == null || "".equals(tj.getPid()) || "null".equals(tj.getPid()) || "NULL".equals(tj.getPid())){
					temRootCount ++;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			
		}
		return map;
	}
    /**
     * 函数方法描述:将树行map经过迭代，输出只含root节点的map集合
     * 
     * @param map 树形map
     * @param rootCount root个数
     * @return
     */
    private static Map<String,TreeJson> setChildrenToParent(Map<String,TreeJson> map,int rootCount){
    	if(map.size() > rootCount){
    		Map<String,TreeJson> pMap = new LinkedHashMap<String,TreeJson>();
    		Map<String,TreeJson> cMap = map;
    		String key;
    		String pKey;
    		TreeJson tj;
    		Iterator<String> iter = cMap.keySet().iterator();
    		List<String> pids = new ArrayList<String>();
    		while (iter.hasNext()) {
    			key = iter.next();
    			if(!cMap.containsKey(key)){
    				continue;
    			}
    			tj = cMap.get(key);
    			pKey = tj.getPid();
    			if(pKey == null || "".equals(pKey) || "null".equals(pKey) || "NULL".equals(pKey)){
    				pKey = tj.getId();
    			}
    			if(!pMap.containsKey(pKey)){
    				pMap.put(pKey, cMap.get(pKey));
    				pids.add(pKey);
    			}
    		}
    		for(String item : pids){
    			cMap.remove(item);
    		}
    		Iterator<String> iter2 = cMap.keySet().iterator();
    		while (iter2.hasNext()) {
    			key = iter2.next();
    			pKey = cMap.get(key).getPid();
    			if(pMap.containsKey(pKey)){
    				pMap.get(pKey).getChildren().add(cMap.get(key));
    			}
    		}
    		cMap.clear();
    		if(pMap.size() > rootCount){
    			return setChildrenToParent(pMap,rootCount);
    		}else{
    			return pMap;
    		}
    	}else{
    		return map;
    	}
    	
    }
}
