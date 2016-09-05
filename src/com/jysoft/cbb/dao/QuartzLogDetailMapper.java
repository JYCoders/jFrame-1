package com.jysoft.cbb.dao;

import java.util.List;

import com.jysoft.cbb.vo.QuartzLogDetailVo;

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
public interface QuartzLogDetailMapper {
	// 通过任务ID获取任务执行日志
	public List<QuartzLogDetailVo> getTaskLogDetailById(QuartzLogDetailVo quartzLogDetailVo);
	
	// 通过任务ID获取任务执行日志总数
	public String getTaskLogDetailByIdTotal(QuartzLogDetailVo quartzLogDetailVo);
	
	// 插入任务执行日志
	public void insertLog(QuartzLogDetailVo quartzLogDetailVo);
}
