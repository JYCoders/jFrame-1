package com.jysoft.framework.scheduler;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			/*SchedulerFactory schedFact = new StdSchedulerFactory();
			Scheduler  sched = schedFact.getScheduler();*/
			Class<Job> a = (Class<Job>)Class.forName("com.jysoft.framework.scheduler.DbDictionaryJob");
			/*JobDetail jobDetail = JobBuilder.newJob(
					(Class<Job>) Class.forName("com.jysoft.framework.scheduler.DbDictionaryJob"))
					.withIdentity("1001", "002")
					.build();*/
			/*JobDetail jobDetail = JobBuilder.newJob(DbDictionaryJob.class)
					.withIdentity("1001", "002")
					.build();
			//jobDetail.getJobDataMap().put("scheduleJob", job);

			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule("0 * 23 * * ?");

			// 按新的cronExpression表达式构建一个新的trigger
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(
					"1001", "002").withSchedule(
					scheduleBuilder).build();

			sched.scheduleJob(jobDetail, trigger);*/
			//scheduler.dynamicConfig();
			SchedulerFactory schedFact = new StdSchedulerFactory();
			Scheduler sched  =  schedFact.getScheduler();  
            sched.start();  
            JobDetail jobDetail  =   new  JobDetail( " Income Report " ,  	
                     " Report Generation " , Class.forName("com.jysoft.framework.scheduler.DbDictionaryJob") );  
            jobDetail.getJobDataMap().put( " type " ,  " FULL " );  
            CronTrigger trigger  =   new  CronTrigger( " Income Report " ,  
                     " Report Generation " );  
            
            //    每1分钟执行一次     
            //trigger.setCronExpression( "0 * 23 * * ?" );  
            trigger.setCronExpression( "14/3 * * * * ?" );  
            sched.scheduleJob(jobDetail, trigger);

		} catch (Exception ex) {
			
			ex.printStackTrace();  
		}


	}

}



