package com.jysoft.cbb.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jysoft.cbb.dao.CommonMapper;
import com.jysoft.cbb.vo.ComBoxSelectVo;
import com.jysoft.cbb.vo.TreeVo;

/**
 * 
 * 类功能描述: 公共服务层
 * 创建者： 吴立刚
 * 创建日期： 2016-1-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Service
public class CommonService {
	@Autowired
	private CommonMapper commonMapper; // 自动加载公共持久层

	/**
	 * 函数方法描述: 根据类型查询下拉框
	 * 
	 * @param comBoxVo
	 *            下拉框类型信息
	 * @return 下拉框信息
	 */
	public List<ComBoxSelectVo> getMiddleWareComList(ComBoxSelectVo comBoxVo) {

		return commonMapper.getMiddleWareComList(comBoxVo);
	}
	
	

	/**
	 * 函数方法描述:将排好序的list转换为树，list中的节点必须层级之间必须排好序
	 * 
	 * @param <T> 将转换的对象类型
	 * @param list 要转换的list
	 * @param rootVo 树的根结点
	 * @param rootId 树的根结点id
	 * @param _id    将转换的对象类型id的get方法
	 * @param _pId   将转换的对象父节点字段的get方法
	 * @param _text  将转换的对象显示内容的的get方法
	 * @return
	 * @throws Exception
	 */
	public static <T> TreeVo buildTree (List<T> list,TreeVo rootVo,String rootId,String _id, String _pId, String _text) throws Exception{
		Map<String, TreeVo> treeMap = new HashMap<String, TreeVo>();
		treeMap.put(rootVo.getId(), rootVo);
		return buildTreeRecusive(list,treeMap,rootVo,rootId,_id,_pId,_text);
	}
	
	/**
	 * 函数方法描述:将排好序的list转换为树，list中的节点必须层级之间必须排好序
	 * 
	 * @param <T> 将转换的对象类型
	 * @param list 要转换的list
	 * @param treeMap  存储树结点的maps             
	 * @param rootVo 树的根结点
	 * @param rootId 树的根结点id
	 * @param _id    将转换的对象类型id的get方法
	 * @param _pId   将转换的对象父节点字段的get方法
	 * @param _text  将转换的对象显示内容的的get方法
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	private static<T> TreeVo buildTreeRecusive (List<T> list,Map<String, TreeVo> treeMap,TreeVo rootVo,String root,String _id, String _pId, String _text) throws Exception{
		if ((list == null) || (list != null && list.size() == 0) ) return null;
		Class c = null;
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null){
				c = list.get(i).getClass();
				break;
			}
		}
		Method m1 = c.getDeclaredMethod(_id);
		Method m2 = c.getDeclaredMethod(_pId);
		Method m3 = c.getDeclaredMethod(_text);
		List<T> list2 = new ArrayList<T>();
		Iterator<T> iter = list.iterator();  
		while(iter.hasNext()){  
			T t=iter.next();
			if(m2.invoke(t)!=null && m2.invoke(t).equals(root)){
				list2.add(t);
				iter.remove();
			}
			if(m2.invoke(t)==null ){
				iter.remove();
			}
		}  
		// 递归的设置子节点
		for (T vo : list2) {
			if (treeMap.containsKey(m2.invoke(vo))) {
				// MAP中包含父节点，开始构建子节点
				TreeVo childrenVo = new TreeVo();
				childrenVo.setId((String)m1.invoke(vo));
				childrenVo.setText((String)m3.invoke(vo));
				Map<String,Object> map = new HashMap<String,Object>();
				map = transBean2Map(vo);
				childrenVo.setAttributes(map);
				treeMap.get(m2.invoke(vo)).getChildren().add(
						childrenVo); // 同时子节点也放到map中
				treeMap.put(childrenVo.getId(), childrenVo);
				buildTreeRecusive(list,treeMap,childrenVo,(String)m1.invoke(vo),_id,_pId,_text);
			}
			
		}
		return rootVo;
	}
	
	/**
	 * 函数方法描述:利用Introspector和PropertyDescriptor将Bean转化为Map
	 * 
	 * @param 要转化的bean
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}
		return map;
	}
}
