package com.jysoft.cbb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jysoft.cbb.dao.MenuMapper;
import com.jysoft.cbb.vo.MenuVo;
import com.jysoft.cbb.vo.RoleVo;
/**
 * 
 * 类功能描述:菜单服务类，处理菜单增删改查都功能
 * 创建者： 管马舟
 * 创建日期： 2016-01-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
@Service
public class MenuService {
	@Autowired(required = true)
	private MenuMapper mapper;
	
	/**
	 * 函数方法描述:查询所有菜单
	 * 
	 * @return
	 */
	public List<MenuVo> getAll(){
		return mapper.getAll();
	}
	
	/**
	 * 函数方法描述:通过角色读取角色权限菜单集合
	 * 
	 * @param 角色对象信息
	 * @return
	 */
	public List<MenuVo> getMenusByRoleId(RoleVo roleVo) {
		return mapper.getMenusByRoleId(roleVo);
	}

    /**
     * 函数方法描述: 通过用户id读取用户权限菜单集合
     * 
     * @param 用户id
     * @return
     */
	public List<MenuVo> getMenusByUserId(String userId){
		return mapper.getMenusByUserId(userId);
	}
    
    /**
     * 函数方法描述:根据条件查询菜单项
     * 
     * @param 菜单模型信息
     * @return
     */
	public Map<String, Object>  getMenusByCondition(MenuVo menuModel) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MenuVo> list = mapper.getMenusByCondition(menuModel);
		String total =  mapper.getMenusByConditionTotal(menuModel);
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	/**
	 * 函数方法描述:根据菜单Id获取菜单的基本信息
	 * 
	 * @param 菜单模型信息
	 * @return
	 */
	public Map<String, Object> queryMenuDataByMenuId(MenuVo menuModel) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MenuVo> list = mapper.queryMenuDataByMenuId(menuModel);
		String total = mapper.queryMenuDataByMenuIdTotal(menuModel);
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

    /**
     * 函数方法描述:根据菜单Id获取菜单对象
     * 
     * @param 菜单模型信息
     * @return
     */
	public MenuVo getMenuById(MenuVo menuVo) {
		return mapper.getMenuInfoByMenuId(menuVo);
	}
    
	/**
	 * 函数方法描述:更新菜单
	 * 
	 * @param 菜单模型信息
	 * @return
	 */
	public boolean update(MenuVo menuModel) {
		 mapper.update(menuModel);
		 return true;
	}
	
	/**
	 * 函数方法描述:删除菜单
	 * 
	 * @param 菜单模型信息
	 * @return
	 */
	public boolean deleteMenuInfo(MenuVo menuModel) {
		boolean flag=true;
		try{
			mapper.deleteRoleMnu(menuModel);
			mapper.delete(menuModel);
		}catch (Exception e) {
			flag=false;
			System.out.println(e.getMessage());
		}
		 return flag;
	}
    
	/**
	 * 函数方法描述:新增菜单
	 * 
	 * @param 菜单模型信息
	 * @return
	 */
	public boolean addMenu(MenuVo menuModel) {
		MenuVo	vo = mapper.getMaxSubId(menuModel.getParentId());
		String id="";
		if(vo==null){
			id=menuModel.getParentId();
			String newId = id + "_1";
			menuModel.setMenuId(newId);
			mapper.changeFatherUrl(menuModel);
			mapper.insert(menuModel);
			return true;
		}else{
			id = vo.getMenuId();
		}
		
		int newLast = 1;
		Pattern pattern = Pattern.compile("[0-99]*"); 
		Matcher isNum = pattern.matcher(id);
		String newId = "";
		if(isNum.matches() ){
			newLast = Integer.parseInt(id) + 1; 
			if("M".equals(menuModel.getParentId())){
				newId = menuModel.getParentId() + String.valueOf(newLast);
			}else{
				newId = menuModel.getParentId() +'_'+ String.valueOf(newLast);
			}			
		} else{
			newId = id + "_1";
		}
		menuModel.setMenuId(newId);
		mapper.changeFatherUrl(menuModel);
		mapper.insert(menuModel);		
		return true;
	}

}
