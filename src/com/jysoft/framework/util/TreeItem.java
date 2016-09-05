package com.jysoft.framework.util;

/**
 * TreeItem entity.
 * 
 * @author
 */

public class TreeItem implements java.io.Serializable {

	// Fields
	private String id; //ID

	private String name; //名称

	private String parentId; // 父ID

	private String type; // 节点类型
	
	// Constructors
	/** default constructor */
	public TreeItem() {
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
