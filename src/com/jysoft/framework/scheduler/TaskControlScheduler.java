package com.jysoft.framework.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.jysoft.cbb.dao.QuartzSetMapper;
import com.jysoft.cbb.vo.MessageVo;
import com.jysoft.cbb.vo.QuartzSetVo;
import com.jysoft.framework.listener.TaskJobListener;
import com.jysoft.framework.util.Constant;

/**
 * 
 * 类功能描述: 调度任务控制器 
 * 创建者： 吴立刚
 * 创建日期： 2014-11-15
 * 
 * 修改内容：调度任务工厂Bean直接从Spring容器中加载，同时加载quartz的线程池等参数
 * 修改者：吴立刚
 * 修改日期：2015-11-03
 */
@Component
public class TaskControlScheduler {
	@Autowired(required = true)
	SchedulerFactoryBean schedFact; // 任务工厂

	@Autowired(required = true)
	QuartzSetMapper mapper;

	Scheduler sched; // 调度任务执行器

	/**
	 * 函数方法描述: 初始化任务执行器
	 * 
	 */
	private void initScheduler() {
		try {
			if (sched == null) {
				sched = schedFact.getScheduler();
				sched.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 任务执行器关闭
	 * 
	 * @throws SchedulerException
	 */
	public void shutdown() {
		try {
			sched.shutdown();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 初始化所有任务
	 * 
	 * @throws Exception
	 */
	public MessageVo init(List<QuartzSetVo> jobList) {
		initScheduler();
		// 返回消息
		MessageVo msgVo = new MessageVo();
		try {
			// 循环调度任务数据集，动态加入执行计划
			for (QuartzSetVo job : jobList) {
				// 判断任务是否在执行计划中存在
				boolean isExist = this.isExist(job);
				if (isExist) {
					// 定义触发器
					CronTrigger trigger = new CronTrigger(job.getTaskName(),
							job.getTaskId());

					// 定义执行周期
					trigger.setCronExpression(job.getCronExpression());
					trigger.setJobName(job.getTaskName());
					trigger.setJobGroup(job.getTaskId());

					// 变更表达式后重新执行任务
					sched.rescheduleJob(trigger.getName(), job.getTaskId(),
							trigger);
				} else {
					// 定义Job
					JobDetail jobDetail = new JobDetail(job.getTaskName(), job
							.getTaskId(), Class.forName(job.getTaskProcClass()
							.trim()));
					jobDetail.setDescription(job.getTaskDesc());
					jobDetail.getJobDataMap().put("scheduleJob", job);

					// 定义触发器
					CronTrigger trigger = new CronTrigger(job.getTaskName(),
							job.getTaskId());

					// 定义执行周期
					trigger.setCronExpression(job.getCronExpression());

					// 定义任务监听
					TaskJobListener jobListener = new TaskJobListener();
					sched.addJobListener(jobListener);
					jobDetail.addJobListener("TaskJobListener");

					// 任务加入执行计划
					sched.scheduleJob(jobDetail, trigger);

					// 设置任务执行状态
					QuartzSetVo quartzSetVo = new QuartzSetVo();
					quartzSetVo.setTaskId(jobDetail.getGroup());
					quartzSetVo.setStartStat(Constant.COMM_START_STAT_START); // 任务启动状态
					mapper.updateTaskStartStatus(quartzSetVo);
				}
				msgVo.setResultCode("1");
				msgVo.setResultMsg("任务初始化成功！");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			msgVo.setResultCode("0");
			msgVo.setResultMsg("任务初始化失败：" + ex.getMessage());
		}

		return msgVo;
	}

	/**
	 * 动态配置任务
	 * 
	 * @throws Exception
	 */
	public MessageVo dynamicAdd(QuartzSetVo job) {
		initScheduler();
		// 返回消息
		MessageVo msgVo = new MessageVo();
		try {
			// 判断任务是否在执行计划中存在
			boolean isExist = this.isExist(job);
			if (isExist) {
				// 定义触发器
				CronTrigger trigger = new CronTrigger(job.getTaskName(), job
						.getTaskId());

				// 定义执行周期
				trigger.setCronExpression(job.getCronExpression());
				trigger.setJobName(job.getTaskName());
				trigger.setJobGroup(job.getTaskId());

				// 变更表达式后重新执行任务
				sched
						.rescheduleJob(trigger.getName(), job.getTaskId(),
								trigger);
			} else {
				// 定义Job
				JobDetail jobDetail = new JobDetail(job.getTaskName(), job
						.getTaskId(), Class.forName(job.getTaskProcClass()
						.trim()));
				jobDetail.setDescription(job.getTaskDesc());
				jobDetail.getJobDataMap().put("scheduleJob", job);

				// 定义触发器
				CronTrigger trigger = new CronTrigger(job.getTaskName(), job
						.getTaskId());

				// 定义执行周期
				trigger.setCronExpression(job.getCronExpression());

				// 定义任务监听
				TaskJobListener jobListener = new TaskJobListener();
				sched.addJobListener(jobListener);
				jobDetail.addJobListener("TaskJobListener");

				// 任务加入执行计划
				sched.scheduleJob(jobDetail, trigger);

				// 设置任务执行状态
				QuartzSetVo quartzSetVo = new QuartzSetVo();
				quartzSetVo.setTaskId(jobDetail.getGroup());
				quartzSetVo.setStartStat(Constant.COMM_START_STAT_START); // 任务启动状态
				mapper.updateTaskStartStatus(quartzSetVo);
			}
			msgVo.setResultCode("1");
			msgVo.setResultMsg("动态配置任务" + job.getTaskName() + "成功！");
		} catch (Exception ex) {
			ex.printStackTrace();
			msgVo.setResultCode("0");
			msgVo.setResultMsg("动态配置任务" + job.getTaskName() + "失败！异常信息："
					+ ex.getMessage());
		}
		return msgVo;
	}

	/**
	 * 暂停调度任务
	 * 
	 * @param scheduleJob
	 * @throws Exception
	 */
	public MessageVo pauseJob(QuartzSetVo job) {
		initScheduler();
		// 返回消息
		MessageVo msgVo = new MessageVo();
		try {
			sched.pauseJob(job.getTaskName(), job.getTaskId());

			// 设置任务执行状态
			QuartzSetVo quartzSetVo = new QuartzSetVo();
			quartzSetVo.setTaskId(job.getTaskId());
			quartzSetVo.setStartStat(Constant.COMM_START_STAT_PAUSED); // 任务暂停状态
			mapper.updateTaskStartStatus(quartzSetVo);
			
			msgVo.setResultCode("1");
			msgVo.setResultMsg("暂停调度任务" + job.getTaskName() + "成功！");
		} catch (Exception ex) {
			ex.printStackTrace();
			msgVo.setResultCode("0");
			msgVo.setResultMsg("暂停调度任务" + job.getTaskName() + "失败！异常信息："
					+ ex.getMessage());
		}
		return msgVo;
	}

	/**
	 * 恢复调度任务
	 * 
	 * @param scheduleJob
	 * @throws Exception
	 */
	public MessageVo resumeJob(QuartzSetVo job) {
		initScheduler();
		// 返回消息
		MessageVo msgVo = new MessageVo();
		try {
			sched.resumeJob(job.getTaskName(), job.getTaskId());

			// 设置任务执行状态
			QuartzSetVo quartzSetVo = new QuartzSetVo();
			quartzSetVo.setTaskId(job.getTaskId());
			quartzSetVo.setStartStat(Constant.COMM_START_STAT_START); // 任务启动状态
			mapper.updateTaskStartStatus(quartzSetVo);
			
			msgVo.setResultCode("1");
			msgVo.setResultMsg("恢复调度任务" + job.getTaskName() + "成功！");
		} catch (Exception ex) {
			ex.printStackTrace();

			msgVo.setResultCode("0");
			msgVo.setResultMsg("恢复调度任务" + job.getTaskName() + "失败！异常信息："
					+ ex.getMessage());
		}

		return msgVo;
	}

	/**
	 * 删除调度任务
	 * 
	 * @param scheduleJob
	 * @throws Exception
	 */
	public MessageVo deleteJob(QuartzSetVo job) {
		initScheduler();
		// 返回消息
		MessageVo msgVo = new MessageVo();
		try {
			sched.deleteJob(job.getTaskName(), job.getTaskId());

			// 设置任务执行状态
			QuartzSetVo quartzSetVo = new QuartzSetVo();
			quartzSetVo.setTaskId(job.getTaskId());
			quartzSetVo.setStartStat(Constant.COMM_START_STAT_DELETE); // 任务删除状态
			mapper.updateTaskStartStatus(quartzSetVo);
			
			msgVo.setResultCode("1");
			msgVo.setResultMsg("删除调度任务" + job.getTaskName() + "成功！");
		} catch (Exception ex) {
			ex.printStackTrace();

			msgVo.setResultCode("0");
			msgVo.setResultMsg("删除调度任务" + job.getTaskName() + "失败！异常信息："
					+ ex.getMessage());
		}

		return msgVo;
	}

	/**
	 * 立即运行调度任务
	 * 
	 * @param scheduleJob
	 * @throws Exception
	 */
	public MessageVo triggerJob(QuartzSetVo job) {
		initScheduler();
		// 返回消息
		MessageVo msgVo = new MessageVo();

		try {
			// 判断任务是否在执行计划中存在
			boolean isExist = this.isExist(job);
			if (isExist) {
				// 删除执行计划中的任务
				deleteJob(job);
				job.setStartStat(Constant.COMM_START_STAT_START); // 启用
			} else {
				job.setStartStat(Constant.COMM_START_STAT_INIT); // 未启用
			}
			job.setTaskDesc("立即执行");

			// 动态添加任务到执行计划
			msgVo = dynamicAdd(job);
			if ("0".equals(msgVo.getResultCode())) {
				return msgVo;
			}

			// 立即执行该任务
			sched.triggerJob(job.getTaskName(), job.getTaskId());

			msgVo.setResultCode("1");
			msgVo.setResultMsg("立即运行调度任务" + job.getTaskName() + "成功！");
		} catch (Exception ex) {
			ex.printStackTrace();

			msgVo.setResultCode("0");
			msgVo.setResultMsg("立即运行调度任务" + job.getTaskName() + "失败！异常信息："
					+ ex.getMessage());
		}

		return msgVo;
	}

	/**
	 * 判断任务是否在执行计划中存在
	 * 
	 * @param scheduleJob
	 * @return False：不存在； True：存在
	 * @throws Exception
	 */
	public boolean isExist(QuartzSetVo scheduleJob) throws Exception {
		try {
			boolean existFlg = false;
			// 获取所有在执行计划中的任务
			List<QuartzSetVo> jobList = this.getPlanTaskList();

			// 判断任务是否存在
			for (QuartzSetVo job : jobList) {
				if (job.getTaskId().equals(scheduleJob.getTaskId())
						&& job.getTaskName().equals(scheduleJob.getTaskName())) {
					existFlg = true;
				}
			}

			return existFlg;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * 获取已加入计划中的Job
	 * 
	 * @return Job结果集
	 */
	public List<QuartzSetVo> getPlanTaskList() throws Exception {
		try {
			List<QuartzSetVo> jobList = new ArrayList<QuartzSetVo>();
			String[] grpNms = sched.getJobGroupNames();
			for (int i = 0; i < grpNms.length; i++) {
				String groupName = grpNms[i];
				String[] jobNames = sched.getJobNames(groupName);
				for (int j = 0; j < jobNames.length; j++) {
					QuartzSetVo job = new QuartzSetVo();
					// 根据任务名、组名获取JobDetail
					JobDetail tmpJDetail = sched.getJobDetail(jobNames[j],
							groupName);

					// 设置任务ID
					job.setTaskId(tmpJDetail.getGroup());
					job.setTaskName(tmpJDetail.getName());
					job.setTaskDesc(tmpJDetail.getDescription());

					Trigger trigger = sched.getTrigger(tmpJDetail.getName(),
							tmpJDetail.getGroup());
					if (trigger instanceof CronTrigger) {
						CronTrigger cronTrigger = (CronTrigger) trigger;
						job.setCronExpression(cronTrigger.getCronExpression());
					}
					jobList.add(job);
				}
			}
			return jobList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * 获取正在运行的Job
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<QuartzSetVo> getRunningTaskList() throws Exception {
		try {
			List<JobExecutionContext> executingJobs = sched
					.getCurrentlyExecutingJobs();
			List<QuartzSetVo> jobList = new ArrayList<QuartzSetVo>(
					executingJobs.size());
			for (JobExecutionContext executingJob : executingJobs) {
				QuartzSetVo job = new QuartzSetVo();
				JobDetail jobDetail = executingJob.getJobDetail();

				job.setTaskName(jobDetail.getName());
				job.setTaskId(jobDetail.getGroup());
				job.setTaskDesc(jobDetail.getDescription());

				Trigger trigger = sched.getTrigger(jobDetail.getName(),
						jobDetail.getGroup());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					job.setCronExpression(cronTrigger.getCronExpression());
				}
				jobList.add(job);
			}

			return jobList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
