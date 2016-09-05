package com.jysoft.cbb.vo;

import java.util.Date;

/**
 * 
 * 类功能描述: 角色实体类
 * 创建者： 管马舟
 * 创建日期：2016-01-19
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class RoleVo extends BaseVo {
	private String roleId; // 角色ID
	private String roleName; // 角色名称
	private String roleDesc; // 角色描述

	private String isDefault;
	private String enable;
	private Date modifyDate;
	
	public static final String ADMIN_ID = "1";
	public static boolean isAdmin(String roleId) {
		return ADMIN_ID.equals(roleId);
	}
	
	public RoleVo() {
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
	public String getIsDefault() {
		return isDefault;
	}
	
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	public String getEnable() {
		return enable;
	}
	
	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
