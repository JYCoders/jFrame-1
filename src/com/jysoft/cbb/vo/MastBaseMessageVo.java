package com.jysoft.cbb.vo;


/**
 * 
 * 类功能描述:码表基础Vo，用于存放从数据库中取出的码表基础数据
 * 创建者：黄智强
 * 创建日期： 2016-1-14
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class MastBaseMessageVo {
	private String id; // 码表id
	private String master_code; // 码表编码
	private String master_name; // 码表名称
	private String p_id; // 父节点
	private String remark; // 描述
	private String sort_num; // 排序号
	private String use_flag; // 启用表示

	public String getUse_flag() {
		return use_flag;
	}

	public void setUse_flag(String use_flag) {
		this.use_flag = use_flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaster_code() {
		return master_code;
	}

	public void setMaster_code(String master_code) {
		this.master_code = master_code;
	}

	public String getMaster_name() {
		return master_name;
	}

	public void setMaster_name(String master_name) {
		this.master_name = master_name;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSort_num() {
		return sort_num;
	}

	public void setSort_num(String sort_num) {
		this.sort_num = sort_num;
	}
}
