package com.zhyy.test;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Map;


public class MyJobDetail implements Job
{


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		JobDetail jobDetail=context.getJobDetail();
        Map map=jobDetail.getJobDataMap();
		System.out.println("time is ="+getTime()+",hello myjobdetail -- data is "+map.get("msg")+"->警告！");
		TestSend testSend =new TestSend();
		testSend.sendMail(String.valueOf(map.get("msg")));
	}

	public String getTime(){
	    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
		Long mills = System.currentTimeMillis();
		String time = dateFormat.format(mills);
		return time;
}
}
