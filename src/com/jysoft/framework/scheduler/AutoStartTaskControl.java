/*package com.jysoft.framework.scheduler;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jysoft.dcms.dao.QuartzSetMapper;
import com.jysoft.dcms.vo.QuartzSetVo;

*//**
 * 
 * 类功能描述: 调度任务初始化加载
 * 创建者： 吴立刚
 * 创建日期： 2014-11-18
 * 
 * 修改内容：直接用调度任务管理器直接加载
 * 修改者：吴立刚
 * 修改日期： 2015-11-03
 *//*
@Service
public class AutoStartTaskControl {
	@Autowired
	TaskControlScheduler taskControlScheduler;

	@Resource
	QuartzSetMapper mapper;
	
	*//**
	 * 函数方法描述: 在服务启动时自动加载启动方式为“自动”的任务
	 *
	 *//*
	@PostConstruct
	public void autoStartDynamicAdd(){
		// 从数据库获取所有调度任务
		List<QuartzSetVo> list = mapper.getAutoStartTask();
		taskControlScheduler.init(list);
	}
}
*/