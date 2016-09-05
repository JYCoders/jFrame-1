package com.jysoft.cbb.vo;

import java.util.Date;

/**
 * 
 * 类功能描述: 菜单实体类
 * 创建者： 吴立刚
 * 创建日期： 2016-1-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class MenuVo extends BaseVo {
	private String menuId; // 菜单ID
	private String menuName; // 菜单名称
	private String display; // 菜单描述
	private String linkUrl; // 链接地址
	private String linkTarget; // 目标页面
	private String icon; // 默认图标
	private String iconOpen; // 展开图标
	private Long openStatus; // 开启状态
	private Long menuType; // 菜单类别
	private Date modifyDate;
	private Integer ordinal;
	private String parentId; // 父菜单ID
	private Integer pageWidth; // 页面宽度
	private Integer pageHeight; // 页面高度
	private Integer defaultWidth; // 原始宽度
	private Integer defaultHeight; // 原始高度
	private String nodeType; // 树节点类型
	private String menuCode; // code

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getLinkUrl() {
		return this.linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getLinkTarget() {
		return this.linkTarget;
	}

	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconOpen() {
		return this.iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public Long getOpenStatus() {
		return this.openStatus;
	}

	public void setOpenStatus(Long openStatus) {
		this.openStatus = openStatus;
	}

	public Long getMenuType() {
		return this.menuType;
	}

	public void setMenuType(Long menuType) {
		this.menuType = menuType;
	}

	public Integer getOrdinal() {
		return this.ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public Integer getPageWidth() {
		return this.pageWidth;
	}

	public void setPageWidth(Integer pageWidth) {
		this.pageWidth = pageWidth;
	}

	public Integer getPageHeight() {
		return this.pageHeight;
	}

	public void setPageHeight(Integer pageHeight) {
		this.pageHeight = pageHeight;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public void setDefaultWidth(Integer defaultWidth) {
		this.defaultWidth = defaultWidth;
	}

	public Integer getDefaultWidth() {
		return defaultWidth;
	}

	public void setDefaultHeight(Integer defaultHeight) {
		this.defaultHeight = defaultHeight;
	}

	public Integer getDefaultHeight() {
		return defaultHeight;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

}
