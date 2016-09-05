package com.jysoft.cbb.vo;
/**
 * 
 * 类功能描述:审计日志表实体类
 * 创建者： 黄智强
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class AuditLogVo extends BaseVo {
	private String id;// ID
	private String loginId;// 登陆账号
	private String userName;// 用户名称
	private String loginTime;// 登陆时间
	private String loginOutTime;// 退出时间
	private String loginState;// 登陆状态
	private String addMac;// MAC
	private String addIp;// IP
	private String audState;// 审计状态

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginOutTime() {
		return loginOutTime;
	}

	public void setLoginOutTime(String loginOutTime) {
		this.loginOutTime = loginOutTime;
	}

	public String getLoginState() {
		return loginState;
	}

	public void setLoginState(String loginState) {
		this.loginState = loginState;
	}

	public String getAddMac() {
		return addMac;
	}

	public void setAddMac(String addMac) {
		this.addMac = addMac;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public String getAudState() {
		return audState;
	}

	public void setAudState(String audState) {
		this.audState = audState;
	}
}
