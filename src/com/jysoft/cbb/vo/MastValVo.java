package com.jysoft.cbb.vo;

/**
 * 
 * 类功能描述:码表值实体类
 * 创建者： 黄智强
 * 创建日期： 2016-1-25
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class MastValVo extends BaseVo {
	private String id; // 码表值ID
	private String typeId; // 码表值类型
	private String masterCode; // 码表编码
	private String valName; // 码表值名称
	private String valCode; // 值编码
	private String remark; // 备注
	private String useFlag; // 使用标识，启用和未启用
	private String masterName; // 码表名称
	private int ext1; // 扩展字段1
	private int sortNum; // 排序

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getMasterCode() {
		return masterCode;
	}

	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}

	public String getValName() {
		return valName;
	}

	public void setValName(String valName) {
		this.valName = valName;
	}

	public String getValCode() {
		return valCode;
	}

	public void setValCode(String valCode) {
		this.valCode = valCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public int getExt1() {
		return ext1;
	}

	public void setExt1(int ext1) {
		this.ext1 = ext1;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
}
