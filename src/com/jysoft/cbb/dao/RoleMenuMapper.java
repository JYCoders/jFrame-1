package com.jysoft.cbb.dao;

import com.jysoft.cbb.vo.RoleMenuVo;
import com.jysoft.cbb.vo.RoleVo;
/**
 * 
 * 类功能描述:角色菜单类持久层接口
 * 创建者： 管马舟
 * 创建日期：  2016-01-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public interface RoleMenuMapper {
    
	/**
	 * 函数方法描述:根据角色id删除角色所对应的菜单
	 * 
	 * @param 角色信息
	 * @return
	 */
	public Integer deleteByRoleId(RoleVo vo);
	
	/**
	 * 函数方法描述:新增角色所对应的菜单
	 * 
	 * @param 角色信息
	 * @return
	 */
	public Integer insert(RoleMenuVo roleMenu);

}
