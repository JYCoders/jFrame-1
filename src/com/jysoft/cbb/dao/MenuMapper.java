package com.jysoft.cbb.dao;

import java.util.List;

import com.jysoft.cbb.vo.MenuVo;
import com.jysoft.cbb.vo.RoleVo;

/**
 * 
 * 类功能描述:菜单类持久层接口
 * 创建者： 管马舟
 * 创建日期：  2016-01-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public interface MenuMapper {
    
	/**
	 * 函数方法描述:改变菜单的父菜单的URL
	 * 
	 * @param 菜单信息
	 * @return
	 */
	public Integer changeFatherUrl(MenuVo vo);
    
	/**
	 * 函数方法描述:获取所有菜单
	 * 
	 * @return
	 */
	public List<MenuVo> getAll();
    
	/**
	 * 函数方法描述:根据角色信息获取角色所对应的所有菜单
	 * 
	 * @param roleVo
	 * @return
	 */
	public List<MenuVo> getMenusByRoleId(RoleVo roleVo);
    
	/**
	 * 函数方法描述:根据用户id获取用户所对应的所有菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<MenuVo> getMenusByUserId(String userId);
    
	/**
	 * 函数方法描述:根据条件查询所需要菜单
	 * 
	 * @param 查询条件
	 * @return
	 */
	public List<MenuVo> getMenusByCondition(MenuVo menuModel);
     
	/**
	 * 函数方法描述:根据条件查询所有菜单的总条数
	 * 
	 * @param 查询条件
	 * @return
	 */
	public String getMenusByConditionTotal(MenuVo menuModel);

	/**
	 * 根据菜单ID获取菜单的基本信息
	 * 
	 * @param menuModel
	 * @return
	 */
	public List<MenuVo> queryMenuDataByMenuId(MenuVo menuModel);

	/**
	 * 根据菜单ID获取菜单的基本信息
	 * 
	 * @param menuModel
	 * @return
	 */
	public String queryMenuDataByMenuIdTotal(MenuVo menuModel);

	/**
	 * 根据菜单Id 获取菜单对象
	 * 
	 * @param 菜单ID
	 * @return 菜单对象
	 */
	public MenuVo getMenuInfoByMenuId(MenuVo menuVo);
    
	/**
	 * 函数方法描述:根据父级菜单获取子级菜单最大的id
	 * 
	 * @param 父级菜单id
	 * @return
	 */
	public MenuVo getMaxSubId(String pId);
    
	/**
	 * 函数方法描述:新增菜单
	 * 
	 * @param vo
	 * @return
	 */
	public Integer insert(MenuVo vo);
    
	/**
	 * 函数方法描述:更新菜单
	 * 
	 * @param vo
	 * @return
	 */
	public Integer update(MenuVo vo);
    
	/**
	 * 函数方法描述:删除菜单
	 * 
	 * @param vo
	 * @return
	 */
	public Integer delete(MenuVo menuModel);
    
	/**
	 * 函数方法描述:删除角色和菜单对应关系
	 * 
	 * @param vo
	 * @return
	 */
	public int deleteRoleMnu(MenuVo menuModel);

}
