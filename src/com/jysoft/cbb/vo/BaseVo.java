package com.jysoft.cbb.vo;


/**
 * 
 * 类功能描述: 基础VO
 * 创建者： 吴立刚
 * 创建日期： 2016-1-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class BaseVo {
	private int page = 1; // 默认第一页
	private int rows = 10; // 默认一页10条数据
	private String sort; // 排序字段
	private String order; // 排序关键字

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
