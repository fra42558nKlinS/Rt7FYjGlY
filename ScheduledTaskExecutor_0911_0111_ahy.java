// 代码生成时间: 2025-09-11 01:11:21
package com.example.scheduling;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

/**
 * ScheduledTaskExecutor class to handle scheduling of tasks.
 */
public class ScheduledTaskExecutor extends ActionSupport {
    
    /*
     * This method schedules a job using Quartz Scheduler.
     * @param cronExpression The cron expression to define the schedule
     * @param jobClass The class to be executed
     */
    public void scheduleJob(String cronExpression, Class jobClass) {
        try {
            // Create a Quartz scheduler instance
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            
            // Define the job and tie it to our HelloJob class
            JobDetail job = JobBuilder.newJob(jobClass)
                    .withIdentity("scheduledTask", "group1")
                    .build();
            
            // Define the trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
            
            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
            
            // Start the scheduler
            scheduler.start();
        } catch (SchedulerException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }

    /*
     * This action method will trigger the scheduling of a job.
     */
    @Action(value = "scheduleTask")
    public String scheduleTask() {
        // Define the cron expression for your task
        String cronExpression = "0 * * * * ?";
        
        // The class to be executed by the scheduled job
        Class jobClass = MyScheduledTask.class;
        
        // Schedule the job
        scheduleJob(cronExpression, jobClass);
        
        // Return the result of the action
        return SUCCESS;
    }
}

/*
 * MyScheduledTask.java
 * 
 * This class defines the task to be executed by the scheduler.
 */
package com.example.scheduling;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * MyScheduledTask class to execute scheduled tasks.
 */
public class MyScheduledTask implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Execute your task here
        System.out.println("Executing scheduled task at: " + new Date());
    }
}