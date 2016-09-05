package com.jysoft.cbb.dao;

import com.jysoft.cbb.vo.UserRoleVo;

/**
 * 
 * 类功能描述: 用户、角色持久层接口
 * 创建者： 管马舟
 * 创建日期： 2016-01-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public interface UserRoleMapper {
    
	/**
	 * 函数方法描述:新增用户和角色的对应关系
	 * 
	 * @param 用户和角色的对应关系
	 * @return
	 */
	public Integer insert(UserRoleVo userRole);
	
	/**
	 * 函数方法描述:根据用户id删除对应的用户和角色的对应关系
	 * 
	 * @param 用户id
	 * @return
	 */
	public Integer deleteByUserId(String userId);

}
