package com.jysoft.cbb.dao;

import java.util.List;

import com.jysoft.cbb.vo.QuartzLogVo;

/**
 * 
 * 类功能描述: 任务日志持久层接口
 * 创建者： 吴立刚
 * 创建日期： 2014-11-24
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public interface QuartzLogMapper {
	// 通过任务ID获取任务执行日志
	public List<QuartzLogVo> getTaskLogById(QuartzLogVo quartzLogVo);
	
	// 插入任务执行日志
	public void insertLog(QuartzLogVo quartzLogVo);
	
	public void updateLog(QuartzLogVo quartzLogVo);
	
	// 根据taskId获取日志表信息
	public List<QuartzLogVo> getLogByTaskId(QuartzLogVo quartzLogVo);
	
	public String getLogByTaskIdTotal(QuartzLogVo quartzLogVo);

	public QuartzLogVo getLastTaskLog(QuartzLogVo lastLogVo);
}
