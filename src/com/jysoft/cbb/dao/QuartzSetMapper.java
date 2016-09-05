package com.jysoft.cbb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jysoft.cbb.vo.QuartzSetVo;

/**
 * 
 * 类功能描述: 调度任务持久层接口
 * 创建者： 吴立刚
 * 创建日期： 2014-11-15
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public interface QuartzSetMapper {
	// 获取所有的任务
	public List<QuartzSetVo> getAllTask();
	
	// 获取当页所有的任务
	public List<QuartzSetVo> getAllTaskByPage(QuartzSetVo quartzSetVo);
	
	// 获取所有的任务总数
	public String getAllTaskTotal(QuartzSetVo quartzSetVo);
	
	// 获取所有系统启动后自动装置的任务
	public List<QuartzSetVo> getAutoStartTask();
	
	// 通过任务ID获取任务信息
	public QuartzSetVo getTaskById(QuartzSetVo quartzSetVo);
	
	// 根据条件获取任务信息
	public List<QuartzSetVo> getTaskByCondition(QuartzSetVo quartzSetVo);
	
	// 更新任务状态
	public void setTaskStatus(QuartzSetVo quartzSetVo);
	
	// 新增调度任务信息
	public Integer insert(QuartzSetVo quartzSetVo);
	
	// 删除调度任务信息
	public Integer deleteById(QuartzSetVo quartzSetVo);
	
	// 更新调度任务信息
	public Integer updateById(QuartzSetVo quartzSetVo);
	
	//检查任务是否执行完成
	public int checkQuartzLog(@Param("taskId")String taskId, @Param("date")String date);
	
	// 更新任务执行状态
	public int updateTaskExeStatus(QuartzSetVo quartzSetVo);
	
	// 更新任务启动状态
	public int updateTaskStartStatus(QuartzSetVo quartzSetVo);
}
