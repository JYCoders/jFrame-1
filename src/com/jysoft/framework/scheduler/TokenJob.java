package com.jysoft.framework.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;

public class TokenJob implements Job {

	private static String GET_TOKEN_JOB_NAME = "getBOTOKENJob";

	public TokenJob() {

	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String jobName = context.getJobDetail().getKey().getName();
		System.out.println("["
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date()) + "],任务：" + jobName + "开始执行....");
		try {
			// BOHelper.resetBOToken();
			// System.out.println("        任务："+jobName+"执行成功:"+BOHelper.getBOTOKEN());
		} catch (Exception e) {
			System.out.println("        任务：" + jobName + "执行失败:"
					+ e.getMessage());
			if (GET_TOKEN_JOB_NAME.toUpperCase().equals(jobName.toUpperCase())) {
				SimpleTrigger simpleTrigger = new SimpleTrigger(
						"retry_trigger", 2, 10000);
				simpleTrigger.setStartTime(new Date(
						new Date().getTime() + 10000));
				JobDetail job = new JobDetail("retry_job",
						Scheduler.DEFAULT_GROUP, TokenJob.class);
				try {
					context.getScheduler().scheduleJob(job, simpleTrigger);
				} catch (Exception ex) {
				}
			}
		}

	}
}