package com.jysoft.cbb.vo;


/**
 * 
 * 类功能描述:员工基本信息，包含与组织机构关联信息
 * 创建者：黄智强
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class EmpInfoVo {
	private String emp_id;// 主键
	private String emp_code;// 员工编号
	private String emp_name;// 员工姓名
	private String emp_sex;// 员工性别
	private String emp_birth;// 出生日期
	private String org_id;// 所属组织机构
	private String org_name;// 组织机构名称
	private String mob_tel;// 移动电话
	private String fix_tel;// 固定电话
	private String email;// 电子邮件

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getEmp_sex() {
		return emp_sex;
	}

	public void setEmp_sex(String emp_sex) {
		this.emp_sex = emp_sex;
	}

	public String getEmp_birth() {
		return emp_birth;
	}

	public void setEmp_birth(String emp_birth) {
		this.emp_birth = emp_birth;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getMob_tel() {
		return mob_tel;
	}

	public void setMob_tel(String mob_tel) {
		this.mob_tel = mob_tel;
	}

	public String getFix_tel() {
		return fix_tel;
	}

	public void setFix_tel(String fix_tel) {
		this.fix_tel = fix_tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

}
