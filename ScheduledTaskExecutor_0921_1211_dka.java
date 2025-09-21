// 代码生成时间: 2025-09-21 12:11:51
package com.example;

import org.apache.struts.action.ActionServlet;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.apache.struts.action.Action;

import javax.servlet.ServletException;
import java.io.IOException;

public class ScheduledTaskExecutor extends ActionServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Initialize the scheduler
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            // Define the job and trigger
            JobDetail job = JobBuilder.newJob(MyTask.class)
                    .withIdentity("myJob", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")) // Every 5 seconds
                    .build();

            // Schedule the job
            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // Define the task to be scheduled
    public static class MyTask implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            // Write your task logic here
            try {
                // Simulate task execution
                Thread.sleep(1000); // Simulate a task taking 1 second to execute
                System.out.println("Task executed at: " + new java.util.Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
