package com.jysoft.framework.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 
 * 类功能描述:  easyui中的tree_data.json数据,只能有一个root节点 [{ "id":1, "text":"Folder1",
 * "iconCls":"icon-save", "children":[{ "text":"File1", "checked":true }] }]
 * 提供静态方法formatTree(List<TreeJson> list) 返回结果 TreeJson.formatTree(treeJsonlist)
 * 创建者： 管马舟
 * 创建日期：  2016-01-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class TreeJson implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String pid;
	private String text;
	private String iconCls;
	private String state;
	private String checked;
	private Map<String,String> attributes ;
	private List<TreeJson> children = new ArrayList<TreeJson>();
	//统计根节点个数，在list转化成map方法parseListToMap执行的时候先清零，再累加统计
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
    public static<T> String formatListToTreeJsonString(List<T> _list,String _id, String _pId, String _text){
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
    	Map<String,TreeJson> map = new HashMap<String,TreeJson>();
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
    		Map<String,TreeJson> pMap = new HashMap<String,TreeJson>();
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
	

	/*********** 以下是get set 方法 *******************/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}


	public List<TreeJson> getChildren() {
		return children;
	}

	public void setChildren(List<TreeJson> children) {
		this.children = children;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	
	
	  public static<T> String formatListToTreeJsonString1(List<T> _list,String _id, String _pId, String _text,String _panduan){
	    	if (_list == null || _list.size() == 0) return null;
	    	temRootCount = 0;
	    	Map<String,TreeJson> temMap = parseListToMap1(_list,_id,_pId,_text,_panduan);
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
		private static <T> Map<String, TreeJson> parseListToMap1(List<T> list,String _id, String _pId, String _text,String _panduan) {
	    	Map<String,TreeJson> map = new HashMap<String,TreeJson>();
			String mn1 = _id;
			String mn2 = _pId;
			String mn3 = _text;
			String mn4=_panduan;
			for (T item : list) {
				TreeJson tj = new TreeJson();
				Class c = item.getClass();
				try {
					Method m1 = c.getDeclaredMethod(mn1);
					Method m2 = c.getDeclaredMethod(mn2);
					Method m3 = c.getDeclaredMethod(mn3);
					Method m4 = c.getDeclaredMethod(mn4);
					tj.setId((String) m1.invoke(item));
					tj.setPid((String) m2.invoke(item));
					tj.setText((String) m3.invoke(item));
					String flag=(String) m4.invoke(item);
					tj.setAttributes(JSONObject.fromObject(item));
					map.put(tj.getId(), tj);
					if(tj.getPid() == null || "".equals(tj.getPid()) || "null".equals(tj.getPid()) || "NULL".equals(tj.getPid()) ){
						if(flag==null)
						{
							temRootCount ++;
						}else{
							Method m5 = c.getDeclaredMethod("getAssVId");
							String temp=(String) m5.invoke(item);
							tj.setPid(temp);
						}
						  
					
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
}
