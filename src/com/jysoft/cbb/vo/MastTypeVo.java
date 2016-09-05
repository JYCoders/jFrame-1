package com.jysoft.cbb.vo;

/**
 * 
 * 类功能描述:码表实体类
 * 创建者： 黄智强
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class MastTypeVo extends BaseVo {
	private String masterVal; // 码表
	private String id; // 码表ID
	private String masterCode; // 代码编码
	private String masterName; // 代码名称
	private String remark; // 代码描述
	private String pid; // 上级代码
	private String ndType; // 节点类型
	private String useFlag; // 启用标识(1:使用，0：停用)
	private String useFlagName; // 启用标识(1:使用，0：停用)
	private String sortNum; // 排序号

	public String getMasterVal() {
		return masterVal;
	}

	public void setMasterVal(String masterVal) {
		this.masterVal = masterVal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMasterCode() {
		return masterCode;
	}

	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getNdType() {
		return ndType;
	}

	public void setNdType(String ndType) {
		this.ndType = ndType;
	}

	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	public String getUseFlagName() {
		return useFlagName;
	}

	public void setUseFlagName(String useFlagName) {
		this.useFlagName = useFlagName;
	}

	public String getSortNum() {
		return sortNum;
	}

	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}
}
