package com.jysoft.cbb.vo;

/**
 * 
 * 类功能描述: 任务配置实体类
 * 创建者： 吴立刚
 * 创建日期： 2014-11-15
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class QuartzSetVo extends BaseVo {
	private String taskId; // 任务ID
	private String taskName; // 任务名称
	private String taskDesc; // 任务描述
	private String taskCycle; // 任务频度
	private String cronExpression; // 任务调度表达式
	private String taskParm1; // 参数1
	private String taskParmVal1; // 参数值1
	private String taskParm2; // 参数2
	private String taskParmVal2; // 参数值2
	private String taskParm3; // 参数3
	private String taskParmVal3; // 参数值3
	private String taskProcName; // 启动程序名
	private String taskProcClass; // 启动类
	private String startMode; // 启动方式，0：手动、1：自动
	private String exeStat; // 任务执行状态，0：未执行、1：执行中、2：执行完成、3：执行异常
	private String startStat;//启动状态，0：未启动、1：启动、2：停用
	private String ctiveTimeName;//调度时间说明
	
	
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
	 * 获取taskName
	 * @return taskName taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * 设置taskName
	 * @param taskName taskName
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * 获取taskDesc
	 * @return taskDesc taskDesc
	 */
	public String getTaskDesc() {
		return taskDesc;
	}
	/**
	 * 设置taskDesc
	 * @param taskDesc taskDesc
	 */
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	/**
	 * 获取taskCycle
	 * @return taskCycle taskCycle
	 */
	public String getTaskCycle() {
		return taskCycle;
	}
	/**
	 * 设置taskCycle
	 * @param taskCycle taskCycle
	 */
	public void setTaskCycle(String taskCycle) {
		this.taskCycle = taskCycle;
	}
	
	/**
	 * 获取cronExpression
	 * @return cronExpression cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}
	/**
	 * 设置cronExpression
	 * @param cronExpression cronExpression
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	/**
	 * 获取taskParm1
	 * @return taskParm1 taskParm1
	 */
	public String getTaskParm1() {
		return taskParm1;
	}
	/**
	 * 设置taskParm1
	 * @param taskParm1 taskParm1
	 */
	public void setTaskParm1(String taskParm1) {
		this.taskParm1 = taskParm1;
	}
	/**
	 * 获取taskParmVal1
	 * @return taskParmVal1 taskParmVal1
	 */
	public String getTaskParmVal1() {
		return taskParmVal1;
	}
	/**
	 * 设置taskParmVal1
	 * @param taskParmVal1 taskParmVal1
	 */
	public void setTaskParmVal1(String taskParmVal1) {
		this.taskParmVal1 = taskParmVal1;
	}
	/**
	 * 获取taskParm2
	 * @return taskParm2 taskParm2
	 */
	public String getTaskParm2() {
		return taskParm2;
	}
	/**
	 * 设置taskParm2
	 * @param taskParm2 taskParm2
	 */
	public void setTaskParm2(String taskParm2) {
		this.taskParm2 = taskParm2;
	}
	/**
	 * 获取taskParmVal2
	 * @return taskParmVal2 taskParmVal2
	 */
	public String getTaskParmVal2() {
		return taskParmVal2;
	}
	/**
	 * 设置taskParmVal2
	 * @param taskParmVal2 taskParmVal2
	 */
	public void setTaskParmVal2(String taskParmVal2) {
		this.taskParmVal2 = taskParmVal2;
	}
	/**
	 * 获取taskParm3
	 * @return taskParm3 taskParm3
	 */
	public String getTaskParm3() {
		return taskParm3;
	}
	/**
	 * 设置taskParm3
	 * @param taskParm3 taskParm3
	 */
	public void setTaskParm3(String taskParm3) {
		this.taskParm3 = taskParm3;
	}
	/**
	 * 获取taskParmVal3
	 * @return taskParmVal3 taskParmVal3
	 */
	public String getTaskParmVal3() {
		return taskParmVal3;
	}
	/**
	 * 设置taskParmVal3
	 * @param taskParmVal3 taskParmVal3
	 */
	public void setTaskParmVal3(String taskParmVal3) {
		this.taskParmVal3 = taskParmVal3;
	}
	/**
	 * 获取taskProcName
	 * @return taskProcName taskProcName
	 */
	public String getTaskProcName() {
		return taskProcName;
	}
	/**
	 * 设置taskProcName
	 * @param taskProcName taskProcName
	 */
	public void setTaskProcName(String taskProcName) {
		this.taskProcName = taskProcName;
	}
	/**
	 * 获取taskProcClass
	 * @return taskProcClass taskProcClass
	 */
	public String getTaskProcClass() {
		return taskProcClass;
	}
	/**
	 * 设置taskProcClass
	 * @param taskProcClass taskProcClass
	 */
	public void setTaskProcClass(String taskProcClass) {
		this.taskProcClass = taskProcClass;
	}
	
	
	/**
	 * @return the startMode
	 */
	public String getStartMode() {
		return startMode;
	}
	/**
	 * @param startMode the startMode to set
	 */
	public void setStartMode(String startMode) {
		this.startMode = startMode;
	}
	/**
	 * 获取startStat
	 * @param startStat startStat
	 */
	public String getStartStat() {
		return startStat;
	}
	
	/**
	 * 设置startStat
	 * @param startStat startStat
	 */
	public void setStartStat(String startStat) {
		this.startStat = startStat;
	}
	
	/**
	 * 获取ctiveTimeName
	 * @param ctiveTimeName ctiveTimeName
	 */
	public String getCtiveTimeName() {
		return ctiveTimeName;
	}
	
	/**
	 * 设置ctiveTimeName
	 * @param ctiveTimeName ctiveTimeName
	 */
	public void setCtiveTimeName(String ctiveTimeName) {
		this.ctiveTimeName = ctiveTimeName;
	}
	/**
	 * @return the exeStat
	 */
	public String getExeStat() {
		return exeStat;
	}
	/**
	 * @param exeStat the exeStat to set
	 */
	public void setExeStat(String exeStat) {
		this.exeStat = exeStat;
	}
}
