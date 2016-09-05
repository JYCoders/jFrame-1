package com.jysoft.framework.vo;

import org.springframework.beans.factory.annotation.Value;

/**
 * 
 * 类功能描述: 翻页实体类
 * 创建者： 吴立刚
 * 创建日期： 2016-1-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class PageVo {
	private String queryId;

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	private int page;
	@Value("${pageRow}")
	private int rows;
	private int totalRows;
	private int qs = (page - 1) * rows + 1;// ��ѯ��ʼ
	private int qe = page * rows;// ��ѯ����

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
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

	public int getQs() {
		return qs;
	}

	public int getQe() {
		return qe;
	}

	public void setQs(int qs) {
		this.qs = (this.page - 1) * this.rows;
	}

	public void setQe(int qe) {
		this.qe = this.page * this.rows - 1;// ��ѯ����
	}

	public void reCompute() {
		this.qs = (this.page - 1) * this.rows;// ��ѯ��ʼ
		this.qe = this.page * this.rows - 1;// ��ѯ����
	}
}