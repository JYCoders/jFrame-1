package com.jysoft.cbb.vo;

import java.util.Date;

/**
 * 
 * 类功能描述: 用户实体类
 * 创建者： 吴立刚
 * 创建日期： 2016-1-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class UserVo extends BaseVo {
	private String userId; // 用户ID
	private String loginId; // 登录名
	private String userName; // 用户姓名
	private String userPass; // 密码
	private String deptId; // 部门ID
	private String deptName; // 部门名称
	private String enable;
	private Date modifyDate;
	private String userLevel;

	private String orgId; // 组织机构ID
	private String empId; // 员工Id
	private String roleId; // 角色ID
	private String comId; // 公司ID
	private String comName; // 公司名称
	private String empName; // 员工姓名

	private String lockTime; // 锁定时间
	private String lockLimit; // 登陆错误次数锁定阀值
	private String lockState; // 用户锁定状态
	private String topComId; // 省公司ID
	private String loginOnTime; // 当前用户登录与否

	public String getLoginOnTime() {
		return loginOnTime;
	}

	public void setLoginOnTime(String loginOnTime) {
		this.loginOnTime = loginOnTime;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getLockTime() {
		return lockTime;
	}

	public void setLockTime(String lockTime) {
		this.lockTime = lockTime;
	}

	public String getLockLimit() {
		return lockLimit;
	}

	public void setLockLimit(String lockLimit) {
		this.lockLimit = lockLimit;
	}

	public String getLockState() {
		return lockState;
	}

	public void setLockState(String lockState) {
		this.lockState = lockState;
	}

	public String getTopComId() {
		return topComId;
	}

	public void setTopComId(String topComId) {
		this.topComId = topComId;
	}
}
