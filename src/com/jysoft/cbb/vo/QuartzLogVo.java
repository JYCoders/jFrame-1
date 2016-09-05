package com.jysoft.cbb.vo;

/**
 * 
 * 类功能描述: 调度任务日志实体类
 * 创建者： 吴立刚
 * 创建日期： 2014-11-18
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class QuartzLogVo extends BaseVo{
	private String taskInstid; // 调度任务实例ID
	private String taskId; // 调度任务ID
	private String startDate; // 启动时间
	private String endDate; // 结束时间
	private String excStat; // 执行状态
	private String excSrc; // 执行来源
	
	//新增
	private String taskName;
	
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * 获取taskInstid
	 * @return taskInstid taskInstid
	 */
	public String getTaskInstid() {
		return taskInstid;
	}
	/**
	 * 设置taskInstid
	 * @param taskInstid taskInstid
	 */
	public void setTaskInstid(String taskInstid) {
		this.taskInstid = taskInstid;
	}
	/**
	 * 获取taskId
	 * @return taskId taskId
	 */
	public String getTaskId() {
		return taskId;
	}
	/**
	 * 设置taskId
	 * @param taskId taskId
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	/**
	 * 获取startDate
	 * @return startDate startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * 设置startDate
	 * @param startDate startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * 获取endDate
	 * @return endDate endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * 设置endDate
	 * @param endDate endDate
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * 获取excStat
	 * @return excStat excStat
	 */
	public String getExcStat() {
		return excStat;
	}
	/**
	 * 设置excStat
	 * @param excStat excStat
	 */
	public void setExcStat(String excStat) {
		this.excStat = excStat;
	}
	/**
	 * 获取excSrc
	 * @return excSrc excSrc
	 */
	public String getExcSrc() {
		return excSrc;
	}
	/**
	 * 设置excSrc
	 * @param excSrc excSrc
	 */
	public void setExcSrc(String excSrc) {
		this.excSrc = excSrc;
	}
}
