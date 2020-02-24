package com.zhyy.test;


import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTest
{



    public static void sendMail(String drugCode){
	    try
	    {
		    Scheduler scheduler= StdSchedulerFactory.getDefaultScheduler();
		    Trigger trigger=newTrigger().withIdentity("tr1","group1")//设置触发器的名字和组别
				    .startNow()//启动trigger
				    .withSchedule(simpleSchedule().withIntervalInSeconds(2)//每x秒触发一次任务
						    .repeatForever()//不间断的去触发
				    ).build();

		    JobDetail jobDetail=newJob(MyJobDetail.class).withIdentity("jobdetail","group1")
				    .usingJobData("msg",drugCode)//添加实例共享数据
				    .build();
		    scheduler.scheduleJob(jobDetail,trigger);

		    scheduler.start();

		    try
		    {
			    Thread.sleep(1000);
		    } catch (InterruptedException e)
		    {
			    e.printStackTrace();
		    }
		    scheduler.shutdown();

	    } catch (SchedulerException e)
	    {
		    e.printStackTrace();
	    }
    }


}
