package com.jysoft.cbb.dao;

import java.util.List;

import com.jysoft.cbb.vo.RoleVo;
import com.jysoft.cbb.vo.UserVo;

/**
 * 
 * 类功能描述:角色类持久层接口
 * 创建者： 管马舟
 * 创建日期： 2016-01-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public interface RoleMapper {
    
	/**
	 * 函数方法描述:获取所有角色,不分页
	 * 
	 * @return
	 */
	List<RoleVo> getAll();
    
	/**
	 * 函数方法描述:获取所有角色，分页
	 * 
	 * @param vo
	 * @return
	 */
	List<RoleVo> getAllPage(RoleVo vo);
	
    /**
     * 函数方法描述:获取所有角色的条数
     * 
     * @return
     */
	String getAllTotal();
    
	/**
	 * 函数方法描述:根据角色id获取对应角色
	 * 
	 * @param vo
	 * @return
	 */
	RoleVo getRoleById(RoleVo vo);
    
	/**
	 * 函数方法描述:根据角色名称获取所有角色
	 * 
	 * @param vo
	 * @return
	 */
	List<RoleVo> getRolesByName(RoleVo vo);
    
	/**
	 * 函数方法描述:删除角色
	 * 
	 * @param vo
	 * @return
	 */
	Integer delete(RoleVo vo);
    
	/**
	 * 函数方法描述:更新角色
	 * 
	 * @param vo
	 * @return
	 */
	Integer update(RoleVo vo);
    
	/**
	 * 函数方法描述:根据用户id获取所对应的角色
	 * 
	 * @param vo
	 * @return
	 */
	List<RoleVo> getRolesByUserId(UserVo vo);
    
	/**
	 * 函数方法描述:插入角色
	 * 
	 * @param vo
	 * @return
	 */
	Integer insert(RoleVo vo);
}
