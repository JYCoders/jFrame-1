package com.jysoft.cbb.vo;


/**
 * 
 * 类功能描述: 用户管理操作日志实体类
 * 创建者： 吴立刚
 * 创建日期： 2016-1-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class AdminOptLogVo {
	private String id;//ID
	private String adminUser;//管理员账号
	private String optUser;//操作用户
	private String optTime;//操作时间
	private String optType;//操作类型
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdminUser() {
		return adminUser;
	}
	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}
	public String getOptUser() {
		return optUser;
	}
	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}
	public String getOptTime() {
		return optTime;
	}
	public void setOptTime(String optTime) {
		this.optTime = optTime;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	
}
