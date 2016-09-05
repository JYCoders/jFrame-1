package com.jysoft.framework.listener;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jysoft.cbb.service.QuartzSetService;
import com.jysoft.cbb.vo.QuartzSetVo;
import com.jysoft.framework.scheduler.TaskControlScheduler;
import com.jysoft.framework.util.Constant;


/**
 * 
 * 类功能描述: 调度任务执行情况监听器
 * 创建者： 吴立刚
 * 创建日期： 2015-10-26
 * 
 * 修改内容：
 * 修改者：
 * 修改日期：
 */
public class TaskJobListener implements JobListener {
	// 获取上下文信息
	WebApplicationContext webAppContext;

	// 调度任务管理服务
	QuartzSetService service;
	
	// 调度任务控制器 
	TaskControlScheduler taskScheduler;

	public String getName() {
		return "TaskJobListener";
	}

	/**
	 * 函数方法描述: 获取服务实例
	 *
	 */
	public void initServiceBean() {
		webAppContext = ContextLoader
		.getCurrentWebApplicationContext();
		if (service == null) {
			service = (QuartzSetService) webAppContext
			.getBean("quartzSetService");
		}
		if (taskScheduler == null) {
			taskScheduler = (TaskControlScheduler) webAppContext
			.getBean("taskControlScheduler");
		}
	}
	
	/**
	 * Scheduler 在 JobDetail 即将被执行，但又被 TriggerListener 否决了时调用这个方法
	 */
	public void jobExecutionVetoed(JobExecutionContext context) {
		System.out.println("调度任务不执行……");
	}

	/**
	 * Scheduler 在 JobDetail 将要被执行时调用这个方法。
	 */
	public void jobToBeExecuted(JobExecutionContext context) {
		// 初始化服务实例
		initServiceBean();
		System.out.println("调度任务开始执行……");
		JobDetail jobDetail = context.getJobDetail();

		// 调度任务基本信息
		QuartzSetVo quartzSetVo = new QuartzSetVo();
		quartzSetVo.setTaskId(jobDetail.getGroup());
		quartzSetVo.setExeStat(Constant.COMM_TASK_STATUS_EXECUTING); // 任务执行中状态
		service.updateTaskExeStatus(quartzSetVo);
	}

	/**
	 * Scheduler 在 JobDetail 被执行之后调用这个方法
	 */
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException ex) {
		// 初始化服务实例
		initServiceBean();
		QuartzSetVo job = new QuartzSetVo();
		JobDetail jobDetail = context.getJobDetail();
		
		System.out.println("调度任务执行结束……");
		
		// 如果是立即执行，执行完成后重置该任务在任务执行器中的状态
		job = (QuartzSetVo) jobDetail.getJobDataMap().get("scheduleJob");

		if ("立即执行".equals(job.getTaskDesc())) {
			// 任务启动状态为"1"启用的任务立即执行完成后需在任务执行器中重置当前任务状态
			if (Constant.COMM_START_STAT_START.equals(job.getStartStat())) {
				taskScheduler.deleteJob(job);
				job.setTaskDesc("自动");
				taskScheduler.dynamicAdd(job);
			} else {
				// 任务启动状态为"0"未启动或“2”停用的任务立即执行完成后需将任务执行器中删除该任务
				taskScheduler.deleteJob(job);
			}
		}

		// 调度任务基本信息
		QuartzSetVo quartzSetVo = new QuartzSetVo();
		quartzSetVo.setTaskId(jobDetail.getGroup());

		// 如果任务执行异常则将设置任务为异常执行状态
		if (ex != null) {
			quartzSetVo.setExeStat(Constant.COMM_TASK_STATUS_ERROR); // 任务异常执行状态
			ex.printStackTrace();
		} else {
			quartzSetVo.setExeStat(Constant.COMM_TASK_STATUS_COMPLETE); // 任务正常执行状态
		}
		service.updateTaskExeStatus(quartzSetVo);
	}
}
