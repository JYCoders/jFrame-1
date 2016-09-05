package com.jysoft.framework.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DbDictionaryJob implements Job {
	//@Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("任务1成功运行");
        SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(sdm.format(new Date()));
       // String jobName = context.getJobDetail().getKey().getName();
        //System.out.println("任务名称 = [" + jobName + "]");
    }
}
