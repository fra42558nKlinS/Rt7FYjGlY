// 代码生成时间: 2025-09-02 11:52:22
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.TriggerBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.Scheduler;
import org.quartz.JobExecutionContext;
import org.quartz.Job;

import java.util.Date;

/**
 * A simple Quartz Job class that implements the Job interface.
 */
public class ScheduledTaskExecutor implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        // Your task logic goes here
        System.out.println("Task executed at " + new Date());
    }

    /**
     * Schedules the task using Quartz and Struts.
     */
    public void scheduleTask() {
        try {
            // Initialize Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // Define the job and tie it to our HelloJob class
            JobDetail job = JobBuilder.newJob(ScheduledTaskExecutor.class)
                    .withIdentity("scheduledTask", "group1").build();

            // Trigger the job to run now, and then repeat every 40 seconds
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.dailyAtHourAndMinute(10, 0)
                    .withMisfireHandlingInstructionDoNothing();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .withSchedule(scheduleBuilder).build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

            // Start
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    /**
     * Main method to run the scheduled task.
     */
    public static void main(String[] args) {
        ScheduledTaskExecutor executor = new ScheduledTaskExecutor();
        executor.scheduleTask();
    }
}