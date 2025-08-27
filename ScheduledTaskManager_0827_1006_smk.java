// 代码生成时间: 2025-08-27 10:06:16
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.JobBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Properties;
import java.util.Date;

public class ScheduledTaskManager {
    // 定义Job实现类
    public static class MyJob implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            // 任务执行逻辑
            System.out.println("Task executed at: " + new Date());
        }
    }

    public void scheduleJob() {
        try {
            // 获取Scheduler实例
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // 定义JobDetail
            JobDetail job = JobBuilder.newJob(MyJob.class)
                    .withIdentity("myJob", "group1") // 设置Job的名称和组名
                    .build();

            // 定义CronTrigger
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.dailyAtHourAndMinute(10, 30); // 每天10点30分执行
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1\) // 设置Trigger的名称和组名
                    .withSchedule(scheduleBuilder)
                    .build();

            // 将Job和Trigger注册到Scheduler
            scheduler.scheduleJob(job, trigger);

            // 启动Scheduler
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            // 错误处理
            System.out.println("Error scheduling job: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ScheduledTaskManager manager = new ScheduledTaskManager();
        manager.scheduleJob();
    }
}