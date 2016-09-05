package com.jysoft.cbb.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 类功能描述:树结构实体类
 * 创建者： 黄智强
 * 创建日期： 2016-1-18
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class TreeVo {
	private String id; // 节点ID
	private String text; // 节点名称
	private String iconCls; // 图片
	private Boolean checked; // true\false
	private String state;//'open'或'closed' 默认为'open'
	private Map<String, Object> attributes;//自定义属性
	private List<TreeVo> children;// 子节点
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Map<String, Object> getAttributes() {
		if(attributes == null) {
			attributes = new HashMap<String, Object>();
		}
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public List<TreeVo> getChildren() {
		if(children == null) {
			children = new ArrayList<TreeVo>();
		}
		return children;
	}
	public void setChildren(List<TreeVo> children) {
		this.children = children;
	}
	
	
}
