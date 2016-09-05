package com.jysoft.cbb.vo;


/**
 * 
 * 类功能描述: 下拉框实体类
 * 创建者： 吴立刚
 * 创建日期： 2016-1-22
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class ComBoxSelectVo {
	private String id; // 键
	private String text; // 值
	private String type; // 值类型
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
